package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTheme(UserConfig.getThemeResourceId(this))
        val sharedPreferences = this.getSharedPreferences(
            "org.bedu.sharedpreferences",
            Context.MODE_PRIVATE
        )

        if(sharedPreferences?.getBoolean("USER_ACCESS", false) == true){
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        else{
            setContentView(R.layout.activity_main)
            supportActionBar?.hide()
        }
    }
}



