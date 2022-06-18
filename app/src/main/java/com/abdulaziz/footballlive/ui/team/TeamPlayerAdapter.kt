package com.abdulaziz.footballlive.ui.team

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.footballlive.databinding.ItemPlayerBinding
import com.abdulaziz.footballlive.models.team.Player

class TeamPlayerAdapter : RecyclerView.Adapter<TeamPlayerAdapter.TPVH>() {

    private var list = arrayListOf<Player>()

    inner class TPVH(private val itemBinding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(player: Player) {
            itemBinding.apply {
                playerNumberTv.text = player.player_number
                playerNameTv.text = player.player_name
                val type = getType(player.player_type)
                playerPosTv.text = type
                playerAgeTv.text = player.player_age
                playerAppsTv.text = player.player_match_played
                playerGoalsTv.text = player.player_goals
            }
        }
    }

    private fun getType(type:String):String{
        val ty = when(type){
            "Goalkeepers" -> "GK"
            "Defenders" -> "DF"
            "Midfielders" -> "MF"
            "Forwards" -> "AT"
            else -> ""
        }
        return ty
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TPVH {
        return TPVH(ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TPVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Player>) {
        this.list = list as ArrayList<Player>
        notifyDataSetChanged()
    }


}