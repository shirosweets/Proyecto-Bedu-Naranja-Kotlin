package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

class Rating(val rate:Float,val count:Int): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readInt()
    ) {}

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeFloat(rate)
        parcel.writeInt(count)
    }

    companion object CREATOR : Parcelable.Creator<Rating> {
        override fun createFromParcel(parcel: Parcel): Rating {
            return Rating(parcel)
        }

        override fun newArray(size: Int): Array<Rating?> = arrayOfNulls(size)
    }
}