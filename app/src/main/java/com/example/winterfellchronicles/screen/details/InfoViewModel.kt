package com.example.winterfellchronicles.screen.details

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
class InfoViewModel @Inject constructor(
    private val winterfellRepository: WinterfellRepository,
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
            when (val result = winterfellRepository.getWinterfellCharactersDetails(id)) {
                is Resource.Error -> {
                    _characterInfoUiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message,
                        )
                    }
                }

                is Resource.Success -> {
                    _characterInfoUiState.update {
                        it.copy(
                            isLoading = false,
                            data = result.data?.firstOrNull(),
                        )
                    }
                }
            }
        }
    }

    data class CharacterInfoUiState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val data: WinterFellResponse? = null,
    )
}
