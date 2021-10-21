package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import io.realm.Realm
import io.realm.RealmResults

class MainActivity : AppCompatActivity() {
    val realm: Realm = Realm.getDefaultInstance()
    val products: RealmResults<Product> = realm.where(Product::class.java).findAll()
    init {
        Log.d("MYDEBUG","$products")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTheme(UserConfig.getThemeResourceId(this))
        val sharedPreferences = this.getSharedPreferences(
            getString(R.string.login_shared_preference_file),
            Context.MODE_PRIVATE
        )
        if(sharedPreferences.getBoolean("USER_ACCESS", false)) {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        else {
            setContentView(R.layout.activity_main)
            supportActionBar?.hide()
        }
    }
}
