package com.recycl.ui.upload

import android.net.Uri
import java.io.File


interface UploadView {

    fun verifyPermissions(): Boolean

    fun getFilePath(): File

    fun startCamera(file: File)

    fun chooseGallery()

    fun displayImagePreview(filePath: String)

    fun displayImagePreview(fileUri: Uri)

    fun showItemNameError()

    fun showItemCategoryError()

    fun showItemDescriptionError()

    fun showItemLocationError()

    fun newFile(): File?

    fun showSignupError()

    fun onUploadItemSuccess()

}