package com.example.lostfound.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lostfound.AddItemFragment
import com.example.lostfound.R
import com.example.lostfound.SettingsFragment
import com.example.lostfound.databinding.ActivityMainBinding
import com.example.lostfound.ui.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
        replaceFragment(HomeFragment())
    }

    private fun setOnClickListeners(){
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_add -> {
                    replaceFragment(AddItemFragment())
                    true
                }
                R.id.item_settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                R.id.item_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}