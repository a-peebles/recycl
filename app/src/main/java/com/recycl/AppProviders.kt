package com.recycl

/**
 * Used to assign Presenter functions to their corresponding components
 */
private fun component() = BaseApplication.component


fun signupPresenter() = component().signupPresenter()

fun loginPresenter() = component().loginPresenter()

fun welcomePresenter() = component().welcomePresenter()

fun profileDetailsPresenter() = component().profileDetailsPresenter()

fun forgotPasswordPresenter() = component().forgotPasswordPresenter()

fun allItemsPresenter() = component().allItemsPresenter()

fun donatedItemsPresenter() = component().donatedItemsPresenter()

fun profilePresenter() = component().profilePresenter()

fun uploadPresenter() = component().uploadPresenter()

fun mainPresenter() = component().mainPresenter()

fun viewItemPresenter() = component().viewItemPresenter()

fun reminderPresenter() = component().reminderPresenter()

fun editItemPresenter() = component().editItemPresenter()