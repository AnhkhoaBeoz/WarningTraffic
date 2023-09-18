package com.khoabeo.doannghanh.FragmentView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.khoabeo.doannghanh.R

class FragmentVerifyOTP : Fragment(R.layout.fragment_otp) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_otp, container, false)
        return view
    }
    var resendingToken: ForceResendingToken? = null
    var mAuth = FirebaseAuth.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arguments?.getString("phone_number")

        val btnVetify = view.findViewById<Button>(R.id.btnVetify)
        val etextOTP = view.findViewById<EditText>(R.id.etextOTP)
        val btnResendCode = view.findViewById<Button>(R.id.btnResendCode)

        btnVetify.setOnClickListener {

        }

    }
}