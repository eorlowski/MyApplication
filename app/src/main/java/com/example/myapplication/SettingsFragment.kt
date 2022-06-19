package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        getSupportActionBar()?.setTitle("Settings")
//    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) {
//        val rootView = inflater.inflate(R.layout.activity_settings, container, false)
//        val toolbar = rootView.findViewById<Toolbar>(R.id.toolbar)
//        toolbar.setTitle("Settings")
//    }
}