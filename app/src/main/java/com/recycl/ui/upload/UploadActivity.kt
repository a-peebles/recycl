package com.recycl.ui.upload

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
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
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UploadFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [UploadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UploadActivity : AppCompatActivity(), UploadView{

    private val presenter by lazy { uploadPresenter() }

    private lateinit var photoUri: Uri

    private val TAG = "UploadFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        setSupportActionBar(uploadToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.upload_item_title)
        presenter.setView(this)
        initUi()
    }

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
        // uploadItemPhoto.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.ic_upload_img))
        uploadItemImageButton.onClick { selectImage() }
        resetItemButton.onClick { resetForm() }
        uploadItemButton.onClick { presenter.onUploadItemTap() }
    }

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

    override fun getFilePath(): File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!

    override fun startCamera(file: File) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE)
    }

    override fun chooseGallery() {
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(pickPhoto, Constants.OPEN_MEDIA_PICKER )
    }

    override fun displayImagePreview(filePath: String) {
        Picasso.get().load(filePath).into(uploadItemPhoto)
    }

    override fun displayImagePreview(fileUri: Uri) {
        Picasso.get().load(fileUri).into(uploadItemPhoto)
    }

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

    override fun showItemNameError() {
        uploadItemName.error = getString(R.string.item_name_error, Constants.ITEM_MIN_NAME_LENGTH, Constants.ITEM_MAX_NAME_LENGTH)
    }

    override fun showItemCategoryError() {

    }
    override fun showItemDescriptionError() {
        uploadItemDescription.error = getString(R.string.item_description_error, Constants.ITEM_MIN_DESCRIPTION_LENGTH, Constants.ITEM_MAX_DESCRIPTION_LENGTH)
    }

    override fun showItemLocationError() {
        uploadItemLocation.error = getString(R.string.item_location_error,Constants.ITEM_MIN_LOCATION_LENGTH, Constants.ITEM_MAX_LOCATION_LENGTH)
    }

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
    override fun showSignupError() {
        showToast(this ,getString(R.string.item_upload_unsuccessful) )
    }

    override fun onUploadItemSuccess() {
        showDialog(this, getString(R.string.item_upload_success_title), getString(R.string.item_upload_success), getString(R.string.item_positive) )
        resetForm()
    }
}