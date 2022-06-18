package com.abdulaziz.footballlive.ui.standing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abdulaziz.footballlive.MainActivity
import com.abdulaziz.footballlive.adapter.PagerAdapter
import com.abdulaziz.footballlive.database.entity.League
import com.abdulaziz.footballlive.databinding.FragmentStandingBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

private const val ARG_PARAM1 = "league"

class StandingFragment : Fragment() {

    private var league: League? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            league = it.getSerializable(ARG_PARAM1) as League
        }
    }

    private var _binding: FragmentStandingBinding? =null
    private val binding get() = _binding!!
    lateinit var pagerAdapter: PagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStandingBinding.inflate(layoutInflater, container, false)

        Picasso.get().load(league?.league_logo).into(binding.imageView)
        binding.backIv.setOnClickListener {(activity as MainActivity).onBackPressed() }
        pagerAdapter = PagerAdapter(requireActivity(), league!!.league_key)
        binding.viewPager.adapter = pagerAdapter
        val list = arrayListOf("Matches", "Table")
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = list[position]
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()

        return binding.root
    }

}