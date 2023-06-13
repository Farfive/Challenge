package com.example.challenge.ui.pixabay.list

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.challenge.commons.communicator.HitsItem
import com.example.challenge.commons.utils.Constants
import com.app.androidPixabay.commons.utils.UiHelper
import com.example.challenge.datasource.api.NetworkState
import com.example.challenge.commons.extensions.SearchView
import com.example.challenge.domain.entities.HitsList
import com.example.challenge.ui.pixabay.details.PixaBayDetailsActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.example.challenge.commons.utils.Constants.Companion.FRUITS_QUERY_TAG
import com.example.namespace.R


class PixabayListActivity : AppCompatActivity(), View.OnClickListener, HitsItem {

    private val uiHelper: UiHelper by inject()
    private val pixabayListVM: PixabayListVM by viewModel()
    val pixabayAdapter = PixabayAdapter()
    private var query: String = FRUITS_QUERY_TAG
    private val espressoIdlingResource = CountingIdlingResource("PixabayListActivity") // Declare the idling resource
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pixabay_list)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        savedInstanceState?.getString(Constants.EXTRA_QUERY)?.let { query = it }

        initRecyclerView()

        /*
         * Check Internet Connection
         * */

        if (uiHelper.getConnectivityStatus()) subscribeObservables(query)
        else uiHelper.showSnackBar(findViewById(android.R.id.content), resources.getString(R.string.error_message_network))

        findViewById<View>(R.id.execute_search_button).setOnClickListener(this)
    }


    private fun subscribeObservables(query: String) {
        pixabayListVM.fetchKeyQuery(query)
        pixabayListVM.pixabayData.observe(this, Observer { pixabayAdapter.submitList(it) })

        pixabayListVM.networkState?.observe(this, Observer { networkState ->
            espressoIdlingResource.increment() // Increment the resource before network call
            espressoIdlingResource.decrement() // Decrement the resource after network call
            /*
                 * Progress Updater
                 * */

            when (networkState) {
                is NetworkState.Loading -> {
                    espressoIdlingResource.increment()
                    showProgressBar(true)
                }

                is NetworkState.Success -> {
                    espressoIdlingResource.decrement()
                    showProgressBar(false)
                }

                is NetworkState.Error -> showProgressBar(false)
                else -> {}
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.execute_search_button -> {
                val searchInputText = findViewById<SearchView>(R.id.searchView)
                if (!TextUtils.isEmpty(searchInputText.getQueryText())) {
                    uiHelper.hideKeyBoard(v)
                    query = searchInputText.getQueryText()
                    searchQuery()
                } else {
                    uiHelper.showSnackBar(findViewById(android.R.id.content), resources.getString(R.string.enter_query_to_search))
                }
            }
        }
    }

    fun SearchView.getQueryText(): String {
        return query.toString()
    }


    fun searchQuery() {
        val closeSearchButton = findViewById<View>(R.id.close_search_button)
        closeSearchButton.performClick()
        subscribeObservables(query)
    }

    // Save data to Bundle when screen rotates
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constants.EXTRA_QUERY, query)
    }

    // Restore data from Bundle when screen rotates
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString(Constants.EXTRA_QUERY)?.let { query = it }
    }

    // Setup the adapter class for the RecyclerView
    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recylv_pixabay)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = pixabayAdapter
        pixabayAdapter.setOnHitsItemClickListener(this)
    }

    // UPDATE UI ----
    private fun showProgressBar(display: Boolean) {
        val progressBar = findViewById<View>(R.id.progress_bar)
        progressBar.visibility = if (display) View.VISIBLE else View.GONE
    }

    override fun onHitsItemClickListener(hitsList: HitsList?) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle(R.string.show_details)
        alertDialog.setMessage(R.string.show_more_details)
        alertDialog.setPositiveButton(R.string.yes) { _, _ ->
            hitsList?.let {
                val intent = Intent(this, PixaBayDetailsActivity::class.java)
                intent.putExtra(Constants.EXTRA_HITSLIST, hitsList)
                startActivity(intent)
            }
        }
        alertDialog.setNegativeButton(R.string.no) { dialog, _ -> dialog.cancel() }
        alertDialog.show()
    }
}
