package com.recycl

import android.app.Application
import com.google.firebase.FirebaseApp
import com.recycl.di.AppComponent
import com.recycl.di.DaggerAppComponent


/**
 * BaseApplication
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see Application
 * Used to launch the application and perform dependency injection
 */
class BaseApplication: Application() {


    companion object {
        // Instance of BaseApplication
        lateinit var instance: BaseApplication
        private set
        // Initialises dependency injection builder
        val component: AppComponent by lazy { DaggerAppComponent.builder().build()}

    }

    /**
     * onCreate
     * called when the app is loaded and initialises the app an Firebase
     */
    override fun onCreate() {
        super.onCreate()
        instance = this
        FirebaseApp.initializeApp(this)
    }
}