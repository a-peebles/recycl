package com.recycl.ui.profiledetails

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.recycl.BuildConfig
import com.recycl.R
import com.recycl.common.Constants
import com.recycl.common.Constants.MY_PERMISSION_REQUEST
import com.recycl.common.onClick
import com.recycl.profileDetailsPresenter
import com.recycl.ui.main.MainActivity
import com.squareup.picasso.Picasso
import dalvik.system.PathClassLoader
import kotlinx.android.synthetic.main.activity_profile_details.*
import kotlinx.android.synthetic.main.activity_profile_details.uploadDescription
import java.io.File
import java.io.IOException
import java.security.Permission
import java.util.*
import java.util.jar.Manifest


class ProfileDetailsActivity : AppCompatActivity(), ProfileDetailsView {


    private val presenter by lazy { profileDetailsPresenter() }

    private lateinit var photoUri: Uri

    private val TAG = "ProfileDetailsActivity"
     /**private lateinit var fusedLocationClient: FusedLocationProviderClient
     private lateinit var lastLocation: Location
    private lateinit var locationRequest: LocationRequest

    private lateinit var locationCallback: LocationCallback */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_details)
        presenter.setView(this)
        initUi()

/**
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setUpLocation()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                lastLocation = p0!!.lastLocation
            }
        }*/
        //resultReceiver = AddressResultReceiver(Handler())
       // locationButton.setOnClickListener(this)
        // uploadProfileImageButton.setOnClickListener(this)
    }

    private fun initUi() {

        uploadProfileImageButton.onClick { selectImage() }
        continueButton.onClick { presenter.onContinueTap() }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        verifyPermissions()
    }

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

       /** Glide.with(this)
            .load(File(filePath))
            .apply(RequestOptions.centerCropTransform().circleCrop().placeholder(R.drawable.ic_profile_green))
            .into(profileImagePreview) */
       // profileImagePreview.setImageBitmap()

    Picasso.get().load(filePath).placeholder(R.drawable.ic_profile_green)
        .resize(120,120).centerCrop()
        .into(profileImagePreview)
        Log.i(TAG, "Image set")
    }

    override fun displayImagePreview(fileUri: Uri) {
       /** Glide.with(this)
            .load(File(fileUri.toString()))
            .apply(RequestOptions.centerCropTransform().circleCrop().placeholder(R.drawable.ic_profile_green))
            .into(profileImagePreview) */
        Picasso.get().load(fileUri).placeholder(R.drawable.ic_profile_green)
            .resize(120,120).centerCrop()
            .into(profileImagePreview)
        Log.i(TAG, "Image set")
    }

    override fun newFile(): File? {
        val calendar = Calendar.getInstance()
        val timeInMilli = calendar.timeInMillis
        val fileName = "$timeInMilli.jpeg"
        val filePath  = getFilePath()
        try {
            // filePath.mkdirs()
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
                // val photoPath = getRealPathFromUri(selectedImage!!)
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

    override fun showDescriptionError() {
        uploadDescription.error = getString(R.string.profile_description_error, Constants.ITEM_MIN_DESCRIPTION_LENGTH, Constants.ITEM_MAX_DESCRIPTION_LENGTH)
    }

    override fun showImageError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onDetailsSuccess() {
        startActivity(MainActivity.getLaunchIntent(this))
    }


}

