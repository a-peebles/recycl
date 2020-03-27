package com.recycl.ui.profiledetails

import android.Manifest.permission.*
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.recycl.BuildConfig
import com.recycl.R
import com.recycl.common.Constants
import com.recycl.common.Constants.MY_PERMISSION_REQUEST
import com.recycl.common.onClick
import com.recycl.common.showToast
import com.recycl.profileDetailsPresenter
import com.recycl.ui.main.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_details.*
import java.io.File
import java.io.IOException
import java.util.*

/**
 * ProfileDetailsView
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see AppCompatActivity
 * @see ProfileDetailsView
 * Activity for the user to upload additional information
 */
class ProfileDetailsActivity : AppCompatActivity(), ProfileDetailsView {

    // Presenter used to handle button clicks
    private val presenter by lazy { profileDetailsPresenter() }

    // Image Uri for the user profile image
    private lateinit var photoUri: Uri

    /**
     * onCreate
     * Sets up the Activity user interface
     * and the presenter view
     * @param savedInstanceState - Activity's previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_details)
        presenter.setView(this)
        initUi()

    }

    /**
     * initUi
     * Sets up the interactive components of the user interface
     */
    private fun initUi() {
        uploadProfileImageButton.onClick { selectImage() }
        continueButton.onClick { presenter.onContinueTap() }
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
     * verifyPermissions
     * Checks the permissions for storage and camera access or requests them
     * @return Boolean value if permission is set
     */
    override fun verifyPermissions(): Boolean {
        val permissions = arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA)
        if (ContextCompat.checkSelfPermission(this,
                permissions[0]) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this,
                permissions[1]) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this,
                permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSION_REQUEST)
        }
        return false
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
        Picasso.get().load(filePath).placeholder(R.drawable.ic_profile_green)
            .resize(120,120).centerCrop()
            .into(profileImagePreview)
    }

    /**
     * displayImagePreview
     * Displays the image preview from the camera image taken
     * @param fileUri - The Uri where image is located
     */
    override fun displayImagePreview(fileUri: Uri) {
        Picasso.get().load(fileUri).placeholder(R.drawable.ic_profile_green)
            .resize(120,120).centerCrop()
            .into(profileImagePreview)
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
     * Used to set the user Profile Image to the image captured or selected
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
     * showDescriptionError
     * Shows error regarding a profile description
     */
    override fun showDescriptionError() {
        uploadDescription.error = getString(R.string.profile_description_error, Constants.ITEM_MIN_DESCRIPTION_LENGTH, Constants.ITEM_MAX_DESCRIPTION_LENGTH)
    }


    /**
     * showImageError
     * Shows error regarding image upload
     */
    override fun showImageError() {
        showToast(this, getString(R.string.no_image))
    }

    /**
     * onDetailsSuccess
     * Used to perform action if details were uploaded successfully
     */
    override fun onDetailsSuccess() {
        startActivity(MainActivity.getLaunchIntent(this))
    }

    /**
     * onImageError
     * Used to show a toast if an image has not been uploaded.
     */
    override fun onImageError() {
        showImageError()
    }


}

