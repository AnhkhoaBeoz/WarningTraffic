package com.khoabeo.doannghanh.Presenter

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.khoabeo.doannghanh.Contract.RegisterContract
import com.khoabeo.doannghanh.Modal.service.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class RegisterPresenterImpl(var registerView: RegisterContract.RegisterView) :
    RegisterContract.RegisterPresenter {
    private val fbUser: FirebaseUser = FirebaseUser()
    override fun createUser(email: String, password: String) {
        val isSuccess: Boolean = fbUser.createUserWithEmailAndPass(email, password);
        Log.d("Dang ki user ", "createUser: $isSuccess")

        registerView.showProgessBar()

        when (isSuccess) {
            true -> registerView.showRegisterSuccess()
            false -> registerView.showRegisterFail()
        }
    }
}


