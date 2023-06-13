package com.example.challenge.di

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.paging.toLiveData
import com.example.challenge.commons.communicator.base.BaseViewModel
import com.example.challenge.commons.utils.Constants.Companion.PER_PAGE
import com.app.androidPixabay.commons.utils.UiHelper
import com.example.challenge.datasource.api.NetworkState
import com.example.challenge.datasource.api.PixabayApi
import com.example.challenge.datasource.api.createNetworkClient
import com.example.challenge.datasource.pagination.PixabayDataSourceFactory
import com.example.challenge.domain.entities.HitsList
import com.example.challenge.domain.repository.PixabayRepository
import com.example.challenge.ui.pixabay.list.PixabayListVM
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "YOUR_BASE_URL"

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            viewModelModule,
            networkModule,
            databaseModule,
            uiHelperModule
        )
    )
}

val viewModelModule = module {
    viewModel { PixabayListVM(pixabayRepository = get() as PixabayRepository) }
}



val networkModule = module {
    single { pixabayApi }
}

val databaseModule = module {
    single { providePixabayDatabase(androidContext()) }
    single { provideHitsListDao(get()) }
}

val uiHelperModule = module {
    single { UiHelper(androidContext()) }
}

private fun providePixabayDatabase(context: Context): PixabayDatabase {
    return Room.databaseBuilder(context, PixabayDatabase::class.java, "pixabay-db")
        .build()
}

private fun provideHitsListDao(database: PixabayDatabase): HitsListDao {
    return database.hitsListDao()
}

private val retrofit: Retrofit = createNetworkClient(BASE_URL)

private val pixabayApi: PixabayApi = retrofit.create(PixabayApi::class.java)

@Dao
interface HitsListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(hitsList: List<HitsList>)


}

@Database(entities = [HitsList::class], version = 1)
abstract class PixabayDatabase : RoomDatabase() {
    abstract fun hitsListDao(): HitsListDao
}

class PixabayListVM(
    private val pixabayRepository: PixabayRepository,
    viewModelScope: CoroutineScope
) : BaseViewModel() {

    private val pixabayDataSource =
        PixabayDataSourceFactory(pixabayRepository = pixabayRepository, scope = viewModelScope)

    val pixabayData: LiveData<PagedList<HitsList>> =
        pixabayDataSource.sortById().toLiveData(pagedListConfig())

    val networkState: LiveData<NetworkState<Int>>? =
        Transformations.switchMap(pixabayDataSource.dataSource) { it.getNetworkState() }

    init {
        fetchKeyQuery("fruits") // Trigger initial search for "fruits" when the app starts
    }

    fun fetchKeyQuery(query: String) {
        if (pixabayDataSource.getQuery() == query.trim()) return
        pixabayDataSource.updateQuery(query.trim())
    }

    private fun pagedListConfig() = PagedList.Config.Builder().setPageSize(PER_PAGE).build()

    private fun DataSource.Factory<Int, HitsList>.sortById(): DataSource.Factory<Int, HitsList> {
        return mapByPage {
            it.sortedWith(compareByDescending { item -> item.id })
        }
    }
}



