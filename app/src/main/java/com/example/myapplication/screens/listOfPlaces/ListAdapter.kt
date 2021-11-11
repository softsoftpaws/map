package com.example.myapplication.screens.listOfPlaces

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.placeData.Place

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {


    var onItemClick: ((Place) -> Unit)? = null

//    private lateinit var mListener:onItemClickListener
//
    override fun getItemCount() = placeList.size
    private var placeList = emptyList<Place>()

//    interface onItemClickListener{
//        fun onItemClick(position:Int)
//    }


//    fun setOnItemClickListener(listener: onItemClickListener){
//        mListener = listener
//    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(placeList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = placeList[position]

        holder.itemView.findViewById<TextView>(R.id.id_txt).text = (position+1).toString()
        holder.itemView.findViewById<TextView>(R.id.placeName_txt).text = currentItem.object_name
        holder.itemView.findViewById<TextView>(R.id.placeType_txt).text = currentItem.object_type

    }

    fun setData(place: List<Place>) {
        this.placeList = place
        notifyDataSetChanged() // адаптер рисует все элементы списка
    }
}





