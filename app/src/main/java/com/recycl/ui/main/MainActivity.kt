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

class MainActivity : AppCompatActivity(), MainView {

    private val TAG = "MainActivity"

    private val presenter by lazy { mainPresenter() }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.mainToolbar))
        presenter.setView(this)
        initUi()

    }

    private fun initUi() {

        val adapter = MainPagerAdapter(supportFragmentManager)
        adapter.setPages(
            listOf(ItemsFragment(), DonatedItemsFragment(), ProfileFragment())
        )
        fragmentContainer.adapter = adapter
        fragmentContainer.offscreenPageLimit = 5



        bottomNav.setOnNavigationItemSelectedListener {
            switchNavigationTab(it.order)
            Log.i(TAG, "nav item clicked ")

            true

        }

        fragmentContainer.onPageChange { position ->
            val item = bottomNav.menu.getItem(position)
            bottomNav.selectedItemId = item.itemId
            Log.i(TAG, "page changed $position")
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

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

    private fun switchNavigationTab(position: Int) {
        fragmentContainer.setCurrentItem(position, true)

    }

    override fun openWelcome() {
        startActivity(Intent(this, WelcomeActivity::class.java))
        this.finish()
    }

    override fun openUpload() {
        startActivity(Intent(this, UploadActivity::class.java))
    }

}
