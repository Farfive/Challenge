package com.example.challenge.domain.entities

import android.os.Parcel
import android.os.Parcelable
import com.example.challenge.commons.utils.Constants


@Parcelize
data class HitsList(
    @field:Json(name = Constants.ID_TAG) val id: Long?,
    @field:Json(name = Constants.LARGE_IMAGE_URL_TAG) val largeImageURL: String?,
    @field:Json(name = Constants.LIKES_TAG) val likes: Long?,
    @field:Json(name = Constants.COMMENTS_TAG) val comments: Long?,
    @field:Json(name = Constants.TAGS_TAG) val tags: String?,
    @field:Json(name = Constants.USER_TAG) val user: String?,
    @field:Json(name = Constants.FAVORITES_TAG) val favorites: Long?,
    @field:Json(name = Constants.PREVIEW_URL_TAG) val previewURL: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<HitsList> {
        override fun createFromParcel(parcel: Parcel): HitsList {
            return HitsList(parcel)
        }

        override fun newArray(size: Int): Array<HitsList?> {
            return arrayOfNulls(size)
        }
    }
}

annotation class Parcelize
