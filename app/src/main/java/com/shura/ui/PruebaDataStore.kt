package com.shura.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.shura.mvvmaris.PruebaDataStoreViewModel
import com.shura.mvvmaris.databinding.ActivityPruebaDataStoreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PruebaDataStore : AppCompatActivity() {

    private var _binding: ActivityPruebaDataStoreBinding? = null
    val binding get() =  _binding!!


    private val viewModel: PruebaDataStoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPruebaDataStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObservers()
        initListeners()

    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{ uiState ->
                    binding.mtvShowName.text = uiState.textUser
                }
            }
        }
    }

    private fun initListeners() {
        binding.btnSend.setOnClickListener {
            val name = binding.tietWriteName.text.toString().trim()
            viewModel.saveUserPreference(name)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}