package com.khoabeo.warningtraffic.Presenter

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Phone
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.khoabeo.warningtraffic.Contract.VerifyContract
import com.khoabeo.warningtraffic.Modal.service.FirebasePhone
import com.khoabeo.warningtraffic.Modal.service.FirebaseUserHelper

class VerifyPhonePresenterImpl(var verifyView: VerifyContract.VerifyView) : VerifyContract.VerifyPresenter {
    private val pAuth = FirebasePhone()
    override fun verifyPhone(phone: String, context: Context) {
        pAuth.initializer(context)
        pAuth.startPhoneNumberVerification(phone)
    }

    override fun verifyPhoneNumberWithOTP(code: String, context: Context) {
        pAuth.initializer(context)

        val credential = PhoneAuthProvider.getCredential(pAuth.getStoredVerificationId().toString(), code)
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    verifyView.onPhoneVerificationSuccess()
                } else {

                    verifyView.onPhoneVerificationFailed(task.exception)
                }
            }
    }

    override fun resendCode(phone: String, context: Context) {
        pAuth.initializer(context)

            pAuth.resendVerificationCode(phone)
    }
}