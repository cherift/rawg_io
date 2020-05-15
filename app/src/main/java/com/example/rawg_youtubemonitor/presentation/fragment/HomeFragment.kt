package com.example.rawg_youtubemonitor.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rawg_youtubemonitor.R
import com.example.rawg_youtubemonitor.data.model.Video
import com.example.rawg_youtubemonitor.presentation.adapter.VideoAdapter

class HomeFragment : Fragment() {

    var rootView : View? = null
    var recyclerView : RecyclerView? = null
    var videoAdapter : VideoAdapter? = null


    companion object {
        fun newInstance() : HomeFragment  = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.all_videos_fragment, container, false)

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


        //Testing with static list
        var videos : List<Video> = listOf(
            Video("", "Titre 0", "Chaine 0", 15),
            Video("", "Titre 0", "Chaine 0", 15),
            Video("", "Titre 0", "Chaine 0", 15),
            Video("", "Titre 0", "Chaine 0", 15),
            Video("", "Titre 0", "Chaine 0", 15),
            Video("", "Titre 0", "Chaine 0", 15))

        println("je suis homefragment")

        videoAdapter!!.bindViewModels(videos)
    }
}