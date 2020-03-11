package com.recycl.ui.items.all.list

import android.graphics.BitmapFactory
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.recycl.common.onClick
import com.recycl.model.Item
import kotlinx.android.synthetic.main.row_items.view.*


/**
 * ItemHolder
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see RecyclerView.ViewHolder
 * @param itemView - The view for the Card
 * @param onItemClickHandler - Used to handle clicks on items
 * Used to display Item data in a feed
 */
class ItemHolder(
    itemView: View,
    private inline val onItemClickHandler: (Item,String) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    /**
     * displayData
     * Displays the data for the item in the corresponding view and sets click
     *
     * @param item - Item data to display
     * @param imageUri - Corresponding image location on the device
     */
    fun displayData(item: Item, imageUri: String) = with(itemView) {
        itemCard.onClick { onItemClickHandler(item, imageUri) }
        cardItemImage.setImageBitmap(BitmapFactory.decodeFile(imageUri))
        cardItemName.text = item.itemName
    }
}