package com.abdulaziz.footballlive.ui.standing.match

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.footballlive.databinding.ItemMatchLeagueBinding
import com.abdulaziz.footballlive.models.fixtures.Match
import com.squareup.picasso.Picasso

class MatchLeagueAdapter(var list: List<Match>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<MatchLeagueAdapter.MVH>() {

    var date = ""

    inner class MVH(private val itemBinding: ItemMatchLeagueBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(match: Match) {
            itemBinding.apply {

                if (date.isEmpty()){
                    dateTv.visibility = View.VISIBLE
                    dateTv.text = match.event_date
                    date = match.event_date
                }else if (date!=match.event_date){
                    dateTv.visibility = View.VISIBLE
                    dateTv.text = match.event_date
                    date = match.event_date
                }


                Picasso.get().load(match.home_team_logo).into(homeTeamIv)
                Picasso.get().load(match.away_team_logo).into(awayTeamIv)
                homeTeamNameTv.text = match.event_home_team
                awayTeamNameTv.text = match.event_away_team
                matchResultTv.text = if (match.event_ft_result.isEmpty()) match.event_time
                else match.event_final_result

                homeTeamNameTv.setTextColor(Color.WHITE)
                awayTeamNameTv.setTextColor(Color.WHITE)
                matchResultTv.setTextColor(Color.WHITE)

                root.setOnClickListener {
                    listener.onItemClicked(match_id = match.event_key)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MVH {
        return MVH(ItemMatchLeagueBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(mlist: List<Match>) {
        list = mlist
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClicked(match_id: String)
    }
}