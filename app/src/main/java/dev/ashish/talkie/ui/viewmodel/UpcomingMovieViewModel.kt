package dev.ashish.talkie.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ashish.talkie.data.model.MoviesDto
import dev.ashish.talkie.repository.NowPlayingRepo
import dev.ashish.talkie.ui.base.UiState
import dev.ashish.talkie.utils.DispatcherProvider
import dev.ashish.talkie.utils.NetworkHelper
import dev.ashish.talkie.utils.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpcomingMovieViewModel @Inject constructor(
    private val nowPlayingRepo: NowPlayingRepo,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
    private val logger: Logger
) : ViewModel() {
    private val _upcomingMovieUiState = MutableStateFlow<UiState<List<MoviesDto>>>(UiState.Loading)
     val upcomingUiState: StateFlow<UiState<List<MoviesDto>>> = _upcomingMovieUiState
    private fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

    init {
        if (checkInternetConnection()) {
            fetchUpcomingMovie()
        } else {
            _upcomingMovieUiState.value = UiState.Error("No Internet Connection")
        }
    }

    private fun fetchUpcomingMovie() {
        viewModelScope.launch(dispatcherProvider.main) {
            nowPlayingRepo.getNowPlayingList("upcoming", 1)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _upcomingMovieUiState.value = UiState.Error(e.toString())
                }
                .map {
                    it.map { moviesDto -> moviesDto }
                }
                .collect {
                    _upcomingMovieUiState.value = UiState.Success(it)
                    logger.logger("Upcoming", "success")
                }
        }
    }
}