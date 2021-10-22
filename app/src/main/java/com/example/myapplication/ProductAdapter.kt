package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

class ProductAdapter(
    private val click_listener: (Product, FragmentNavigator.Extras) -> Unit,
    private val product_list: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

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
        val extras = holder.getExtras(currentProduct)
        holder.itemView.setOnClickListener { click_listener(currentProduct, extras) }

        val animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.slide_in_left_slow
        )
        holder.itemView.startAnimation(animation)
    }

    override fun getItemCount(): Int = product_list.size

    inner class ProductHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val productTitle: TextView = view.findViewById(R.id.product_title)
        private val productVotes: TextView = view.findViewById(R.id.product_votes)
        private val productPrice: TextView = view.findViewById(R.id.product_price)
        private val productRating: RatingBar = view.findViewById(R.id.product_rating)
        private val productImage: ImageView = view.findViewById(R.id.product_contact_image)
        private val productCardView: MaterialCardView = view.findViewById(R.id.product_card_view)

        fun render(product: Product) {
            productTitle.text = product.title
            productVotes.text = product.ratingCount?.toString() ?: "0"
            productRating.rating = product.ratingRate ?: 5f
            productPrice.text = "$ ${product.price}"
            Picasso.get().load(product.image).into(productImage)
        }

        fun getExtras(product: Product): FragmentNavigator.Extras {
            productCardView.transitionName = "product_${product.title}"
            productTitle.transitionName = "product_title_${product.title}"
            productVotes.transitionName = "product_votes_${product.title}"
            productPrice.transitionName = "product_price_${product.title}"
            productRating.transitionName = "product_rating_${product.title}"
            productImage.transitionName = "product_image_${product.title}"

            return FragmentNavigatorExtras(
                productCardView to "product_${product.title}",
                productTitle to "product_title_${product.title}",
                productVotes to "product_votes_${product.title}",
                productPrice to "product_price_${product.title}",
                productRating to "product_rating_${product.title}",
                productImage to "product_image_${product.title}",
            )
        }
    }
}
