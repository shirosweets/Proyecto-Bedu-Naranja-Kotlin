package com.example.myapplication

import android.util.Log
import io.realm.Realm
import io.realm.exceptions.RealmException
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import kotlin.math.max

object ProductDatabase {
    private val realm: Realm = Realm.getDefaultInstance()

    private fun getOrCreate(id: Int): Product {
        return try {
            Log.v("MYDEBUG", "Creating new Product!")
            realm.createObject(Product::class.java, id)
        } catch (e: RealmPrimaryKeyConstraintException) {
            Log.v("MYDEBUG", "Fetching found Product")
            realm.where(Product::class.java).equalTo("id", id).findFirst()!!
        }
    }

    fun addProduct(
        id: Int,
        title: String,
        price: Float,
        description: String,
        category: String,
        image: String,
        ratingCount: Int,
        ratingRate: Float,
        amountAddedToCart: Int
    ) {
        realm.beginTransaction()
        val product: Product = getOrCreate(id)
        product.title = title
        product.price = price
        product.description = description
        product.category = category
        product.image = image
        product.ratingCount = ratingCount
        product.ratingRate = ratingRate
        product.amountAddedToCart = amountAddedToCart
        realm.commitTransaction()
    }

    fun fetchProduct(id: Int): Product? {
        return realm.where(Product::class.java).equalTo("id", id).findFirst()
    }

    fun resetProductCartAmount(id: Int) {
        val product = fetchProduct(id)
        if (product != null) {
            realm.beginTransaction()
            product.amountAddedToCart = 0
            realm.commitTransaction()
        }
    }

    fun fetchAllProducts(): List<Product> = realm.where(Product::class.java).findAll()

    fun fetchCartProducts(): List<Product> {
        return realm
            .where(Product::class.java)
            .greaterThan(
                "amountAddedToCart",
                0
            )
            .findAll()
    }

    fun addOneToCart(id: Int) {
        val product: Product? = fetchProduct(id)
        product?.let {
            realm.beginTransaction()
            product.amountAddedToCart = (product.amountAddedToCart ?: 0) + 1
            realm.commitTransaction()
        }
    }

    fun removeOneFromCart(id: Int) {
        val product: Product? = fetchProduct(id)
        product?.let {
            realm.beginTransaction()
            product.amountAddedToCart = max((product.amountAddedToCart ?: 0) - 1, 0)
            realm.commitTransaction()
        }
    }
}