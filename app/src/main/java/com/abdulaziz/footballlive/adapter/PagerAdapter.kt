package com.abdulaziz.footballlive.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abdulaziz.footballlive.ui.standing.match.MatchesFragment
import com.abdulaziz.footballlive.ui.standing.table.TableFragment

class PagerAdapter(fa: FragmentActivity, val league_key:String) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MatchesFragment.newInstance(league_key)
            1 -> TableFragment.newInstance(league_key)
            else -> MatchesFragment.newInstance(league_key)
        }
    }
}