package com.appsfactory.lastfm.data.server.models


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Album(
    var _id: Long = 0,
    @SerializedName("name") var name: String = "",
    @SerializedName("playcount") var playcount: Long = 0,
    @SerializedName("url") var url: String = "",
    @SerializedName("mbid") var mbid: String = "",
    @SerializedName("artist") var artist: Artist = Artist(),
    @SerializedName("image") var image: ArrayList<Image> = ArrayList()
) : BaseResponse(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Artist::class.java.classLoader)
    )
    fun getBestImage(): String {
        return if (image.isNotEmpty())
            image[image.size - 1].text else ""

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_id)
        parcel.writeString(name)
        parcel.writeLong(playcount)
        parcel.writeString(url)
        parcel.writeString(mbid)
        parcel.writeParcelable(artist, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Album> {
        override fun createFromParcel(parcel: Parcel): Album {
            return Album(parcel)
        }

        override fun newArray(size: Int): Array<Album?> {
            return arrayOfNulls(size)
        }
    }

}