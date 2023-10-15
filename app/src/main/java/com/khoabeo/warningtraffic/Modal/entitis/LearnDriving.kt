package com.khoabeo.warningtraffic.Modal.entitis

import java.util.Date

data class LearnDriving(
    val id: Int?,
    val title: String,
    val content: String,
    val type: String,
    val createdAt: Date,
    val updatedAt: Date
)
