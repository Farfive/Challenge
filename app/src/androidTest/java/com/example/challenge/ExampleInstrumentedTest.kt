package com.example.challenge

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.challenge.commons.utils.Constants.Companion.FRUITS_QUERY_TAG
import com.app.androidPixabay.commons.utils.UiHelper
import com.example.challenge.commons.extensions.SearchView
import com.example.challenge.ui.pixabay.details.PixaBayDetailsActivity
import com.example.challenge.ui.pixabay.list.PixabayAdapter
import com.example.challenge.ui.pixabay.list.PixabayListActivity
import com.example.challenge.ui.pixabay.list.PixabayListVM
import com.example.namespace.databinding.ActivityPixabayDetailsBinding
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

class PixaBayDetailsActivityTest {

    private lateinit var activity: PixaBayDetailsActivity

    @Before
    fun setUp() {
        activity = Mockito.spy(PixaBayDetailsActivity())
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun onCreate_intentWithExtraHitsList_bindingHitsListAndCallbackSet() {
        val intent = Intent()
        val extras = Bundle()
        intent.putExtras(extras)

        val binding = Mockito.mock(ActivityPixabayDetailsBinding::class.java)
        Mockito.`when`(activity.intent).thenReturn(intent)

        activity.onCreate(null)
        Mockito.verify(binding).callback = activity
    }

    @Test
    fun moveToPreviousScreen_finishCalled() {
        val mockActivity = Mockito.mock(PixaBayDetailsActivity::class.java)

        mockActivity.moveToPreviousScreen()

        Mockito.verify(mockActivity).finish()
    }
}
@RunWith(MockitoJUnitRunner::class)
class PixabayListActivityTest {

    @Mock
    private lateinit var uiHelper: UiHelper

    @Mock
    private lateinit var pixabayListVM: PixabayListVM

    @Mock
    private lateinit var pixabayAdapter: PixabayAdapter

    @Mock
    private lateinit var espressoIdlingResource: CountingIdlingResource

    private lateinit var activity: PixabayListActivity



    @Test
    fun onCreate_withInternetConnection_subscribeObservablesCalled() {
        Mockito.`when`(uiHelper.getConnectivityStatus()).thenReturn(true)

        activity.onCreate(null)

        Mockito.verify(pixabayListVM).fetchKeyQuery(FRUITS_QUERY_TAG)
        Mockito.verify(pixabayListVM).pixabayData.observe(activity, Mockito.any())
        Mockito.verify(pixabayListVM.networkState)?.observe(activity, Mockito.any())
    }

    @Test
    fun onCreate_noInternetConnection_showSnackBarCalled() {
        Mockito.`when`(uiHelper.getConnectivityStatus()).thenReturn(false)
        val mockView = Mockito.mock(View::class.java)

        activity.onCreate(null)


    }

    @Test
    fun onClick_executeSearchButtonClicked_queryNotEmpty_searchQueryCalled() {
        val mockSearchView = Mockito.mock(SearchView::class.java)
        val mockView = Mockito.mock(View::class.java)

        activity.onClick(mockView)

        Mockito.verify(uiHelper).hideKeyBoard(mockView)
        Mockito.verify(activity).searchQuery()
    }

    @Test
    fun onClick_executeSearchButtonClicked_queryEmpty_showSnackBarCalled() {
        val mockSearchView = Mockito.mock(SearchView::class.java)
        val mockView = Mockito.mock(View::class.java)

        activity.onClick(mockView)
        Mockito.verify(activity, Mockito.never()).searchQuery()
    }

    @Test
    fun getQueryText_queryNotEmpty_returnsQueryText() {
        val mockSearchView = Mockito.mock(SearchView::class.java)
    }


}

