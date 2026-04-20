package com.refugios

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import java.io.File

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        val txtInfo = findViewById<TextView>(R.id.txtInfo)

        val info = buildString {
            appendLine("=== RefugioS - Configuración ===")
            appendLine()
            appendLine("Versión: 1.0.0")
            appendLine()
            appendLine("Ruta de datos USB:")
            appendLine(RefugioSApp.usbPath)
            appendLine()
            appendLine("Carpeta de datos:")
            appendLine(RefugioSApp.dataPath)
            appendLine()

            val dataDir = File(RefugioSApp.dataPath)
            if (dataDir.exists()) {
                appendLine("Archivos disponibles:")
                dataDir.listFiles()?.forEach { file ->
                    appendLine("  - ${file.name}")
                }
            }
        }

        txtInfo.text = info
    }
}