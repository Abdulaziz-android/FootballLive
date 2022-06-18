package com.abdulaziz.footballlive.ui.matches.match_details

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
import com.abdulaziz.footballlive.adapter.match_detail_adapters.EventAdapter
import com.abdulaziz.footballlive.databinding.FragmentMatchDetailsBinding
import com.abdulaziz.footballlive.models.fixtures.CustomEvent
import com.abdulaziz.footballlive.models.fixtures.Match
import com.abdulaziz.footballlive.network.Status
import com.abdulaziz.footballlive.ui.team.TeamFragment
import com.abdulaziz.footballlive.viewmodels.ViewModelFactory
import com.google.gson.internal.LinkedTreeMap
import com.squareup.picasso.Picasso


private const val ARG_PARAM1 = "match_id"

class MatchDetailsFragment : Fragment() {

    private var matchId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            matchId = it.getString(ARG_PARAM1)
        }
    }

    private var _binding: FragmentMatchDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var matchViewModel: MatchDetailsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailsBinding.inflate(layoutInflater, container, false)

        matchViewModel = ViewModelProvider(
            this@MatchDetailsFragment,
            ViewModelFactory(requireContext())
        )[MatchDetailsViewModel::class.java]
        matchId?.let { matchViewModel.fetchMatchDetails(it) }

        matchViewModel.getMatchDetails().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    setMatchDetails(it.data)
                    binding.progressBar.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.backIv.setOnClickListener{(activity as MainActivity).onBackPressed()}


        return binding.root
    }

    private fun setMatchDetails(match: Match?) {
        binding.apply {
            Picasso.get().load(match?.home_team_logo).into(homeTeamLogoIv)
            Picasso.get().load(match?.away_team_logo).into(awayTeamLogoIv)
            homeTeamNameTv.text = match?.event_home_team
            awayTeamNameTv.text = match?.event_away_team
            resultTv.text = if (match?.event_final_result?.isNotEmpty()!! && match.event_final_result!="-")
                match.event_final_result else match.event_time
            statusTv.text = match.event_status

            if (match.event_status.isNotEmpty()) {
                val eventList = getEvents(match)
                val adapter = EventAdapter()
                adapter.submitList(eventList)
                binding.eventsRv.adapter = adapter
                binding.eventLayout.visibility = View.VISIBLE
            }

            setOnClickListeners(match)

        }
    }

    private fun setOnClickListeners(match: Match) {
        binding.apply {
            homeTeamLogoIv.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container, TeamFragment::class.java,
                        bundleOf(Pair("team_id", match.home_team_key))
                    ).addToBackStack("team").commit()
            }
            homeTeamNameTv.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container, TeamFragment::class.java,
                        bundleOf(Pair("team_id", match.home_team_key))
                    ).addToBackStack("team").commit()
            }
            awayTeamLogoIv.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container, TeamFragment::class.java,
                        bundleOf(Pair("team_id", match.away_team_key))
                    ).addToBackStack("team").commit()
            }
            awayTeamNameTv.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container, TeamFragment::class.java,
                        bundleOf(Pair("team_id", match.away_team_key))
                    ).addToBackStack("team").commit()
            }
        }
    }


    private fun getEvents(match: Match): List<CustomEvent> {
        val list = arrayListOf<CustomEvent>()
        match.goalscorers.forEach {
            val event = CustomEvent(
                time = it.time,
                home_scorer = it.home_scorer,
                away_scorer = it.away_scorer,
                score = it.score,
                score_info = it.score_info,
                info_time = it.info_time
            )
            list.add(event)
        }

        match.substitutes.forEach {
            if (it.away_scorer is LinkedTreeMap<*, *>) {
                val event = CustomEvent(
                    time = it.time,
                    away_subs = it.away_scorer,
                    score = it.score,
                    info_time = it.info_time
                )
                list.add(event)

            } else if (it.home_scorer is LinkedTreeMap<*, *>) {

                val event = CustomEvent(
                    time = it.time,
                    home_subs = it.home_scorer,
                    score = it.score,
                    info_time = it.info_time
                )
                list.add(event)
            }
        }

        match.cards.forEach {
            val event = CustomEvent(
                time = it.time,
                away_fault = it.away_fault,
                home_fault = it.home_fault,
                card = it.card,
                info_time = it.info_time
            )
            list.add(event)
        }

        list.sortBy { it.time }

        return list
    }



}