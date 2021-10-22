package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ProductCartAdapter(
    private var product_list: List<Product>
) : RecyclerView.Adapter<ProductCartAdapter.ProductHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductHolder(
            inflater.inflate(
                R.layout.fragment_product_cart_contact,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val currentProduct: Product = product_list[position]
        holder.render(currentProduct, position)
    }

    inner class ProductHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val productCartTitle: TextView = view.findViewById(R.id.cart_contact_title)
        private val productCartPrice: TextView = view.findViewById(R.id.cart_contact_price)
        private val productCartTotalPrice: TextView = view.findViewById(R.id.cart_contact_total_price)
        private val productCartImage: ImageView = view.findViewById(R.id.cart_contact_image)
        private val productCartPlusSign: ImageView = view.findViewById(R.id.cart_contact_plus_sign)
        private val productCartAmount: TextView = view.findViewById(R.id.cart_contact_cart_amount)
        private val productCartRemoveSign: ImageView = view.findViewById(
            R.id.cart_contact_remove_sign
        )

        fun render(product: Product, position: Int) {
            productCartTitle.text = product.title
            productCartPrice.text = product.price.toString()
            productCartTotalPrice.text =  "$ %.2f".format(product.amountAddedToCart?.let { product.price?.times(it)})
            Picasso.get().load(product.image).into(productCartImage)
            productCartAmount.text = product.amountAddedToCart.toString()
            setListeners(product, position)
        }

        private fun setListeners(product: Product, position: Int) {
            productCartPlusSign.setOnClickListener {
                product.id?.let { id ->
                    ProductDatabase.addOneToCart(id)
                    productCartAmount.text = ProductDatabase
                        .fetchProduct(id)
                        ?.amountAddedToCart
                        .toString()
                }
                productCartTotalPrice.text = "$ %.2f".format(product.amountAddedToCart?.let { product.price?.times(it)})
            }

            productCartRemoveSign.setOnClickListener {
                product.id?.let { id ->
                    ProductDatabase.removeOneFromCart(id)
                    val afterClickAmount: Int = ProductDatabase
                        .fetchProduct(id)
                        ?.amountAddedToCart
                        ?: 0
                    productCartAmount.text = afterClickAmount.toString()
                    if (afterClickAmount == 0) {
                        product_list = product_list.filter { it.id != product.id }
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, product_list.size)
                    }
                }
                productCartTotalPrice.text = "$ %.2f".format(product.amountAddedToCart?.let { product.price?.times(it)})
            }
        }
    }

    override fun getItemCount(): Int = product_list.size
}
