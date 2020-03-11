package com.recycl.presentation

import android.content.Context
import com.recycl.ui.reminder.ReminderView


/**
 * ReminderPresenterInterface
 * @author Alexander Peebles
 * Student Number: 150328687
 *
 * Interface used to set a recurring reminder notification
 */
interface ReminderPresenterInterface: BasePresenterInterface<ReminderView> {

    /**
     * onSetReminderTap
     * Creates a reminder and schedules it also updates the display with the day and time
     * */
    fun onSetReminderTap()

    /**
     * onResetTap
     * Used to reset the form and unschedule the reminder
     */
    fun onResetTap()

    /**
     * setTime
     * Used to set the TimeDialog to the current hour and minute
     */
    fun setTime()

    /**
     * setContext
     * Sets the context for the presenter to utilise the AlarmScheduler
     * @param context - The Context of the Activity
     */
    fun setContext(context: Context)

}