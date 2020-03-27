package com.recycl.ui.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.recycl.R
import com.recycl.common.onClick
import com.recycl.profilePresenter
import com.recycl.ui.reminder.ReminderActivity
import kotlinx.android.synthetic.main.fragment_profile.*


/**
 * ProfileFragment
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see ProfileView
 * @see Fragment
 * Used to display the users profile information
 */
class ProfileFragment : Fragment(), ProfileView {

    // Presenter used to handle app logic
    private val presenter by lazy { profilePresenter() }


    /**
     * onCreateView
     * Used to create the view for the Fragment
     * @param inflater - Used to instantiate xml layout
     * @param container - Contains xml views to load
     * @param savedInstanceState - Fragments previous state
     * @return View for the Fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        presenter.getProfile()
        return rootView
    }
    /**
     *
     * onViewCreated
     * Sets the fragments presenter and other prerequisite tasks
     * @param view - View for the Fragment
     * @param savedInstanceState Fragments previously saved state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)
        openReminderButton.onClick{ presenter.onOpenReminderTap() }

    }

    /**
     * showName
     * Used to show a users name on the profileName TextView
     * @param firstName - Users first name
     * @param lastName - Users last name
     */
    override fun showName(firstName: String, lastName: String) {
        profileName.text = getString(R.string.profile_name, firstName, lastName)

    }

    /**
     * showProfileImage
     * Used to show the users profile image
     */
    override fun showProfileImage(photoPath: String) {
        profileImage.setImageBitmap(BitmapFactory.decodeFile(photoPath))
    }

    /**
     * openReminder
     * Opens the reminder activity
     */
    override fun openReminder() {
        startActivity(Intent(context, ReminderActivity::class.java))
    }

    /**
     * showNumberOfItemsUploaded
     * Shows the number of items uploaded by the user
     * @param itemCount - The number of items the user has uploaded
     */
    override fun showNumberOfItemsUploaded(itemCount: Int) {
        numberOfUploadedItems.text = itemCount.toString()
    }
    /**
     * showNumberOfItemsDonated
     * Shows the number of items donated by the user
     * @param donatedItemCount - The number of items the user has donated
     */
    override fun showNumberOfItemsDonated(donatedItemCount: Int) {
        numberOfDonatedItems.text = donatedItemCount.toString()
    }

    /**
     * showProfileDescription
     * Show the users profile description in the profileDescription TextView
     * @param description - User description to display
     */
    override fun showProfileDescription(description: String) {
        profileDescription.text = getString(R.string.profile_description, description)
    }
}
