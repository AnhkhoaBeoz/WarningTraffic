package com.khoabeo.warningtraffic.FragmentView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.khoabeo.warningtraffic.Contract.EditProfileContract
import com.khoabeo.warningtraffic.Modal.entitis.User
import com.khoabeo.warningtraffic.Modal.service.FirebaseUserHelper
import com.khoabeo.warningtraffic.Presenter.EditProfilePresenterImpl
import com.khoabeo.warningtraffic.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class FragmentEditProfile : Fragment(R.layout.fragment_update_infor_user),
    EditProfileContract.EditProfileView {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update_infor_user, container, false)
        return view
    }

    private lateinit var edtFName: EditText
    private lateinit var edtLName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPhone: EditText
    private lateinit var btnUpdate: Button
    private var mAuth: FirebaseUserHelper = FirebaseUserHelper()
    private val userCurrent = mAuth.getCurrentUser()!!
    private lateinit var editProfilePresenter: EditProfileContract.EditProfilePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        attachOnClick()
    }

    private fun initView(view: View) {
        edtFName = view.findViewById(R.id.edtFName)
        edtLName = view.findViewById(R.id.edtLName)
        edtEmail = view.findViewById(R.id.edtEmail)
        edtPhone = view.findViewById(R.id.edtPhone)
        btnUpdate = view.findViewById(R.id.btnUpdate)
        editProfilePresenter = EditProfilePresenterImpl(this)
        edtEmail.setText(userCurrent.email)

    }

    override fun transitionFragment(fragment: Fragment) {
        val transition = requireActivity().supportFragmentManager.beginTransaction()
        transition.replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null).commit()
    }

    private fun attachOnClick() {


        btnUpdate.setOnClickListener {
            val FName = edtFName.text.toString()
            val LName = edtLName.text.toString()
            val phone = edtPhone.text.toString()
            editProfilePresenter.updateProfile(FName, LName, phone)
        }
    }

    override fun showUpdateSuccess() {
        Toast.makeText(requireContext(), "Update Success!!!!", Toast.LENGTH_SHORT).show()
    }
}