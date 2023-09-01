package com.example.lostfound.data.models

import java.util.Date
import java.util.UUID

class Item(
    val id: UUID,
    val type: Type,
    val name: String,
    val description: String,
    val imageId: Int,
    val latitude: Double,
    val longitude: Double,
    val user: String,
    val dateLost: Date,
    val status: ResolutionStatus = ResolutionStatus.PENDING) {
}