package com.example.animedekho.ui.anime.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animedekho.data.repository.TopAnimeListRepositoryImplementation
import com.example.animedekho.domain.usecase.TopAnimeListUseCase
import javax.inject.Inject

class AnimeViewModelFactory @Inject constructor (
    private val topAnimeListUseCase: TopAnimeListUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnimeScreenViewModel(
            topAnimeListUseCase
        ) as T
    }
}
