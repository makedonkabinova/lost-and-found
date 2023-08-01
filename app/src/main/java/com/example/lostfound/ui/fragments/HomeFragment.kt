package com.example.lostfound.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Carousel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.lostfound.R
import com.example.lostfound.data.models.Item
import com.example.lostfound.data.models.Type
import com.example.lostfound.databinding.FragmentHomeBinding
import com.example.lostfound.ui.adapters.Adapter
import com.google.android.material.card.MaterialCardView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.MultiBrowseCarouselStrategy
import com.google.android.material.circularreveal.CircularRevealHelper.Strategy
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val items = arrayListOf(Item(UUID.randomUUID(), Type.LOST, "wallet", "black", R.drawable.img, 41.1, 42.5, "makedonka", Date()),
    Item(UUID.randomUUID(), Type.LOST, "wallet", "black", R.drawable.img_1, 41.1, 42.5, "makedonka", Date()),
    Item(UUID.randomUUID(), Type.LOST, "wallet", "black", R.drawable.img_2, 41.1, 42.5, "makedonka", Date()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.carouselLostRecyclerView.layoutManager = CarouselLayoutManager()
        val adapterLost = Adapter(items, object:Adapter.MyOnClick{
            override fun onClick(p0: View?, pos:Int) {
                Toast.makeText(requireContext(), "clicked", Toast.LENGTH_SHORT).show()
            }
        })
        binding.carouselLostRecyclerView.adapter = adapterLost
        binding.carouselFoundRecyclerView.layoutManager = CarouselLayoutManager()
        val adapterFound = Adapter(items, object:Adapter.MyOnClick{
            override fun onClick(p0: View?, pos:Int) {
                Toast.makeText(requireContext(), "clicked", Toast.LENGTH_SHORT).show()
            }
        })
        binding.carouselFoundRecyclerView.adapter = adapterFound
        val snapHelper = PagerSnapHelper()
        //snapHelper.attachToRecyclerView(binding.carouselRecyclerView)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}