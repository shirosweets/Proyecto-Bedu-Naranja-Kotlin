package com.example.myapplication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuActivity : AppCompatActivity() {
    private val helpUrl = "https://www.bedu.org/"
    private lateinit var menuNavigationBottom: BottomNavigationView
    private lateinit var popSearch: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ConfigManager.setLocale(this, ConfigManager.getLanguage(this))
        this.setTheme(ConfigManager.getThemeResourceId(this))
        setContentView(R.layout.activity_menu)
        popSearch.findViewById(R.id.popSearchFrameLayout)
        popSearch.onFocusChangeListener()
        menuNavigationBottom = findViewById(R.id.bottomNavigationView)
        setupNavController()
    }

    private fun setupNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.shoppingCartFragment -> showBottomNav()
                R.id.homeFragment -> showBottomNav()
                R.id.profileFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }
        menuNavigationBottom.setupWithNavController(navController)
    }

    private fun showBottomNav() {
        menuNavigationBottom.visibility = View.VISIBLE
        menuNavigationBottom
            .animate()
            .translationY(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    menuNavigationBottom.visibility = View.VISIBLE
                }
            })
    }

    private fun hideBottomNav() {
        menuNavigationBottom
            .animate()
            .translationY(100f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    menuNavigationBottom.visibility = View.GONE
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

//    search = findViewById(R.id.top_menu_search)
//    search.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
//        override fun onQueryTextSubmit(query: String?): Boolean {
//            search.clearFocus()
//            return false
//        }
//
//        override fun onQueryTextChange(newText: String?): Boolean {
//            Log.v("MYDEBUG", "Querying $newText")
//            return true
//        }
//    })


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.top_menu_search -> {
                Toast.makeText(
                    this,
                    getString(R.string.search_toast_deac),
                    Toast.LENGTH_SHORT
                ).show()
            }
            R.id.top_menu_help -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(helpUrl)
                startActivity(intent)
            }
            else -> {
                Toast.makeText(
                    this,
                    "Functionality ${item.itemId} not implemented",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
