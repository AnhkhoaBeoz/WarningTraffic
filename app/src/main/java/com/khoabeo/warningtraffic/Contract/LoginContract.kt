package com.khoabeo.warningtraffic.Contract

import android.content.Context

interface LoginContract {
    interface LoginView {
        fun showProgressDialog()
        fun hideProgressDialog()
        fun isEmailValid(email: String): Boolean
        fun showInvalidEmailError()
        fun showEmptyFieldsError()
    }

    interface LoginPresenter {
        fun signInUser(email: String, password: String, context: Context)
        fun restPass(email: String, context: Context)
    }
}