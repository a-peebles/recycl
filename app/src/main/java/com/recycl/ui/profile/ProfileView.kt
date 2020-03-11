package com.recycl.ui.profile


interface ProfileView {

    fun showName(firstName: String, lastName: String)

    fun showProfileDescription(description: String)

    fun showProfileImage(photoPath: String)

    fun openReminder()

    fun showNumberOfItemsUploaded(itemCount: Int)

    fun showNumberOfItemsDonated(donatedItemCount: Int)
}