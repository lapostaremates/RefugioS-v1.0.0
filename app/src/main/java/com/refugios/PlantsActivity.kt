package com.refugios

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.refugios.data.MedicinalPlant
import com.refugios.data.PlantsDatabase
import com.refugios.data.PlantsAdapter

class PlantsActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var spinnerCategory: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var plantsAdapter: PlantsAdapter

    private var currentPlants: List<MedicinalPlant> = PlantsDatabase.plants

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plants)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.title = "Plantas Medicinales"
        toolbar.setNavigationOnClickListener { finish() }

        searchView = findViewById(R.id.searchView)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        recyclerView = findViewById(R.id.listPlants)

        setupCategorySpinner()
        setupSearch()
        setupList()
    }

    private fun setupCategorySpinner() {
        val categories = mutableListOf("Todas las categorías")
        categories.addAll(PlantsDatabase.getCategories())

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categories
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filterPlants()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterPlants()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterPlants()
                return true
            }
        })
    }

    private fun setupList() {
        plantsAdapter = PlantsAdapter(currentPlants) { plant ->
            val intent = Intent(this, PlantDetailActivity::class.java)
            intent.putExtra("plant_id", plant.id)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = plantsAdapter
    }

    private fun filterPlants() {
        val query = searchView.query?.toString() ?: ""
        val category = spinnerCategory.selectedItem?.toString() ?: "Todas las categorías"

        currentPlants = if (category == "Todas las categorías") {
            PlantsDatabase.searchPlants(query)
        } else {
            PlantsDatabase.searchPlants(query).filter {
                it.categoria.equals(category, ignoreCase = true)
            }
        }

        plantsAdapter.updateData(currentPlants)
    }
}