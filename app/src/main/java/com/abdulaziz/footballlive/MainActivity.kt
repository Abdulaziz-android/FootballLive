package com.abdulaziz.footballlive

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abdulaziz.footballlive.databinding.ActivityMainBinding
import com.abdulaziz.footballlive.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                HomeFragment()
            ).commit()
        }

    }



}