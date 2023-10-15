package com.khoabeo.warningtraffic.Presenter

import android.content.Context
import android.util.Log
import com.khoabeo.warningtraffic.Contract.LoginContract
import com.khoabeo.warningtraffic.Modal.service.FirebaseUserHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.log

class LoginPresenterImpl(var loginView: LoginContract.LoginView) : LoginContract.LoginPresenter {
    private val mAuth = FirebaseUserHelper()
    override fun signInUser(email: String, password: String, context: Context) {

        if (email.isNotEmpty() && password.isNotEmpty() && loginView.isEmailValid(email)) {
            loginView.showProgressDialog()
            mAuth.signInWithEmail(email, password, context)
        } else {
            // Nếu trường email hoặc mật khẩu trống, hiển thị lỗi và ẩn progress bar
            loginView.hideProgressDialog()
            loginView.showEmptyFieldsError()
        }


    }

    override fun restPass(email: String, context: Context) {
        mAuth.restPassword(email, context)
    }
}