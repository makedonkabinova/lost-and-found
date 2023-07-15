package com.example.lostfound.location

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import com.example.lostfound.Constants
import com.example.lostfound.R
import com.example.lostfound.databinding.FragmentLocationChoiceBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationChoiceFragment : Fragment() {

    private var _binding : FragmentLocationChoiceBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var sharedPreferences: SharedPreferences

    private val requestLocationPermissions: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            it.forEach{permission ->
                val name = permission.key
                val granted = permission.value
                //TODO: Put the toast messages into string.xml
                if(name == ACCESS_COARSE_LOCATION){
                    if(granted)
                        Toast.makeText(requireContext(), "Permission for COARSE LOCATION allowed", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(requireContext(), "Please allow permission for location or enter a custom location", Toast.LENGTH_SHORT).show()
                }else if(name == ACCESS_FINE_LOCATION){
                    if(granted)
                        Toast.makeText(requireContext(), "Permission for FINE LOCATION allowed", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(requireContext(), "Please allow permission for location or enter a custom location", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLocationChoiceBinding.inflate(inflater, container, false)
        setOnClickListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private fun setOnClickListeners(){
        binding.deviceLocationButton.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(requireActivity().applicationContext, ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED &&
                ActivityCompat.checkSelfPermission(requireActivity().applicationContext, ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_DENIED) //if FINE and COARSE locations permissions are both denied, request them
                    requestLocationPermissions.launch(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))
            else //if at least one permission is allowed, get the location
                getLocation()
        }
    }

    private fun getLocation(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(), ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return //we need to check for the permissions, although we have that in the listener too
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            //if location is not null, put the info in the shared preferences
            location?.let {
                sharedPreferences.edit {
                    putFloat(Constants.LATITUDE, it.latitude.toFloat())
                    putFloat(Constants.LONGITUDE, it.longitude.toFloat())
                }
                Toast.makeText(binding.root.context, binding.root.context.getString(R.string.location_fetch_success_msg), Toast.LENGTH_SHORT).show()
            }

            if(location == null)
                Toast.makeText(binding.root.context, binding.root.context.getString(R.string.location_fetch_fail_msg), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}