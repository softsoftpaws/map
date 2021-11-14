package com.example.myapplication.screens.listOfPlaces

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.place_data.PlaceDto
import com.example.myapplication.databinding.ItemPlaceBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private lateinit var binding: ItemPlaceBinding
    var onItemClick: ((PlaceDto) -> Unit)? = null

    override fun getItemCount() = placeList.size
    private var placeList = emptyList<PlaceDto>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(placeList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = placeList[position]
        binding.numberTextView.text = (position + 1).toString()
        binding.placeNameTextView.text = currentItem.object_name
        binding.placeTypeTextView.text = currentItem.object_type
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(placeDto: List<PlaceDto>) {
        this.placeList = placeDto
        notifyDataSetChanged() // адаптер рисует все элементы списка
    }
}
