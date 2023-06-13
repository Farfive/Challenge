package com.example.challenge.domain.repository


import com.example.challenge.commons.utils.Constants.Companion.PER_PAGE
import com.example.challenge.datasource.api.PixabayApi

class PixabayRepository(private val pixabayApi: PixabayApi) {

    private val pixabayKey = "37155856-de62177f4d0f5b761b3077187"

    suspend fun fetchPixabayData(key: String, page: Int) =
        pixabayApi.getPixabayDataAsync(pixabayKey, key, page, PER_PAGE).await()
}