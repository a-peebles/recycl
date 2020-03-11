package com.recycl.common

/***
 * @author Alex Peebles
 * Student Number: 150328687

 * Constants to be used throughout the project
 */
object Constants {
    const val SUCCESS_RESULT = 0
    const val FAILURE_RESULT = 1
    private const val PACKAGE_NAME = "com.google.android.gms.location.sample.locationaddress"
    const val RECEIVER = "$PACKAGE_NAME.RECEIVER"
    const val RESULT_DATA_KEY = "$PACKAGE_NAME.RESULT_DATA_KEY"
    const val LOCATION_DATA_EXTRA = "$PACKAGE_NAME.LOCATION_DATA_EXTRA"
    const val LOCATION_PERMISSION_CODE = 1
    const val REQUEST_CHECK_SETTINGS  = 2
    const val REQUEST_IMAGE_CAPTURE = 101
    const val OPEN_MEDIA_PICKER = 102

    const val REQUEST_READ_EXTERNAL_STORAGE = 100
    const val EMAIL_REGEX = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
    const val NAME_REGEX = "^(?=.{1,40}\$)[a-zA-Z]+(?:[-' ][a-zA-Z]+)*\$"
    const val MIN_PASSWORD_LENGTH = 6

    const val PROFILE_MIN_DESCRIPTION_LENGTH = 10
    const val PROFILE_MAX_DESCRIPTION_LENGTH = 100

    const val ITEM_MIN_DESCRIPTION_LENGTH = 10

    const val ITEM_MAX_DESCRIPTION_LENGTH = 1000

    const val ITEM_MIN_NAME_LENGTH = 4
    const val ITEM_MAX_NAME_LENGTH = 25

    const val ITEM_MIN_LOCATION_LENGTH = 4
    const val ITEM_MAX_LOCATION_LENGTH = 50

    const val MY_PERMISSION_REQUEST = 103

}