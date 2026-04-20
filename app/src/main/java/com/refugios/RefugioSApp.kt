package com.refugios

import android.app.Application
import android.os.Environment
import org.osmdroid.config.Configuration

class RefugioSApp : Application() {
    companion object {
        var dataPath: String = ""
        var usbPath: String = ""
    }

    override fun onCreate() {
        super.onCreate()
        Configuration.getInstance().userAgentValue = packageName
        dataPath = getExternalFilesDir(null)?.absolutePath ?: filesDir.absolutePath
    }
}