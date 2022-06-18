package com.abdulaziz.footballlive.ui.standing.table

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.abdulaziz.footballlive.R
import com.abdulaziz.footballlive.adapter.StandingAdapter
import com.abdulaziz.footballlive.databinding.FragmentTableBinding
import com.abdulaziz.footballlive.models.standing.Standing
import com.abdulaziz.footballlive.network.Status
import com.abdulaziz.footballlive.ui.team.TeamFragment
import com.abdulaziz.footballlive.viewmodels.ViewModelFactory
import com.google.android.material.tabs.TabLayout

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TableFragment : Fragment() {

    private var league_key: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            league_key = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        adapter = StandingAdapter(object : StandingAdapter.OnTeamClickListener {
            override fun onTeamClicked(team_id: String) {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container, TeamFragment::class.java,
                        bundleOf(Pair("team_id", team_id))
                    ).addToBackStack("team").commit()
            }

        })
    }

    private var _binding: FragmentTableBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: StandingAdapter
    lateinit var viewModel: TableViewModel
    private var standing: Standing? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTableBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(
            this@TableFragment,
            ViewModelFactory(requireContext())
        )[TableViewModel::class.java]

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("All"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Home"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Away"))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (standing != null) {
                    when (tab?.position) {
                        0 -> {
                            adapter.submitTotal(standing?.total)
                        }
                        1 -> {
                            adapter.submitHome(standing?.home)
                        }
                        2 -> {
                            adapter.submitAway(standing?.away)
                        }
                        else -> {
                            adapter.submitTotal(standing?.total)
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        viewModel.getStanding(league_key!!).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.linear.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    standing = it.data
                    when (binding.tabLayout.selectedTabPosition) {
                        0 -> {
                            adapter.submitTotal(standing?.total)
                        }
                        1 -> {
                            adapter.submitHome(standing?.home)
                        }
                        2 -> {
                            adapter.submitAway(standing?.away)
                        }
                    }
                    binding.progressBar.visibility = View.GONE
                    binding.linear.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.linear.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        binding.rv.adapter = adapter


        return binding.root
    }

    companion object {


        @JvmStatic
        fun newInstance(param1: String/*, param2: String*/) =
            TableFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}