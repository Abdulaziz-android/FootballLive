package com.abdulaziz.footballlive.adapter.match_detail_adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.footballlive.R
import com.abdulaziz.footballlive.databinding.ItemEventBinding
import com.abdulaziz.footballlive.models.fixtures.CustomEvent
import android.text.style.ForegroundColorSpan
import android.text.SpannableString
import com.google.gson.internal.LinkedTreeMap

class EventAdapter : RecyclerView.Adapter<EventAdapter.EVH>() {

    private var list = arrayListOf<CustomEvent>()

    inner class EVH(private val itemBinding: ItemEventBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun onBind(event: CustomEvent) {
            itemBinding.apply {
                scoreInfoTv.text =
                    if (event.score != null && event.score.isNotEmpty()) event.score else event?.card
                if (event.score == "substitution") {
                    setSubs(event)
                } else if (event.card != null && event.card.isNotEmpty()) {
                    setCards(event)
                } else {
                    setGoals(event)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        private fun setSubs(event: CustomEvent) {
            itemBinding.apply {
                eventIv.setImageResource(R.drawable.ic_twotone_repeat_24)
                if (event.home_subs is LinkedTreeMap<*, *>) {
                    val t: LinkedTreeMap<*, *> = event.home_subs
                    val in_ = t["in"].toString()
                    val out = t["out"].toString()
                    homeTeamTimeTv.text = "${event.time}'"
                    val ss = SpannableString(in_ + "\n" + out)
                    ss.setSpan(
                        ForegroundColorSpan(Color.parseColor("#999999")),
                        in_.length,
                        in_.length+out.length+1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    homeTeamEventTv.text = ss
                } else if (event.away_subs is LinkedTreeMap<*, *>) {
                    val t: LinkedTreeMap<*, *> = event.away_subs as LinkedTreeMap<*, *>
                    val in_ = t["in"].toString()
                    val out = t["out"].toString()

                    awayTeamTimeTv.text = "${event.time}'"
                    val ss = SpannableString(in_ + "\n" + out)
                    ss.setSpan(
                        ForegroundColorSpan(Color.parseColor("#999999")),
                        in_.length,
                        in_.length+out.length+1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    awayTeamEventTv.text = ss
                }
            }
        }

        @SuppressLint("SetTextI18n")
        private fun setCards(event: CustomEvent) {
            itemBinding.apply {
                if (event.card == "yellow card")
                    eventIv.setImageResource(R.drawable.ic_yellow_card)
                else if (event.card == "red card")
                    eventIv.setImageResource(R.drawable.ic_red_card)
                if (event.home_fault?.isNotEmpty() == true) {
                    homeTeamTimeTv.text = "${event.time}'"
                    homeTeamEventTv.text = event.home_fault
                } else if (event.away_fault?.isNotEmpty() == true) {
                    awayTeamTimeTv.text = "${event.time}'"
                    awayTeamEventTv.text = event.away_fault
                }
            }
        }

        @SuppressLint("SetTextI18n")
        private fun setGoals(event: CustomEvent) {
            itemBinding.apply {
                eventIv.setImageResource(R.drawable.ic_baseline_sports_goal)
                if (event.home_scorer?.isNotEmpty() == true) {
                    homeTeamTimeTv.text = "${event.time}'"
                    homeTeamEventTv.text = event.home_scorer
                } else if (event.away_scorer?.isNotEmpty() == true) {
                    awayTeamTimeTv.text = "${event.time}'"
                    awayTeamEventTv.text = event.away_scorer
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EVH {
        return EVH(ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: EVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size


    fun submitList(list: List<CustomEvent>) {
        this.list = list as ArrayList<CustomEvent>
        notifyDataSetChanged()
    }

}