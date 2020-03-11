package com.recycl.di

import com.recycl.di.module.PresentationModule
import com.recycl.presentation.implementation.*
import dagger.Component
import javax.inject.Singleton

/**
 * AppComponent
 * @author Alex Peebles
 * Student Number: 150328687
 * @see PresentationModule
 * Used to access Presenters in Activities and Fragments
 */
@Component(modules = [PresentationModule::class])
@Singleton
interface AppComponent {

    fun signupPresenter(): SignupPresenter

    fun loginPresenter(): LoginPresenter

    fun welcomePresenter(): WelcomePresenter

    fun profileDetailsPresenter(): ProfileDetailsPresenter

    fun forgotPasswordPresenter(): ForgotPasswordPresenter

    fun allItemsPresenter(): AllItemsPresenter

    fun donatedItemsPresenter(): DonatedItemsPresenter

    fun profilePresenter(): ProfilePresenter

    fun uploadPresenter(): UploadPresenter

    fun mainPresenter(): MainPresenter

    fun viewItemPresenter(): ViewItemPresenter

    fun reminderPresenter(): ReminderPresenter

    fun editItemPresenter(): EditItemPresenter
}