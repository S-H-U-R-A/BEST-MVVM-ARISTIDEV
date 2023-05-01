package com.shura.mvvmaris

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shura.mvvmaris.data.local.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class PruebaDataStoreViewModel @Inject constructor(
    private val  userPreferences: UserPreferences
) : ViewModel() {

    private var _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()


    init {
        getUserPreferences()
    }

    fun getUserPreferences(){

        _uiState.update { uiState ->
            uiState.copy(
                isLoading = true
            )
        }

        try {

            viewModelScope.launch( Dispatchers.IO ) {
                userPreferences.data.collect{ namePreference ->

                    _uiState.update { uiState ->
                        uiState.copy(
                            isLoading = false,
                            textUser = namePreference
                        )
                    }

                }
            }

        }catch (e: Exception){
            _uiState.update { uiState ->
                uiState.copy(
                    isLoading = false,
                    userMessage = "Error de consulta de datastore"
                )
            }
        }

    }

    fun saveUserPreference(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            userPreferences.saveData(name)
        }
    }

}

data class MainUiState(
    val isLoading: Boolean = false,
    val textUser: String = "",
    val userMessage: String = ""
)