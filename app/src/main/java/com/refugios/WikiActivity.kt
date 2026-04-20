package com.refugios

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import java.io.File

class WikiActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wiki)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)

        val wikiType = intent.getStringExtra("type") ?: "wikipedia"
        toolbar.title = if (wikiType == "wikipedia") "Wikipedia" else "WikiMed"

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
        }

        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
            }
        }

        loadWikiContent(wikiType)
    }

    private fun loadWikiContent(type: String) {
        val basePath = RefugioSApp.dataPath
        val zimFile = when (type) {
            "wikipedia" -> findFile(basePath, "wikipedia_es_all_maxi_2026-02")
            "wikimed" -> findFile(basePath, "wiktionary_es_all_2025-03")
            else -> null
        }

        if (zimFile != null) {
            val url = "file://${zimFile.absolutePath}"
            webView.loadUrl(url)
        } else {
            val kiwixDataPath = "$basePath/kiwix"
            val altFile = File(kiwixDataPath).listFiles()?.find {
                it.name.contains(type) && it.extension == "zim"
            }
            if (altFile != null) {
                webView.loadUrl("file://${altFile.absolutePath}")
            } else {
                Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_LONG).show()
                loadOfflineHtml(type)
            }
        }
    }

    private fun findFile(basePath: String, search: String): File? {
        val dir = File(basePath)
        if (!dir.exists()) return null

        return dir.walkTopDown().filter { it.extension == "zim" }.firstOrNull { file ->
            when (search) {
                "wikipedia_es_all_maxi_2026-02" -> file.name.contains("wikipedia_es_all_maxi")
                "wiktionary_es_all_2025-03" -> file.name.contains("wiktionary") && file.name.contains("_es_")
                else -> file.name.contains(search, ignoreCase = true)
            }
        }
    }

    private fun loadOfflineHtml(type: String) {
        val htmlPath = if (type == "wikipedia") {
            "file:///android_asset/wikipedia_offline.html"
        } else {
            "file:///android_asset/wikimed_offline.html"
        }
        webView.loadUrl(htmlPath)
    }

    override fun onBackPressed(): Boolean {
        return if (webView.canGoBack()) {
            webView.goBack()
            true
        } else {
            super.onBackPressed()
        }
    }
}