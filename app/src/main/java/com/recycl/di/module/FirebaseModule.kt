package com.recycl.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * FirebaseModule
 * @author Alex Peebles
 * Student Number: 150328687
 * Direct Injection components for Firebase calls
 */
@Module
@Singleton
class FirebaseModule {

    /**
     * firebaseAuth
     * Assigns getInstance function whenever firebaseAuth() is called
     */
    @Provides
    fun firebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * firebaseDatabase
     * Assigns getInstance function whenever firebaseDatabase() is called
     */
    @Provides
    fun firebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    /**
     * firebaseStorage
     * Assigns getInstance function whenever firebaseStorage() is called
     */
    @Provides
    fun firebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()
}