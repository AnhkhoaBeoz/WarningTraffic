package com.khoabeo.warningtraffic.Presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import com.khoabeo.warningtraffic.Contract.ProfileContract
import com.khoabeo.warningtraffic.MainActivity
import com.khoabeo.warningtraffic.Modal.service.FirebaseUserHelper

class ProfilePresenterImpl(var profileView : ProfileContract.ProfileView) : ProfileContract.ProfilePresenter {
    private val mAuth = FirebaseUserHelper()
    override fun signOutUserPre() {
        mAuth.sigOutUser()
        profileView.signOutSuccessful()
        profileView.navigateToMainActivity()
    }

    override fun getDataUser(uuid: String): CrashlyticsReport.Session.User {
        TODO("Not yet implemented")
    }

}