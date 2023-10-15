package com.khoabeo.warningtraffic.Presenter

import com.google.android.gms.maps.model.LatLng
import com.khoabeo.warningtraffic.Contract.EditProfileContract
import com.khoabeo.warningtraffic.Contract.GMapContract
import com.khoabeo.warningtraffic.Modal.entitis.TrafficAlert
import com.khoabeo.warningtraffic.Modal.service.FirebaseReport
import com.khoabeo.warningtraffic.Modal.service.FirebaseUserHelper
import java.util.*

class GGMapPresenterImpl(var gMapView: GMapContract.GMapView) : GMapContract.GMapPresenter {
    private val mAuth = FirebaseUserHelper()
    private val gFirebase = FirebaseReport()

    override fun pushReport(location: LatLng) {
        gFirebase.initializeDbRef()
        val user = mAuth.getCurrentUser()
        val latitude = location.latitude.toString()
        val longitude = location.longitude.toString()

        val trafficAlert = TrafficAlert(
            id = null,
            userId = user!!.uid,
            latitude = latitude,
            longitude = longitude,
            level = "High",
            createdAt = Date(),
            updatedAt = Date()
        )

        gFirebase.writeNewTrafficAlert(trafficAlert)
        gMapView.showPushReportSuccess()
    }


    override fun getReport() {
        gFirebase.initializeDbRef()
        gFirebase.getTrafficAlerts {
            for (i in it) {
                gMapView.markerTraffic(i)
            }
        }
    }
}