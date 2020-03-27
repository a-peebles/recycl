package com.recycl.ui.reminder

import java.util.*

/**
 * ReminderView
 * @author Alexander Peebles
 * Student Number: 150328687
 * Used to handle results of actions in reminders
 */
interface ReminderView {

    /**
     * onReminderSuccess
     * Called to update the display when a reminder has been set successfully
     * @param datetime - The time of the reminder
     * @param day - The day the reminder is set for
     */
    fun onReminderSuccess(datetime: Calendar, day: String)

    /**
     * onReminderReset
     * Used to reset the reminder if the user has requested it
     */
    fun onReminderReset()

}