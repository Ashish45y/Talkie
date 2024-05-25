package dev.ashish.talkie.data.api

import dev.ashish.talkie.data.model.MoviesResponse
import dev.ashish.talkie.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MoviesResponse

}