package com.example.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
//        setSupportActionBar(null)
//        closeOptionsMenu()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()
    }

//    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
//        menu?.close()
////        val menuItem = menu?.findItem(R.menu.menu_main)
////        menuItem?.setEnabled(false)
////        menuItem?.setVisible(false)
//        return super.onPrepareOptionsMenu(menu)
//    }

    // This doesn't do the trick
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val item: MenuItem = menu!!.findItem(R.menu.menu_main)
//        menuInflater.inflate(R.menu.menu_main, menu)
//        item.setEnabled(false)
//        item.setVisible(false)
////        super.onPrepareOptionsMenu(menu)
//        super.onCreateOptionsMenu(menu)
//        return true //
//    }
}