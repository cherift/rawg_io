package com.example.rawg_youtubemonitor.presentation.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rawg_youtubemonitor.R
import com.example.rawg_youtubemonitor.data.dao.GameDao
import com.example.rawg_youtubemonitor.data.database.GameDatabase
import com.example.rawg_youtubemonitor.data.model.Game
import com.example.rawg_youtubemonitor.data.model.GetGameResponse
import com.example.rawg_youtubemonitor.data.model.TypeOfView
import com.example.rawg_youtubemonitor.presentation.adapter.SearchAdapter
import com.example.rawg_youtubemonitor.presentation.presenter.GetVideosContrat
import com.example.rawg_youtubemonitor.presentation.presenter.SearchPresenter
import com.google.android.material.snackbar.Snackbar

class FavouriteFragment : Fragment(), GetVideosContrat.SearchView {

    var rootView : View? = null
    var recyclerView : RecyclerView? = null
    var searchAdapter: SearchAdapter? = null
    var gameDao: GameDao? = null
    var emptyMsqView: TextView? = null

    companion object {
        val presenter : SearchPresenter = SearchPresenter()

        fun newInstance() : FavouriteFragment  = FavouriteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("quand je supprime oncreateview")

        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.save_fragment, container, false)

        emptyMsqView = rootView!!.findViewById(R.id.empty)

        setupRecyclerView()

        // Initializes the game dao object
        gameDao = GameDatabase.getInstance(activity!!.application).gameDao()

        searchGames()

        return rootView
    }

    /**
     * Sets the associated fragment recycler view.
     */
    fun setupRecyclerView() {
        recyclerView = rootView!!.findViewById(R.id.favourite_recycler)
        searchAdapter = SearchAdapter(this)
        recyclerView!!.adapter = searchAdapter

        val viewManager : LinearLayoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = viewManager
    }

    /**
     * Sets the type of view as an adder of data in database
     */
    override fun typeOfView(): TypeOfView = TypeOfView.REMOVER

    /**
     * Searches all games saved in database
     */
    override fun searchGames() {
        presenter.attachView(this)
        presenter.getFavouriteGames(gameDao!!)
    }

    /**
     * Removes a game from database.
     *
     * @param game: the game to remove
     */
    override fun addOrRemoveGame(game: Game) {
        presenter.removeGame(game, gameDao!!)

        Snackbar.make(
            recyclerView!!,
            "${game.name} has been removed from your favourites",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    /**
     * Displays the games in the recycler view
     *
     * @param games: the list of games
     */
    override fun displayGames(games: MutableList<Game>){
        if (games.isEmpty()){
            emptyMsqView!!.visibility = View.VISIBLE
        }
        searchAdapter!!.bindViewModels(games)
    }

    /**
     * This function won't be use in this fragment
     */
    override fun prepareGames(gameResponse: GetGameResponse) = Unit
}