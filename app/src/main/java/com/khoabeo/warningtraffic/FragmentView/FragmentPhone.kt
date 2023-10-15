package com.khoabeo.warningtraffic.FragmentView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.khoabeo.warningtraffic.R
import com.khoabeo.warningtraffic.databinding.FragmentLoginNumberhoneBinding

class FragmentPhone : Fragment(R.layout.fragment_login_numberhone) {
    private var _binding: FragmentLoginNumberhoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginNumberhoneBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnBack = view.findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressed();
        }

        val btnSignEmail = view.findViewById<Button>(R.id.btnSignEmail)
        btnSignEmail.setOnClickListener {
            transitionFragment(FragmentSignIn())
        }
        val btnOtp = view.findViewById<Button>(R.id.btnLoginPhone)
        btnOtp.setOnClickListener() {
            val phoneNumber = binding.eloginPhone.text.toString()
            val startIndex = 4
            val endIndex = phoneNumber.length
            val phoneVerify = "+84" + phoneNumber.substring(startIndex, endIndex)
            val bundle = Bundle()
            bundle.putString("phone_number", phoneVerify)

            val fragment = FragmentVerifyOTP()
            fragment.arguments = bundle

            transitionFragment(fragment)
        }

    }

    private fun onBackPressed() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun transitionFragment(fragment: Fragment) {
        val transition = requireActivity().supportFragmentManager.beginTransaction()
        transition.replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}