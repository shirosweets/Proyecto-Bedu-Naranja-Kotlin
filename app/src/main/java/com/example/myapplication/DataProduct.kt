package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable


data class DataProduct(
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readParcelable(Rating::class.java.classLoader) ?: Rating(0f, 0)
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeFloat(price)
        parcel.writeString(description)
        parcel.writeString(category)
        parcel.writeString(image)
        parcel.writeParcelable(rating,Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
    }

    companion object CREATOR : Parcelable.Creator<DataProduct> {
        override fun createFromParcel(parcel: Parcel): DataProduct {
            return DataProduct(parcel)
        }

        override fun newArray(size: Int): Array<DataProduct?> {
            return arrayOfNulls(size)
        }
    }
}
