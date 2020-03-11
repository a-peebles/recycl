package com.recycl.di.module


import com.recycl.firebase.authentication.FirebaseAuthenticationInterface
import com.recycl.firebase.authentication.FirebaseAuthenticationManager
import com.recycl.firebase.database.FirebaseDatabaseInterface
import com.recycl.firebase.database.FirebaseDatabaseManager
import com.recycl.firebase.storage.FirebaseStorageInterface
import com.recycl.firebase.storage.FirebaseStorageManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


/**
 * InteractionModule
 * @author Alex Peebles
 * Student Number: 150328687
 * @see FirebaseDatabaseInterface
 * @see FirebaseAuthenticationInterface
 * @see FirebaseStorageInterface
 * @see FirebaseDatabaseManager
 * @see FirebaseAuthenticationManager
 * @see FirebaseStorageManager
 * Assigns Firebase classes to their respective interfaces through direct injection
 */
@Module(includes = [FirebaseModule::class, AppModule::class])
@Singleton
abstract class InteractionModule {

    /**
     * authentication
     * @param authentication - Handles any firebase authentication requests
     * @return FirebaseAuthenticationInterface
     */
    @Binds
    abstract fun authentication(authentication: FirebaseAuthenticationManager): FirebaseAuthenticationInterface

    /**
     * database
     * @param database - Handles any database requests
     * @return FirebaseDatabaseInterface
     */
    @Binds
    abstract fun database(database: FirebaseDatabaseManager): FirebaseDatabaseInterface


    /**
     * storage
     * @param storage - Handles any storage requests
     * @return FirebaseStorageInterface
     */
    @Binds
    abstract fun storage(storage: FirebaseStorageManager): FirebaseStorageInterface


}