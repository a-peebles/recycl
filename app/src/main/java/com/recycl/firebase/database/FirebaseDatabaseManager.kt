package com.recycl.firebase.database

import com.google.firebase.database.*
import com.recycl.model.*
import javax.inject.Inject


private const val KEY_USER = "user"
private const val KEY_ITEM = "item"
private const val KEY_DONATED = "donated"


/**
 * FirebaseAuthenticationManager
 * @author Alex Peebles
 * Student Number: 150328687
 * @see FirebaseDatabase
 * @see FirebaseDatabaseInterface
 * Class used to perform Firebase database tasks
 */
class FirebaseDatabaseManager @Inject constructor(private val database: FirebaseDatabase): FirebaseDatabaseInterface {

    /**
     * createUser
     * After authenticating user details are added to a database
     * @param userId - Users unique Firebase id
     * @param firstName - Users first name
     * @param lastName - Users last name
     * @param email - Users email
     */
    override fun createUser(userId: String, firstName: String, lastName: String, email: String) {
        // Creates User model and adds it to the database
        val user = User(userId, firstName, lastName, email)
        database.reference.child(KEY_USER).child(userId).setValue(user)
    }


    /**
     * addUserDetails
     * Adds additional information about users to the Firebase database
     * @param userId - Users unique firebase id
     * @param description - Users profile description
     */
    override fun addProfileDescription(userId: String, description: String) {
        // Finds the corresponding user in the user database with a unique id and sets additional info
        val user = database.reference.child(KEY_USER).child(userId)
        // user.child("location").setValue(location)
        user.child("profileDescription").setValue(description)
    }


    /**
     * addProfileImage
     * Adds the Firebase storage Uri to the Firebase entry of the user
     * @param userId - Users unique Firebase id
     * @param imgUri - The image Uri on Firebase storage
     */
    override fun addProfileImage(userId: String, imgUri: String) {
        val user = database.reference.child(KEY_USER).child(userId)
        user.child("profileImageUri").setValue(imgUri)
    }


    /**
     * getProfileInfo
     * Used to retrieve profile information from Firebase to display
     * @param userId - Users unique firebase id
     * @param onResult - Result of Firebase request
     */
    override fun getProfileInfo(userId: String, onResult: (User) -> Unit) {
        // Finds the corresponding user in user database with id
        database.reference
            .child(KEY_USER)
            .child(userId)
            .addValueEventListener(object : ValueEventListener {


                override fun onCancelled(p0: DatabaseError) = Unit

                /**
                 * onDataChange
                 * Checks if users profile information has changed on the database and displays it
                 * on the users profile page
                 * @param p0 Firebase data from
                 */
                override fun onDataChange(p0: DataSnapshot) {
                    val user = p0.getValue(UserResponse::class.java)

                    // Gets all the items for the the user has uploaded
                   val itemsUploaded = p0.child(KEY_ITEM)
                        .children.map { it?.getValue(ItemResponse::class.java) }
                        .mapNotNull { it?.mapToItem() }

                    val itemsDonated = p0.child(KEY_DONATED)
                        .children.map { it?.getValue(ItemResponse::class.java) }
                        .mapNotNull { it?.mapToItem() }

                    // Creates a user model with corresponding information from the Firebase data
                    user?.run { onResult(User(userId,firstName, lastName, email, profileDescription, profileImageUri,itemsUploaded,itemsDonated)) }
                }
            })
    }

    /**
     * addItem
     * Adds a users uploaded item to Firebase
     * @param userId - Id of user currently logged in
     * @param item - item to be added to Firebase
     * @param onResult - Result of Firebase request
     */
    override fun addItem(userId: String, item: Item, onResult: (Boolean) -> Unit) {
        // Creates a new Item Model from user inputs
        // Creates a database reference to access item table
        val newItemReference = database.reference.child(KEY_USER)
            .child(userId).child(KEY_ITEM).push()
        // Creates a copy of the new Item to add to the database
        val newItem = item.copy(itemId = newItemReference.key!!)
        newItemReference.setValue(newItem).addOnCompleteListener{
            // onResult true if successful and isComplete
            onResult(it.isSuccessful && it.isComplete)}
    }

    /**
     * deleteItem
     * Deletes an uploaded item to Firebase
     * @param userId - id of user currently logged in
     * @param item - item to delete from Firebase
     * @param onResult - Result of Firebase request
     */
    override fun deleteItem(userId: String, item: Item, onResult: (Boolean) -> Unit) {
        val itemReference: DatabaseReference = if (!item.isDonated) {
            database.reference.child(KEY_USER).child(userId).child(KEY_ITEM).child(item.itemId)
        } else {
            database.reference.child(KEY_USER).child(userId).child(KEY_DONATED).child(item.itemId)
        }
        itemReference.removeValue()
        onResult(true)
    }


    /**
     * editItem
     * Edits an item on the Firebase database
     * @param userId - id of user currently logged in
     * @param item - item to edit onFirebase
     * @param onResult - Result of Firebase request
     */
    override fun editItem(userId: String, item: Item, onResult: (Boolean) -> Unit) {
        val itemReference  = database.reference.child(KEY_USER).child(userId).child(KEY_ITEM).child(item.itemId)
        itemReference.setValue(item).addOnCompleteListener {
            onResult(it.isComplete && it.isSuccessful)
        }

    }


    /**
     * listenForItems
     * Checks to see if a new item has been added to the Firebase database and adds it to the feed
     * @param userId - Id of user currently logged in
     * @param onItemAdded - Result of mapping Item
     */
    override fun listenForItemsToDonate(userId: String, onItemAdded: (Item) -> Unit) {
        // Adds a child listener to item database
        database.reference.child(KEY_USER).child(userId).child(KEY_ITEM)
            .orderByKey()
            .addChildEventListener(object : ChildEventListener {

                override fun onCancelled(p0: DatabaseError) = Unit
                override fun onChildMoved(p0: DataSnapshot, p1: String?) = Unit
                override fun onChildChanged(p0: DataSnapshot, p1: String?) = Unit
                override fun onChildRemoved(p0: DataSnapshot) = Unit

                /**
                 * onChildAdded
                 * Retrieves item from Firebase database and adds it to the user feed
                 * @param p0 - Firebase data: DataSnapshot
                 * @param p1 - Previous name for child: String
                 *
                 */
                override fun onChildAdded(p0: DataSnapshot, p1: String?) {

                    // Gets the value and assigns it to ItemResponse and then creates an Item model
                    // from it
                    p0.getValue(ItemResponse::class.java)?.run {
                        if (isValid()) {
                            onItemAdded(mapToItem())
                        }
                    }
                }
            })
    }


    /**
     * getBookmarkedItems
     * Gets all the users bookmarked items from the Firebase database
     * @param userId - The current users id: String
     * @param onItemDonated - Result to be returned: Unit
     *
     */
    override fun listenForDonatedItems(userId: String, onItemDonated: (Item) -> Unit) {
        // Gets the users bookmarked items and checks for new bookmarked items
        database.reference
            .child(KEY_USER)
            .child(userId)
            .child(KEY_DONATED)
            .orderByKey()
            .addChildEventListener(object : ChildEventListener {

                override fun onCancelled(p0: DatabaseError) = Unit
                override fun onChildMoved(p0: DataSnapshot, p1: String?) = Unit
                override fun onChildChanged(p0: DataSnapshot, p1: String?) = Unit
                override fun onChildRemoved(p0: DataSnapshot) = Unit

                /**
                 * onChildAdded
                 * Retrieves item from Firebase database and adds it to the user feed
                 * @param p0 - Firebase data: DataSnapshot
                 * @param p1 - Previous name for child: String
                 *
                 */
                override fun onChildAdded(p0: DataSnapshot, p1: String?) {

                    // Gets the value and assigns it to ItemResponse and then creates an Item model
                    // from it
                    p0.getValue(ItemResponse::class.java)?.run {
                        if (isValid()) {
                            onItemDonated(mapToItem())
                        }
                    }
                }
            })
    }


    /**
     * changeItemDonationStatus
     * Toggles whether an item is bookmarked or not/
     * @param item - Item to be checked for bookmark: Item
     * @param userId - Users id: String
     * @param onResult - The result of changing the item donation status
     */
    override fun changeItemDonationStatus(item: Item, userId: String, onResult: (Int) -> Unit) {
        // Database reference assigned to value
        val donatedReference = database.reference
            .child(KEY_USER)
            .child(userId)
            .child(KEY_DONATED)
            .child(item.itemId)
        val itemReference = database.reference
            .child(KEY_USER)
            .child(userId)
            .child(KEY_ITEM)
            .child(item.itemId)

        // Listener to see if Item is bookmarked/unbookmarked
        donatedReference.addListenerForSingleValueEvent(object: ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {}

        /**
         * onDataChange
         * @param p0 - Firebase data: DataSnapshot
        */
        override fun onDataChange(p0: DataSnapshot) {
            // Gets the value from the database and places it in an ItemResponse
            val oldItem = p0.getValue(ItemResponse::class.java)

            // Toggles whether the item is stored in the bookmark table
            if (oldItem != null) {
                item.isDonated = false
                itemReference.setValue(item)
                donatedReference.setValue(null)
                onResult(0)
            } else {
                item.isDonated = true
                donatedReference.setValue(item)
                itemReference.setValue(null)
                onResult(1)
            }
        }
        })
    }
}