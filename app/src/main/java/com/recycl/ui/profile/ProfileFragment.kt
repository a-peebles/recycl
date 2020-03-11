package com.recycl.ui.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.recycl.R
import com.recycl.common.onClick
import com.recycl.profilePresenter
import com.recycl.ui.reminder.ReminderActivity
import kotlinx.android.synthetic.main.fragment_profile.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment(), ProfileView {


    private val presenter by lazy { profilePresenter() }
    private val  TAG = "ProfileFragment"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "Fragment loaded")
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        presenter.getProfile()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)
        openReminderButton.onClick{ presenter.onOpenReminderTap() }

    }

    override fun showName(firstName: String, lastName: String) {
        profileName.text = getString(R.string.profile_name, firstName, lastName)

    }

    override fun showProfileImage(photoPath: String) {
        profileImage.setImageBitmap(BitmapFactory.decodeFile(photoPath))
    }

    override fun openReminder() {
        startActivity(Intent(context, ReminderActivity::class.java))
    }

    override fun showNumberOfItemsUploaded(itemCount: Int) {
        numberOfUploadedItems.text = itemCount.toString()
    }

    override fun showNumberOfItemsDonated(donatedItemCount: Int) {
        numberOfDonatedItems.text = donatedItemCount.toString()
    }

    override fun showProfileDescription(description: String) {
        profileDescription.text = getString(R.string.profile_description, description)
    }
}
