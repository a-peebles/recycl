package com.recycl

import android.app.Application
import com.google.firebase.FirebaseApp
import com.recycl.di.AppComponent
import com.recycl.di.DaggerAppComponent


class BaseApplication: Application() {


    companion object {
        lateinit var instance: BaseApplication
        private set

        val component: AppComponent by lazy { DaggerAppComponent.builder().build()}

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        FirebaseApp.initializeApp(this)
    }
}