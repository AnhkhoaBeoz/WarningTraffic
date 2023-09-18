package com.khoabeo.doannghanh.FragmentView


import android.Manifest
import android.Manifest.permission
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.khoabeo.doannghanh.R


class FragmentGMap : Fragment(R.layout.fragment_map), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_map, container, false)
        return view
    }

    private lateinit var map: FrameLayout
    private lateinit var mMap: GoogleMap
    private val permissionFine = Manifest.permission.ACCESS_FINE_LOCATION
    private val permissionCoarse = Manifest.permission.ACCESS_COARSE_LOCATION
    private var permissionDenied = false
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var locationCurrentUser: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map = view.findViewById(R.id.map)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
            val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        mMap = map
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation()
        showMapCurrent()
       val btnMyLocation  = view?.findViewById<FloatingActionButton>(R.id.btnMyLocation)
        btnMyLocation?.setOnClickListener(View.OnClickListener {
            showMapCurrent()
        })
    }

    @SuppressLint("MissingPermission")
    public fun showMapCurrent() {
        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener {
            if (it != null) {
                locationCurrentUser = it
                updateMapLocation(locationCurrentUser)
            } else
                Log.d("GETLASTLOCATION", "GET LOCATION FAILED $it")
        }
    }

    private fun updateMapLocation(location: Location?) {
        val current = LatLng(location!!.latitude, location!!.longitude)
        mMap.addMarker(
            MarkerOptions()
                .position(current)
                .title("Marker in Sydney")
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLng(current))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 15f))
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(requireContext(), permissionCoarse)
            == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                permissionFine
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true

        } else {
            ActivityCompat.requestPermissions(
                requireContext() as Activity,
                arrayOf(permissionCoarse, permissionFine),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        if (ContextCompat.checkSelfPermission(requireContext(), permissionFine)
            == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                permissionCoarse
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            enableMyLocation();
            showMapCurrent()
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
        }

    }


    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(requireContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(requireContext(), "Current location:\n$location", Toast.LENGTH_LONG).show()
    }
}


