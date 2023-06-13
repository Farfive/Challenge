package com.example.challenge.datasource.api

import com.example.challenge.commons.utils.Constants.Companion.API_URL
import com.example.challenge.commons.utils.Constants.Companion.KEY_TAG
import com.example.challenge.commons.utils.Constants.Companion.PAGE_TAG
import com.example.challenge.commons.utils.Constants.Companion.PER_PAGE_TAG
import com.example.challenge.commons.utils.Constants.Companion.QUERY_TAG
import com.example.challenge.domain.entities.PixabayApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET(API_URL)
    fun getPixabayDataAsync(
        @Query(KEY_TAG) key: String,
        @Query(QUERY_TAG) query: String,
        @Query(PAGE_TAG) page: Int,
        @Query(PER_PAGE_TAG) perPage: Int
    ): Deferred<Response<PixabayApiResponse>>
}
