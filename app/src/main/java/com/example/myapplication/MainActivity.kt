package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.activity.viewModels
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.Image
import com.example.myapplication.model.ImageListViewModel
import com.example.myapplication.model.ImageListViewModelFactory
import com.example.myapplication.model.ImagesAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    val imageListViewModel by viewModels<ImageListViewModel> {
        ImageListViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imagesAdapter = ImagesAdapter { image -> adapterOnClick(image)}

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

//        val imagesAdapter = ImagesAdapter { image -> adapterOnClick(image)}
        val recyclerView: RecyclerView = findViewById(androidx.preference.R.id.recycler_view)
        recyclerView.adapter = imagesAdapter
//
        imageListViewModel.imageLiveData.observe( this, {
            it?.let {
                imagesAdapter.submitList(it as MutableList<Image>)
            }
        })
    }

    private fun adapterOnClick(image: Image) {
        // do nothing
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, SettingsActivity::class.java)
//        if (this::navController.isInitialized) {
//             item.onNavDestinationSelected(this.navController) ||
//               super.onOptionsItemSelected(item)
//           } else false
        startActivity(intent)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}