package com.recycl.firebase.storage

import android.net.Uri

/**
 * FirebaseStorageInterface
 * @author Alex Peebles
 * Student Number: 150328687
 * @see FirebaseStorageManager
 *
 * Used to upload, download and remove images placed on Firebase Storage
 */
interface FirebaseStorageInterface {

    /**
     * uploadImage
     * Used to upload an image to Firebase Storage
     * @param img - Uri of the image to upload
     * @param onResult - returns the Firebase path of the image
     */
    fun uploadImage(img: Uri, onResult: (String) -> Unit )

    /**
     * downloadImage
     * Used to download an image from Firebase Storage and places it in a local file to be retrieved later
     * @param path - path of the image in Firebase Storage
     * @param onResult - The path of the local file when downloaded
     */
    fun downloadImage(path: String, onResult: (String) -> Unit)

    /**
     * deleteImage
     * Used to delete an image permanently on Firebase Storage
     * @param path - path of the image in Firebase Storage
     * @param onResult - Result of attempting to delete the image
     */
    fun deleteImage(path: String, onResult: (Boolean) -> Unit)
}