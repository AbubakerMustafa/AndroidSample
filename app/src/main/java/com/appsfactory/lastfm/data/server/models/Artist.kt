package com.appsfactory.lastfm.data.server.models


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("image")
    var image: List<Image?>? = listOf(),
    @SerializedName("listeners")
    var listeners: String? = "",
    @SerializedName("mbid")
    var mbid: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("streamable")
    var streamable: Long? = 0,
    @SerializedName("url")
    var url: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Image),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(image)
        parcel.writeString(listeners)
        parcel.writeString(mbid)
        parcel.writeString(name)
        parcel.writeValue(streamable)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Artist> {
        override fun createFromParcel(parcel: Parcel): Artist {
            return Artist(parcel)
        }

        override fun newArray(size: Int): Array<Artist?> {
            return arrayOfNulls(size)
        }
    }

    fun getBestImage(): String {
        return image?.let {
            if (it.isNotEmpty())
                it[it.size - 1]?.text else ""
        } ?: ""
    }
}