# Zoom Calculation in Google Maps with Dynamic Circle Radius

## Problem
When displaying a circle on a Google Map with a dynamic radius controlled by a slider, the map's zoom level needs to automatically adjust to keep the entire circle visible on screen at all times.

## Solution: Logarithmic Zoom Calculation

The ViewModel automatically calculates an appropriate zoom level when the radius changes using a logarithmic approach:

```kotlin
fun updateCircleRadiusKm(radiusKm: Float) {
    val radiusDiff = radiusKm / INITIALS_RADIUS_KM  // Ratio of new radius to initial (500 km)
    val newZoom = INITIAL_ZOOM - log2(radiusDiff)    // Zoom inversely proportional to radius
    _mapState.value = _mapState.value.copy(circleRadiusKm = radiusKm, zoom = newZoom)
}
```

### Constants
- `INITIALS_RADIUS_KM = 500f` - Default/baseline radius
- `INITIAL_ZOOM = 5.9f` - Default zoom level at baseline radius

### How It Works
1. **Calculate the ratio** between the new radius and the baseline radius (500 km)
2. **Use logarithmic scale** (`log2`) to convert the ratio to zoom level changes
3. **Decrease zoom as radius increases** - This ensures larger circles are displayed with more zoom out, while smaller circles are displayed with more zoom in

### Examples
- **Default**: 500 km radius → zoom level 5.9
- **Double radius**: 1000 km radius → zoom decreases by ~1 level (≈ 4.9)
- **Half radius**: 250 km radius → zoom increases by ~1 level (≈ 6.9)

### Benefits
- ✅ Larger circles use lower zoom levels (zoomed out view)
- ✅ Smaller circles use higher zoom levels (zoomed in view)
- ✅ Smooth, visually balanced transitions between radius changes
- ✅ Entire circle remains visible on the map at all times
- ✅ Mathematical relationship ensures consistent visual scaling
