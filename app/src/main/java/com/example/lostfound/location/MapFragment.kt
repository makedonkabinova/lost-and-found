package com.example.lostfound.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lostfound.R
import com.example.lostfound.databinding.CountriesDropdownBinding
import com.example.lostfound.databinding.FragmentMapBinding
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.viewAnnotationOptions

class MapFragment : Fragment() {
    private var _binding : FragmentMapBinding? = null
    private val binding get() = _binding!!
    lateinit var mapView: MapView
    lateinit var viewAnnotationManager: ViewAnnotationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        mapView = binding.mapView
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
        viewAnnotationManager = binding.mapView.viewAnnotationManager
        val viewAnnotation = viewAnnotationManager.addViewAnnotation(R.layout.countries_dropdown,
            viewAnnotationOptions { geometry(Point.fromLngLat(41.8101, 21.0937)).anchor(ViewAnnotationAnchor.TOP) }
        )
        CountriesDropdownBinding.bind(viewAnnotation)
        return binding.root
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment MapFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            MapFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}