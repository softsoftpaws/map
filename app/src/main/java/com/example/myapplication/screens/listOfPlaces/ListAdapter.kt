package com.example.myapplication.screens.listOfPlaces

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.placeData.Place

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    override fun getItemCount() = placeList.size

    private var placeList = emptyList<Place>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = placeList[position]
        holder.itemView.findViewById<TextView>(R.id.id_txt).text = currentItem.id.toString()
        holder.itemView.findViewById<TextView>(R.id.placeName_txt).text = currentItem.object_type
        holder.itemView.findViewById<TextView>(R.id.placeType_txt).text = currentItem.object_name
    }

    fun setData(place: List<Place>) {
        this.placeList = place
        notifyDataSetChanged() // адаптер рисует все элементы списка
    }
}
