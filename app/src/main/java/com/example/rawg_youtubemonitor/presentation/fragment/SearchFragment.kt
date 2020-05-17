package com.example.rawg_youtubemonitor.presentation.fragment

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.SearchView
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

class SearchFragment : Fragment(), GetVideosContrat.SearchView {

    var rootView : View? = null
    var recyclerView : RecyclerView? = null
    var searchView : SearchView? = null
    var progress: ProgressBar? = null
    var searchQuery: String? = null
    var searchAdapter: SearchAdapter? = null
    var gameDao: GameDao? = null

    companion object {
        val presenter : SearchPresenter = SearchPresenter()

        var listGames : MutableList<Game> = mutableListOf<Game>()

        fun newInstance() : SearchFragment  = SearchFragment()
    }

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     * Initialises also the game dao model
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.search_fragment, container, false)

        searchView = rootView!!.findViewById(R.id.search_view)

        progress = rootView!!.findViewById(R.id.progress_circular)

        setupRecyclerView()

        if(listGames.isNotEmpty()){
            searchAdapter!!.bindViewModels(listGames)
        }

        // Initializes the game dao object
        gameDao = GameDatabase.getInstance(activity!!.application).gameDao()

        return rootView
    }

    /**
     * Tells the fragment that its activity has completed its own
     * and starts initialises the searching game event listener.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchGames()
    }

    /**
     * Sets the associated fragment recycler view.
     */
    fun setupRecyclerView() {
        recyclerView = rootView!!.findViewById(R.id.search_list_rec)
        searchAdapter = SearchAdapter(this)
        recyclerView!!.adapter = searchAdapter

        val viewManager : LinearLayoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = viewManager
    }

    /**
     * Searches the list of Games corresponding to the query text
     * passed in the search view.
     */
    override fun searchGames() {

        presenter.attachView(this)

        searchView!!.setOnQueryTextListener( object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    presenter.cancelSubscription()
                    progress!!.visibility = View.GONE
                }
                else {
                    // clear the list before each search
                    listGames.clear()

                    searchQuery = query

                    progress!!.visibility = View.VISIBLE

                    val myHandler: Handler = Handler()
                    myHandler.postDelayed(Runnable {
                        presenter.getSearchedGames(query, 1)
                    }, 300)
                }
                searchView!!.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })
    }

    /**
     * Prepares list of Game by expanded the value to the list founded
     * after each call of search request.
     * This function can be recursive if the response has a next data
     *
     * @param gameResponse: the game response object
     */
    override fun prepareGames(gameResponse: GetGameResponse){
        listGames.addAll(gameResponse.games)

        // if the result has next result we are going to do another search
        gameResponse.nextUrl?.let {
            val uri: Uri = Uri.parse(it)

            val myHandler: Handler = Handler()
            myHandler.postDelayed(Runnable {
                searchQuery?.let { presenter.getSearchedGames(it, uri.getQueryParameter("page")!!.toInt()) }
            }, 300)
        }

        displayGames(listGames)
    }

    /**
     * Displays the games in the recycler view
     *
     * @param games: the list of games
     */
    override fun displayGames(games: MutableList<Game>){
        progress!!.visibility = View.GONE
        searchAdapter!!.bindViewModels(games)
    }


    /**
     * Adds a game in the database.
     *
     * @param game: the game to be added
     */
    override fun addOrRemoveGame(game: Game) {
        presenter.addGameInFavourites(game, gameDao!!)

        Snackbar.make(
            recyclerView!!,
            "${game.name} has been added in your favourites",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    /**
     * Sets the type of view as an adder of data in database
     */
    override fun typeOfView(): TypeOfView = TypeOfView.ADDER
}