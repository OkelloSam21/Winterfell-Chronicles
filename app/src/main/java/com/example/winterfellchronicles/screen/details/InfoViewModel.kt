package com.example.winterfellchronicles.screen.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.winterfellchronicles.data.remote.WinterFellCharacterDetailsResponse
import com.example.winterfellchronicles.data.repository.WinterfellRepository
import com.example.winterfellchronicles.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val winterfellRepository: WinterfellRepository
) : ViewModel() {

    private val _characterInfoUiState = MutableStateFlow(CharacterInfoUiState())
    val characterInfoUiState = _characterInfoUiState.asStateFlow()

    fun getCharactersInfo(id: Int) {
        viewModelScope.launch {
            _characterInfoUiState.update {
                it.copy(
                    isLoading = true,
                )
            }

            when (val response = winterfellRepository.getWinterFellCharactersDetails(id)) {
                is Resource.Success -> {
                    Log.d("InfoViewModel", "getCharactersInfo: ${response.data}")
                    _characterInfoUiState.update {
                        it.copy(
                            isLoading = false,
                            data = response.data,
                            error = null
                        )
                    }
                }

                is Resource.Error -> {
                    _characterInfoUiState.update {
                        it.copy(
                            isLoading = false,
                            error = response.message
                        )
                    }
                }
            }
        }
    }
}
data class CharacterInfoUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: WinterFellCharacterDetailsResponse? = null,
)

