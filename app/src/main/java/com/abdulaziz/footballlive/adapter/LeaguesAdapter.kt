package com.abdulaziz.footballlive.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.footballlive.database.entity.League
import com.abdulaziz.footballlive.databinding.ItemLeagueBinding
import com.squareup.picasso.Picasso

class LeaguesAdapter(val listener:OnItemClickListener):RecyclerView.Adapter<LeaguesAdapter.LVH>() {

    private var list = arrayListOf<League>()

    inner class LVH(private val itemBinding:ItemLeagueBinding):RecyclerView.ViewHolder(itemBinding.root){
        fun onBind(league: League){
            itemBinding.apply {
                leagueTv.text = league.league_name
                countryTv.text = league.country_name
                if (league.country_logo!=null) Picasso.get().load(league.country_logo).into(imageView)
                else Picasso.get().load(league.league_logo).into(imageView)
                root.setOnClickListener {
                    listener.onItemClicked(league)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LVH {
        return LVH(ItemLeagueBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: LVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<League>?) {
        this.list = list as ArrayList<League>
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onItemClicked(league: League)
    }
}