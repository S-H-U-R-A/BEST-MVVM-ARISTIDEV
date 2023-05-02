package com.shura.mvvmaris.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shura.mvvmaris.data.local.datastore.UserPreferences
import com.shura.mvvmaris.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PruebaDataStoreViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()


    init {
        getUserPreferences()
    }

    private fun getUserPreferences() {

        _uiState.update { uiState ->
            uiState.copy(
                isLoading = true
            )
        }

        try {

            viewModelScope.launch {
                userPreferences.data.collect { namePreference ->

                    _uiState.update { uiState ->
                        uiState.copy(
                            isLoading = false,
                            textUser = namePreference
                        )
                    }

                }
            }

        } catch (e: Exception) {
            _uiState.update { uiState ->
                uiState.copy(
                    isLoading = false,
                    userMessage = "Error de consulta de datastore"
                )
            }
        }

    }

    fun saveUserPreference(name: String): Job = viewModelScope.launch(coroutineDispatcher) {
        userPreferences.saveData(name)
    }


}

data class MainUiState(
    val isLoading: Boolean = false,
    val textUser: String = "",
    val userMessage: String = ""
)