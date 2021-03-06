package com.example.rawg_youtubemonitor.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rawg_youtubemonitor.R
import com.example.rawg_youtubemonitor.data.model.Video
import com.example.rawg_youtubemonitor.presentation.fragment.HomeFragment
import com.example.rawg_youtubemonitor.presentation.holder.VideoViewHolder


/**
 * Class VideoAdapter used to create a view of each data founded from de Home Fragment
 */
class VideoAdapter(val fragment: HomeFragment) : RecyclerView.Adapter<VideoViewHolder>() {

    var videos : MutableList<Video> = mutableListOf<Video>()

    /**
     * Creates a new view
     */
    override fun onCreateViewHolder(parent: ViewGroup, typeView: Int): VideoViewHolder {
        val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.video_cardview, parent, false)

        return VideoViewHolder(view, this)
    }

    /**
     * Helps the fragment to play a video.
     *
     * It will send to the fragment the clicked item video
     *
     * @param position: position of listview clicked
     */
    fun playVideoHelper(position: Int) = fragment.readVideo(videos[position])


    /**
     * Sets the list of vides
     *
     * @param videos : the list of videos
     */
    fun bindViewModels(videos: MutableList<Video>){
        this.videos = videos
        notifyDataSetChanged()
    }


    /**
     * return the number of videos in the video list
     */
    override fun getItemCount(): Int = videos.size


    /**
     * Adds a video information into the view
     *
     * @param videoViewHolder : the view holder
     * @param position : the postion of the the video to display
     */
    override fun onBindViewHolder(videoViewHolder: VideoViewHolder, position: Int) {
        val video: Video = videos[position]
        videoViewHolder.bind(video)
    }
}