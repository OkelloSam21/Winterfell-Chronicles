package com.example.winterfellchronicles.screen.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.winterfellchronicles.data.remote.WinterFellResponse
import com.example.winterfellchronicles.data.repository.WinterfellRepository
import com.example.winterfellchronicles.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val winterfellRepository: WinterfellRepository,
) : ViewModel() {
    private val _winterfellCharacterUiState = MutableStateFlow(WinterfellCharacterUiState())
    val winterFellCharacterUiState = _winterfellCharacterUiState.asStateFlow()

    fun getWinterfellCharacters() {
        viewModelScope.launch {
            _winterfellCharacterUiState.update {
                it.copy(isLoading = true)
            }

            when (val result = winterfellRepository.getWinterfellCharacters()) {
                is Resource.Success -> {
                    _winterfellCharacterUiState.update {
                        it.copy(
                            isLoading = false,
                            data = result.data ?: emptyList(),
                        )
                    }
                }
                is Resource.Error -> {
                    _winterfellCharacterUiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Unknown Error",
                        )
                    }
                }
            }
        }
    }

    init {
        getWinterfellCharacters()
    }

    data class WinterfellCharacterUiState(
        val isLoading: Boolean = false,
        val data: List<WinterFellResponse> = emptyList(),
        val error: String? = null,
    )
}
