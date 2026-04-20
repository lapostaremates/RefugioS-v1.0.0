package com.refugios

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.refugios.data.PdfItem
import com.refugios.data.PdfAdapter
import java.io.File
import java.io.FileFilter

class PdfActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        recyclerView = findViewById(R.id.recyclerPdfs)
        progressBar = findViewById(R.id.progressBar)

        val folderType = intent.getStringExtra("folder") ?: "guides"
        toolbar.title = when (folderType) {
            "survival" -> "Guía de Supervivencia"
            else -> "Guías y Manuales"
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        loadPdfs(folderType)
    }

    private fun loadPdfs(folderType: String) {
        val basePath = RefugioSApp.dataPath
        val searchFolder = when (folderType) {
            "survival" -> listOf("survival", "supervivencia", " Sobreviv", "emergency")
            else -> listOf("guides", "manuales", "guias", "docs")
        }

        val pdfFiles = mutableListOf<File>()
        val baseDir = File(basePath)

        if (baseDir.exists()) {
            baseDir.walkTopDown().filter {
                it.extension.lowercase() in listOf("pdf", "html", "htm", "txt", "md")
            }.forEach { file ->
                val name = file.name.lowercase()
                if (searchFolder.any { search -> name.contains(search.lowercase()) }) {
                    pdfFiles.add(file)
                }
            }
        }

        if (pdfFiles.isEmpty()) {
            baseDir.walkTopDown().filter {
                it.extension.lowercase() in listOf("pdf", "html", "htm")
            }.take(50).forEach { pdfFiles.add(it) }
        }

        if (pdfFiles.isEmpty()) {
            Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_LONG).show()
        }

        val items = pdfFiles.map { PdfItem(it.name, it.absolutePath, it.extension) }
        recyclerView.adapter = PdfAdapter(items) { item ->
            openPdf(item)
        }

        progressBar.visibility = View.GONE
    }

    private fun openPdf(item: PdfItem) {
        if (item.extension == "pdf") {
            try {
                val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
                intent.setDataAndType(android.net.Uri.fromFile(File(item.path)), "application/pdf")
                intent.flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Necesitas un visor PDF", Toast.LENGTH_SHORT).show()
            }
        } else {
            val intent = android.content.Intent(this, ViewerActivity::class.java)
            intent.putExtra("path", item.path)
            startActivity(intent)
        }
    }
}