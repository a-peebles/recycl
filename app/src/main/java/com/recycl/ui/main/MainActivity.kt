package com.recycl.ui.main

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.recycl.R
import com.recycl.common.onPageChange
import com.recycl.mainPresenter
import com.recycl.ui.items.all.ItemsFragment
import com.recycl.ui.items.donated.DonatedItemsFragment
import com.recycl.ui.main.pager.MainPagerAdapter
import com.recycl.ui.profile.ProfileFragment
import com.recycl.ui.upload.UploadActivity
import com.recycl.ui.welcome.WelcomeActivity
import kotlinx.android.synthetic.main.activity_main.*


/**
 * MainActivity
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see AppCompatActivity
 * @see MainView
 * Displays Fragments and responds to button clicks and events on the Navigation
 * components
 */
class MainActivity : AppCompatActivity(), MainView {

    // Presenter used to handle button clicks
    private val presenter by lazy { mainPresenter() }

    /**
     * Used to set the launch intent to be MainActivity if not opened previously
     */
    companion object {
        /**
         * Used to set the launch intent to be MainActivity if not opened previously
         *  @param from - Context of the application
         *  @return Intent - The intent of MainActivity
         */
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    /**
     * onCreate
     * Sets up the Activity  user interface
     * and the presenter view
     * @param savedInstanceState - Activity's previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.mainToolbar))
        presenter.setView(this)
        initUi()

    }

    /**
     * initUi
     * Sets up the interactive components of the user interface
     */
    private fun initUi() {

        val adapter = MainPagerAdapter(supportFragmentManager)
        adapter.setPages(
            listOf(ItemsFragment(), DonatedItemsFragment(), ProfileFragment())
        )
        fragmentContainer.adapter = adapter
        fragmentContainer.offscreenPageLimit = 5



        bottomNav.setOnNavigationItemSelectedListener {
            switchNavigationTab(it.order)
            true

        }

        fragmentContainer.onPageChange { position ->
            val item = bottomNav.menu.getItem(position)
            bottomNav.selectedItemId = item.itemId
        }


    }

    /**
     * onCreateOptionsMenu
     * Creates the options menu when the corresponding button is clicked
     * @param menu - Menu to display
     * @return true when option button clicked
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /**
     * onOptionsItemSelected
     * Performs corresponding action when a menu option is pressed
     * @param menuItem - menu option that was clicked
     *
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionAdd -> {
                presenter.onUploadTap()
            }

            R.id.actionLogout -> {
                presenter.onLogoutTap()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * switchNavigationTab
     * Switches the Fragment displayed to the navigation item that was clicked
     * @param position - The item in the List of Fragments to display
     */
    private fun switchNavigationTab(position: Int) {
        fragmentContainer.setCurrentItem(position, true)

    }

    /**
     * openWelcome
     * Opens the WelcomeActivity to display login/signup options if the user is not signed in
     */
    override fun openWelcome() {
        startActivity(Intent(this, WelcomeActivity::class.java))
        this.finish()
    }

    /**
     * openUpload
     * Opens the UploadActivity to upload an item
     */
    override fun openUpload() {
        startActivity(Intent(this, UploadActivity::class.java))
    }

}
