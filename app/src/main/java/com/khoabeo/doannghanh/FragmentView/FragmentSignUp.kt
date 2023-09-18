package com.khoabeo.doannghanh.FragmentView

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.khoabeo.doannghanh.Contract.RegisterContract
import com.khoabeo.doannghanh.Presenter.RegisterPresenterImpl
import com.khoabeo.doannghanh.R
import com.khoabeo.doannghanh.databinding.FragmentSigupBinding
import kotlinx.coroutines.runBlocking

class FragmentSignUp : Fragment(R.layout.fragment_sigup), RegisterContract.RegisterView {
    private var _binding: FragmentSigupBinding? = null
    private val binding get() = _binding!!
    private lateinit var registerPresenter: RegisterContract.RegisterPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSigupBinding.inflate(
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

        val btnImgPhone = view.findViewById<ImageButton>(R.id.imgPhone)
        btnImgPhone.setOnClickListener {
            transitionFragment(FragmentPhone())
        }
        binding.btnSignUpEmail.setOnClickListener {
            var email: String = binding.edtEmail.text.toString()
            var pass: String = binding.edtPassword.text.toString()

        }
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    fun initialize() {

        registerPresenter = RegisterPresenterImpl(this)
    }

    private fun onBackPressed() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun transitionFragment(fragment: Fragment) {
        val transition = requireActivity().supportFragmentManager.beginTransaction()
        transition.replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()
    }

    override fun showRegisterSuccess() {
        Toast.makeText(requireContext(), "Register Success!!", Toast.LENGTH_SHORT).show()

    }

    override fun showRegisterFail() {
        Toast.makeText(requireContext(), "Register Fail!!", Toast.LENGTH_SHORT).show()
    }


    override fun startLoginFragment() {
        transitionFragment(FragmentSignIn())
    }

    override fun showProgessBar() {
        binding.fragProgess.visibility = View.VISIBLE
        binding.fragProgess.alpha = 0.5f

        Handler(Looper.getMainLooper()).postDelayed({
            binding.fragProgess.visibility = View.GONE
        }, 5000) // 5000 mill
    }


}