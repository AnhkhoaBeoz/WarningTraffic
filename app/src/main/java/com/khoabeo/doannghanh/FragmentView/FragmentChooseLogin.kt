package com.khoabeo.doannghanh.FragmentView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.khoabeo.doannghanh.R


class FragmentChooseLogin : Fragment(R.layout.fragment_choose_login) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choose_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonPhone = view.findViewById<Button>(R.id.btnSignPhone)
        buttonPhone.setOnClickListener {
            transitionFragment(FragmentPhone())
        }

        val btnSignEmail = view.findViewById<Button>(R.id.btnSignEmail)
        btnSignEmail.setOnClickListener {
            transitionFragment(FragmentSignIn())
        }
        val btnSignUp = view.findViewById<Button>(R.id.btnSignUp)
        btnSignUp.setOnClickListener {
            transitionFragment(FragmentSignUp())
        }

    }

    private fun transitionFragment(fragment: Fragment) {
        val transition = requireActivity().supportFragmentManager.beginTransaction()
        transition.replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()
    }




}
