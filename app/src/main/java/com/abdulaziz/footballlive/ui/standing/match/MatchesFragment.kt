package com.abdulaziz.footballlive.ui.standing.match

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.abdulaziz.footballlive.R
import com.abdulaziz.footballlive.databinding.FragmentMatchesBinding
import com.abdulaziz.footballlive.models.fixtures.Match
import com.abdulaziz.footballlive.network.Status
import com.abdulaziz.footballlive.ui.matches.match_details.MatchDetailsFragment
import com.abdulaziz.footballlive.viewmodels.ViewModelFactory


private const val ARG_PARAM1 = "param1"

class MatchesFragment : Fragment() {

    private var leagueId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            leagueId = it.getString(ARG_PARAM1)
        }
        list = arrayListOf()
        adapter = MatchLeagueAdapter(list, object : MatchLeagueAdapter.OnItemClickListener {
            override fun onItemClicked(match_id: String) {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container, MatchDetailsFragment::class.java,
                        bundleOf(Pair("match_id", match_id))
                    ).addToBackStack("match_details").commit()
            }

        })
    }

    private var _binding: FragmentMatchesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MatchLeagueAdapter
    private lateinit var matchViewModel: MatchViewModel
    private lateinit var list: ArrayList<Match>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesBinding.inflate(layoutInflater, container, false)

        matchViewModel = ViewModelProvider(
            this@MatchesFragment,
            ViewModelFactory(requireContext())
        )[MatchViewModel::class.java]

        leagueId?.let { matchViewModel.fetchFixtures(it) }
        matchViewModel.getFixtures().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    list = arrayListOf()
                    it.data?.let { it1 -> list.addAll(it1) }
                    list.sortBy { it.event_time }
                    list.sortBy { it.event_date }
                    adapter.submitList(list)
                    binding.progressBar.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.rv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rv.isNestedScrollingEnabled = true
        binding.rv.adapter = adapter

        return binding.root
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String/*, param2: String*/) =
            MatchesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    /* putString(ARG_PARAM2, param2)*/
                }
            }
    }
}