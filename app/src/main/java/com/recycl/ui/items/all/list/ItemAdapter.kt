package com.recycl.ui.items.all.list


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recycl.R
import com.recycl.model.Item


/**
 * ItemAdapter
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see RecyclerView.Adapter
 * @param onItemClickHandler - Used to load ItemAcivity if a ListItem is clicked
 * Adapter used to load item information into ItemHolder class for viewing in a feed
 */
class ItemAdapter(private val onItemClickHandler: (Item,String) -> Unit) :
        RecyclerView.Adapter<ItemHolder>() {

    // Lists of items, their images and donated items
    private val items = mutableListOf<Item>()
    private val itemImages = mutableListOf<String>()
    private val donatedItemsIds = mutableListOf<String>()


    /**
     * getItemCount()
     * @return number of items held in the adapter
     */
    override fun getItemCount(): Int = items.size


    /**
     * onCreateViewHolder
     * Creates a Card element on the adapter to hold information and to be clicked
     * @param parent - Parent View elements in a ViewGroup
     * @param viewType - The type of view e.g. list or grid
     * @return ItemHolder - a Card element holding image, and Item name
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_items, parent, false)
        return ItemHolder(itemView, onItemClickHandler)
    }

    /**
     * onBindViewHolder
     * Inserts Item information into the Card element created above
     * @param holder  - ItemHolder to load data into
     * @param position - Position in the adapter to load information from the corresponding list
     */
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        // Gets item from the list, checks if its donated and adds it to the list
        val item = items[position].apply{ isDonated = itemId in donatedItemsIds }
        // Gets the corresponding imageUri for the Item
        val itemImageUri  = itemImages[position]
        // Displays the Item data in the ItemHolder
        holder.displayData(item, itemImageUri)
    }


    /**
     * addItem
     * Adds an Item to the items list along with its corresponding image
     * @param item - Item to add to the adapter
     * @param imageUri - The image location on device
     */
    fun addItem(item: Item, imageUri: String) {
        items.add(item)
        itemImages.add(imageUri)
        notifyItemInserted(items.size - 1 )
        notifyItemInserted(itemImages.size - 1 )
    }
}

