package com.example.challenge.ui.pixabay.list


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.challenge.commons.communicator.base.BaseViewModel
import com.example.challenge.commons.utils.Constants.Companion.PER_PAGE
import com.example.challenge.datasource.api.NetworkState
import com.example.challenge.datasource.pagination.PixabayDataSourceFactory
import com.example.challenge.domain.entities.HitsList
import com.example.challenge.domain.repository.PixabayRepository

class PixabayListVM(private val pixabayRepository: PixabayRepository) : BaseViewModel() {

    // FOR DATA ---
    private val pixabayDataSource =
        PixabayDataSourceFactory(pixabayRepository = pixabayRepository, scope = ioScope)

    // OBSERVABLES ---
    val pixabayData: LiveData<PagedList<HitsList>> =
        pixabayDataSource.sortById().toLiveData(pagedListConfig())

    val networkState: LiveData<NetworkState<Int>>? =
        Transformations.switchMap(pixabayDataSource.dataSource) { it.getNetworkState() }

    init {
        // Trigger initial search for "fruits" when the app starts
        fetchKeyQuery("fruits")
    }

    // PUBLIC API ---

    /**
     * Fetch a list of [HitsList] by Query
     */
    fun fetchKeyQuery(query: String) {
        if (pixabayDataSource.getQuery() == query.trim()) return
        pixabayDataSource.updateQuery(query.trim())
    }

    // UTILS ---
    private fun pagedListConfig() = PagedList.Config.Builder().setPageSize(PER_PAGE).build()

    //https://stackoverflow.com/questions/51320874/sort-array-items-when-using-pagedlistadapter
    /** sorts HitsList by ID in descending order */
    private fun DataSource.Factory<Int, HitsList>.sortById(): DataSource.Factory<Int, HitsList> {
        return mapByPage {
            it.sortedWith(compareByDescending { item -> item.id })
        }
    }
}
