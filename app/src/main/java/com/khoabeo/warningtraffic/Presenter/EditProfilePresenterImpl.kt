package com.khoabeo.warningtraffic.Presenter


import com.khoabeo.warningtraffic.Contract.EditProfileContract
import com.khoabeo.warningtraffic.FragmentView.FragmentGMap
import com.khoabeo.warningtraffic.Modal.entitis.User
import com.khoabeo.warningtraffic.Modal.service.FirebaseUserHelper

class EditProfilePresenterImpl(var editProfileView: EditProfileContract.EditProfileView) :
    EditProfileContract.EditProfilePresenter {
    private val mAuth = FirebaseUserHelper()
    override fun updateProfile(FName: String, LName: String, Phone: String) {
        mAuth.updateProfileUser(FName, LName, Phone)
        editProfileView.showUpdateSuccess()
        editProfileView.transitionFragment(FragmentGMap())
    }

}