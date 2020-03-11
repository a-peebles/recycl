package com.recycl.firebase.database

import com.recycl.model.Item
import com.recycl.model.User

/**
 * FirebaseDatabaseInterface
 * @author Alex Peebles
 * Student Number: 150328687
 * @see FirebaseDatabaseManager
 * @see FirebaseDatabase
 * Interface for database tasks
 */
interface FirebaseDatabaseInterface {


    /**
     * createUser
     * After authenticating user details are added to a database
     * @param userId - Users unique Firebase id
     * @param firstName - Users first name
     * @param lastName - Users last name
     * @param email - Users email
     */
    fun createUser(userId: String, firstName: String, lastName: String, email: String)

    /**
     * addUserDetails
     * Adds additional information about users to the Firebase database
     * @param userId - Users unique firebase id
     * @param description - Users profile description
     */
    fun addProfileDescription(userId: String, description: String)


    /**
     * addProfileImage
     * Attaches the users profile Image to their entry in the Firebase database
     * @param userId - Users unique firebase id
     * @param imgUri  - The Firebase Storage uri for the profile image
     */
    fun addProfileImage(userId: String, imgUri: String)

    /**
     * getProfileInfo
     * Used to retrieve profile information from Firebase to display
     * @param userId- Users unique firebase id
     * @param onResult - Result of Firebase request
     */
    fun getProfileInfo(userId: String, onResult: (User) -> Unit)


    /**
     * addItem
     * Adds a users uploaded item to Firebase
     * @param userId - id of user currently logged in
     * @param item - item to add to Firebase
     * @param onResult - Result of Firebase request
     */
    fun addItem(userId: String, item: Item, onResult: (Boolean) -> Unit)



    /**
     * deleteItem
     * Deletes an uploaded item to Firebase
     * @param userId - id of user currently logged in
     * @param item - item to delete from Firebase
     * @param onResult - Result of Firebase request
     */
    fun deleteItem(userId: String, item: Item, onResult: (Boolean) -> Unit)


    /**
     * editItem
     * Edits an item on the Firebase database
     * @param userId - id of user currently logged in
     * @param item - item to edit onFirebase
     * @param onResult - Result of Firebase request
     */
    fun editItem(userId: String, item: Item, onResult: (Boolean) -> Unit)


    /**
     * listenForItems
     * Checks to see if a new item has been added to the Firebase database and adds it to the feed
     * @param onItemAdded - parsed function from view
     */
    fun listenForItemsToDonate(userId: String, onItemAdded: (Item) -> Unit)

    /**
     * getBookmarkedItems
     * Gets all the users bookmarked items from the Firebase database
     * @param userId - The current users id: String
     * @param onResult - Result to be returned: Unit
     *
     */
    fun listenForDonatedItems(userId: String, onItemDonated:  (Item) -> Unit)


    /**
     * changeItemBookmarkStatus
     * Toggles whether an item is bookmarked or not/
     * @param item - Item to be checked for bookmark: Item
     * @param userId - Users id: String
     */
    fun changeItemDonationStatus(item: Item, userId: String, onResult: (Int) -> Unit)


}
