package com.abdulaziz.footballlive.ui.matches.current_matches

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
import com.abdulaziz.footballlive.adapter.FixturesAdapter
import com.abdulaziz.footballlive.database.entity.League
import com.abdulaziz.footballlive.databinding.FragmentMatchBinding
import com.abdulaziz.footballlive.models.fixtures.Fixture
import com.abdulaziz.footballlive.models.fixtures.Match
import com.abdulaziz.footballlive.network.Status
import com.abdulaziz.footballlive.ui.matches.match_details.MatchDetailsFragment
import com.abdulaziz.footballlive.ui.standing.StandingFragment
import com.abdulaziz.footballlive.viewmodels.ViewModelFactory


class CurrentMatchFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FixturesAdapter(object : FixturesAdapter.OnItemClickListener{
            override fun onItemClicked(match_id: String) {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container, MatchDetailsFragment::class.java,
                        bundleOf(Pair("match_id", match_id))
                    ).addToBackStack("match_details").commit()
            }

            override fun onLeagueClicked(league:League) {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container, StandingFragment::class.java,
                        bundleOf(Pair("league", league))
                    ).addToBackStack("standing").commit()
            }
        })
    }

    private var _binding: FragmentMatchBinding? =null
    private val binding get() = _binding!!
    private lateinit var adapter: FixturesAdapter
    private lateinit var matchViewModel: CurrentMatchViewModel
    private var isCreated = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchBinding.inflate(layoutInflater, container, false)


        if (!isCreated) {
            matchViewModel = ViewModelProvider(
                this@CurrentMatchFragment,
                ViewModelFactory(requireContext())
            )[CurrentMatchViewModel::class.java]

            matchViewModel.getFixtures().observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        val list = it.data?.let { it1 -> sortList(it1) }
                        list?.let { it1 -> adapter.submitList(it1) }
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            binding.backIv.setOnClickListener{(activity as MainActivity).onBackPressed()}

            isCreated = true
        }


        binding.rv.adapter = adapter


        return binding.root
    }


    private fun sortList(body: List<Match>): List<Fixture> {
        var currentMatch: Match? = null
        val list = body as ArrayList<Match>
        val fixList = arrayListOf<Fixture>()

        while (list.isNotEmpty()) {
            val iterator = list.iterator()
            val myList = arrayListOf<Match>()
            while (iterator.hasNext()) {
                val match = iterator.next()
                if (currentMatch == null) {
                    currentMatch = match
                    myList.add(match)
                    iterator.remove()
                } else if (currentMatch.league_key == match.league_key) {
                    myList.add(match)
                    iterator.remove()
                }
            }
            if (currentMatch != null) {
                fixList.add(
                    Fixture(
                        league_id = currentMatch.league_key,
                        league_name = currentMatch.league_name,
                        league_image_url = currentMatch.league_logo,
                        list = myList
                    )
                )
            }
            currentMatch = null


        }
        return fixList
    }

}