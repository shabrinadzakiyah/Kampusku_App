package com.example.kampuskuapp.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kampuskuapp.R
import com.example.kampuskuapp.model.Mahasiswa


class MahasiswaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvNama: TextView = itemView.findViewById(R.id.tv_nama)

    fun bind(mahasiswa: Mahasiswa) {
        tvNama.text = mahasiswa.nama
    }
}
