package com.recycl.common

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.util.AttributeSet
import android.widget.TimePicker


/**
 * TimePickerCustom
 * @author Alex Peebles
 * Student number: 150328687
 *
 * Class to display a custom TimePicker that can handle input
 * from android OS versions before and after android  M
 * @see TimePicker
 */
@Suppress("DEPRECATION")
class TimePickerCustom: TimePicker {

    /**
     * @param context - The application context to determine OS version
     */
    constructor(context: Context): super(context)

    /**
     * @param context - The application context to determine OS version
     * @param attrs - Any styling from XML documents to be applied
     */
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)


    /**
     * @param context - The application context to determine OS version
     * @param attrs - Any styling from XML documents to be applied
     * @param defStyleAttr - Reference for a particular style attribute
     */
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)


    /**
     * setHour
     * Used to set the hour of the TimePicker
     * @param hour - hour to set time to
     */
    override fun setHour(hour: Int) {
        when {
            SDK_INT >= M ->super.setHour(hour)
            else -> super.setCurrentHour(hour)
        }
    }
    /**
     * setHour
     * Used to set the minute of the TimePicker
     * @param minute - minute to set time to
     */
    override fun setMinute(minute: Int) {
        when {
            SDK_INT >= M ->super.setHour(minute)
            else -> super.setCurrentMinute(minute)
        }
    }

    /**
     * getHour
     * @return - The current hour displayed on the TimePicker
     */
    override fun getHour(): Int {
       return when {
            SDK_INT >= M ->super.getHour()
            else -> super.getCurrentHour()
        }
    }

    /**
     * getMinute
     * @return - The current minute displayed on the TimePicker
     */
    override fun getMinute(): Int {
        return when {
            SDK_INT >= M ->super.getHour()
            else -> super.getCurrentMinute()
        }
    }
}