# 🗺️ Maps Example - Setup Guide

## Quick Start

### Prerequisites
- Android Studio Flamingo or later
- Android SDK 26 or higher
- Google Cloud account with billing enabled

---

## 📋 Step-by-Step Setup

### Step 1: Get Your Google Maps API Key

1. Open [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select existing one
3. Enable the Maps SDK for Android:
   - Go to APIs & Services → Library
   - Search for "Maps SDK for Android"
   - Click "Enable"
4. Create credentials:
   - Go to APIs & Services → Credentials
   - Click "Create Credentials" → API Key
   - Copy the API key

### Step 2: Get Your App's SHA-1 Fingerprint

Run this command in the project root:

```bash
./gradlew signingReport
```

Look for the **SHA1** value (it looks like: `AA:BB:CC:DD:...`)

### Step 3: Configure API Key in Google Cloud

1. In Google Cloud Console, select your API key
2. Under "Key restrictions":
   - Choose "Android apps"
   - Click "Add an item"
   - Paste your SHA-1 fingerprint
   - Enter package name: `com.vasberc.mapsexample`
3. Save changes (wait ~5 minutes for propagation)

### Step 4: Add API Key to Your App

Edit `/app/src/main/AndroidManifest.xml`:

Find this line:
```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_GOOGLE_MAPS_API_KEY_HERE" />
```

Replace `YOUR_GOOGLE_MAPS_API_KEY_HERE` with your actual API key.

### Step 5: Build and Run

```bash
# Clean build
./gradlew clean

# Build debug APK
./gradlew build

# Install and run
./gradlew installDebug
adb shell am start -n com.vasberc.mapsexample/.MapActivity
```

---

## 🎮 Using the App

1. **Initial View**: Map displays centered at equator with zoom level 4
2. **Place a Circle**: Tap anywhere on the map
3. **Adjust Radius**: Use the slider at the bottom to change circle size (10-2000 km)
4. **Update Circle**: Tap on a new location to move the circle

---

## 📁 Project Structure

```
Mapsexample/
├── app/
│   ├── build.gradle.kts              # Dependencies configured here
│   └── src/main/
│       ├── AndroidManifest.xml       # Add API key here
│       ├── java/com/vasberc/mapsexample/
│       │   ├── MapActivity.kt        # Main UI (Compose)
│       │   └── MapViewModel.kt       # State management
│       └── res/                      # Resources
├── gradle/libs.versions.toml         # Version catalog
└── README.md                         # Overview
```

---

## 🔍 Architecture Overview

### State Hoisting
- All state is stored in `MapViewModel`
- `MapScreen` composable receives state from ViewModel
- `MapContent` and `RadiusSlider` are stateless - receive state via parameters

### Key Components
- **MapViewModel**: Manages circle center and radius
- **MapScreen**: Root composable with theme and layout
- **MapContent**: Displays Google Map with circle
- **RadiusSlider**: UI for controlling circle radius

### Data Flow
```
MapActivity (onCreate)
    ↓
MapScreen (uses MapViewModel)
    ├→ MapContent (receives state, handles clicks)
    └→ RadiusSlider (receives state, handles changes)
         ↓
    MapViewModel (stores & updates state)
```

---

## ⚙️ Configuration

### Map Zoom Level
Modify in `MapContent`:
```kotlin
CameraPosition.fromLatLngZoom(location, zoomLevel)
// zoomLevel: 1-21 (lower = zoomed out, higher = zoomed in)
```

### Circle Radius Range
Modify in `RadiusSlider`:
```kotlin
Slider(
    valueRange = 10f..2000f,  // Change min and max km here
    ...
)
```

### Circle Colors
Modify in `MapContent`:
```kotlin
Circle(
    strokeColor = Color.Blue,           // Change outline color
    fillColor = Color.Blue.copy(alpha = 0.12f),  // Change fill color
    strokeWidth = 2f                    // Change outline width
)
```

---

## ✅ Verification Checklist

Before running the app, ensure:

- [ ] Google Maps API key obtained
- [ ] API key added to AndroidManifest.xml
- [ ] SHA-1 fingerprint registered with API key
- [ ] Package name `com.vasberc.mapsexample` registered
- [ ] Maps SDK for Android enabled in Google Cloud Console
- [ ] Device/Emulator has internet connection
- [ ] Location permissions enabled on device
- [ ] Using Android 5.0 (API 21) or higher

---

## 🆘 Troubleshooting

If the map doesn't display:

1. **Check logcat for errors:**
   ```bash
   adb logcat | grep -i "maps\|googlemap\|error"
   ```

2. **Verify API key:**
   - Ensure it's correctly formatted
   - Check it's enabled in Google Cloud Console
   - Wait 5 minutes after creation/modification

3. **Check permissions:**
   - Settings → Apps → Maps Example → Permissions
   - Enable "Location"

4. **Try a physical device:**
   - Emulator sometimes has issues with Google Play Services
   - Physical device usually works better

See `TROUBLESHOOTING.md` for detailed troubleshooting steps.

---

## 📚 Resources

- [Google Maps Android SDK Documentation](https://developers.google.com/maps/documentation/android-sdk)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Android ViewModel Guide](https://developer.android.com/guide/components/viewmodels)
- [State Hoisting in Compose](https://developer.android.com/jetpack/compose/state)

