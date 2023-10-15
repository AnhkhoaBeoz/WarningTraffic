package com.khoabeo.warningtraffic.FragmentView

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.khoabeo.warningtraffic.Contract.LoginContract
import com.khoabeo.warningtraffic.HomeActivity
import com.khoabeo.warningtraffic.MainActivity
import com.khoabeo.warningtraffic.MyProgressDialog
import com.khoabeo.warningtraffic.Presenter.LoginPresenterImpl
import com.khoabeo.warningtraffic.Presenter.RegisterPresenterImpl
import com.khoabeo.warningtraffic.R
import kotlinx.coroutines.Dispatchers

class FragmentSignIn : Fragment(R.layout.fragment_signin), LoginContract.LoginView {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signin, container, false)
        return view
    }

    private lateinit var myProgressDialog: MyProgressDialog
    private lateinit var progressBar: ProgressBar
    private lateinit var btnBack: Button
    private lateinit var btnLoginEmail: Button
    private lateinit var btnForgot: Button
    private lateinit var btnImgPhone: ImageButton
    private lateinit var loginPresenter: LoginContract.LoginPresenter
    private lateinit var emailEditText: EditText
    private lateinit var passEditText: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializerView(view)
        attachOnClick()
    }

    private fun initializerView(view: View) {
        btnForgot = view.findViewById(R.id.btnForgot)
        progressBar = view.findViewById(R.id.progressBar)
        myProgressDialog = MyProgressDialog(progressBar)
        loginPresenter = LoginPresenterImpl(this)
        btnBack = view.findViewById(R.id.btnBack)
        btnImgPhone = view.findViewById(R.id.imgPhone)
        btnLoginEmail = view.findViewById(R.id.btnLoginEmail)
        passEditText = view.findViewById(R.id.edtPassSignIn)
        emailEditText = view.findViewById(R.id.edtEmailSignIn)
    }

    private fun attachOnClick() {
        btnBack.setOnClickListener {
            onBackPressed();
        }
        btnImgPhone.setOnClickListener {
            transitionFragment(FragmentPhone())
        }
        btnLoginEmail.setOnClickListener {
            val email = emailEditText.text.toString()
            val passworld = passEditText.text.toString()
            loginPresenter.signInUser(
                email, passworld,
                requireContext()
            )
        }

        btnForgot.setOnClickListener {
            val email = emailEditText.text.toString()
            if (email.isEmpty())
                Toast.makeText(requireContext(), "Please Input Your Email", Toast.LENGTH_SHORT).show()
            loginPresenter.restPass(email, requireContext())
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

    override fun showProgressDialog() {
        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            myProgressDialog.showProgressDialog()

            // Sau khi đã hiển thị thanh tiến trình trong 3 giây, bạn có thể gọi dismissProgressDialog

        }, 2000)

    }

    override fun hideProgressDialog() {
        myProgressDialog.dismissProgressDialog()
    }

    override fun showInvalidEmailError() {
        Toast.makeText(requireContext(), "Invalid Email Error !!", Toast.LENGTH_SHORT).show()
    }


    override fun showEmptyFieldsError() {
        val errorMessage = "Please enter both email and password"
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}"
        return email.matches(emailPattern.toRegex())
    }
}