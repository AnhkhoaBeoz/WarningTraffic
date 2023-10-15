package com.khoabeo.warningtraffic.Modal.entitis

import java.util.Date

data class TrafficAlert(
    val id: Int?,
    val userId: String,
    val latitude: String,
    val longitude: String,
    val level: String,
    val createdAt: Date,
    val updatedAt: Date
)
