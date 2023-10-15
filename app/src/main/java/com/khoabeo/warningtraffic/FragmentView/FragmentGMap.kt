package com.khoabeo.warningtraffic.FragmentView


import android.Manifest

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.beust.klaxon.*
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.maps.android.SphericalUtil
import com.khoabeo.warningtraffic.Contract.GMapContract
import com.khoabeo.warningtraffic.Modal.entitis.TrafficAlert
import com.khoabeo.warningtraffic.Presenter.GGMapPresenterImpl
import com.khoabeo.warningtraffic.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.log

class FragmentGMap : Fragment(R.layout.fragment_map), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, GMapContract.GMapView {


    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val latitude = location.latitude
            val longitude = location.longitude
            // ...
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Truy cập context tại đây và lấy giá trị từ resource
        apiKey = getString(R.string.PLACES_API_KEY)
    }

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
    private lateinit var navDrawbtn: FloatingActionButton
    private val permissionFine = Manifest.permission.ACCESS_FINE_LOCATION
    private val permissionCoarse = Manifest.permission.ACCESS_COARSE_LOCATION
    private var permissionDenied = false
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var locationCurrentUser: Location
    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var squareFrame: FrameLayout
    private lateinit var mapFragment: SupportMapFragment
    private val TAG: String = "INFOR"
    private var mapReady = false
    private lateinit var autoCompleteEditText: AutoCompleteTextView
    private var selectedPlace: Place? = null
    private var apiKey: String? = null
    private lateinit var placesClient: PlacesClient
    private lateinit var btnReportTraffic: FloatingActionButton
    private var currentPolyline: Polyline? = null
    private val routeMarkers: MutableList<Marker> = mutableListOf()
    private val monitoredLocations = mutableListOf<LatLng>()
    private lateinit var gMapPresenter: GMapContract.GMapPresenter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        checkLocationPermission()
        initPlace()
        reportTraffic()

    }

    private fun initView(view: View) {
        gMapPresenter = GGMapPresenterImpl(this)
        btnReportTraffic = view.findViewById(R.id.btnReportTraffic)
        Places.initialize(requireContext(), apiKey)
        placesClient = Places.createClient(requireContext())

        map = view.findViewById(R.id.map)!!
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)


    }

    private fun reportTraffic() {
        btnReportTraffic.setOnClickListener {
            gMapPresenter.pushReport(LatLng(locationCurrentUser.latitude, locationCurrentUser.longitude))
        }
    }

    override fun markerTraffic(trafficAlert: TrafficAlert) {
        val latitudeStr = trafficAlert.latitude
        val longitudeStr = trafficAlert.longitude

        try {
            val latitude = latitudeStr.toDouble()
            val longitude = longitudeStr.toDouble()
            monitoredLocations.add(LatLng(latitude, longitude))
            val location = LatLng(latitude, longitude)
            val markerOptions = MarkerOptions()
                .position(location)
                .title("Địa điểm có thể kẹt xe")
            mMap.addMarker(markerOptions)

        } catch (e: NumberFormatException) {

        }
    }

    override fun showPushReportSuccess() {
        Toast.makeText(requireContext(), "Push Successful !!!", Toast.LENGTH_SHORT).show()
    }

    fun getRoutes(place: Place) {

        currentPolyline?.remove()

        for (marker in routeMarkers) {
            marker.remove()
        }
        routeMarkers.clear()

        val LatLongB = LatLngBounds.Builder()
        val originLocation = LatLng(locationCurrentUser.latitude, locationCurrentUser.longitude)
        val destination = LatLng(place.latLng!!.latitude, place.latLng!!.longitude)


        // Declare polyline object and set up color and width
        val options = PolylineOptions()
        options.color(Color.RED)
        options.width(10f)

        val url = getURL(originLocation, destination)
        lifecycleScope.launch {
            try {
                // Bắt đầu tác vụ bất đồng bộ trên luồng IO
                val result = withContext(Dispatchers.IO) {

                    URL(url).readText()
                }


                val parser: Parser = Parser()
                val stringBuilder: StringBuilder = StringBuilder(result)
                val json: JsonObject = parser.parse(stringBuilder) as JsonObject

                val routes = json.array<JsonObject>("routes")
                val points = routes!!["legs"]["steps"][0] as JsonArray<JsonObject>
                val polypts = points.flatMap { decodePoly(it.obj("polyline")?.string("points")!!) }

                options.add(originLocation)
                LatLongB.include(originLocation)
                for (point in polypts) {
                    options.add(point)
                    LatLongB.include(point)
                }
                options.add(destination)
                LatLongB.include(destination)
                val bounds = LatLongB.build()
                currentPolyline = mMap?.addPolyline(options)
                routeMarkers.add(
                    mMap.addMarker(
                        MarkerOptions().position(destination).title("Destination")
                    )!!
                )  // Thêm Marker vào danh sách
                val legs = routes?.get(0)?.array<JsonObject>("legs")
                val firstLeg = legs?.get(0)
                val distance = firstLeg!!.obj("distance")?.string("text") ?: ""
                val duration = firstLeg.obj("duration")?.string("text") ?: ""
                Log.d(TAG, "Distance: $distance, Duration: $duration")

                showBottomSheetDialog(distance, duration)
            } catch (e: Exception) {
                // Xử lý lỗi nếu cần
                e.printStackTrace()
            }
        }

    }

    private fun getURL(from: LatLng, to: LatLng): String {
        val apiKey = "AIzaSyCt-usfF62-eA0AV01MgE54rKnyrtYKQrM"
        val origin = "origin=" + from.latitude + "," + from.longitude
        val dest = "destination=" + to.latitude + "," + to.longitude
        val sensor = "sensor=false"
        val params = "$origin&$dest&$sensor&key=$apiKey"
        val test = "https://maps.googleapis.com/maps/api/directions/json?$params"
        Log.d(TAG + "123", test)
        return "https://maps.googleapis.com/maps/api/directions/json?$params"
    }

    private fun decodePoly(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(
                lat.toDouble() / 1E5,
                lng.toDouble() / 1E5
            )
            poly.add(p)
        }

        return poly
    }

    private fun initPlace() {

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        val hoChiMinhBounds = RectangularBounds.newInstance(
            LatLng(10.649814, 106.275155),  // Tọa độ tây nam của Hồ Chí Minh
            LatLng(10.890134, 106.739808)  // Tọa độ đông bắc của Hồ Chí Minh
        )

        autocompleteFragment.setLocationBias(hoChiMinhBounds)
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);
        autocompleteFragment.setCountries("VN")
        autocompleteFragment.setPlaceFields(listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG))


        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                updateMapWithPlace(place)
                getRoutes(place)
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
    }


    private fun updateMapWithPlace(place: Place) {
        val latLng = place.latLng

        if (latLng != null) {
            val cameraPosition = CameraPosition.Builder()
                .target(latLng)
                .zoom(15.0f)
                .bearing(90.0f)
                .tilt(45.0f)
                .build()
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
            mMap.animateCamera(cameraUpdate)
        }
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        mMap = map
        mMap.clear()
        mapReady = true
        mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        mMap.isTrafficEnabled
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)
        enableMyLocation()
        showMapCurrent()
        val btnMyLocation = view?.findViewById<FloatingActionButton>(R.id.btnMyLocation)
        btnMyLocation?.setOnClickListener(View.OnClickListener {
            enableMyLocation()
            showMapCurrent()
        })
        if (mapReady) {
            gMapPresenter.getReport()
        }

        var isNearby = false // Biến cờ

        mMap.setOnCameraMoveListener {
            val userLocation = mMap.cameraPosition.target
            if (!isNearby) {
                for (location in monitoredLocations) {
                    val distance = calculateDistance(userLocation, location)
                    Log.d(TAG, distance.toString())
                    if (distance < 100.0) {
                        Toast.makeText(requireContext(), "Hiện tại đang gần địa điểm kẹt xe", Toast.LENGTH_SHORT).show()
                        isNearby = true
                        break
                    }
                }
            }
        }


    }

    @SuppressLint("MissingPermission")
    private fun showMapCurrent() {
        if (checkLocationPermission()) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        locationCurrentUser = location
                        updateMapLocation(locationCurrentUser)

                    } else {
                        Log.d("GETLASTLOCATION", "GET LOCATION FAILED")
                    }
                }
        }
    }

    private fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permissionCoarse
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                permissionFine
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        } else {
            requestLocationPermission()
            return false
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(permissionCoarse, permissionFine),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun updateMapLocation(location: Location?) {
        val current = LatLng(location!!.latitude, location.longitude)
        val latLngTo = LatLng(15.8701261, 100.9905821)
        mMap.animateCamera(CameraUpdateFactory.newLatLng(current))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 15f))
        val test = SphericalUtil.computeDistanceBetween(current, latLngTo)
        Log.d("CALCULATOR DISTANCE ", test.toString())
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), permissionCoarse)
            == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                permissionFine
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation()
                showMapCurrent()
            } else {
                Toast.makeText(requireContext(), "FAIL", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(requireContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(requireContext(), "Current location:\n$location", Toast.LENGTH_LONG).show()
    }

    fun showBottomSheetDialog(distance: String, duration: String) {
        val view: View = getLayoutInflater().inflate(com.khoabeo.warningtraffic.R.layout.bottom_sheet_map, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)

        val textViewDistance = view.findViewById<TextView>(R.id.textViewDistance)
        val textViewDuration = view.findViewById<TextView>(R.id.textViewDuration)

        textViewDistance.text = "Distance: $distance"
        textViewDuration.text = "Duration: $duration"

        dialog.show()
    }

    override fun showNotification() {

    }

    private fun calculateDistance(location1: LatLng, location2: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            location1.latitude,
            location1.longitude,
            location2.latitude,
            location2.longitude,
            results
        )
        return results[0]
    }


}


