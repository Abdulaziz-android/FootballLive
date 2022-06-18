package com.abdulaziz.footballlive.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.footballlive.database.entity.League
import com.abdulaziz.footballlive.databinding.ItemFixturesBinding
import com.abdulaziz.footballlive.models.fixtures.Fixture
import com.squareup.picasso.Picasso

class FixturesAdapter(val listener:OnItemClickListener):RecyclerView.Adapter<FixturesAdapter.FVH>() {

    private var list = arrayListOf<Fixture>()

    inner class FVH(private val itemBinding:ItemFixturesBinding):RecyclerView.ViewHolder(itemBinding.root){
        fun onBind(fixture: Fixture){
            itemBinding.apply {
                leagueTv.text = fixture.league_name
                Picasso.get().load(fixture.league_image_url).into(leagueIv)
                leagueLayout.setOnClickListener {
                    val league = League(
                        fixture.league_id,
                        fixture.league_image_url,
                        fixture.league_name
                    )
                    listener.onLeagueClicked(league)
                }

                val adapter = MatchAdapter(fixture.list, object : MatchAdapter.OnItemClickListener{
                    override fun onItemClicked(match_id: String) {
                        listener.onItemClicked(match_id)
                    }
                })
                rv.adapter = adapter
                rv.addItemDecoration(DividerItemDecoration(rv.context, DividerItemDecoration.VERTICAL))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FVH {
        return FVH(ItemFixturesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<Fixture>) {
        this.list = list as ArrayList<Fixture>
        notifyDataSetChanged()
    }


    interface OnItemClickListener{
        fun onItemClicked(match_id:String)
        fun onLeagueClicked(league: League)
    }

}