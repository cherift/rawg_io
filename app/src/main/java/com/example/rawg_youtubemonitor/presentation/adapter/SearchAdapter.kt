package com.example.rawg_youtubemonitor.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.rawg_youtubemonitor.R
import com.example.rawg_youtubemonitor.data.model.Game
import com.example.rawg_youtubemonitor.data.model.TypeOfView
import com.example.rawg_youtubemonitor.presentation.holder.SearchViewHolder
import com.example.rawg_youtubemonitor.presentation.presenter.GetVideosContrat

class SearchAdapter(val searchview: GetVideosContrat.SearchView) : RecyclerView.Adapter<SearchViewHolder>() {

    var games: MutableList<Game> = mutableListOf<Game>()

    /**
     * Creates a new view
     */
    override fun onCreateViewHolder(parent: ViewGroup, typeView: Int):  SearchViewHolder{
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.game_card_view, parent, false)

        return SearchViewHolder(view, this)
    }

    /**
     * Sets the list of games
     *
     * @param games : the list of games
     */
    fun bindViewModels(games: MutableList<Game>){
        this.games = games
        notifyDataSetChanged()
    }

    /**
     * Helps the associated fragment ta add of remove
     * the game founded at the position passed as parameter
     *
     * @param position: the position of the game in the list
     */
    fun toogleAction(position: Int) = searchview.addOrRemoveGame(games[position])

    /**
     * Gets the number of games
     */
    override fun getItemCount(): Int = games.size

    /**
     * Adds a game information into the view
     *
     * @param holder : the view holder
     * @param position : the position of the the game to display
     */
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val game: Game = games[position]
        holder.bind(game, searchview.typeOfView())
    }


}