package com.recycl.notifications

import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.recycl.R
import java.util.*
import java.util.Calendar.*


/**
 * AlarmScheduler
 * @author Alexander Peebles
 * Student Number: 150328687
 *
 *
 * Used to schedule reminders for users to sort their items
 *
 */
object AlarmScheduler {


    /**
     * scheduleAlarmsForReminder
     * @see ReminderData
     * @param context - The context of the application
     * @param reminderData - Data for the reminder
     */
    fun scheduleAlarmsForReminder(context: Context, reminderData: ReminderData) {
        // Gets alarm data and the day set in reminder data
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as  AlarmManager
        val day = reminderData.day
        if(day != null) {
            // Creates an intent
            val alarmIntent = createPendingIntent(context, reminderData, day)

            // Gets the day of the week and schedules an alarm
            val dayOfWeek = getDayOfWeek(day)
            scheduleAlarm(reminderData, dayOfWeek, alarmIntent, alarmManager)
        }
    }

    /**
     * scheduleAlarm
     *
     * Sets a weekly repeating notification for the user to sort through their items
     * @param reminderData - data to save to device for the reminder
     * @param dayOfWeek - The day of the week to set the alarm for (e.g. 1 for Monday)
     * @param alarmIntent - The intent to set the alarm
     * @param alarmManager - The corresponding AlarmManager to set repeating notification for
     *
     */
    private fun scheduleAlarm(reminderData: ReminderData, dayOfWeek: Int, alarmIntent: PendingIntent?, alarmManager: AlarmManager) {
        // Initialises the calendar and sets the data to the values stored in reminderData
        val datetimeToAlarm = getInstance(Locale.getDefault())
        datetimeToAlarm.timeInMillis = System.currentTimeMillis()
        datetimeToAlarm.set(HOUR_OF_DAY,reminderData.hour)
        datetimeToAlarm.set(MINUTE, reminderData.minute)
        datetimeToAlarm.set(SECOND,0)
        datetimeToAlarm.set(MILLISECOND,0)
        datetimeToAlarm.set(DAY_OF_WEEK, dayOfWeek)

        // Initialises another calendar to check todays date
        val today = getInstance(Locale.getDefault())

        // Checks if the notification is to be sent out today
        if (notifyToday(dayOfWeek,today,datetimeToAlarm)) {
            // Sets the alarm to issue  a notification at the specified time
            alarmManager.setRepeating(RTC_WAKEUP, datetimeToAlarm.timeInMillis, (1000 * 60 * 60 * 24 * 7).toLong(), alarmIntent)
        }

        // If the notification is not issued for today set it for the next week
        datetimeToAlarm.roll(WEEK_OF_YEAR,1)
        alarmManager.setRepeating(RTC_WAKEUP, datetimeToAlarm.timeInMillis, (1000 * 60 * 60 * 24 * 7).toLong(), alarmIntent)

    }

    /**
     * removeAlarmForReminder
     *
     * Used to reset the reminder
     * @param context - The context of the application
     * @param reminderData - data of reminder stored locally on device
     */
    fun removeAlarmForReminder(context: Context, reminderData: ReminderData) {
        // Create an intent for the AlarmReceiver class
        val intent  = Intent(context.applicationContext, AlarmReceiver::class.java)
        // Checks if the action is equal to reset flag
        intent.action = context.getString(R.string.action_notify_reminder_reset)
        // If reminder data is set
        if (reminderData.day != null) {
            // Fetches the alarmIntent and the corresponding manager and cancels them
            val alarmIntent = PendingIntent.getBroadcast(context, 0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            val alarmManager  = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(alarmIntent)
        }
    }

    /**
     * notifyToday
     * Boolean function used to calculate if the notification should be issued today
     *
     * @param dayOfWeek - The day of the week to set the alarm for (e.g. 1 for Monday)
     * @param today - The current date
     * @param datetimeToAlarm - The date of the scheduled alarm
     * @return true if the day of the week is the same and the time of the alarm is now or
     * in the future
     */
    private fun notifyToday(dayOfWeek: Int, today: Calendar, datetimeToAlarm: Calendar): Boolean {
        return dayOfWeek == today.get(DAY_OF_WEEK) &&
                today.get(HOUR_OF_DAY) <= datetimeToAlarm.get(HOUR_OF_DAY) &&
                today.get(MINUTE) <= datetimeToAlarm.get(MINUTE)
    }


    /**
     * getDayOfWeek
     * Returns Calendar integer value of the day of the week
     * @param day - Day of the week (e.g. Monday)
     * @return Integer constant value stored in the Calendar class
     */
    private fun getDayOfWeek(day: String): Int {
        return when {
            day.equals("Monday", true)-> MONDAY
            day.equals("Tuesday", true)-> TUESDAY
            day.equals("Wednesday", true)-> WEDNESDAY
            day.equals("Thursday", true)-> THURSDAY
            day.equals("Friday", true)-> FRIDAY
            day.equals("Saturday", true)-> SATURDAY
            else -> SUNDAY
        }

    }

    /**
     * createPendingIntent
     *
     * Creates an intent for the AlarmReceiver to pickup
     * @param context - The context of the application
     * @param reminderData - data for reminder stored locally on device
     * @param day - Day reminder is set for
     */
    private fun createPendingIntent(context: Context, reminderData: ReminderData, day: String): PendingIntent? {
        val intent = Intent(context.applicationContext, AlarmReceiver::class.java).apply {
            action = context.getString(R.string.action_notify_sort_items)
        }
        return PendingIntent.getBroadcast(context,0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    }
}