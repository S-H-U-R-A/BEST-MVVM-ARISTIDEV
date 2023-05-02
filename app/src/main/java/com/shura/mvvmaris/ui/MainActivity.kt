package com.shura.mvvmaris.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shura.mvvmaris.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() =  _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()

    }

    private fun initListeners() {

        binding.btnDataStore.setOnClickListener {

            Intent(
                this,
                PruebaDataStore::class.java
            ).also {
                startActivity(it)
            }

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}