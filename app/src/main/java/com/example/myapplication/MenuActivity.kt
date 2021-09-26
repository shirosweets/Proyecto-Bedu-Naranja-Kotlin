package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuActivity : AppCompatActivity() {
    private val helpUrl = "https://www.bedu.org/"
    private lateinit var menuNavigationBottom : BottomNavigationView

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.top_menu_search -> {
                Toast.makeText(
                    this,
                    "La búsqueda está deshabilitada",
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        menuNavigationBottom =  findViewById(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        menuNavigationBottom.setupWithNavController(navController)
    }
}