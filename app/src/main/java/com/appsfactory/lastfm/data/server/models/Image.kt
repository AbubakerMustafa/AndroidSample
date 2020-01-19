package com.appsfactory.lastfm.data.server.models


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("size")
    var size: String = "",
    @SerializedName("#text")
    var text: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(size)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }
}