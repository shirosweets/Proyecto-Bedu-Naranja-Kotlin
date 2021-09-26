package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ProductAdapter(
    private val view: View,
    private val click_listener: (Product) -> Unit,
    private val product_list: List<Product>
): RecyclerView.Adapter<ProductAdapter.ProductHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductHolder(
            inflater.inflate(
                R.layout.fragment_product_contact,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val currentProduct: Product = product_list[position]
        holder.render(currentProduct)
        holder.itemView.setOnClickListener{
            click_listener(currentProduct)
        }
    }

    override fun getItemCount(): Int = product_list.size

    inner class ProductHolder(view: View): RecyclerView.ViewHolder(view) {
        private val productTitle: TextView = view.findViewById(R.id.product_contact_title)
        private val productVotes: TextView = view.findViewById(R.id.product_contact_voters)
        private val productPrice: TextView = view.findViewById(R.id.product_contact_price)
        private val productRating: RatingBar = view.findViewById(R.id.product_contact_rating)
        private val productImage: ImageView = view.findViewById(R.id.product_contact_image)
        fun render(product: Product) {
            productTitle.text = product.title
            productVotes.text = (10..999).random().toString()
            productRating.rating = (3..5).random().toFloat()
            productPrice.text = "$ ${product.price}"
            Picasso.get().load(product.image).into(productImage)
        }
    }
}