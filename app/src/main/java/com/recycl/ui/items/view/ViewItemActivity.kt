package com.recycl.ui.items.view

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Transition
import com.recycl.R
import com.recycl.common.onClick
import com.recycl.common.showToast
import com.recycl.model.Item
import com.recycl.ui.items.edit.EditItemActivity
import com.recycl.ui.main.MainActivity
import com.recycl.viewItemPresenter
import kotlinx.android.synthetic.main.activity_view_item.*

class ViewItemActivity: AppCompatActivity(), ViewItemView {


    private var TAG = "ItemActivity"

    private val presenter by lazy { viewItemPresenter() }

    private lateinit var item: Item
    private lateinit var imageUri: String

    private val ITEM = "item"
    private val ITEM_IMAGE_URI = "itemImageUri"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)
        setSupportActionBar(itemToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.setView(this)
        initUi()

    }

    private fun initUi() {
        windowTransition()
        item = intent.getParcelableExtra("item")!!
        loadItem()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.actionEditItem -> {
                presenter.onEditItemButtonTap(this.item, this.imageUri)
            }

            R.id.actionDeleteItem -> {
                presenter.onDeleteButtonTap(this.item)
            }

        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putParcelable(ITEM, item)
            putString(ITEM_IMAGE_URI, imageUri)
        }
        super.onSaveInstanceState(outState)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        item = savedInstanceState.getParcelable(ITEM)!!
        imageUri = savedInstanceState.getString(ITEM_IMAGE_URI)!!
        loadItem()
    }

    private fun loadItem() {
        itemName.text = item.itemName
        itemCategory.text = item.itemCategory
        itemDescription.text = item.itemDescription
        itemLocation.text = item.itemLocation
        this.imageUri = intent.getStringExtra("imageUri")!!
        itemImage.setImageBitmap(BitmapFactory.decodeFile(this.imageUri))
        if(item.isDonated) {
            setDonated()
        }
        if (!item.isDonated) {
            donateButton.onClick { presenter.onDonatedButtonTap(item) }
            setUndonated()
        }
    }

    private fun windowTransition() {
        window.enterTransition.addListener(object : Transition.TransitionListener,
            android.transition.Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition) {
                donateButton.animate().alpha(1.0f)
                window.enterTransition.removeListener(this)
            }

            override fun onTransitionCancel(transition: Transition) { }
            override fun onTransitionPause(transition: Transition) { }
            override fun onTransitionResume(transition: Transition) { }
            override fun onTransitionStart(transition: Transition) { }
            override fun onTransitionEnd(transition: android.transition.Transition?) {
                donateButton.animate().alpha(1.0f)
                window.enterTransition.removeListener(this)
            }

            override fun onTransitionResume(transition: android.transition.Transition?) {
            }

            override fun onTransitionPause(transition: android.transition.Transition?) {
            }

            override fun onTransitionCancel(transition: android.transition.Transition?) {}

            override fun onTransitionStart(transition: android.transition.Transition?) { }

        })
    }


    override fun setDonated() {
        donateButton.setImageResource(R.drawable.ic_donated_white)
    }

    override fun setUndonated() {
        donateButton.setImageResource(R.drawable.ic_donate_white)
    }

    override fun setError() {
        showToast(this, getString(R.string.donation_error))
    }

    override fun itemDeleteSuccess() {
        showToast(this, getString(R.string.item_delete_success_title))
        startActivity(Intent(this, MainActivity::class.java))


    }


    override fun itemDeleteError() {
        com.recycl.common.showDialog(this, getString(R.string.item_delete_error_title), getString(
            R.string.item_delete_error_text), getString(R.string.item_positive))    }

    override fun openEditItem(item: Item, imageUri: String) {
        val intent = Intent(applicationContext, EditItemActivity::class.java)
        intent.putExtra("item",item)
        intent.putExtra("imageUri", imageUri)
        startActivity(intent)
    }
}