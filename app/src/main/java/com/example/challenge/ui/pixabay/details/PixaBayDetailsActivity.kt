package com.example.challenge.ui.pixabay.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.challenge.commons.utils.Constants.Companion.EXTRA_HITSLIST
import com.example.namespace.R
import com.example.namespace.databinding.ActivityPixabayDetailsBinding


class PixaBayDetailsActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityPixabayDetailsBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_pixabay_details
        )

        if (intent != null && intent.hasExtra(EXTRA_HITSLIST)) {
            binding.hitsList = intent.getParcelableExtra(EXTRA_HITSLIST)
            binding.callback = this
        }
    }

    fun moveToPreviousScreen() = finish()
}
