package com.vasberc.mapsexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.rememberCameraPositionState

class MapActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapScreen()
        }
    }
}

@Composable
fun MapScreen(viewModel: MapViewModel = viewModel()) {
    MaterialTheme {
        val mapState by viewModel.mapState.collectAsStateWithLifecycle()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Map takes up most of the space
                MapContent(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    circleCenter = mapState.circleCenter,
                    circleRadiusKm = mapState.circleRadiusKm,
                    zoom = mapState.zoom,
                    onMapClick = { latLng ->
                        viewModel.updateCircleCenter(latLng)
                    }
                )

                // Slider for circle radius at the bottom
                RadiusSlider(
                    radius = mapState.circleRadiusKm,
                    onRadiusChange = { newRadius ->
                        viewModel.updateCircleRadiusKm(newRadius)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun MapContent(
    modifier: Modifier = Modifier,
    circleCenter: LatLng?,
    circleRadiusKm: Float,
    zoom: Float,
    onMapClick: (LatLng) -> Unit
) {
    val defaultLocation = LatLng(20.0, 0.0)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, zoom)
    }

    // Update camera position when circle center or zoom changes
    LaunchedEffect(circleCenter, zoom) {
        if (circleCenter != null) {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(circleCenter, zoom),
                durationMs = 1000
            )
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        onMapClick = onMapClick
    ) {
        // Draw circle if a center location is set
        if (circleCenter != null) {
            // Convert km to meters for the circle
            val radiusMeters = circleRadiusKm * 1000

            Circle(
                center = circleCenter,
                radius = radiusMeters.toDouble(),
                strokeColor = Color.Blue,
                fillColor = Color.Blue.copy(alpha = 0.12f),
                strokeWidth = 2f
            )
        }
    }
}

@Composable
fun RadiusSlider(
    radius: Float,
    onRadiusChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Circle Radius: ${radius.toInt()} km",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Slider(
            value = radius,
            onValueChange = onRadiusChange,
            valueRange = 10f..2000f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}



