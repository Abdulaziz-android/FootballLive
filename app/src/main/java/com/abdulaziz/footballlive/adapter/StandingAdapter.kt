package com.abdulaziz.footballlive.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.footballlive.databinding.ItemStandingBinding
import com.abdulaziz.footballlive.models.standing.Away
import com.abdulaziz.footballlive.models.standing.Home
import com.abdulaziz.footballlive.models.standing.Total

class StandingAdapter(val listener:OnTeamClickListener) : RecyclerView.Adapter<StandingAdapter.SVH>() {

    private var totalList = arrayListOf<Total>()
    private var homeList = arrayListOf<Home>()
    private var awayList = arrayListOf<Away>()
    private var tabPosition = 0

    inner class SVH(private val itemBinding: ItemStandingBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBindTotal(total: Total) {
            itemBinding.apply {
                positionTv.text = total.standing_place
                teamNameTv.text = total.standing_team
                standingP.text = total.standing_P
                standingW.text = total.standing_W
                standingD.text = total.standing_D
                standingL.text = total.standing_L
                standingGd.text = total.standing_GD
                standingPts.text = total.standing_PTS
                root.setOnClickListener {
                    listener.onTeamClicked(total.team_key)
                }
            }
        }
        fun onBindHome(total: Home) {
            itemBinding.apply {
                positionTv.text = total.standing_place
                teamNameTv.text = total.standing_team
                standingP.text = total.standing_P
                standingW.text = total.standing_W
                standingD.text = total.standing_D
                standingL.text = total.standing_L
                standingGd.text = total.standing_GD
                standingPts.text = total.standing_PTS
                root.setOnClickListener {
                    listener.onTeamClicked(total.team_key)
                }
            }
        }
        fun onBindAway(total: Away) {
            itemBinding.apply {
                positionTv.text = total.standing_place
                teamNameTv.text = total.standing_team
                standingP.text = total.standing_P
                standingW.text = total.standing_W
                standingD.text = total.standing_D
                standingL.text = total.standing_L
                standingGd.text = total.standing_GD
                standingPts.text = total.standing_PTS
                root.setOnClickListener {
                    listener.onTeamClicked(total.team_key)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SVH {
        return SVH(ItemStandingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SVH, position: Int) {
        when (tabPosition) {
            0 -> holder.onBindTotal(totalList[position])
            1 -> holder.onBindHome(homeList[position])
            2 -> holder.onBindAway(awayList[position])
        }
    }

    override fun getItemCount(): Int =
        when(tabPosition){
            0 -> totalList.size
            1 -> homeList.size
            2 -> awayList.size
            else -> totalList.size
        }

    @SuppressLint("NotifyDataSetChanged")
    fun submitTotal(list: List<Total>?) {
        tabPosition = 0
        totalList = list as ArrayList<Total>
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitHome(list: List<Home>?) {
        tabPosition = 1
        homeList = list as ArrayList<Home>
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitAway(list: List<Away>?) {
        tabPosition = 2
        awayList = list as ArrayList<Away>
        notifyDataSetChanged()
    }

    interface OnTeamClickListener{
        fun onTeamClicked(team_id:String)
    }

}