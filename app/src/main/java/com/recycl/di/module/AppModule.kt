package com.recycl.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * AppModule
 * @author Alex Peebles
 * Student Number: 150328687
 *
 * Main module for Direct Injection
 * Parsed the Application object and can return the Context
 *
 */

@Module
@Singleton
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

}

