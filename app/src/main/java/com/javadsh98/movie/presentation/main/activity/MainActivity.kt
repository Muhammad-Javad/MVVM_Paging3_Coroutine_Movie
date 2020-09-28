package com.javadsh98.movie.presentation.main.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.javadsh98.movie.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var appbarConfige: AppBarConfiguration
    lateinit var navController: NavController

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setNightMode()

        navController = findNavController(R.id.fragment_main)
        val navigationView = bottomNavigationView_main

        appbarConfige = AppBarConfiguration(
            setOf(
                R.id.movieFragment,
                R.id.seriesFragment,
                R.id.favoriteFragment
            )
        )
        setupActionBarWithNavController(navController, appbarConfige)
        navigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {

                R.id.movieFragment -> navigationView.visibility = View.VISIBLE
                R.id.seriesFragment -> navigationView.visibility = View.VISIBLE
                R.id.favoriteFragment -> navigationView.visibility = View.VISIBLE

                else -> navigationView.visibility = View.GONE
            }
        }

    }

    private fun setNightMode() {
        val value = sharedPreferences.getString("theme", "1")
        when (value) {
            "1" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "2" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appbarConfige) || super.onSupportNavigateUp()
    }

}