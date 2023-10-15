package com.khoabeo.warningtraffic.Contract

import com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.User

interface ProfileContract {
    interface ProfileView {
        fun signOutSuccessful()
        fun navigateToMainActivity()
    }

    interface ProfilePresenter {
        fun signOutUserPre()
        fun getDataUser(uuid: String): User
    }
}