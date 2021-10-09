package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

class ProductAdapter(
    private val click_listener: (Product,FragmentNavigator.Extras) -> Unit,
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
        val extras = holder.getExtras(currentProduct)

        holder.itemView.setOnClickListener{
            click_listener(currentProduct,extras)
        }

        val animation = AnimationUtils.loadAnimation(holder.itemView.context,R.anim.slide_in_left_slow)
        holder.itemView.startAnimation(animation)
    }

    override fun getItemCount(): Int = product_list.size

    inner class ProductHolder(view: View): RecyclerView.ViewHolder(view) {
        val productTitle: TextView = view.findViewById(R.id.product_title)
        val productVotes: TextView = view.findViewById(R.id.product_votes)
        val productPrice: TextView = view.findViewById(R.id.product_price)
        val productRating: RatingBar = view.findViewById(R.id.product_rating)
        val productImage: ImageView = view.findViewById(R.id.product_contact_image)

        val productCardView: MaterialCardView =  view.findViewById(R.id.product_card_view)



        fun render(product: Product) {
            productTitle.text = product.title
            productVotes.text = product.votes.toString()
            productRating.rating = product.rating
            productPrice.text = "$ ${product.price}"
            Picasso.get().load(product.image).into(productImage)
        }

        fun getExtras(product:Product):FragmentNavigator.Extras{
            productCardView.transitionName = "product_${product.title}"

            return FragmentNavigatorExtras(
                productCardView to "product_${product.title}",
            )

        }
    }
}