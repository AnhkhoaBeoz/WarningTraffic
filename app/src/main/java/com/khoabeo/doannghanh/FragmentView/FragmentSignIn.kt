package com.khoabeo.doannghanh.FragmentView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.khoabeo.doannghanh.R

class FragmentSignIn : Fragment(R.layout.fragment_signin) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signin, container, false)
        return view
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
    }

    private fun onBackPressed() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun transitionFragment(fragment: Fragment) {
        val transition = requireActivity().supportFragmentManager.beginTransaction()
        transition.replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()
    }
}