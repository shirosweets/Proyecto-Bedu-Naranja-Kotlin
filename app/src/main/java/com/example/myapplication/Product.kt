package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

class Product(
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val image: String
): Parcelable {
    var rating: Float = 0f
    var votes: Int = 0
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readInt()
    ) {
    }

    constructor(
        id: Int,
        title: String,
        price: Float,
        description: String,
        category: String,
        image: String,
        rating: Float,
        votes: Int
    ) : this(
        id, title, price, description, category, image
    ) {
        this.rating = rating
        this.votes = votes
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeFloat(price)
        parcel.writeString(description)
        parcel.writeString(category)
        parcel.writeString(image)
        parcel.writeFloat(rating)
        parcel.writeInt(votes)
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
