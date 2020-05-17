package com.example.rawg_youtubemonitor.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rawg_youtubemonitor.R
import com.example.rawg_youtubemonitor.YoutubeActivity
import com.example.rawg_youtubemonitor.data.dao.GameDao
import com.example.rawg_youtubemonitor.data.database.GameDatabase
import com.example.rawg_youtubemonitor.data.model.Game
import com.example.rawg_youtubemonitor.data.model.GetGameResponse
import com.example.rawg_youtubemonitor.data.model.GetVideoResponse
import com.example.rawg_youtubemonitor.data.model.Video
import com.example.rawg_youtubemonitor.presentation.adapter.VideoAdapter
import com.example.rawg_youtubemonitor.presentation.presenter.GetVideosContrat
import com.example.rawg_youtubemonitor.presentation.presenter.GetVideosPresenter

class HomeFragment : Fragment(), GetVideosContrat.GetVideosView {

    var rootView : View? = null
    var recyclerView : RecyclerView? = null
    var videoAdapter : VideoAdapter? = null
    var progress: ProgressBar? = null
    val EXTRA_MESSAGE = "MESSAGE"
    var gameDao: GameDao? = null


    companion object {
        var listGames : MutableList<Game> = mutableListOf<Game>()
        var listVideos : MutableList<Video> = mutableListOf<Video>()
        var currentGameId: Int? = null

        val presenter : GetVideosPresenter = GetVideosPresenter()

        fun newInstance() : HomeFragment  = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.all_videos_fragment, container, false)

        progress = rootView!!.findViewById(R.id.progress_circular)

        // Initializes the game dao object
        gameDao = GameDatabase.getInstance(activity!!.application).gameDao()

        setupRecyclerView()

        return rootView
    }


    /**
     * Manages the recycler view and puts list of videos in it.
     */
    fun setupRecyclerView(){
        recyclerView = rootView!!.findViewById(R.id.recyclerview)
        videoAdapter = VideoAdapter(this)
        recyclerView!!.adapter = videoAdapter

        val viewManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = viewManager

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter.attachView(this)

        searchGames()
    }

    /**
     * Searches games saved in database and passes each one
     * to the presenter for searching associated videos.
     */
    override fun searchGames(){
        listGames.clear()
        presenter.getFavouriteGames(gameDao!!)
        listGames.addAll(presenter.favouriteGames)

        listGames.forEach { game ->
            currentGameId = game.id
            val myHandler: Handler = Handler()
            myHandler.postDelayed(Runnable {
                presenter.searchGameVideos(game.id.toString(), 1)
            }, 300)
        }
    }

    /**
     * Displays all videos founded in the list view
     *
     * @param videos: the videos founded
     */
    override fun displayVideos(videoResponse: GetVideoResponse) {
        listVideos.addAll(videoResponse.videos)

        videoResponse.nextUrl?.let {
            val uri: Uri = Uri.parse(it)

            val myHandler: Handler = Handler()
            myHandler.postDelayed(Runnable {
                currentGameId?.let {
                    presenter.searchGameVideos(it.toString(), uri.getQueryParameter("page")!!.toInt())
                }
            }, 300)
        }

        progress!!.visibility = View.GONE
        videoAdapter!!.bindViewModels(listVideos)
    }

    /**
     * Starts a new activity to play the video by sending the external id to the new activity
     *
     * @param video: the video to play
     */
    override fun readVideo(video: Video) {
        val intent: Intent = Intent(activity, YoutubeActivity::class.java).apply{
            putExtra(EXTRA_MESSAGE, video.externalId)
        }
        startActivity(intent)
    }
}