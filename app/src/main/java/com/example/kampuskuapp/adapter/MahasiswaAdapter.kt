package com.example.kampuskuapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kampuskuapp.R
import com.example.kampuskuapp.model.Mahasiswa

class MahasiswaAdapter(private val data: List<Mahasiswa>) : RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder>() {

    inner class MahasiswaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tv_nama)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val mahasiswa = data[position]
                    showOptionsDialog(itemView.context, mahasiswa)
                }
            }
        }
    }

    private fun showOptionsDialog(context: Context, mahasiswa: Mahasiswa) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Pilihan untuk ${mahasiswa.nama}")
            .setItems(arrayOf("Lihat Data", "Update Data", "Hapus Data")) { dialog, which ->
                when (which) {
                    0 -> {
                        // Navigasi ke halaman detail mahasiswa
                        Toast.makeText(context, "Lihat Data ${mahasiswa.nama}", Toast.LENGTH_SHORT).show()
                    }
                    1 -> {
                        // Buka formulir edit mahasiswa
                        Toast.makeText(context, "Update Data ${mahasiswa.nama}", Toast.LENGTH_SHORT).show()
                    }
                    2 -> {
                        // Hapus data mahasiswa
                        Toast.makeText(context, "Hapus Data ${mahasiswa.nama}", Toast.LENGTH_SHORT).show()
                    }
                }
                dialog.dismiss()
            }
            .show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MahasiswaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mahasiswa, parent, false)
        return MahasiswaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MahasiswaViewHolder, position: Int) {
        val mahasiswa = data[position]
        holder.tvNama.text = mahasiswa.nama
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
