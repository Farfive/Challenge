package com.example.challenge.domain.entities

import android.os.Parcel
import android.os.Parcelable
import com.example.challenge.commons.utils.Constants.Companion.HITS_TAG


@Parcelize
data class PixabayApiResponse(@field:Json(name = HITS_TAG) val hits: List<HitsList>?) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<PixabayApiResponse> {
        override fun createFromParcel(parcel: Parcel): PixabayApiResponse {
            // Implement the logic to create an instance of PixabayApiResponse from the parcel data
            return TODO("Provide the return value")
        }

        override fun newArray(size: Int): Array<PixabayApiResponse?> {
            return arrayOfNulls(size)
        }
    }
}

annotation class Json(val name: String)
