package com.example.sportsdbapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsdbapp.R
import com.example.sportsdbapp.model.Sport
import com.example.sportsdbapp.util.getImage
import com.example.sportsdbapp.util.makePlaceholder
import com.example.sportsdbapp.view.SportsListFragmentDirections
import kotlinx.android.synthetic.main.sports_recycler_row.view.*

class SportRecyclerAdapter(val sportList : ArrayList<Sport>) : RecyclerView.Adapter<SportRecyclerAdapter.SportViewHolder>() {
    class SportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.sports_recycler_row,parent,false)
        return SportViewHolder(view)
    }

    override fun onBindViewHolder(holder: SportViewHolder, position: Int) {
        holder.itemView.Name.text = sportList.get(position).sportName
        holder.itemView.Format.text = sportList.get(position).sportFormat


        holder.itemView.setOnClickListener {
            val action = SportsListFragmentDirections.actionSportsListFragmentToSportDetailFragment(sportList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.imageView.getImage(sportList.get(position).sportImage, makePlaceholder(holder.itemView.context))
    }

    override fun getItemCount(): Int {
        return sportList.size
    }

    fun updateSportList(newSportList: List<Sport>) {
        sportList.clear()
        sportList.addAll(newSportList)
        notifyDataSetChanged()
    }
}