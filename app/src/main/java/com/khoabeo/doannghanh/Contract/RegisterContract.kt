package com.khoabeo.doannghanh.Contract

interface RegisterContract {
    interface RegisterView {
        fun showRegisterSuccess()
        fun showRegisterFail()
        fun startLoginFragment()
        fun showProgessBar()
    }
    interface RegisterPresenter {
        fun createUser(email:String,password: String)
    }
}