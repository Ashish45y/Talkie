package dev.ashish.talkie.repository

import dev.ashish.talkie.data.api.NetworkService
import dev.ashish.talkie.data.model.MoviesDto
import dev.ashish.talkie.di.ActivityScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityScope
class NowPlayingRepo @Inject constructor(private val networkService: NetworkService) {
    fun getNowPlayingList(category: String, page: Int) : Flow<List<MoviesDto >>{
        return flow {
            emit(networkService.getMoviesList(category,page))
        }.map {
            it.results
        }
    }
}