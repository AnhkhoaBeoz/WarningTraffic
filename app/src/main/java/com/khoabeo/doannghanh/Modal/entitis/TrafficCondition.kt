package com.khoabeo.doannghanh.Modal.entitis

import java.util.Date

data class TrafficCondition(
    val id: Int?,
    val routeId: Int,
    val speed: Float,
    val level: String,
    val createdAt: Date,
    val updatedAt: Date
)
