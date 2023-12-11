package com.bignerdranch.android.criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class GameAdapter(private val games: List<Game>, private val onItemClicked: (String) -> Unit) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.bind(game)
        holder.itemView.setOnClickListener { onItemClicked(game.id) }
    }

    override fun getItemCount() = games.size

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gameTitle: TextView = itemView.findViewById(R.id.game_title)
        private val gameScore: TextView = itemView.findViewById(R.id.game_score)

        fun bind(game: Game) {
            gameTitle.text = "${game.homeTeam} vs ${game.visitorTeam}"
            gameScore.text = "${game.homeTeamScore} - ${game.visitorTeamScore}"
        }
    }
}