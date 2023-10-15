package com.khoabeo.warningtraffic.FragmentView

import android.content.Intent
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
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.khoabeo.warningtraffic.Contract.RegisterContract
import com.khoabeo.warningtraffic.Contract.VerifyContract
import com.khoabeo.warningtraffic.HomeActivity
import com.khoabeo.warningtraffic.Presenter.VerifyPhonePresenterImpl
import com.khoabeo.warningtraffic.R

class FragmentVerifyOTP : Fragment(R.layout.fragment_otp), VerifyContract.VerifyView {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_otp, container, false)
        return view
    }

    private lateinit var verifyPresenter: VerifyContract.VerifyPresenter
    private lateinit var btnVetify: Button
    private lateinit var etextOTP: EditText
    private lateinit var btnResendCode: Button
    private lateinit var data: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        this.data = arguments?.getString("phone_number")!!
        Log.d("phone", data.toString())
        verifyPresenter.verifyPhone(data.toString(), requireContext())
        attachOnClick()
    }

    fun attachOnClick() {
        btnVetify.setOnClickListener {
            if (etextOTP.text.isEmpty())
                Toast.makeText(requireContext(), "Please Input OTP !!!", Toast.LENGTH_SHORT).show()
            verifyPresenter.verifyPhoneNumberWithOTP(etextOTP.text.toString(), requireContext())
        }
        btnResendCode.setOnClickListener {
            verifyPresenter.resendCode(data.toString(), requireContext())
        }

    }

    override fun onPhoneVerificationSuccess() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        activity?.startActivity(intent)

    }

    override fun onPhoneVerificationFailed(exception: Exception?) {
        val errorMessage = exception?.message ?: "Xác thực thất bại."
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun transitionFragment(fragment: Fragment) {
        val transition = requireActivity().supportFragmentManager.beginTransaction()
        transition.replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()
    }

    fun initView(view: View) {

        verifyPresenter = VerifyPhonePresenterImpl(this)
        btnVetify = view.findViewById<Button>(R.id.btnVetify)
        etextOTP = view.findViewById<EditText>(R.id.etextOTP)
        btnResendCode = view.findViewById<Button>(R.id.btnResendCode)
    }
}