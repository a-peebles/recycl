package com.recycl.di.module

import com.recycl.presentation.*
import com.recycl.presentation.implementation.*
import dagger.Binds
import dagger.Module



/**
 * PresentationModule
 * @author Alex Peebles
 * Student Number: 150328687
 * Assigns Presenter classes to their respective interfaces through direct injection
 */
@Module(includes = [InteractionModule::class])
abstract class PresentationModule {

    @Binds
    abstract fun signupPresenter(signupPresenter: SignupPresenter): SignupPresenterInterface

    @Binds
    abstract fun loginPresenter(loginPresenter: LoginPresenter): LoginPresenterInterface

    @Binds
    abstract fun welcomePresenter(welcomePresenter: WelcomePresenter): WelcomePresenterInterface

    @Binds
    abstract fun profileDetails(profileDetailsPresenter: ProfileDetailsPresenter): ProfileDetailsPresenterInterface

    /**
    @Binds
    abstract fun mapPresenter(mapPresenter: MapPresenter): MapPresenterInterface */

    @Binds
    abstract fun allItemsPresenter(allItemsPresenter: AllItemsPresenter): AllItemsPresenterInterface

    @Binds
    abstract fun forgotPasswordPresenter(forgotPasswordPresenter: ForgotPasswordPresenter) : ForgotPasswordPresenterInterface

    @Binds
    abstract fun donatedItemsPresenter(donatedItemsPresenter: DonatedItemsPresenter) : DonatedItemsPresenterInterface

    @Binds
    abstract fun profilePresenter(profilePresenter: ProfilePresenter): ProfilePresenterInterface

    @Binds
    abstract fun uploadPresenter(uploadPresenter: UploadPresenter): UploadPresenterInterface

    @Binds
    abstract fun mainPresenter(mainPresenter: MainPresenter): MainPresenterInterface

    @Binds
    abstract fun viewItemPresenter(viewItemPresenter: ViewItemPresenter): ViewItemPresenterInterface

    @Binds
    abstract fun reminderPresenter(reminderPresenter: ReminderPresenter): ReminderPresenterInterface


    @Binds
    abstract fun editItemPresenter(editItemPresenter: EditItemPresenter): EditItemPresenterInterface
}
