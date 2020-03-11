package com.recycl.ui.items.view

import com.recycl.model.Item

interface ViewItemView {

    fun setDonated()

    fun setUndonated()

    fun setError()

    fun itemDeleteSuccess()

    fun itemDeleteError()

    fun openEditItem(item: Item, imageUri: String)
}