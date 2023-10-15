package com.khoabeo.warningtraffic.Modal.service

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.khoabeo.warningtraffic.Modal.entitis.TrafficAlert
import java.util.Date

class FirebaseReport {
    private val TAG = "ReadAndWriteSnippets"

    private lateinit var database: FirebaseDatabase
    private lateinit var trafficAlertsRef: DatabaseReference


    fun initializeDbRef() {
        database = FirebaseDatabase.getInstance()
        trafficAlertsRef = database.getReference("traffic_alerts")
    }

    fun writeNewTrafficAlert(trafficAlert: TrafficAlert) {
        // Sử dụng push() để tạo một nút mới trong "traffic_alerts" và lấy ra id của nút đó
        val newTrafficAlertRef = trafficAlertsRef.push()

        // Gán dữ liệu của đối tượng TrafficAlert vào nút mới
        newTrafficAlertRef.setValue(trafficAlert)
    }

    fun getTrafficAlerts(callback: (List<TrafficAlert>) -> Unit) {
        trafficAlertsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val trafficAlerts = ArrayList<TrafficAlert>()
                for (childSnapshot in snapshot.children) {
                    val id = childSnapshot.child("id").getValue(Int::class.java)
                    val userId = childSnapshot.child("userId").getValue(String::class.java)
                    val latitude = childSnapshot.child("latitude").getValue(String::class.java)
                    val longitude = childSnapshot.child("longitude").getValue(String::class.java)
                    val level = childSnapshot.child("level").getValue(String::class.java)
                    val createdAtHashMap = childSnapshot.child("createdAt").getValue() as? HashMap<*, *>
                    val updatedAtHashMap = childSnapshot.child("updatedAt").getValue() as? HashMap<*, *>

                    if (userId != null && latitude != null && longitude != null && level != null && createdAtHashMap != null && updatedAtHashMap != null) {
                        val createdAtTime = createdAtHashMap["time"] as? Long
                        val updatedAtTime = updatedAtHashMap["time"] as? Long

                        if (createdAtTime != null && updatedAtTime != null) {
                            val createdAt = Date(createdAtTime)
                            val updatedAt = Date(updatedAtTime)
                            val trafficAlert = TrafficAlert(id, userId, latitude, longitude, level, createdAt, updatedAt)
                            trafficAlerts.add(trafficAlert)
                        }
                    }
                }
                callback(trafficAlerts)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }



}