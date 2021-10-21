package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTheme(ConfigManager.getThemeResourceId(this))
        if (LoginManager.isLoggedIn(this)) {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        else {
            setContentView(R.layout.activity_main)
            supportActionBar?.hide()
        }
    }
}
