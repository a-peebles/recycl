package com.recycl.ui.profiledetails

import android.net.Uri
import java.io.File

interface ProfileDetailsView {


    fun verifyPermissions(): Boolean

    fun getFilePath(): File

    fun startCamera(file: File)

    fun chooseGallery()

    fun displayImagePreview(filePath: String)

    fun displayImagePreview(fileUri: Uri)

    fun showDescriptionError()

    fun showImageError()

    fun onDetailsSuccess()

    fun newFile(): File?
}