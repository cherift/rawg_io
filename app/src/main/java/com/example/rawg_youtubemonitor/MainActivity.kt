package com.example.rawg_youtubemonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.rawg_youtubemonitor.presentation.fragment.FavouriteFragment
import com.example.rawg_youtubemonitor.presentation.fragment.HomeFragment
import com.example.rawg_youtubemonitor.presentation.fragment.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
            Initialising home framgment without backstacking so that to
            avoid empty screen when back button press on the first fragment.
         */
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragment_container, HomeFragment.newInstance())
        fragmentTransaction.commit()

        // manging bottom menu selection
        setupNavigationElements()
    }

    /**
     * Displays the fragment passed as parameter by replacing it by the last one
     *
     * @param fragment : the fragment to display
     */
    fun replaceFragment(fragment: Fragment?) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragment?.let { fragmentTransaction.replace(R.id.fragment_container, it) }
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }


    /**
     * Accourding to the navigation button selected, this method sets the appropriate fragment.
     */
    fun setupNavigationElements() {

        val  navigationButton : BottomNavigationView = findViewById(R.id.bottom_navigation)

        navigationButton.setOnNavigationItemSelectedListener(object: BottomNavigationView.OnNavigationItemSelectedListener{

            override fun onNavigationItemSelected(itemMenu: MenuItem): Boolean {
                when (itemMenu.itemId) {
                    R.id.home -> {
                        val fragment : Fragment = HomeFragment.newInstance()
                        replaceFragment(fragment)
                        return true
                    }
                    R.id.favourites -> {
                        val fragment : Fragment = FavouriteFragment.newInstance()
                        replaceFragment(fragment)
                        return true
                    }
                    R.id.search -> {
                        val fragment : Fragment = SearchFragment.newInstance()
                        replaceFragment(fragment)
                        return true
                    }
                    else -> return false

                }
            }
        })

    }
}
