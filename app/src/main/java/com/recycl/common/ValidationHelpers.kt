package com.recycl.common

import com.recycl.common.Constants.EMAIL_REGEX
import com.recycl.common.Constants.ITEM_MAX_DESCRIPTION_LENGTH
import com.recycl.common.Constants.ITEM_MAX_LOCATION_LENGTH
import com.recycl.common.Constants.ITEM_MAX_NAME_LENGTH
import com.recycl.common.Constants.ITEM_MIN_DESCRIPTION_LENGTH
import com.recycl.common.Constants.ITEM_MIN_LOCATION_LENGTH
import com.recycl.common.Constants.ITEM_MIN_NAME_LENGTH
import com.recycl.common.Constants.MIN_PASSWORD_LENGTH
import com.recycl.common.Constants.NAME_REGEX
import com.recycl.common.Constants.PROFILE_MAX_DESCRIPTION_LENGTH
import com.recycl.common.Constants.PROFILE_MIN_DESCRIPTION_LENGTH
import java.lang.System.currentTimeMillis
import java.util.*
import java.util.regex.Pattern

/**
 * ValidationHelpers
 * @author Alex Peebles
 * Student Number: 150328687
 *
 * Various functions used to validate user input in the app
 */

/**
 * isEmailValid
 * @param email - User email input String
 * @return boolean value - True if email matches regex pattern
 */
fun isEmailValid(email: String) = Pattern.matches(EMAIL_REGEX, email)


/**
 * isPasswordValid
 * @param password - Users password input String
 * @return Boolean value - True if password longer than MIN_PASSWORD_LENGTH
 */
fun isPasswordValid(password: String) = password.length >= MIN_PASSWORD_LENGTH


/**
 * isNameValid
 * @param name - Users name input String
 * @return Boolean value - True if the users first/last name resembles a name
 */
fun isNameValid(name: String) = Pattern.matches(NAME_REGEX, name)


/**
 * arePasswordsSame
 * @param password - Users first password input String
 * @param repeatPassword - Users second password input String
 * @return Boolean value - True if both passwords valid and are the same
 */
fun arePasswordsSame(password: String, repeatPassword: String) = isPasswordValid(password) &&
    isPasswordValid(repeatPassword) && password == repeatPassword


/**
 * isProfileDescriptionValid
 * @param description - Users description input String
 * @return Boolean value - True if the profile description is correct number of characters
 */
fun isProfileDescriptionValid(description: String) = description.length in
        PROFILE_MIN_DESCRIPTION_LENGTH..PROFILE_MAX_DESCRIPTION_LENGTH

/**
 * isItemNameValid
 * @param itemName - Users item name input
 * @return Boolean value - True if the item name is the correct number of characters
 */
fun isItemNameValid(itemName: String) = itemName.length in
        ITEM_MIN_NAME_LENGTH .. ITEM_MAX_NAME_LENGTH

/**
 * TODO rewrite this to check category array
 */
fun isItemCategoryValid(itemCategory: String) = itemCategory != "Select Category"


/**
 * isItemLocationValid
 * @param itemLocation = Users item location input
 * @return Boolean value - True if itemLocation is correct number of characters
 */
fun isItemLocationValid(itemLocation: String) = itemLocation.length in
        ITEM_MIN_LOCATION_LENGTH .. ITEM_MAX_LOCATION_LENGTH


/**
 * isItemDescriptionValid
 * @param itemDescription - Users item description input
 * @return Boolean value - True if itemDescription is correct number of characters
 */
fun isItemDescriptionValid(itemDescription: String) = itemDescription.length in
        ITEM_MIN_DESCRIPTION_LENGTH .. ITEM_MAX_DESCRIPTION_LENGTH


