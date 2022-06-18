package com.abdulaziz.footballlive.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdulaziz.footballlive.R
import com.abdulaziz.footballlive.databinding.FragmentHomeBinding
import com.abdulaziz.footballlive.ui.league.LeagueFragment
import com.abdulaziz.footballlive.ui.matches.current_matches.CurrentMatchFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        setOnClickListeners()

        return binding.root
    }

    private fun setOnClickListeners() {
        binding.apply {
            matchesCard.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container, CurrentMatchFragment()
                    ).addToBackStack("current_match").commit()
            }
            leaguesCard.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container, LeagueFragment()
                    ).addToBackStack("league").commit()
            }
          /*  myTeamsCard.setOnClickListener {
              *//*  parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container, CurrentMatchFragment()
                    ).addToBackStack("standing").commit()*//*
            }*/
        }
    }

}