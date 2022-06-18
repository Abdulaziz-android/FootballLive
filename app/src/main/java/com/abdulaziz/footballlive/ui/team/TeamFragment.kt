package com.abdulaziz.footballlive.ui.team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.abdulaziz.footballlive.MainActivity
import com.abdulaziz.footballlive.databinding.FragmentTeamBinding
import com.abdulaziz.footballlive.models.team.Team
import com.abdulaziz.footballlive.network.Status
import com.abdulaziz.footballlive.viewmodels.ViewModelFactory
import com.squareup.picasso.Picasso

private const val ARG_PARAM1 = "team_id"

class TeamFragment : Fragment() {

    private var teamId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            teamId = it.getString(ARG_PARAM1)
        }
    }

    private var _binding: FragmentTeamBinding? = null
    private val binding get() = _binding!!
    private lateinit var teamViewModel: TeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamBinding.inflate(layoutInflater, container, false)

        teamViewModel = ViewModelProvider(
            this@TeamFragment,
            ViewModelFactory(requireContext())
        )[TeamViewModel::class.java]

        teamId?.let { teamViewModel.fetchTeamDetails(it) }

        teamViewModel.getTeamDetails().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    it.data?.result?.get(0)?.let { it1 -> setData(it1) }
                    binding.progressBar.visibility = View.GONE
                    binding.dataLayout.visibility = View.VISIBLE
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

    private fun setData(team: Team) {
        binding.apply {
            Picasso.get().load(team.team_logo).into(teamLogoIv)
            teamNameTv.text = team.team_name

            val adapter = TeamPlayerAdapter()
            adapter.submitList(team.players)
            playerRv.adapter = adapter
            playerRv.isNestedScrollingEnabled = true

            managerNameTv.text = team.coaches[0].coach_name

        }
    }

}