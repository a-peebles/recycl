package com.recycl.presentation.implementation

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.TimePicker
import com.recycl.notifications.AlarmScheduler
import com.recycl.notifications.ReminderData
import com.recycl.presentation.ReminderPresenterInterface
import com.recycl.ui.reminder.ReminderView
import java.util.*
import java.util.Calendar.*
import javax.inject.Inject


/**
 * ReminderPresenter
 * @author Alexander Peebles
 * Student Number: 150328687
 *
 * Activity used for the user to set a recurring reminder notification
 */
class ReminderPresenter @Inject constructor(): ReminderPresenterInterface , AdapterView.OnItemSelectedListener, TimePicker.OnTimeChangedListener{


    // Day of reminder e.g. Monday
    private lateinit var reminderDay: String
    // Calendars for current time and the time for the reminder
    private lateinit var reminderTime: Calendar
    private lateinit var currentTime: Calendar
    // View for the Activity
    private lateinit var view: ReminderView
    // Context of the Activity
    private lateinit var context: Context


    /**
     * onSetReminderTap
     * Creates a reminder and schedules it also updates the display with the day and time
     */
    override fun onSetReminderTap() {
        val reminderData = setReminderData()
        AlarmScheduler.scheduleAlarmsForReminder(context,reminderData)
        view.onReminderSuccess(reminderTime, reminderDay)
    }

    /**
     * setContext
     * Sets the context for the presenter to utilise the AlarmScheduler
     * @param context - The Context of the Activity
     */
    override fun setContext(context: Context) {
        this.context = context
    }


    /**
     * setView
     * Sets the view for the Activity
     * @param view  - The view for ReminderActivity
     */
    override fun setView(view: ReminderView) {
        this.view = view
    }

    /**
     * setTime
     * Used to set the TimeDialog to the current hour and minute
     */
    override fun setTime() {
        reminderTime = getInstance(Locale.getDefault())
        currentTime = getInstance(Locale.getDefault())
        reminderTime.set(HOUR_OF_DAY,currentTime.get(HOUR_OF_DAY))
        reminderTime.set(MINUTE,currentTime.get(MINUTE))

    }

    /**
     * onNothingSelected
     * Called when nothing is set on the dropdown menu
     */
    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
    /**
     * onItemSelected
     * Used to set the reminderDay based of the user input for dropdown menu
     * @param parent - The adapter for the Spinner
     * @param position -  The item selected in the Spinner
     * @param id -  the id of the Spinner
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        reminderDay = parent?.getItemAtPosition(position).toString()
    }

    /**
     * onResetTap
     * Used to reset the form and unschedule the reminder
     */
    override fun onResetTap() {
        val reminderData = setReminderData()
        AlarmScheduler.removeAlarmForReminder(context,reminderData)
        view.onReminderReset()

    }

    /**
     * onTimeChanged
     * @param timePickerView - TimePicker view element
     * @param hourOfDay - Hour set by the user
     * @param minute - Minute set by the user
     */
    override fun onTimeChanged(timePickerView: TimePicker?, hourOfDay: Int, minute: Int) {
        reminderTime.set(HOUR_OF_DAY, hourOfDay)
        reminderTime.set(MINUTE, minute)
    }

    /**
     * setReminderData
     * Creates a ReminderData with the data the user has input
     */
    private fun setReminderData(): ReminderData = ReminderData(reminderTime.get(HOUR_OF_DAY), reminderTime.get(MINUTE),reminderDay)

}