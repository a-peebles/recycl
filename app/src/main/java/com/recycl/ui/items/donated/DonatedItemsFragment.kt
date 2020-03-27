package com.recycl.ui.items.donated

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.recycl.R
import com.recycl.donatedItemsPresenter
import com.recycl.model.Item
import com.recycl.ui.items.donated.list.DonatedItemAdapter
import com.recycl.ui.items.view.ViewItemActivity
import kotlinx.android.synthetic.main.fragment_feed.*


/**
 * DonatedItemFragment
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see DonatedItemsView
 * @see Fragment
 * Used to display the Item cards in a feed to scroll through
 */
class DonatedItemsFragment: Fragment(), DonatedItemsView {

    // Presenter used to handle app logic
    private val presenter by lazy { donatedItemsPresenter() }
    // Adapter used to load Items into a scrollable list
    private val adapter by lazy { DonatedItemAdapter( presenter::onItemTap) }

    /**
     * onCreateView
     * Used to create the view for the Fragment
     * @param inflater - Used to instantiate xml layout
     * @param container - Contains xml views to load
     * @param savedInstanceState - Fragments previous state
     * @return View for the Fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed,container, false)
    }

    /**
     * onViewCreated
     * Sets the fragments presenter and other prerequisite tasks
     * @param view - View for the Fragment
     * @param savedInstanceState Fragments previously saved state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)
        presenter.getDonatedItems()
        initUi()
        if (adapter.itemCount == 0) {
            noItems.visibility = VISIBLE
        }


    }

    /**
     * initUi
     * Sets up the user interface
     *
     */
    private fun initUi() {
        itemFeed.layoutManager = LinearLayoutManager(activity)
        itemFeed.setHasFixedSize(true)
        itemFeed.adapter = adapter
        feedTitle.setText(R.string.items_donated)
        noItems.setText(R.string.no_donated_items)


    }
    /**
     * showNoDataDescription
     * Shows the noItem TextField if there are no items
     */
    override fun showNoDataDescription() {
        noItems.visibility = View.VISIBLE
    }

    /**
     * hideNoDataDescription
     * Removes the noItem TextField
     */
    override fun hideNoDataDescription() {
        noItems.visibility = View.INVISIBLE
    }


    /**
     * viewItem
     * Used to view an Item in a seperate Activity
     * @param item - The item to view
     * @param imageUri - The items corresponding image
     */
    override fun viewItem(item: Item, imageUri: String) {
        val intent = Intent(context, ViewItemActivity::class.java)
        intent.putExtra("item",item)
        intent.putExtra("imageUri", imageUri)
        startActivity(intent)
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
}