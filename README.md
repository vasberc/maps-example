# Maps Example - Circle with Radius Slider

## Overview
This Android application demonstrates a Google Maps activity built with Jetpack Compose that allows users to:
- Tap on the map to place a circle at that location
- Adjust the circle's radius using a slider (default: 500 km, range: 10-2000 km)

## Architecture

### State Management
- **ViewModel Pattern**: Uses `MapViewModel` to manage and preserve state
- **State Hoisting**: All state is hoisted to the ViewModel and accessed through the Composable layer
- The ViewModel provides:
  - `circleCenter: LatLng?` - The tapped location for the circle
  - `circleRadiusKm: Float` - The current radius in kilometers
  - `updateCircleCenter(LatLng)` - Updates the circle center
  - `updateCircleRadiusKm(Float)` - Updates the circle radius

### Composable Structure

**MapActivity.kt** - Main activity containing:
- `MapScreen()` - Root composable that applies Material theme and manages layout
- `MapContent()` - Handles the Google Map display and circle rendering
- `RadiusSlider()` - UI component for adjusting circle radius

The layout uses a Column with:
- Map taking up 80% of the screen (weight = 1f)
- Slider taking up 20% of the screen at the bottom

### Key Features
1. **Touch Interaction**: Tap anywhere on the map to set the circle center
2. **Dynamic Circle**: Circle appears with blue outline and semi-transparent blue fill
3. **Radius Control**: Slider updates circle radius in real-time
4. **State Persistence**: Circle position and radius are preserved across recompositions

## Dependencies Added
- Jetpack Compose UI & Material3
- Google Maps Compose
- Google Play Services Maps
- Jetpack Lifecycle ViewModel Compose

## Setup Required
Before running the app, add your Google Maps API key to `AndroidManifest.xml`:

```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_GOOGLE_MAPS_API_KEY_HERE" />
```

You can obtain an API key from the [Google Cloud Console](https://console.cloud.google.com/).

## Permissions
The app requires the following permissions:
- `INTERNET` - For map data
- `ACCESS_FINE_LOCATION` - For precise location access
- `ACCESS_COARSE_LOCATION` - For approximate location access

Note: On Android 6.0+, runtime permissions must be requested from the user.

# maps-example
