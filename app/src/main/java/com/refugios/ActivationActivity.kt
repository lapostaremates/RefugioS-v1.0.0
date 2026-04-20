package com.refugios

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.refugios.data.LicenseManager

class ActivationActivity : AppCompatActivity() {

    private lateinit var licenseManager: LicenseManager
    private lateinit var txtDeviceId: TextView
    private lateinit var editActivationKey: EditText
    private lateinit var btnActivate: Button
    private lateinit var txtInstructions: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activation)

        licenseManager = LicenseManager(this)

        txtDeviceId = findViewById(R.id.txtDeviceId)
        editActivationKey = findViewById(R.id.editActivationKey)
        btnActivate = findViewById(R.id.btnActivate)
        txtInstructions = findViewById(R.id.txtInstructions)

        setupUI()
    }

    private fun setupUI() {
        val deviceId = licenseManager.getDeviceId()
        txtDeviceId.text = "ID del dispositivo: $deviceId"

        txtInstructions.text = """
            Para activar RefugioS:

            1. Copia el ID de dispositivo de arriba
            2. Contacta al administrador del sistema
            3. Ingresa la clave de activación proporcionada

            IMPORTANTE: Esta licencia está vinculada al dispositivo actual.
            No funcionará en otros teléfonos.
        """.trimIndent()

        btnActivate.setOnClickListener {
            val activationKey = editActivationKey.text.toString().trim()

            if (activationKey.isEmpty()) {
                Toast.makeText(this, "Ingresa la clave de activación", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (licenseManager.activate(activationKey)) {
                Toast.makeText(this, "¡Activación exitosa!", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Clave de activación inválida", Toast.LENGTH_SHORT).show()
            }
        }

        if (licenseManager.isLicensed()) {
            finish()
        }
    }

    override fun onBackPressed() {
        if (!licenseManager.isLicensed()) {
            Toast.makeText(this, "Debes activar la aplicación para usarla", Toast.LENGTH_LONG).show()
        } else {
            super.onBackPressed()
        }
    }
}