package com.khoabeo.warningtraffic.Contract

import android.content.Context

interface RegisterContract {
    interface RegisterView {
        fun showRegisterSuccess()
        fun showRegisterFail()
        fun startLoginFragment()
        fun showProcessBar()
        fun hideProcessBar()
        fun showEmptyFieldsError()
//        fun updateFragment(fragment: Fragment)
    }

    interface RegisterPresenter {
        fun createUserWithEmailAndPasswordPre(email: String, password: String, context: Context)
    }
}