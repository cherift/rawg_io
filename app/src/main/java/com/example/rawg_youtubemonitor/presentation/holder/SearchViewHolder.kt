package com.example.rawg_youtubemonitor.presentation.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rawg_youtubemonitor.R
import com.example.rawg_youtubemonitor.data.model.Game
import com.example.rawg_youtubemonitor.data.model.TypeOfView
import com.example.rawg_youtubemonitor.presentation.adapter.SearchAdapter

class SearchViewHolder(val view: View, adapter: SearchAdapter) : RecyclerView.ViewHolder(view) {

    var game_miniature: ImageView? = null
    var game_name: TextView? = null
    var game_note: TextView? = null
    var toogle_action: ImageView? = null

    init {
        game_miniature = view.findViewById(R.id.game_miniature)
        game_name = view.findViewById(R.id.game_name)
        game_note = view.findViewById(R.id.game_note)
        toogle_action = view.findViewById(R.id.toogle_action)
    }


    /**
     * Displays a game information in the view
     *
     * @param game: the game to be displayed
     */
    fun bind(game: Game, typeOfView: TypeOfView){
        Glide
            .with(view)
            .load(game.background_image)
            .into(game_miniature!!)

        game_name!!.text = game.name
        game_note!!.text = "${game.rating.toString()}/5"

        if (typeOfView == TypeOfView.ADDER){
            toogle_action!!.setBackgroundResource(R.drawable.add_circle)
        }
        else {
            toogle_action!!.setBackgroundResource(R.drawable.delete)
        }
    }
}