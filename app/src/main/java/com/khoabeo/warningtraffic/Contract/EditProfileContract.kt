package com.khoabeo.warningtraffic.Contract

import androidx.fragment.app.Fragment
import com.khoabeo.warningtraffic.Modal.entitis.User


interface EditProfileContract {
    interface EditProfileView {
        fun showUpdateSuccess()
        fun transitionFragment(fragment: Fragment)
    }

    interface EditProfilePresenter {
        fun updateProfile(FName: String, LName: String, Phone: String)
    }
}