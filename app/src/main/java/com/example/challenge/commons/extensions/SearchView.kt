package com.example.challenge.commons.extensions


import android.animation.Animator
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.RelativeLayout
import com.example.namespace.R



class SearchView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val duration: Long = 300
    private val searchInputText: EditText
    private val openSearchButton: ImageButton
    private val closeSearchButton: ImageButton
    private val searchOpenView: RelativeLayout


    init {
        LayoutInflater.from(context).inflate(R.layout.view_search, this, true)

        searchInputText = findViewById(R.id.search_input_text)
        openSearchButton = findViewById(R.id.open_search_button)
        closeSearchButton = findViewById(R.id.close_search_button)
        searchOpenView = findViewById(R.id.search_open_view)

        openSearchButton.setOnClickListener { openSearch() }
        closeSearchButton.setOnClickListener { closeSearch() }

        triggerSearchForFruits()
    }

    private fun openSearch() {
        searchInputText.setText("")
        searchOpenView.visibility = View.VISIBLE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val circularReveal = ViewAnimationUtils.createCircularReveal(
                searchOpenView,
                (openSearchButton.right + openSearchButton.left) / 2,
                (openSearchButton.top + openSearchButton.bottom) / 2,
                0f, width.toFloat()
            )
            circularReveal.duration = duration
            circularReveal.start()
        }
    }

    private fun closeSearch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val circularConceal = ViewAnimationUtils.createCircularReveal(
                searchOpenView,
                (openSearchButton.right + openSearchButton.left) / 2,
                (openSearchButton.top + openSearchButton.bottom) / 2,
                width.toFloat(), 0f
            )

            circularConceal.duration = duration
            circularConceal.start()
            circularConceal.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator) = Unit
                override fun onAnimationCancel(p0: Animator) = Unit
                override fun onAnimationStart(animation: Animator, isReverse: Boolean) = Unit
                override fun onAnimationStart(p0: Animator) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                    searchOpenView.visibility = View.GONE
                    searchInputText.setText("")
                    circularConceal.removeAllListeners()
                }

                override fun onAnimationEnd(p0: Animator) {
                    TODO("Not yet implemented")
                }
            })
        } else {
            searchOpenView.visibility = View.GONE
            searchInputText.setText("")
        }
    }
    private fun triggerSearchForFruits() {
        val searchQuery = "fruits" // The search query
    }

}
