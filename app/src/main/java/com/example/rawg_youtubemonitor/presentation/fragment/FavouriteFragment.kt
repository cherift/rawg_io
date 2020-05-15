package com.example.rawg_youtubemonitor.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rawg_youtubemonitor.R

class FavouriteFragment : Fragment() {

    var rootView : View? = null

    companion object {
        fun newInstance() : FavouriteFragment  = FavouriteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.save_fragment, container, false)

        return rootView
    }
}