package com.example.myapplication

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.model.Image
import com.example.myapplication.model.ImageListViewModel
import com.example.myapplication.model.ImagesAdapter
import java.io.File


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
//    private var imageListViewModel: ImageListViewModel? = null
//    val imagesAdapter = ImagesAdapter { image -> adapterOnClick(image)}
//    private var layoutManager: RecyclerView.LayoutManager? = null
//    private var adapter: RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>? = null

    val imagesAdapter = ImagesAdapter { image -> adapterOnClick(image)}

    private fun adapterOnClick(image: Image) {
        println("Clicked on image ${image.fileName}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

//        val view = inflater.inflate(R.layout.fragment_first, container, false)

//        val recylerView = view.findViewById<RecyclerView>(R.id.recycler_view)
//        view.recyclerView.layoutManager = LinearLayoutManager(activity)
//        view.recyclerView.adapter = imagesAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = imagesAdapter

        (activity as MainActivity).imageListViewModel.imageLiveData.observe( activity as MainActivity, {
            it?.let {
                imagesAdapter.submitList(it as MutableList<Image>)
            }
        })

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.button2.setOnClickListener {
            // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
            // browser.
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

            // Filter to only show results that can be "opened", such as a
            // file (as opposed to a list of contacts or timezones)
            intent.addCategory(Intent.CATEGORY_OPENABLE)

            // Filter to show only images, using the image MIME data type.
            // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
            // To search for all documents available via installed storage providers,
            // it would be "*/*".
            intent.type = "image/*"

            resultLauncher.launch(intent)
        }
//        imageListViewModel.imageLiveData.observe(viewLifecycleOwner, Observer<Image> {
//            item ->
//                imagesAdapter.submitList(item as MutableList<Image>)})

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
                    cursor = activity!!.contentResolver.query(uri, null, null, null, null)
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
            (activity!! as MainActivity).imageListViewModel.addImage(Image(uri, displayName?:"noname"))

//            doSomeOperations()
// TODO: constructor of this class should get DataSource as an argument!
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}