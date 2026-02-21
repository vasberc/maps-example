package com.vasberc.mapsexample

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class MapViewModel : ViewModel() {
    // Circle radius in kilometers
    private val _circleRadiusKm = mutableFloatStateOf(500f)
    val circleRadiusKm: Float get() = _circleRadiusKm.floatValue

    // Circle center location (tap location)
    private val _circleCenter = mutableStateOf<LatLng?>(null)
    val circleCenter: LatLng? get() = _circleCenter.value

    fun updateCircleCenter(latLng: LatLng) {
        _circleCenter.value = latLng
    }

    fun updateCircleRadiusKm(radiusKm: Float) {
        _circleRadiusKm.floatValue = radiusKm
    }
}


