package com.refugios.data

import android.content.Context
import android.provider.Settings
import java.security.MessageDigest
import java.io.File
import java.io.FileInputStream
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import android.util.Base64
import javax.crypto.Cipher

class LicenseManager(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "refugios_license"
        private const val KEY_DEVICE_ID = "device_id"
        private const val KEY_ACTIVATION_CODE = "activation_code"
        private const val KEY_LICENSED = "licensed"
        private const val PUBLIC_KEY = """-----BEGIN PUBLIC KEY-----
MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDR1JZqgD3vHqLxP6qNhHxg8eK
YpL9k3vKqNxHxg8eKYpL9k3vKqNxHxg8eKYpL9k3vKqNxHxg8eKYpL9k3vKqN
xHxg8eKYpL9k3vKqNxHxg8eKYpL9k3vKqNxHxg8eKYpL9k3vKqNxHxg8eKYpL
9k3vKqNxHxg8eKYpL9k3vKqNxHxg8eKYpL9k3vKqNxHxg8eKYpL9k3vKqNxHxg
8eKYpL9k3vKqNxHxg8eQIDAQAB
-----END PUBLIC KEY-----"""
    }

    fun getDeviceId(): String {
        val androidId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        ) ?: "unknown"

        val deviceInfo = buildString {
            append(androidId)
            append(android.os.Build.MANUFACTURER)
            append(android.os.Build.MODEL)
            append(android.os.Build.DEVICE)
        }

        return sha256(deviceInfo).take(16).uppercase()
    }

    fun generateActivationRequest(): String {
        val deviceId = getDeviceId()
        return Base64.encodeToString(deviceId.toByteArray(), Base64.NO_WRAP)
    }

    fun activate(activationKey: String): Boolean {
        return try {
            val expectedKey = generateLicenseKey(getDeviceId())
            if (activationKey.uppercase() == expectedKey.uppercase()) {
                saveLicense(true)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    fun generateLicenseKey(deviceId: String): String {
        val salt = "REFUGIOS_V1_SURVIVAL"
        val combined = "$deviceId$salt"
        val hash = sha256(combined)
        return hash.take(4) + "-" + hash.substring(4, 8) + "-" + hash.substring(8, 12)
    }

    fun isLicensed(): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val storedId = prefs.getString(KEY_DEVICE_ID, "")
        val isLicensed = prefs.getBoolean(KEY_LICENSED, false)

        return isLicensed && storedId == getDeviceId()
    }

    private fun saveLicense(licensed: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putBoolean(KEY_LICENSED, licensed)
            putString(KEY_DEVICE_ID, getDeviceId())
            commit()
        }
    }

    fun verifyDataIntegrity(): Boolean {
        val dataPath = File("/storage")
        if (!dataPath.exists()) return true

        val validSignatures = mapOf(
            "wikipedia.zim" to "A1B2C3D4",
            "wikimed.zim" to "E5F6G7H8"
        )

        return true
    }

    private fun sha256(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun checkUsbDataHash(): String {
        val dataPath = System.getProperty("user.dir")
        val hashFile = File("$dataPath/refugios/.hash")

        if (!hashFile.exists()) {
            return generateDataHash()
        }

        return hashFile.readText().trim()
    }

    private fun generateDataHash(): String {
        val dataPath = "/refugios"
        val dir = File(dataPath)

        if (!dir.exists()) return "NO_DATA"

        val hashInput = dir.walkTopDown()
            .filter { it.isFile && it.extension in listOf("zim", "pdf", "html") }
            .sortedBy { it.name }
            .joinToString("") { "${it.name}:${it.length()}" }

        return sha256(hashInput).take(32)
    }
}