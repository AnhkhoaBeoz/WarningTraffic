package com.khoabeo.warningtraffic.FragmentView

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.khoabeo.warningtraffic.Contract.ProfileContract
import com.khoabeo.warningtraffic.MainActivity
import com.khoabeo.warningtraffic.Presenter.ProfilePresenterImpl
import com.khoabeo.warningtraffic.R

class FragmentProfile : Fragment(R.layout.profile_layout), ProfileContract.ProfileView {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private lateinit var imgHeaderProfile: ImageView
    private lateinit var containerLogout: LinearLayout
    private lateinit var containerEditProfile: LinearLayout
    private lateinit var profilePresenter: ProfileContract.ProfilePresenter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        attachOnClickIntoView()

    }

    private fun initViews(view: View) {
        profilePresenter = ProfilePresenterImpl(this)
        containerEditProfile = view.findViewById(R.id.containerEditProfile)
        containerLogout = view.findViewById(R.id.containerLogout)
        imgHeaderProfile = view.findViewById(R.id.imgHeaderProfile)
        Glide.with(view).load(R.drawable.imgheaderprofile).centerCrop().into(imgHeaderProfile)
    }

    private fun onClickEditProfile() {
        transitionFragment(FragmentEditProfile())
    }

    private fun attachOnClickIntoView() {
        containerEditProfile.setOnClickListener {
            onClickEditProfile()
        }
        containerLogout.setOnClickListener {
            profilePresenter.signOutUserPre()
        }
    }

    private fun transitionFragment(fragment: Fragment) {
        val transition = requireActivity().supportFragmentManager.beginTransaction()
        transition.replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null).commit()
    }

    override fun signOutSuccessful() {
        Toast.makeText(requireContext(), "Thành công ", Toast.LENGTH_SHORT).show()
    }

    override fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }


}