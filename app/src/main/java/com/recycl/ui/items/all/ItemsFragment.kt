package com.recycl.ui.items.all

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.recycl.R
import com.recycl.allItemsPresenter
import com.recycl.model.Item
import com.recycl.ui.items.all.list.ItemAdapter
import com.recycl.ui.items.view.ViewItemActivity
import kotlinx.android.synthetic.main.fragment_feed.*

/**
 * ItemFragment
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see ItemsView
 * Used to display the Item cards in a feed to scroll through
 */
class ItemsFragment: Fragment(), ItemsView {

    // Presenter used to handle app logic
    private val presenter by lazy {allItemsPresenter() }
    // Adapter used to load Items into a scrollable list
    private val adapter by lazy {ItemAdapter( presenter::onItemTap) }


    /**
     * onCreateView
     * Used to create the view for the Fragment
     * @param inflater - Used to instansiate xml layout
     * @param container - Contains xml views to load
     * @param savedInstanceState - Fragments previous state
     * @return View for the Fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    /**
     * onViewCreated
     * @param view - View for the Fragment
     * @param savedInstanceState Fragments previously saved state
     * Sets the fragments presenter and other prerequisite tasks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // initialises UI
        initUi()
        // Sets the view for the presenter and gets the items to display
        presenter.setView(this)
        presenter.getItemsToDonate()

        // Displays message if there is no data to display
        if (adapter.itemCount == 0) {
            showNoDataDescription()
        } else {
            hideNoDataDescription()
        }

    }

    /**
     * addItem
     * Used to add an Item to an Adapter
     * @param item - The Item to add to the Fragment
     * @param imageUri - The corresponding Item image
     */
    override fun addItem(item: Item, imageUri: String) {
        adapter.addItem(item, imageUri)
        noItems.visibility = if (adapter.itemCount != 0) View.INVISIBLE else View.VISIBLE
    }

    /**
     * viewItem
     * Used to view an Item in a seperate Activity
     * @param item - The item to view
     * @param imageUri - The items corresponding image
     */
    override fun viewItem(item: Item, imageUri: String) {

        // Intent for the ViewItemActivity
        val intent = Intent(context,ViewItemActivity::class.java)
        // Adds the item and imageUri and starts the Intent
        intent.putExtra("item",item)
        intent.putExtra("imageUri", imageUri)
        startActivity(intent)
    }


    /**
     * initUi
     * Sets up the user interface
     *
     */
    private fun initUi() {

        itemFeed.layoutManager = LinearLayoutManager(activity)
        itemFeed.hasFixedSize()
        itemFeed.adapter = adapter
        feedTitle.setText(R.string.items_to_donate)
        noItems.setText(R.string.no_items_uploaded)



    }

    /**
     * showNoDataDescription
     * Shows the noItem TextField if there are no items
     */
    override fun showNoDataDescription() {
        noItems.visibility = VISIBLE
    }

    /**
     * hideNoDataDescription
     * Removes the noItem TextField
     */
    override fun hideNoDataDescription() {
        noItems.visibility = GONE
    }


}