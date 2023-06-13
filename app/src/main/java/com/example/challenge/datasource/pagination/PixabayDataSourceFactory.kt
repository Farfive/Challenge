package com.example.challenge.datasource.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.challenge.datasource.pagination.PixabayDataSource
import com.example.challenge.domain.entities.HitsList
import com.example.challenge.domain.repository.PixabayRepository
import kotlinx.coroutines.CoroutineScope

class PixabayDataSourceFactory(
    private val pixabayRepository: PixabayRepository,
    private val scope: CoroutineScope,
    private var query: String = ""
) : DataSource.Factory<Int, HitsList>() {
    val dataSource = MutableLiveData<PixabayDataSource>()

    override fun create(): DataSource<Int, HitsList> {

        val dataSource = PixabayDataSource(pixabayRepository, scope, query)
        this.dataSource.postValue(dataSource)
        return dataSource
    }

    // --- PUBLIC API
    fun getQuery() = query

    private fun getSource() = dataSource.value

    fun updateQuery(query: String) {
        this.query = query
        getSource()?.refresh()
    }
}
