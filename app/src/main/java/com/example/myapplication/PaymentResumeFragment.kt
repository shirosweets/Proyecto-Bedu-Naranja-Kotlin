package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class PaymentResumeFragment : Fragment() {
    private lateinit var recycler: RecyclerView
    private lateinit var payButton: MaterialButton
    private lateinit var subTotalValue: TextView
    private lateinit var totalValue: TextView
    private val shippingCost: Float = 30f
    val CHANNEL_OTHERS = "OTROS"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
        }
        return inflater.inflate(R.layout.fragment_payment_resume, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.paymentResumeRecyclerView)
        recycler.adapter = OptionAdapter( getOptionsClickListener(), getPaymentOptions())
        recycler.layoutManager = LinearLayoutManager(activity)

        subTotalValue = view.findViewById(R.id.subtotalValue)
        subTotalValue.text = "$ %.2f".format(getSubtotal())
        totalValue = view.findViewById(R.id.totalValue)
        totalValue.text = "$ %.2f".format(getSubtotal() + shippingCost)

        payButton = view.findViewById(R.id.paymentResumePayButton)
        payButton.setOnClickListener {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                buyNotification()
                resetCartAmount()
            }
            findNavController().navigate(
                R.id.action_paymentResumeFragment_to_sucessfulPaymentFragment,
                null,
            )
        }
    }

    private fun resetCartAmount() {
        val products = ProductDatabase.fetchCartProducts()
        products.forEach { it.id?.let { id ->  ProductDatabase.resetProductCartAmount(id) } }
    }

    private fun getSubtotal(): Float {
        val products = ProductDatabase.fetchCartProducts()
        var subTotal = 0f
        products.forEach {
            val amount: Float = it.amountAddedToCart?.toFloat() ?: 0f
            val singlePrice: Float = it.price ?: 0f
            subTotal += singlePrice * amount
        }
        return subTotal
    }

    private fun getPaymentOptions(): List<Option> {
        return listOf(
            Option("Dirección Actual",R.drawable.ic_location),
            Option("Método de pago actual",R.drawable.ic_credit_card)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotificationChannel(){
        val name = getString(R.string.notification_1)
        val descriptionText = getString(R.string.notify_body_1)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_OTHERS, name, importance).apply{
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun buyNotification(){
        val pendingIntent = NavDeepLinkBuilder(requireActivity())
            .setComponentName(MenuActivity::class.java)
            .setGraph(R.navigation.menu_navigation)
            .setDestination(R.id.sucessfulPaymentFragment)
            .createPendingIntent()

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_OTHERS)
            .setSmallIcon(R.drawable.ic_bedu_shop)
            .setColor(ContextCompat.getColor(requireContext(), R.color.light_secondaryVariant))
            .setContentTitle(getString(R.string.notification_1))
            .setContentText(getString(R.string.notify_body_1))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) //se define aquí el content intend
            .setAutoCancel(true) //la notificación desaparece al dar click sobre ella

        // Send notification
        with(NotificationManagerCompat.from(requireContext())){
            notify(20, builder.build())
        }
    }

    private fun getOptionsClickListener():(String) -> Unit = {}
}