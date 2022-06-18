package com.abdulaziz.footballlive.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.footballlive.databinding.ItemMatchBinding
import com.abdulaziz.footballlive.models.fixtures.Match
import com.squareup.picasso.Picasso

class MatchAdapter(var list: List<Match>, val listener:OnItemClickListener):RecyclerView.Adapter<MatchAdapter.MVH>() {

    inner class MVH(private val itemBinding:ItemMatchBinding):RecyclerView.ViewHolder(itemBinding.root){
        fun onBind(match: Match){
            itemBinding.apply {
                Picasso.get().load(match.home_team_logo).into(homeTeamIv)
                Picasso.get().load(match.away_team_logo).into(awayTeamIv)
                homeTeamNameTv.text = match.event_home_team
                awayTeamNameTv.text = match.event_away_team
                matchResultTv.text = if (match.event_final_result.isEmpty() || match.event_final_result=="-") match.event_time
                else match.event_final_result

                if(match.event_status.isNotEmpty() && match.event_status != "Finished"){
                    liveTv.visibility = View.VISIBLE
                    liveTv.text = match.event_status
                }

                root.setOnClickListener {
                    listener.onItemClicked(match_id = match.event_key)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MVH {
        return MVH(ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size


    interface OnItemClickListener{
        fun onItemClicked(match_id:String)
    }
}