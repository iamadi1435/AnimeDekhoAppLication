package com.example.animedekho.ui.anime.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animedekho.data.remote.DataResult
import com.example.animedekho.data.remote.asResult
import com.example.animedekho.domain.entity.AnimeBasicDetails
import com.example.animedekho.domain.entity.AnimeDetails
import com.example.animedekho.domain.usecase.TopAnimeListUseCase
import com.example.animedekho.ui.utils.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AnimeScreenViewModel(
    private val topAnimeListUseCase: TopAnimeListUseCase
) : ViewModel() {

    private val _topAnimeListState =
        MutableStateFlow<UiState<List<AnimeBasicDetails>>>(UiState.Default)
    val topAnimeListState = _topAnimeListState

    init {
        getTopAnimeList()
    }

    fun getTopAnimeList() {
        viewModelScope.launch {
            topAnimeListUseCase.refreshTopAnime()

            topAnimeListUseCase.getTopAnimeList()
                .asResult()
                .collect { result ->
                    when (result) {
                        DataResult.Loading ->
                            _topAnimeListState.value = UiState.Loading

                        is DataResult.Error ->
                            _topAnimeListState.value =
                                UiState.Error(result.message)

                        is DataResult.Success ->
                            _topAnimeListState.value =
                                UiState.Success(result.data)
                    }
                }
        }
    }

    private val selectedAnimeId = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val animeDetailState: StateFlow<UiState<AnimeDetails>> =
        selectedAnimeId
            .filterNotNull()
            .flatMapLatest { animeId ->
                flow {
                    emit(UiState.Loading)

                    topAnimeListUseCase.refreshAnimeDetails(animeId)

                    topAnimeListUseCase.getAnimeDetails(animeId)
                        .collect { details ->
                            emit(UiState.Success(details))
                        }
                }.catch { e ->
                    emit(UiState.Error(e.message ?: "Something went wrong"))
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                UiState.Default
            )

    fun getAnimeDetails(animeId: Int) {
        selectedAnimeId.value = animeId
    }

}
