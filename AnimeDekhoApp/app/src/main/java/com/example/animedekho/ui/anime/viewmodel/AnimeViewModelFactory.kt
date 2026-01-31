package com.example.animedekho.ui.anime.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animedekho.data.repository.TopAnimeListRepositoryImplementation
import com.example.animedekho.domain.usecase.TopAnimeListUseCase

class AnimeViewModelFactory(
    private val repository: TopAnimeListRepositoryImplementation
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnimeScreenViewModel(
            TopAnimeListUseCase(repository)
        ) as T
    }
}
