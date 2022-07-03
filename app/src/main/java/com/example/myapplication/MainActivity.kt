package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.activity.viewModels
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.Image
import com.example.myapplication.model.ImageListViewModel
import com.example.myapplication.model.ImageListViewModelFactory
import com.example.myapplication.model.ImagesAdapter
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    val imageListViewModel by viewModels<ImageListViewModel> {
        ImageListViewModelFactory(this)
    }
    val imagesAdapter = ImagesAdapter { image -> adapterOnClick(image)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            resultLauncher.launch(intent)
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

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            val uri: Uri? = data?.data
            val uriString: String = uri!!.toString()
            val myFile = File(uriString)
            val path: String = myFile.getAbsolutePath()
            var displayName: String? = null

            if (uriString.startsWith("content://")) {
                var cursor: Cursor? = null
                try {
                    cursor = contentResolver.query(uri, null, null, null, null)
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName =
                            cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                } finally {
                    cursor!!.close()
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName()
            }
            println(displayName)
            imageListViewModel.addImage(Image(displayName?:"noname"))

//            doSomeOperations()
// TODO: constructor of this class should get DataSource as an argument!
        }
    }

}