package com.recycl.common

import android.app.AlertDialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

/**
 * ViewHelpers
 * @author Alex Peebles
 * Student Number: 150328687
 *
 * Used to show response to user actions and inputs
 */

/**
 * showToast
 * Used to show toasts for errors
 * @param from - Context of the application to display the toast
 * @param string - Message to display to the user
 *
 */
fun showToast(from: Context, string: String) {
    Toast.makeText(from, string, Toast.LENGTH_LONG ).show()
}

/**
 * showDialog
 * Used to show a popup box in response to a user operation
 * @param from  - Context of the applicaion to display the toast
 * @param title - Title of the Dialog
 * @param message - Message to display to the user
 * @param positiveButtonText - Text for positive action button
 */
fun showDialog(from: Context, title: String, message: String, positiveButtonText: String) {
    AlertDialog.Builder(from).setTitle(title).setMessage(message)
        .setPositiveButton(positiveButtonText) { dialog, _ -> dialog.dismiss() }.show()
}