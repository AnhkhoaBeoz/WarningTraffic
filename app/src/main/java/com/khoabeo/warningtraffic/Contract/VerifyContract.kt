package com.khoabeo.warningtraffic.Contract

import android.content.Context

interface VerifyContract {
    interface VerifyView {
        fun onPhoneVerificationSuccess()
        fun onPhoneVerificationFailed(exception: Exception?)
    }

    interface VerifyPresenter {
        fun verifyPhone(phone: String, context: Context)
        fun verifyPhoneNumberWithOTP(code:String,context: Context)
        fun resendCode(phone: String, context: Context)
    }
}