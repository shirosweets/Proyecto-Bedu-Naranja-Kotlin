package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Product() : RealmObject(), Parcelable {
    @PrimaryKey
    var id: Int? = null
    var title: String? = null
    var price: Float? = null
    var description: String? = null
    var category: String? = null
    var image: String? = null
    var ratingCount: Int? = null
    var ratingRate: Float? = null
    var amountAddedToCart: Int? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        title = parcel.readString().toString()
        price = parcel.readFloat()
        description = parcel.readString().toString()
        category = parcel.readString().toString()
        image = parcel.readString().toString()
        ratingCount = parcel.readInt()
        ratingRate = parcel.readFloat()
        amountAddedToCart = parcel.readInt()
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeInt(id ?: -1)
        parcel.writeString(title ?: "")
        parcel.writeFloat(price ?: 0f)
        parcel.writeString(description ?: "")
        parcel.writeString(category ?: "")
        parcel.writeString(image ?: "")
        parcel.writeInt(ratingCount ?: 0)
        parcel.writeFloat(ratingRate ?: 0f)
        parcel.writeInt(amountAddedToCart ?: 0)
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> = arrayOfNulls(size)
    }
}





