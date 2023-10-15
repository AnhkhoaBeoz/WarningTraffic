package com.khoabeo.warningtraffic.Presenter

import android.content.Context
import com.khoabeo.warningtraffic.Contract.RegisterContract
import com.khoabeo.warningtraffic.Modal.service.FirebaseUserHelper

class RegisterPresenterImpl(var registerView: RegisterContract.RegisterView) :
    RegisterContract.RegisterPresenter {
    private val mAuth = FirebaseUserHelper()
    override fun createUserWithEmailAndPasswordPre(
        email: String,
        password: String,
        context: Context
    ) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.createUser(email, password, context)
        } else {
            registerView.showEmptyFieldsError()
        }
    }
}
