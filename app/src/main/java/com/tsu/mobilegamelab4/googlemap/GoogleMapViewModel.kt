package com.tsu.mobilegamelab4.googlemap

import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class GoogleMapViewModel : ViewModel() {

    companion object {
        val TSU_LAT_LNG = LatLng(56.469483, 84.948689)
        const val AREA_RADIUS = 1400.0
        const val ZOOM = 17f
        const val LOCATION_CHECK_INTERVAL: Long = 3000
    }

    fun isUserInArea(playerLocation: Location, onSuccess: () -> Unit, onFailure: () -> Unit) {
        if (playerLocation.distanceTo(Location(LocationManager.GPS_PROVIDER).apply {
                latitude = TSU_LAT_LNG.latitude
                longitude = TSU_LAT_LNG.longitude
            }) <= AREA_RADIUS
        ) {
            onSuccess.invoke()
        } else {
            onFailure.invoke()
        }
    }
}