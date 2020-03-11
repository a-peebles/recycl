package com.recycl.model

import android.os.Parcelable
import com.recycl.common.*
import kotlinx.android.parcel.Parcelize


/**
 * ItemResponse
 * @author Alex Peebles
 * Student Number: 150328687
 *
 * Used to retrieve Item information to Firebase database.
 *
 * @param itemId - Items unique id
 * @param itemName -  Name of the item
 * @param itemCategory - The type of item (e.g. CD, clothing, books)
 * @param itemLocation - Where the item is located in the house
 * @param itemDescription - Description of the item
 * @param itemImageUri - The image Uri on Firebase for retrieval
 * @param isDonated - Has the item been given away?
 */
data class ItemResponse(
    val itemId: String = "",
    val itemName: String = "",
    val itemCategory: String = "",
    val itemLocation: String = "",
    val itemDescription: String = "",
    val itemImageUri: String = "",
    var isDonated: Boolean = false)


    /**
     * isValid
     * Checks that all the data to be uploaded to the item database is valid
     * @return Boolean true if all conditions valid
     */
    fun ItemResponse.isValid(): Boolean {
        if (isItemNameValid(itemName)
            && isItemDescriptionValid(itemDescription)
            && isItemLocationValid(itemLocation)) {
                return true
            }
        return false
    }


fun ItemResponse.mapToItem() = Item(itemId, itemName, itemCategory,
    itemLocation, itemDescription, itemImageUri, isDonated)



/**
 * Item Model
 * Used to store data related to Items
 * Parcelable meaning it can be parsed easily between activities and saved to storage
 * @param itemId - the unique id of the item
 * @param itemName -  Name of the item
 * @param itemCategory - Type of item (clothes, furniture, etc)
 * @param itemDescription - Description of the item
 * @param itemImageUri - Image of item stored on database
 * @param isDonated - has the item been given away?
 *
 */
@Parcelize
data class Item(
    val itemId: String,
    val itemName: String,
    val itemCategory: String,
    val itemLocation: String,
    val itemDescription: String,
    val itemImageUri: String,
    var isDonated: Boolean): Parcelable