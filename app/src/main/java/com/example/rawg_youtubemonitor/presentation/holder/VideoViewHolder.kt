package com.example.rawg_youtubemonitor.presentation.holder

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.rawg_youtubemonitor.presentation.adapter.VideoAdapter
import com.example.rawg_youtubemonitor.R
import com.example.rawg_youtubemonitor.data.model.Video

/**
 * class VideoViewHolder used to display only one view of the list view.
 */
class VideoViewHolder(val view: View, val adapter: VideoAdapter) : RecyclerView.ViewHolder(view) {

    var miniature : ImageView?  = null
    var videoTitle : TextView? = null
    var channelName : TextView? = null
    var nbViews : TextView? = null
    var cardButton : CardView? = null

    /**
     * Initilising card view elements
     */
    init {
        miniature = view.findViewById(R.id.miniature)
        videoTitle = view.findViewById(R.id.videoTitle)
        channelName = view.findViewById(R.id.channelName)
        nbViews = view.findViewById(R.id.nbViews)
        cardButton = view.findViewById(R.id.cardButton)

        cardButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val position : Int = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    adapter.playVideoHelper(position)
                }
            }

        })
    }

    /**
     * Displays a video infomation in the views
     *
     * @param video: the video to display informations
     */
    fun bind(video: Video){
        videoTitle?.text = video.videoTitle
        channelName?.text = "Chaine : "+ video.channelName
        nbViews?.text = "Vues : "+video.nbViews.toString()

        Glide
            .with(view)
            .load(video.miniature.high.url)
            .into(miniature!!)
    }
}