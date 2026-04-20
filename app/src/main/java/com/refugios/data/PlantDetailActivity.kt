package com.refugios.data

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.refugios.R
import com.refugios.data.PlantsDatabase

class PlantDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_detail)

        val plantId = intent.getIntExtra("plant_id", -1)
        val plant = PlantsDatabase.plants.find { it.id == plantId }

        if (plant != null) {
            findViewById<TextView>(R.id.txtNombre).text = plant.nombre
            findViewById<TextView>(R.id.txtNombreCientifico).text = plant.nombreCientifico
            findViewById<TextView>(R.id.txtCategoria).text = plant.categoria
            findViewById<TextView>(R.id.txtUso).text = "Usos: ${plant.uso}"
            findViewById<TextView>(R.id.txtParte).text = "Parte utilizada: ${plant.parteUtilizada}"
            findViewById<TextView>(R.id.txtPreparacion).text = "Preparación:\n${plant.preparacion}"
            findViewById<TextView>(R.id.txtContraindicaciones).text = "Contraindicaciones:\n${plant.contraindicaciones}"
        }
    }
}