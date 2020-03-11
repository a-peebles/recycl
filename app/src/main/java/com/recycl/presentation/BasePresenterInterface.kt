package com.recycl.presentation

import android.app.Activity
import android.content.Context

interface BasePresenterInterface<in T> {

    fun setView(view: T)


}