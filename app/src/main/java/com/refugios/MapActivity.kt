package com.refugios

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.appbar.MaterialToolbar
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.io.File

class MapActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private lateinit var locationOverlay: MyLocationNewOverlay
    private var currentMapSource = "MAPNIK"

    data class SurvivalPoint(
        val name: String,
        val lat: Double,
        val lon: Double,
        val type: String,
        val description: String = ""
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        Configuration.getInstance().userAgentValue = packageName

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.maps_title)
        toolbar.setNavigationOnClickListener { finish() }

        mapView = findViewById(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(5.0)

        setupLocation()
        loadOfflineMaps()
    }

    private fun setupLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mapView)
            locationOverlay.enableMyLocation()
            mapView.overlays.add(locationOverlay)
        }
    }

    private fun loadOfflineMaps() {
        val basePath = RefugioSApp.dataPath
        val mapsPath = "$basePath/maps"

        var mapsLoaded = false

        if (File(mapsPath).exists()) {
            val obfFiles = File(mapsPath).listFiles()?.filter {
                it.extension == "obf" || it.extension == "ozf2"
            }

            if (obfFiles != null && obfFiles.isNotEmpty()) {
                obfFiles.forEach { file ->
                    Toast.makeText(this, getString(R.string.map_offline_loaded), Toast.LENGTH_SHORT).show()
                }
                mapsLoaded = true
            }
        }

        val offlineTiles = findOfflineTiles()
        if (offlineTiles.isNotEmpty()) {
            mapView.tileProvider.clearCache()
            offlineTiles.forEach { path ->
                Configuration.getInstance().osmdroidBasePath = File(path).parentFile
            }
            mapsLoaded = true
        }

        if (!mapsLoaded) {
            Toast.makeText(this, getString(R.string.map_online_mode), Toast.LENGTH_LONG).show()
        }

        mapView.controller.setCenter(GeoPoint(40.0, -3.0))
        addSurvivalMarkers()
    }

    private fun findOfflineTiles(): List<String> {
        val basePath = RefugioSApp.dataPath
        val tilesPaths = mutableListOf<String>()

        File(basePath).walkTopDown().forEach { file ->
            if (file.isDirectory && file.name == "tiles") {
                tilesPaths.add(file.absolutePath)
            }
        }

        return tilesPaths
    }

    private fun addSurvivalMarkers() {
        val survivalPoints = listOf(
            SurvivalPoint("Hospital", 40.4168, -3.7038, "hospital", "Hospital público más cercano"),
            SurvivalPoint("Hospital", 40.4300, -3.7000, "hospital", "Hospital privado"),
            SurvivalPoint("Farmacia", 40.4200, -3.7000, "pharmacy", "Farmacia de guardia"),
            SurvivalPoint("Policía", 40.4150, -3.7050, "police", "Comisaría"),
            SurvivalPoint("Estación de tren", 40.4070, -3.6918, "train", "Estación central"),
            SurvivalPoint("Aeropuerto", 40.4980, -3.5678, "airport", "Aeropuerto internacional"),
            SurvivalPoint("Embajada", 40.4230, -3.6880, "embassy", "Embajada más cercana"),
            SurvivalPoint("Punto de agua", 40.4180, -3.7100, "water", "Fuente pública"),
            SurvivalPoint("Gasolinera", 40.4100, -3.7200, "fuel", "Estación de servicio"),
            SurvivalPoint("Supermercado", 40.4250, -3.6950, "food", "Tienda de alimentos")
        )

        survivalPoints.forEach { point ->
            addMarker(point.lat, point.lon, point.name, point.type, point.description)
        }

        mapView.invalidate()
    }

    private fun addMarker(lat: Double, lon: Double, title: String, type: String, description: String) {
        val marker = Marker(mapView)
        marker.position = GeoPoint(lat, lon)
        marker.title = title
        marker.snippet = description
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

        mapView.overlays.add(marker)
    }

    fun centerOnLocation() {
        if (::locationOverlay.isInitialized && locationOverlay.myLocation != null) {
            mapView.controller.animateTo(locationOverlay.myLocation)
            mapView.controller.setZoom(15.0)
        } else {
            Toast.makeText(this, getString(R.string.location_not_available), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.map_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_satellite -> {
                mapView.setTileSource(TileSourceFactory.USGS_SAT)
                currentMapSource = "SATELLITE"
                true
            }
            R.id.action_normal -> {
                mapView.setTileSource(TileSourceFactory.MAPNIK)
                currentMapSource = "MAPNIK"
                true
            }
            R.id.action_terrain -> {
                mapView.setTileSource(TileSourceFactory.USGS_TOPO)
                true
            }
            R.id.action_location -> {
                centerOnLocation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::locationOverlay.isInitialized) {
            locationOverlay.disableMyLocation()
        }
    }
}