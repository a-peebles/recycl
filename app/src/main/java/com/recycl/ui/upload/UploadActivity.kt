package com.recycl.ui.upload

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.recycl.BuildConfig
import com.recycl.R
import com.recycl.common.*
import com.recycl.uploadPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_upload.*
import java.io.File
import java.io.IOException
import java.util.*


/**
 * UploadActivity
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see AppCompatActivity
 * @see EditItemView
 * Activity to upload item data
 */
class UploadActivity : AppCompatActivity(), UploadView{

    // Presenter used to handle button clicks
    private val presenter by lazy { uploadPresenter() }

    // Items image on device
    private lateinit var photoUri: Uri

    /**
     * onCreate
     * Sets up the Activity user interface
     * and the presenter view
     * @param savedInstanceState - Activity's previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        setSupportActionBar(uploadToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.upload_item_title)
        presenter.setView(this)
        initUi()
    }

    /**
     * initUi
     * Sets up the interactive components of the user interface
     */
    private fun initUi() {
        ArrayAdapter.createFromResource(
            applicationContext,
            R.array.upload_categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            uploadItemCategory.adapter = adapter
        }
        uploadItemCategory.onItemSelectedListener = presenter
        uploadItemName.onTextChanged { presenter.onItemNameChanged(it) }
        uploadItemDescription.onTextChanged { presenter.onItemDescriptionChanged(it) }
        uploadItemLocation.onTextChanged { presenter.onItemLocationChanged(it) }
        uploadItemImageButton.onClick { selectImage() }
        resetItemButton.onClick { resetForm() }
        uploadItemButton.onClick { presenter.onUploadItemButtonTap() }
    }

    /**
     * verifyPermissions
     * Checks the permissions for storage and camera access or requests them
     * @return Boolean value if permission is set
     */
    override fun verifyPermissions(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
        if (ContextCompat.checkSelfPermission(this,
                permissions[0]) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this,
                permissions[1]) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this,
                permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            ActivityCompat.requestPermissions(this, permissions, Constants.MY_PERMISSION_REQUEST)
        }
        return false
    }

    /**
     * onRequestPermissionsResult
     * Gets the result of the permission requested and if true opens
     * the select image dialog
     * @param requestCode - Permission request code
     * @param permissions - Permissions requested
     * @grantResults - Result of granting permission
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if(verifyPermissions()) {
            selectImage()
        }
    }

    /**
     * getFilePath
     * Gets the file Path for the image
     * @return File - Corresponding file of the image
     */
    override fun getFilePath(): File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!

    /**
     * startCamera
     * Opens device camera to take an image
     * @param file - File to save image capture to
     */
    override fun startCamera(file: File) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE)
    }

    /**
     * chooseGallery
     * Opens device photo gallery to upload an image
     */
    override fun chooseGallery() {
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(pickPhoto, Constants.OPEN_MEDIA_PICKER )
    }

    /**
     * displayImagePreview
     * Displays the image preview from the selected gallery photo
     * @param filePath - The device file path where image is located
     */
    override fun displayImagePreview(filePath: String) {
        Picasso.get().load(filePath).into(uploadItemPhoto)
    }

    /**
     * displayImagePreview
     * Displays the image preview from the camera image taken
     * @param fileUri - The Uri where image is located
     */
    override fun displayImagePreview(fileUri: Uri) {
        Picasso.get().load(fileUri).into(uploadItemPhoto)
    }

    /**
     * newFile
     * Creates a new file to store the captured image from the camera
     * @return File - File to return
     */
    override fun newFile(): File? {
        val calendar = Calendar.getInstance()
        val timeInMilli = calendar.timeInMillis
        val fileName = "$timeInMilli.jpeg"
        val filePath  = getFilePath()
        try {
            val newFile = File(filePath.absolutePath, fileName)
            newFile.createNewFile()
            return newFile
        } catch(e: IOException){
            e.printStackTrace()
        }
        return null
    }

    /**
     * onActivityResult
     * Used to set the item Image to the image captured or selected
     * @param requestCode - The Activity requested
     * @param resultCode - The result of the Activity
     * @param data - The data from the external Activity
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (resultCode == Constants.REQUEST_IMAGE_CAPTURE) {
                presenter.showPreview(photoUri)
            } else if (requestCode == Constants.OPEN_MEDIA_PICKER) {
                val selectedImage = data?.data
                presenter.showPreview(selectedImage!!)

            }
        }
    }

    /**
     * selectImage
     * Creates a dialog to load an image from camera or gallery
     */
    private fun selectImage() {
        val items = arrayOf(getString(R.string.take_photo), getString(R.string.choose_from_gallery),
            getString(R.string.cancel))
        val builder = AlertDialog.Builder(this)
        builder.setItems(items) { dialog, which ->
            run {
                when {
                    items[which] == "Take Photo" -> {
                        presenter.onCameraTap()
                    }
                    items[which] == "Choose from Gallery" -> {
                        presenter.onUploadFromGalleryTap()
                    }
                    items[which] == "Cancel" -> {
                        dialog.dismiss()
                    }
                }
            }
        }
        builder.show()
    }

    /**
     * showItemNameError
     * Shows error regarding an items name
     */
    override fun showItemNameError() {
        uploadItemName.error = getString(R.string.item_name_error, Constants.ITEM_MIN_NAME_LENGTH, Constants.ITEM_MAX_NAME_LENGTH)
    }

    override fun showItemCategoryError() {

    }
    /**
     * showItemDescriptionError
     * Shows error regarding an item description
     */
    override fun showItemDescriptionError() {
        uploadItemDescription.error = getString(R.string.item_description_error, Constants.ITEM_MIN_DESCRIPTION_LENGTH, Constants.ITEM_MAX_DESCRIPTION_LENGTH)
    }

    /**
     * showItemLocationError
     * Shows error regarding an item location
     */
    override fun showItemLocationError() {
        uploadItemLocation.error = getString(R.string.item_location_error,Constants.ITEM_MIN_LOCATION_LENGTH, Constants.ITEM_MAX_LOCATION_LENGTH)
    }

    /**
     * resetForm
     * Resets the input fields to blank and resets errors to upload a new item
     */
    private fun resetForm() {
        uploadItemName.text.clear()
        uploadItemName.error = null
        uploadItemDescription.text.clear()
        uploadItemDescription.error = null

        uploadItemLocation.text.clear()
        uploadItemLocation.error = null

        Picasso.get().load(R.drawable.ic_upload_img).into(uploadItemPhoto)
        uploadItemCategory.prompt = getString(R.string.select_category)

    }
    /**
     * showEditError
     * Displays a toast if the Item could not be uploaded
     */
    override fun showSignupError() {
        showToast(this ,getString(R.string.item_upload_unsuccessful) )
    }

    /**
     * onEditItemSuccess
     * Shows a dialog and resets the upload form to upload the next item
     */
    override fun onUploadItemSuccess() {
        showDialog(this, getString(R.string.item_upload_success_title), getString(R.string.item_upload_success), getString(R.string.item_positive) )
        resetForm()
    }
}