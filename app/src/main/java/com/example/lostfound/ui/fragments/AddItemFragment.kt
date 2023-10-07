package com.example.lostfound.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.lostfound.R
import com.example.lostfound.databinding.FragmentAddItemBinding
import com.google.api.Distribution.BucketOptions.Linear

class AddItemFragment : Fragment() {
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    private fun setOnClickListeners(){
        binding.selectImageView.setOnClickListener {
            createPhotoPickerDialog()
        }
    }

    private fun createPhotoPickerDialog(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.photo_picker_dialog)
        dialog.findViewById<LinearLayout>(R.id.gallery_photo).setOnClickListener {
            Toast.makeText(requireContext(), "Gallery select clicked", Toast.LENGTH_SHORT).show()
        }
        dialog.findViewById<LinearLayout>(R.id.camera_photo).setOnClickListener {
            Toast.makeText(requireContext(), "Take with camera clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        setOnClickListeners()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}