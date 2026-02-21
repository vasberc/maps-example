package com.vasberc.mapsexample

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.log2

class MapViewModel : ViewModel() {

    private val _mapState = MutableStateFlow(MapState())
    val mapState = _mapState.asStateFlow()

    fun updateCircleCenter(latLng: LatLng) {
        _mapState.value = _mapState.value.copy(circleCenter = latLng)
    }

    fun updateCircleRadiusKm(radiusKm: Float) {
        val radiusDiff = radiusKm / INITIAL_RADIUS_KM
        val newZoom = INITIAL_ZOOM - log2(radiusDiff)
        println("Radius: $radiusKm km, Zoom: $newZoom")
        _mapState.value = _mapState.value.copy(circleRadiusKm = radiusKm, zoom = newZoom)
    }
}

data class MapState(
    val circleCenter: LatLng? = null,
    val circleRadiusKm: Float = INITIAL_RADIUS_KM,
    val zoom: Float = INITIAL_ZOOM
)

private const val INITIAL_RADIUS_KM = 500f
private const val INITIAL_ZOOM = 5.9f




