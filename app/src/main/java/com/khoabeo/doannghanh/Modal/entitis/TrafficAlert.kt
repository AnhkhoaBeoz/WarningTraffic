package com.khoabeo.doannghanh.Modal.entitis
import java.util.Date

data class TrafficAlert(
    val id: Int?,
    val userId: Int,
    val routeId: Int,
    val level: String,
    val createdAt: Date,
    val updatedAt: Date
)
