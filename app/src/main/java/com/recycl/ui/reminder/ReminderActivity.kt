package com.recycl.ui.reminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.get
import com.recycl.R
import com.recycl.common.TimePickerCustom
import com.recycl.common.onClick
import com.recycl.common.showToast
import com.recycl.reminderPresenter
import kotlinx.android.synthetic.main.activity_reminder.*
import java.text.SimpleDateFormat
import java.util.*

class ReminderActivity : AppCompatActivity(), ReminderView {


    private val presenter by lazy { reminderPresenter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
        presenter.setView(this)
        setSupportActionBar(reminderToolbar)
        supportActionBar?.setTitle(R.string.set_reminder)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initUi()
    }

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
    override fun onReminderSuccess(datetime: Calendar, day: String) {

        val timeFormat = SimpleDateFormat("HH:mm",Locale.getDefault())
        reminderTitle.text = getString(R.string.reminder_set_time,timeFormat.format(datetime.timeInMillis), day)
        showToast(this, "Reminder set")
    }


    override fun onReminderReset() {
        reminderTitle.text = getString(R.string.reminder_not_set)

    }


}
