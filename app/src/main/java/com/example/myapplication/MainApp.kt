package com.example.myapplication

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val databaseName = getString(R.string.realm_database)
        val config = RealmConfiguration.Builder().name(databaseName).build()
        Realm.setDefaultConfiguration(config)
    }
}