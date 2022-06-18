package com.abdulaziz.footballlive.ui.league

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abdulaziz.footballlive.MainActivity
import com.abdulaziz.footballlive.R
import com.abdulaziz.footballlive.adapter.LeaguesAdapter
import com.abdulaziz.footballlive.database.entity.League
import com.abdulaziz.footballlive.databinding.FragmentLeagueBinding
import com.abdulaziz.footballlive.network.Status
import com.abdulaziz.footballlive.ui.standing.StandingFragment
import com.abdulaziz.footballlive.viewmodels.ViewModelFactory


class LeagueFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = LeaguesAdapter(object : LeaguesAdapter.OnItemClickListener {
            override fun onItemClicked(league: League) {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container, StandingFragment::class.java,
                        bundleOf(Pair("league", league))
                    ).addToBackStack("standing").commit()
            }

        })
    }

    private var _binding: FragmentLeagueBinding? = null
    private val binding get() = _binding!!
    lateinit var footballViewModel: LeagueViewModel
    lateinit var adapter: LeaguesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeagueBinding.inflate(layoutInflater, container, false)

        binding.backIv.setOnClickListener { (activity as MainActivity).onBackPressed() }
        binding.rv.adapter = adapter

        footballViewModel = ViewModelProvider(
            this@LeagueFragment,
            ViewModelFactory(requireContext())
        )[LeagueViewModel::class.java]

        footballViewModel.getLeagues().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    adapter.submitList(it.data)
                    binding.progressBar.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }


        return binding.root
    }
}