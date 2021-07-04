package ru.agafonovilya.mapapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.agafonovilya.mapapp.R
import ru.agafonovilya.mapapp.databinding.MarkerItemBinding
import ru.agafonovilya.mapapp.model.markersDataBase.MyMarker

class MarkerListAdapter(private val onItemClickListener: (MyMarker) -> Unit) :
    RecyclerView.Adapter<MarkerListAdapter.MarkerViewHolder>() {

    var markersList: List<MyMarker> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkerViewHolder {
        return MarkerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.marker_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MarkerViewHolder, position: Int) {
        holder.bind(markersList[position])
    }

    override fun getItemCount(): Int = markersList.size

    inner class MarkerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = MarkerItemBinding.bind(itemView)

        fun bind(myMarker: MyMarker) {
            binding.markerTitle.text = myMarker.title
            binding.markerDescription.text = myMarker.description
            binding.markerLatitude.text = myMarker.latitude.toString()
            binding.markerLongitude.text = myMarker.longitude.toString()

            itemView.setOnClickListener { onItemClickListener(myMarker) }
        }
    }
}