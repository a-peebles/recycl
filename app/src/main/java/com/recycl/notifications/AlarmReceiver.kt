package com.recycl.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.recycl.R


/**
 * AlarmReceiver
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see BroadcastReceiver
 *
 * Used to display notifications when the users specified date and time is reached
 *
 */
class AlarmReceiver: BroadcastReceiver() {

    /**
     * onRecieve
     * @param context - The context of the application
     * @param intent - The intent triggering the notification
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        // Checks to see if the context and intent are correct
        if (context != null && intent != null && intent.action != null) {
            // Checks the intent action matches the expected value
            if (intent.action!!.equals(context.getString(R.string.action_notify_sort_items), ignoreCase = true)) {
                // Create a notification to launch in the app
                NotificationHelper.createNotification(context)
            }
        }
    }
}