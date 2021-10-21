package com.example.myapplication

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Fade
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import androidx.navigation.navOptions
import com.example.myapplication.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
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

        // Inflate the transition for this fragment
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

       // Inflate the layout for this fragment

        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        binding.productDetailAddToCartButton.setOnClickListener {
            args.product.id?.let { id ->
                ProductDatabase.addOneToCart(id)
                findNavController().navigate(
                    R.id.action_productDetailFragment_to_shoppingCartFragment,
                    null,
                    addToCartTransitionOpt
                )
            }
//            Toast.makeText(
//                it.context,
//                "Se agregó al carrito:\n\n${args.product.title}",
//                Toast.LENGTH_SHORT
//            ).show()
        }
        showProduct(args.product)
        return binding.root
    }

    private fun showProduct(product: Product) {
        binding.productCardView.transitionName  = "product_${product.title}"
        binding.productTitle.transitionName = "product_title_${product.title}"
        binding.productVotes.transitionName = "product_votes_${product.title}"
        binding.productPrice.transitionName = "product_price_${product.title}"
        binding.productRating.transitionName = "product_rating_${product.title}"
        binding.productImage.transitionName = "product_image_${product.title}"


        val splitString = "%.2f".format(product.price ?: 0f / 6f)
        binding.productTitle.text = product.title
        binding.productRating.rating = product.ratingRate ?: 5f
        binding.productVotes.text = product.ratingCount?.toString() ?: "0"
        Picasso.get().load(product.image).into(binding.productImage)
        binding.productPrice.text = "$ ${product.price}"
        binding.productDetailSplitPayment.text = "$ $splitString"
        binding.productDetailDescription.text = product.description
    }
}
