package com.recycl.ui.reminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.recycl.R
import com.recycl.common.onClick
import com.recycl.common.showToast
import com.recycl.reminderPresenter
import kotlinx.android.synthetic.main.activity_reminder.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * ReminderActivity
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see AppCompatActivity
 * @see ReminderView
 * Used to set reminders for the user to donate items
 */
class ReminderActivity : AppCompatActivity(), ReminderView {

    // Presenter used to handle button clicks

    private val presenter by lazy { reminderPresenter() }

    /**
     * onCreate
     * Sets up the Activity user interface
     * and the presenter view
     * @param savedInstanceState - Activity's previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
        presenter.setView(this)
        setSupportActionBar(reminderToolbar)
        supportActionBar?.setTitle(R.string.set_reminder)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initUi()
    }

    /**
     * initUi
     * Sets up the interactive components of the user interface
     */
    private fun initUi() {
        ArrayAdapter.createFromResource(
            applicationContext,
            R.array.days_of_week,
            android.R.layout.simple_spinner_item
        ).also { adapter ->

            presenter.setTime()
            presenter.setContext(this)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            reminderDayOfWeek.adapter = adapter
            reminderDayOfWeek.onItemSelectedListener = presenter
            reminderTimePicker.setOnTimeChangedListener(presenter)
            resetReminderButton.onClick { presenter.onResetTap()  }
            setReminderButton.onClick { presenter.onSetReminderTap() }
        }

    }

    /**
     * onReminderSuccess
     * Called to update the display when a reminder has been set successfully
     * @param datetime - The time of the reminder
     * @param day - The day the reminder is set for
     */
    override fun onReminderSuccess(datetime: Calendar, day: String) {

        val timeFormat = SimpleDateFormat("HH:mm",Locale.getDefault())
        reminderTitle.text = getString(R.string.reminder_set_time,timeFormat.format(datetime.timeInMillis), day)
        showToast(this, "Reminder set")
    }

    /**
     * onReminderReset
     * Used to reset the reminder if the user has requested it
     */
    override fun onReminderReset() {
        reminderTitle.text = getString(R.string.reminder_not_set)

    }


}
