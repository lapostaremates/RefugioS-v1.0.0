package com.refugios.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.refugios.R

class PlantsAdapter(
    private var plants: List<MedicinalPlant>,
    private val onClick: (MedicinalPlant) -> Unit
) : RecyclerView.Adapter<PlantsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombre)
        val txtNombreCientifico: TextView = view.findViewById(R.id.txtNombreCientifico)
        val txtCategoria: TextView = view.findViewById(R.id.txtCategoria)
        val txtUso: TextView = view.findViewById(R.id.txtUso)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = plants[position]
        holder.txtNombre.text = plant.nombre
        holder.txtNombreCientifico.text = plant.nombreCientifico
        holder.txtCategoria.text = plant.categoria
        holder.txtUso.text = plant.uso
        holder.itemView.setOnClickListener { onClick(plant) }
    }

    override fun getItemCount() = plants.size

    fun updateData(newPlants: List<MedicinalPlant>) {
        this.plants = newPlants
        notifyDataSetChanged()
    }
}