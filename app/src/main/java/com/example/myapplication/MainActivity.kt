package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTheme(UserConfig.getThemeResourceId(this))
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
}



