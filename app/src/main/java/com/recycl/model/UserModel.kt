package com.recycl.model


/**
 * UserResponse
 * @author Alex Peebles
 * Student Number: 150328687
 *
 * Model that holds information retrieved from Firebase
 *
 * @param id - Users unique Firebase id: String
 * @param firstName - Users first name: String
 * @param lastName - Users last name: String
 * @param email - Users given email: String
 * @param profileDescription - Users profile description: String
 * @param profileImageUri = Users profile image uri: String
 */
data class UserResponse(val id: String = "", val firstName: String = "",
                        val lastName: String = "", val email: String = "",
val profileDescription: String = "", val profileImageUri: String = "")


/**
 * User
 * @author Alex Peebles
 * Student Number: 150328687
 *
 * Model that holds user information retrieved from Firebase
 *
 * @param id - Users unique Firebase id: String
 * @param firstName - Users first name: String
 * @param lastName - Users last name: String
 * @param email - Users given email: String
 * @param profileDescription - Users profile description: String
 * @param profileImageUri = Users profile image uri: String
 */
data class User(
    val id: String = "",
    val firstName: String? = "",
    val lastName: String? = "",
    val email: String? = "",
    val profileDescription: String? = "",
    val profileImageUri: String? = "",
    val itemsUploaded: List<Item> = listOf(),
    val itemsDonated: List<Item> = listOf()

)