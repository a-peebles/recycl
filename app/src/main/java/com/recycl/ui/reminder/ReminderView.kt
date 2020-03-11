package com.recycl.ui.reminder

import java.util.*

interface ReminderView {

    fun onReminderSuccess(datetime: Calendar, day: String)

    fun onReminderReset()

}