package com.khoabeo.doannghanh.Modal.entitis

import java.util.Date

data class Route(
    val id: Int?,
    val startPoint: String,
    val endPoint: String,
    val distance: Float,
    val duration: Int,
    val createdAt: Date,
    val updatedAt: Date
)
