package com.recycl.notifications

import android.app.Notification.DEFAULT_ALL
import android.app.Notification.PRIORITY_MAX
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.Intent.*
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color.GREEN
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.AudioAttributes.USAGE_NOTIFICATION_RINGTONE
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.media.RingtoneManager.getDefaultUri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import androidx.core.app.NotificationCompat
import androidx.core.graphics.createBitmap
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.recycl.R
import com.recycl.ui.main.MainActivity


/**
 * NotificationHelper
 *
 * Used to create Notification to be displayed
 */
object NotificationHelper {



    const val NOTIFICATION_ID = "de-cluttr_notification_id"
    const val NOTIFICATION_NAME = "de-cluttr"
    const val NOTIFICATION_CHANNEL = "de-cluttr_channel_01"
    const val NOTIFICATION_WORK = "decluttr_notification_work"

    /**
     * createNotification
     *
     * Used to create a notification to display
     * @param context - The application context
     */
    fun createNotification(context: Context) {
        // Creates an intent, sets the flag and adds the notification id
        val intent  = Intent(context, MainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NOTIFICATION_ID, 0)

        // Calls notification manager and sets values
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val bitmap  = context.vectorToBitmap(R.drawable.ic_donate_white)
        val titleNotification = context.getString((R.string.notification_title))
        val subtitleNotification = context.getString(R.string.notification_text)

        // Creates an intent to issue and builds the notification
        val pendingIntent = getActivity(context, 0, intent, 0)
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
            .setLargeIcon(bitmap).setSmallIcon(R.drawable.ic_donate_white)
            .setContentTitle(titleNotification).setContentText(subtitleNotification)
            .setDefaults(DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true)

        // If the SDK is Oreo and above
        if (SDK_INT >= O) {
            // Set additional information for the notification
            notification.setChannelId(NOTIFICATION_CHANNEL)
            val ringtoneManager = getDefaultUri(TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder().setUsage(USAGE_NOTIFICATION_RINGTONE).setContentType(
                CONTENT_TYPE_SONIFICATION).build()
            val channel = NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_NAME, IMPORTANCE_HIGH)
            channel.enableLights(true)
            channel.lightColor = GREEN
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            channel.setSound(ringtoneManager, audioAttributes)
            notificationManager.createNotificationChannel(channel)
        }
        // Show notification
        notificationManager.notify(0, notification.build())
    }

}

/**
 * vectorToBitmap
 * Used to convert a drawable vector into a Bitmap
 * @param drawableId - The id of the vector
 * @return bitmap version of the vector
 */
private fun Context.vectorToBitmap(drawableId: Int): Bitmap? {
    // Retrieves drawable, creates a new canvas and sets it to the size of the original vector
    val drawable = getDrawable( drawableId)?: return null
    val bitmap = createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0,0,canvas.width,canvas.height)
    drawable.draw(canvas)
    return bitmap


}
