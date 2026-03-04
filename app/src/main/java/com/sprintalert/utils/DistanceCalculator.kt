package com.sprintalert.utils

import android.location.Location
import kotlin.math.*

object DistanceCalculator {
    // Haversine formula for accurate GPS distance calculation
    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val EARTH_RADIUS_METERS = 6371000f
        
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return EARTH_RADIUS_METERS * c
    }

    fun calculateSpeed(distanceMeters: Float, timeSeconds: Float): Float {
        if (timeSeconds == 0f) return 0f
        return distanceMeters / timeSeconds * 3.6f // Convert to km/h
    }

    fun getTravelDistance(locations: List<Location>): Float {
        if (locations.size < 2) return 0f
        
        var totalDistance = 0f
        for (i in 0 until locations.size - 1) {
            totalDistance += calculateDistance(
                locations[i].latitude, locations[i].longitude,
                locations[i + 1].latitude, locations[i + 1].longitude
            )
        }
        return totalDistance
    }
}
