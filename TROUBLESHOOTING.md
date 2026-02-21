## 🔧 Troubleshooting: Map Not Displaying

### ✅ Step 1: Add Google Maps API Key (REQUIRED)

The most common reason the map doesn't display is a missing or invalid API key.

1. **Get your API key:**
   - Go to [Google Cloud Console](https://console.cloud.google.com/)
   - Create a new project or select an existing one
   - Enable the Maps SDK for Android
   - Create an API key for Android
   - Add your app's package name and SHA-1 fingerprint

2. **Find your SHA-1 fingerprint:**
   ```bash
   cd /home/vasberc/AndroidStudioProjects/Mapsexample
   ./gradlew signingReport
   ```
   Look for the SHA1 value in the output.

3. **Add the API key to AndroidManifest.xml:**
   ```xml
   <meta-data
       android:name="com.google.android.geo.API_KEY"
       android:value="YOUR_ACTUAL_API_KEY_HERE" />
   ```
   Replace `YOUR_ACTUAL_API_KEY_HERE` with your real API key.

---

### ✅ Step 2: Grant Location Permissions (Runtime)

For Android 6.0+, you need runtime permissions. The app requests them in the manifest, but you need to ensure they're granted on your device/emulator.

**On the device:**
- Install the app
- Go to Settings → Apps → Maps Example
- Permissions → Grant Location access

Or programmatically in your activity:

```kotlin
// Add this to MapActivity if needed
private fun requestLocationPermissions() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            REQUEST_LOCATION_PERMISSION_CODE
        )
    }
}

companion object {
    private const val REQUEST_LOCATION_PERMISSION_CODE = 100
}
```

---

### ✅ Step 3: Verify Internet Connection

The map requires internet to download tiles and data.
- Ensure your device/emulator has internet connectivity
- Check WiFi or mobile data is enabled

---

### ✅ Step 4: Check Emulator Configuration

If using Android Emulator:
1. Make sure you're using an emulator with Google Play Services
2. Try using a physical device instead (more reliable for maps)
3. Ensure Google Play Services is up to date on the emulator

---

### ✅ Step 5: Verify Build Configuration

Ensure your build completed successfully:
```bash
cd /home/vasberc/AndroidStudioProjects/Mapsexample
./gradlew clean build
```

---

### 🐛 Debug Checklist

- [ ] Google Maps API key added to AndroidManifest.xml
- [ ] API key is valid and enabled for Maps SDK for Android
- [ ] App's SHA-1 fingerprint matches the one in Google Cloud Console
- [ ] Location permissions granted on device
- [ ] Internet connection available
- [ ] Using Android 5.0 (API 21) or higher
- [ ] Building and running debug APK (not release)

---

### 📱 Testing the App

1. **Build and run:**
   ```bash
   cd /home/vasberc/AndroidStudioProjects/Mapsexample
   ./gradlew installDebug
   adb shell am start -n com.vasberc.mapsexample/.MapActivity
   ```

2. **Check Android Logs:**
   ```bash
   adb logcat | grep -E "Maps|MapsExample|MapActivity|GoogleMaps"
   ```

3. **Expected behavior:**
   - App launches with a map showing
   - Default view centered around equator (latitude 20, longitude 0) with zoom level 4
   - You can tap on the map to place a circle
   - Slider below controls circle radius (10-2000 km)
   - Circle displays in blue with semi-transparent fill

---

### ⚠️ Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| Blank gray screen | Missing/invalid API key |
| "You are missing a Google Maps API key" message | Add API key to manifest |
| Black screen | Permissions not granted or no internet |
| Map shows but no interaction | Try tapping the map - it might be loading |
| Circle not visible | Make sure you tapped on the map to place it |

---

### 💡 Need More Help?

Check the official documentation:
- [Google Maps Android SDK](https://developers.google.com/maps/documentation/android-sdk)
- [Get Started with Google Maps Platform](https://developers.google.com/maps/gmp-get-started)

