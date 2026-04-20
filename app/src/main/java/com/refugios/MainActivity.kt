package com.refugios

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.refugios.data.LicenseManager
import com.refugios.data.MenuItem
import com.refugios.data.MenuAdapter
import com.refugios.data.SecurityManager
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var securityManager: SecurityManager
    private lateinit var licenseManager: LicenseManager
    private var selectedUsbPath: String = ""

    private val usbPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }
        if (allGranted) {
            findUsbStorage()
        } else {
            Toast.makeText(this, "Se necesita permiso USB", Toast.LENGTH_SHORT).show()
        }
    }

    private val manageStorageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                findUsbStorage()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        securityManager = SecurityManager(this)
        licenseManager = LicenseManager(this)

        checkSecurity()

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.title = "RefugioS - Base de Supervivencia"

        recyclerView = findViewById(R.id.recyclerMenu)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val menuItems = listOf(
            MenuItem("wikipedia", getString(R.string.menu_wikipedia), R.drawable.ic_wikipedia),
            MenuItem("wikimed", getString(R.string.menu_wikimed), R.drawable.ic_medical),
            MenuItem("survival", getString(R.string.menu_survival), R.drawable.ic_survival),
            MenuItem("maps", getString(R.string.menu_maps), R.drawable.ic_map),
            MenuItem("plants", "Plantas", R.drawable.ic_plants),
            MenuItem("guides", getString(R.string.menu_guides), R.drawable.ic_guides),
            MenuItem("settings", getString(R.string.menu_settings), R.drawable.ic_settings)
        )

        recyclerView.adapter = MenuAdapter(menuItems) { item ->
            onMenuClick(item)
        }

        checkPermissions()
    }

    private fun checkSecurity() {
        val report = securityManager.analyzeIntegrity()

        if (!report.valid) {
            Toast.makeText(this, "Advertencia: ${report.issues.first()}", Toast.LENGTH_LONG).show()
        }

        if (!licenseManager.isLicensed()) {
            startActivity(Intent(this, ActivationActivity::class.java))
            finish()
            return
        }
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.data = Uri.parse("package:$packageName")
                    manageStorageLauncher.launch(intent)
                } catch (e: Exception) {
                    val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                    manageStorageLauncher.launch(intent)
                }
            } else {
                findUsbStorage()
            }
        } else {
            usbPermissionLauncher.launch(arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).toTypedArray())
        }
    }

    private fun findUsbStorage() {
        val externalDirs = getExternalFilesDirs(null)
        val usbDir = externalDirs.find {
            it.absolutePath.contains("/storage") &&
            !it.absolutePath.contains("emulated") &&
            it.canRead()
        }

        if (usbDir != null) {
            selectedUsbPath = usbDir.absolutePath
            RefugioSApp.usbPath = selectedUsbPath
            RefugioSApp.dataPath = "$selectedUsbPath/refugios"
            File(RefugioSApp.dataPath).mkdirs()
            Toast.makeText(this, getString(R.string.usb_connected, selectedUsbPath), Toast.LENGTH_SHORT).show()
        } else {
            searchManualUsb()
        }
    }

    private fun searchManualUsb() {
        val storage = File("/storage")
        if (storage.exists()) {
            storage.listFiles()?.forEach { file ->
                if (file.canRead() && file.isDirectory) {
                    val refugiosFolder = File(file, "refugios")
                    if (refugiosFolder.exists() || file.listFiles()?.any { it.name.startsWith("wikipedia") } == true) {
                        selectedUsbPath = file.absolutePath
                        RefugioSApp.usbPath = selectedUsbPath
                        RefugioSApp.dataPath = selectedUsbPath
                        Toast.makeText(this, getString(R.string.usb_connected, selectedUsbPath), Toast.LENGTH_SHORT).show()
                        return
                    }
                }
            }
        }
        Toast.makeText(this, getString(R.string.no_usb), Toast.LENGTH_LONG).show()
    }

    private fun onMenuClick(item: MenuItem) {
        if (RefugioSApp.usbPath.isEmpty()) {
            Toast.makeText(this, getString(R.string.no_usb), Toast.LENGTH_SHORT).show()
            return
        }

        when (item.id) {
            "wikipedia", "wikimed" -> {
                startActivity(Intent(this, WikiActivity::class.java).putExtra("type", item.id))
            }
            "maps" -> {
                startActivity(Intent(this, MapActivity::class.java))
            }
            "plants" -> {
                startActivity(Intent(this, PlantsActivity::class.java))
            }
            "survival", "guides" -> {
                startActivity(Intent(this, PdfActivity::class.java).putExtra("folder", item.id))
            }
            "settings" -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
    }
}