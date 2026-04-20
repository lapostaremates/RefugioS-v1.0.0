package com.refugios.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.refugios.R

class PdfAdapter(
    private val items: List<PdfItem>,
    private val onClick: (PdfItem) -> Unit
) : RecyclerView.Adapter<PdfAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.imgIcon)
        val name: TextView = view.findViewById(R.id.txtName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pdf, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        holder.icon.setImageResource(
            when (item.extension.lowercase()) {
                "pdf" -> R.drawable.ic_pdf
                "html", "htm" -> R.drawable.ic_html
                else -> R.drawable.ic_text
            }
        )
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = items.size
}