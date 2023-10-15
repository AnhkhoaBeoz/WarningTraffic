package com.khoabeo.warningtraffic.Contract

import com.google.android.gms.maps.model.LatLng
import com.khoabeo.warningtraffic.Modal.entitis.TrafficAlert

interface GMapContract {
    interface GMapView {
        fun showNotification()
        fun showPushReportSuccess()
        fun markerTraffic(trafficAlert: TrafficAlert)
    }

    interface GMapPresenter {
        fun pushReport(location: LatLng)
        fun getReport()
    }
}