package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import androidx.navigation.navOptions

class ProductDetailFragment : Fragment() {
    private lateinit var detTitle: TextView
    private lateinit var detRating: RatingBar
    private lateinit var detVotes: TextView
    private lateinit var detImage: ImageView
    private lateinit var detPrice: TextView
    private lateinit var detSplitPayments: TextView
    private lateinit var detAddToCartBtn: Button
    private lateinit var detDescription: TextView

    private val args: ProductDetailFragmentArgs by navArgs()

    private val addToCartTransitionOpt = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detTitle = view.findViewById(R.id.product_detail_title)
        detRating = view.findViewById(R.id.product_detail_rating)
        detVotes = view.findViewById(R.id.product_detail_votes)
        detImage = view.findViewById(R.id.product_detail_image)
        detPrice = view.findViewById(R.id.product_detail_price)
        detSplitPayments = view.findViewById(R.id.product_detail_split_payment)
        detAddToCartBtn = view.findViewById(R.id.product_detail_add_to_cart_button)
        detDescription = view.findViewById(R.id.product_detail_description)
        detAddToCartBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_productDetailFragment_to_shoppingCartFragment,
                null,
                addToCartTransitionOpt
            )

            Toast.makeText(
                it.context,
                "Se agregó al carrito:\n\n${args.product.title}",
                Toast.LENGTH_SHORT
            ).show()
        }
        showProduct(args.product)
    }

    private fun showProduct(product: Product) {
        val splitString = "%.2f".format(product.price / 6f)
        detTitle.text = product.title
        detRating.rating = product.rating
        detVotes.text = product.votes.toString()
        Picasso.get().load(product.image).into(detImage)
        detPrice.text = "$ ${product.price}"
        detSplitPayments.text = "$ $splitString"
        detDescription.text = product.description
    }
}