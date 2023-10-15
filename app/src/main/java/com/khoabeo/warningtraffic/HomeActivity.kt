package com.khoabeo.warningtraffic

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.libraries.places.api.Places
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.khoabeo.warningtraffic.FragmentView.FragmentGMap
import com.khoabeo.warningtraffic.FragmentView.FragmentProfile
import com.khoabeo.warningtraffic.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navBottom: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        onReplaceFragment(FragmentGMap())
        init()
        itemBottomNavSelected()

    }

    fun init() {
        navBottom = findViewById(R.id.navBottomMenu)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyDoAB133S4QxJd0HnOvjoDq32Ii7l-DmOM")
        }

    }

    fun itemBottomNavSelected() {
        navBottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    onReplaceFragment(FragmentGMap())
                    true
                }

                R.id.profileUser -> {
                    onReplaceFragment(FragmentProfile())
                    true
                }

                else -> {
                    TODO()
                }
            }
        }
    }

    public fun onReplaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.commit()
    }


}