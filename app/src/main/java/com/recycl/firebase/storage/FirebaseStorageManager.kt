package com.recycl.firebase.storage


import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import java.io.File
import javax.inject.Inject


/**
 * FirebaseStorageInterface
 * @author Alex Peebles
 * Student Number: 150328687
 * @see FirebaseStorageManager
 *
 * Used to upload, download and remove images placed on Firebase Storage
 */
class FirebaseStorageManager @Inject constructor(private val storage: FirebaseStorage): FirebaseStorageInterface  {



    /**
     * uploadImage
     * Used to upload an image to Firebase Storage
     * @param img - Uri of the image to upload
     * @param onResult - returns the Firebase path of the image
     */
    override fun uploadImage(img: Uri, onResult: (String) -> Unit) {

        // Sets metadata
        val metadata = StorageMetadata.Builder()
            .setContentType("image/jpeg")
            .build()
        // Creates path
        val pathString = "images/${img.lastPathSegment}"

        // Starts a task
        val uploadTask = storage.reference.child(pathString).putFile(img, metadata)
        uploadTask.addOnProgressListener { taskSnapshot ->
            val progress = (100.0 *taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
        }.addOnPausedListener {
        }.addOnFailureListener {
        }.addOnSuccessListener {
            // Returns path to the image on Firebase
            onResult(pathString)
        }

    }

    /**
     * downloadImage
     * Used to download an image from Firebase Storage and places it in a local file to be retrieved later
     * @param path - path of the image in Firebase Storage
     * @param onResult - The path of the local file when downloaded
     */
    override fun downloadImage(path: String, onResult: (String) -> Unit) {
        // Reference to download from and creates local file
        val imgRef = storage.reference.child(path)
        val localFile = File.createTempFile("images", "jpg")

        // Starts a task to download the file
        val downloadTask = imgRef.getFile(localFile)

            downloadTask.addOnProgressListener {
                    taskSnapshot -> val progress = (100.0 *taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount }
                .addOnSuccessListener {
                    // Returns file path on device
              onResult(localFile.absolutePath)
            }.addOnFailureListener {
            }
    }

    /**
     * deleteImage
     * Used to delete an image permanently on Firebase Storage
     * @param path - path of the image in Firebase Storage
     * @param onResult - Result of attempting to delete the image
     */
    override fun deleteImage(path: String, onResult: (Boolean) -> Unit) {
        // Reference to delete an image
        storage.reference.child(path).delete().addOnSuccessListener {
            // Returns true if successful
            onResult(true)
            // Returns false if an error occurs
        }.addOnFailureListener{
            onResult(false)
        }
    }
}