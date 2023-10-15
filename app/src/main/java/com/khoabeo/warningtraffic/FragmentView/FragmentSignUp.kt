package com.khoabeo.warningtraffic.FragmentView

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.khoabeo.warningtraffic.Contract.RegisterContract
import com.khoabeo.warningtraffic.MyProgressDialog
import com.khoabeo.warningtraffic.Presenter.RegisterPresenterImpl
import com.khoabeo.warningtraffic.R
import com.khoabeo.warningtraffic.databinding.FragmentSigupBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentSignUp : Fragment(R.layout.fragment_sigup), RegisterContract.RegisterView {
    private var _binding: FragmentSigupBinding? = null
    private lateinit var btnImgPhone: ImageButton
    private lateinit var btnBack: Button
    private val binding get() = _binding!!
    private lateinit var registerPresenter: RegisterContract.RegisterPresenter
    private lateinit var context: Context
    private lateinit var myProgressDialog: MyProgressDialog
    private lateinit var progressBar: ProgressBar
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
        initialize(view)
        attachOnClick()
    }

    override fun onStart() {
        super.onStart()

    }

    private fun initialize(view: View) {
        progressBar = view.findViewById(R.id.progressBar)
        registerPresenter = RegisterPresenterImpl(this)
        myProgressDialog = MyProgressDialog(progressBar)

        btnBack = view.findViewById<Button>(R.id.btnBack)
        btnImgPhone = view.findViewById(R.id.imgPhone)
    }

    private fun attachOnClick() {
        btnBack.setOnClickListener {
            onBackPressed();
        }

        btnImgPhone.setOnClickListener {
            transitionFragment(FragmentPhone())
        }

        binding.btnSignUpEmail.setOnClickListener {
            val email: String = binding.edtEmail.text.toString()
            val pass: String = binding.edtPassword.text.toString()
            registerPresenter.createUserWithEmailAndPasswordPre(
                email,
                pass,
                requireContext()
            )
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

    override fun showRegisterSuccess() {
        Toast.makeText(requireContext(), "Register Success!!", Toast.LENGTH_SHORT).show()
    }

    override fun showRegisterFail() {
        Toast.makeText(requireContext(), "Register Fail!!", Toast.LENGTH_SHORT).show()
    }


    override fun startLoginFragment() {
        transitionFragment(FragmentSignIn())
    }

    override fun showProcessBar() {
        Handler(Looper.getMainLooper()).postDelayed({
            myProgressDialog.showProgressDialog()
        }, 2000)
    }

    override fun hideProcessBar() {
        myProgressDialog.dismissProgressDialog()
    }

    override fun showEmptyFieldsError() {
        val errorMessage = "Please enter both email and password"
        Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show()
    }
}