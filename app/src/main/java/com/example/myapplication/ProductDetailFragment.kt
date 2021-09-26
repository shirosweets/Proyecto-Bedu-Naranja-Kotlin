package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso

class ProductDetailFragment : Fragment() {
    private lateinit var detailImage: ImageView
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailImage = view.findViewById(R.id.product_detail_image)
        showProduct(args.product)
    }

    private fun showProduct(product: Product) {
        Picasso.get().load(product.image).into(detailImage)
    }
}