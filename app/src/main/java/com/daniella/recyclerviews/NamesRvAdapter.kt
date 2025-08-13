package com.daniella.recyclerviews

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class NamesRvAdapter (val names: List<String>):RecyclerView.Adapter<NamesViewHolder>(){
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamesViewHolder {
//        xml to view object ln 11 & 12

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.name_list_item, parent,false)
        return NamesViewHolder(itemView)
    }

//    How long my list is
    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: NamesViewHolder, position: Int) {
        holder.tvName.text = names[position]
    }

}

//Holds the view
class NamesViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView){
    val tvName = itemView.findViewById<TextView>(R.id.tvName)
}