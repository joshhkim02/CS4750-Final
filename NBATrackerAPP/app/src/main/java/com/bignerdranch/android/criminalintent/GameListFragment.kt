package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.criminalintent.databinding.FragmentGameListBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class GameListFragment : Fragment(){
    private var _binding: FragmentGameListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    // Initialize the ViewModel
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentGameListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadGameDetails { game ->
            updateUI(game)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(gameDetails: Game) {
        with(binding) {
            gameTitle.text = "${gameDetails.homeTeam} vs ${gameDetails.visitorTeam}"
            gameScore.text = "${gameDetails.homeTeamScore} - ${gameDetails.visitorTeamScore}"
            gameSeason.text = "Season: ${gameDetails.season}"
            gameDate.text = "Date: ${formatDate(gameDetails.date)}"
            homeCity.text = "City: ${gameDetails.homeCity}"
            homeConference.text = "${gameDetails.homeTeam}: ${gameDetails.homeConference}"
            visitorConference.text = "${gameDetails.visitorTeam}: ${gameDetails.visitorConference}"

            if (gameDetails.homeTeamScore > gameDetails.visitorTeamScore)
                gameWinner.text = "${gameDetails.homeTeam} win!"
            else
                gameWinner.text = "${gameDetails.visitorTeam} win!"
        }
    }
    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(date)
    }

}