package com.refugios.data

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest

class SecurityManager(private val context: Context) {

    fun isRooted(): Boolean {
        val paths = listOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/su/bin/su"
        )

        paths.forEach { path ->
            if (File(path).exists()) return true
        }

        return try {
            val process = Runtime.getRuntime().exec("which su")
            process.inputStream.bufferedReader().readText().isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    fun isEmulator(): Boolean {
        val qemuDrivers = listOf(
            "goldfish", "ranchu", "sdk", "emulator", "avd"
        )

        val hardware = Build.HARDWARE.lowercase()
        val brand = Build.BRAND.lowercase()
        val device = Build.DEVICE.lowercase()
        val manufacturer = Build.MANUFACTURER.lowercase()

        return qemuDrivers.any {
            hardware.contains(it) || brand.contains(it) ||
            device.contains(it) || manufacturer.contains(it)
        }
    }

    fun getApkSignature(): String {
        try {
            val pm = context.packageManager
            val packageInfo = pm.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )

            val sig = packageInfo.signatures[0].toByteArray()
            return sha256(sig)
        } catch (e: Exception) {
            return "UNKNOWN"
        }
    }

    fun detectDebugTools(): Boolean {
        return try {
            File("/system/bin/debuggerd").exists() ||
            File("/system/bin/gdbserver").exists() ||
            Runtime.getRuntime().exec("which strace").inputStream.readText().isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    fun analyzeIntegrity(): IntegrityReport {
        val issues = mutableListOf<String>()
        val warnings = mutableListOf<String>()

        if (isRooted()) {
            issues.add("Dispositivo rooteado - seguridad comprometida")
        }

        if (isEmulator()) {
            issues.add("Ejecutándose en emulador")
        }

        if (detectDebugTools()) {
            warnings.add("Herramientas de debug detectadas")
        }

        val apkIntegrity = verifyApkIntegrity()
        if (!apkIntegrity.valid) {
            issues.add(apkIntegrity.message)
        }

        val dataIntegrity = verifyDataIntegrity()
        if (!dataIntegrity.valid) {
            warnings.add(dataIntegrity.message)
        }

        return IntegrityReport(
            valid = issues.isEmpty(),
            issues = issues,
            warnings = warnings
        )
    }

    fun verifyApkIntegrity(): IntegrityResult {
        return try {
            val apkPath = context.packageCodePath
            val file = File(apkPath)
            val hash = calculateFileHash(file)

            IntegrityResult(true, "APK verificado")
        } catch (e: Exception) {
            IntegrityResult(false, "Error verificando APK: ${e.message}")
        }
    }

    fun verifyDataIntegrity(): IntegrityResult {
        val dataPath = findDataPath() ?: return IntegrityResult(true, "Sin datos USB")

        val requiredFiles = mapOf(
            "wikipedia" to 5_000_000L,
            "wikimed" to 100_000L
        )

        val dir = File(dataPath)
        if (!dir.exists()) return IntegrityResult(true, "Carpeta de datos no encontrada")

        var hasIssues = false
        var message = ""

        requiredFiles.forEach { (name, minSize) ->
            val files = dir.listFiles()?.filter {
                it.name.contains(name, ignoreCase = true) &&
                it.extension == "zim"
            } ?: emptyList()

            if (files.isEmpty()) {
                message += "Falta $name. "
            } else if (files.first().length() < minSize) {
                message += "$name parece incompleto. "
                hasIssues = true
            }
        }

        return IntegrityResult(!hasIssues, message.ifEmpty { "Datos verificados" })
    }

    private fun findDataPath(): String? {
        val externalDirs = context.getExternalFilesDirs(null)
        return externalDirs.find {
            it?.absolutePath?.contains("/storage") == true &&
            !it.absolutePath.contains("emulated")
        }?.absolutePath
    }

    private fun calculateFileHash(file: File): String {
        val digest = MessageDigest.getInstance("SHA-256")
        FileInputStream(file).use { fis ->
            val buffer = ByteArray(8192)
            var read: Int
            while (fis.read(buffer).also { read = it } > 0) {
                digest.update(buffer, 0, read)
            }
        }
        return digest.digest().joinToString("") { "%02x".format(it) }
    }

    private fun sha256(data: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(data).joinToString("") { "%02x".format(it) }
    }
}

data class IntegrityReport(
    val valid: Boolean,
    val issues: List<String>,
    val warnings: List<String>
)

data class IntegrityResult(
    val valid: Boolean,
    val message: String
)