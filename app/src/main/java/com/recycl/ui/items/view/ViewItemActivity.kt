package com.recycl.ui.items.view

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.recycl.R
import com.recycl.common.onClick
import com.recycl.common.showToast
import com.recycl.model.Item
import com.recycl.ui.items.edit.EditItemActivity
import com.recycl.ui.main.MainActivity
import com.recycl.viewItemPresenter
import kotlinx.android.synthetic.main.activity_view_item.*


/**
 * ViewItemActivity
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see ViewItemView
 * @see AppCompatActivity
 * Activity used to display item information, image and options
 */
class ViewItemActivity: AppCompatActivity(), ViewItemView {


    // Presenter used to handle button clicks
    private val presenter by lazy { viewItemPresenter() }

    // Item and corresponding Item Image to be displayed
    private lateinit var item: Item
    private lateinit var imageUri: String

    // Keys for parcelable item
    private val ITEM = "item"
    private val ITEM_IMAGE_URI = "imageUri"

    /**
     * onCreate
     * Sets up the Activity user interface
     * and the presenter view
     * @param savedInstanceState - Activity's previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)
        setSupportActionBar(itemToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.setView(this)
        initUi()

    }

    /**
     * initUi
     * Sets up the interactive components of the user interface
     */
    private fun initUi() {
        item = intent.getParcelableExtra(ITEM)!!
        loadItem()
    }

    /**
     * onCreateOptionsMenu
     * Creates the options menu when the corresponding button is clicked
     * @param menu - Menu to display
     * @return true when option button clicked
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return true
    }

    /**
     * onOptionsItemSelected
     * Performs corresponding action when a menu option is pressed
     * @param menuItem - menu option that was clicked
     *
     */
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

    /**
     * onSaveInstanceState
     * Saves the state of the Activity if the application is left
     * @param outState - Information to save
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putParcelable(ITEM, item)
            putString(ITEM_IMAGE_URI, imageUri)
        }
        super.onSaveInstanceState(outState)
    }

    /**
     * onRestoreInstanceState
     * Restores the state of the Activity if the application is re-entered
     * @param savedInstanceState - Information to display
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        item = savedInstanceState.getParcelable(ITEM)!!
        imageUri = savedInstanceState.getString(ITEM_IMAGE_URI)!!
        loadItem()
    }

    /**
     * loadItem
     * loads the information from the Item object into their corresponding
     * TextFields and loads the items image
     */
    private fun loadItem() {
        itemName.text = item.itemName
        itemCategory.text = item.itemCategory
        itemDescription.text = item.itemDescription
        itemLocation.text = item.itemLocation
        this.imageUri = intent.getStringExtra(ITEM_IMAGE_URI)!!
        itemImage.setImageBitmap(BitmapFactory.decodeFile(this.imageUri))
        if(item.isDonated) {
            setDonated()
        }
        if (!item.isDonated) {
            donateButton.onClick { presenter.onDonatedButtonTap(item) }
            setUndonated()
        }
    }

    /**
     * setDonated
     * Used to set the item to a donated item
     */
    override fun setDonated() {
        donateButton.setImageResource(R.drawable.ic_donated_white)
    }

    /**
     * setUndonated
     * Used to set the item as an item that is to be donated
     */
    override fun setUndonated() {
        donateButton.setImageResource(R.drawable.ic_donate_white)
    }

    /**
     * setError
     * Used to display an error if the item donation status could not be changed
     */
    override fun setError() {
        showToast(this, getString(R.string.donation_error))
    }

    /**
     * itemDeleteSuccess
     * Displays a toast if the item was able to be deleted successfully
     */
    override fun itemDeleteSuccess() {
        showToast(this, getString(R.string.item_delete_success_title))
        startActivity(Intent(this, MainActivity::class.java))


    }

    /**
     * itemDeleteError
     * Displays a toast if the item could not be deleted
     */
    override fun itemDeleteError() {
        com.recycl.common.showDialog(this, getString(R.string.item_delete_error_title), getString(
            R.string.item_delete_error_text), getString(R.string.item_positive))    }


    /**
     * openEditItem
     * @param item - Item to be edited
     * @param imageUri - Corresponding Item image locations
     */
    override fun openEditItem(item: Item, imageUri: String) {
        val intent = Intent(applicationContext, EditItemActivity::class.java)
        intent.putExtra("item",item)
        intent.putExtra("imageUri", imageUri)
        startActivity(intent)
    }
}