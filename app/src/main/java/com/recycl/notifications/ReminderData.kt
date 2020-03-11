package com.recycl.notifications

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * ReminderData
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see Parcelize
 * Data for the reminder to be stored to the device
 * @param hour - Hour the notification should be issued
 * @param minute - Minute the notification should be issued
 * @param day - The day the notification should be issued
 */
@Parcelize
data class ReminderData(
    var hour: Int = 0,
    var minute: Int  =  0,
    var day: String? = null
): Parcelable
