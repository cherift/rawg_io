package com.example.rawg_youtubemonitor.presentation.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rawg_youtubemonitor.R
import com.example.rawg_youtubemonitor.data.model.Game
import com.example.rawg_youtubemonitor.data.model.Video
import com.example.rawg_youtubemonitor.presentation.adapter.VideoAdapter
import com.example.rawg_youtubemonitor.presentation.presenter.GetVideosContrat
import com.example.rawg_youtubemonitor.presentation.presenter.GetVideosPresenter

class HomeFragment : Fragment(), GetVideosContrat.GetVideosView {

    var rootView : View? = null
    var recyclerView : RecyclerView? = null
    var videoAdapter : VideoAdapter? = null
    var progress: ProgressBar? = null


    companion object {
        var listGames : MutableList<Game> = mutableListOf<Game>()
        var listVideos : MutableList<Video> = mutableListOf<Video>()

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
        setupRecyclerView()

        return rootView
    }


    /**
     * Manages the recycler view and puts list of videos in it.
     */
    fun setupRecyclerView(){
        recyclerView = rootView!!.findViewById(R.id.recyclerview)
        videoAdapter = VideoAdapter()
        recyclerView!!.adapter = videoAdapter

        val viewManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = viewManager

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter.attachView(this)
        presenter.searchGames()
    }

    /**
     *
     */
    override fun prepareVideos(games: MutableList<Game>) {
        listGames.addAll(games)

        listGames.forEach { game -> presenter.searchGameVideos(game.id.toString()) }
    }

    override fun displayVideos(videos: MutableList<Video>) {
        listVideos.addAll(videos)

        progress!!.visibility = View.GONE
        videoAdapter!!.bindViewModels(listVideos)
    }

    override fun readVideo(video: Video) {
        TODO("not implemented")
    }
}