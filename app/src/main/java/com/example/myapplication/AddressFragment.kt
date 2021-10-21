package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class AddressFragment : BottomSheetDialogFragment() {
    private lateinit var imageButtonClose: ImageView
    private lateinit var buttonUpdateAddress: Button
    private lateinit var tvActualDirection: TextView
    private lateinit var tvOldDirection1: TextView
    private lateinit var tvOldDirection2: TextView
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 33

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = ConfigManager.prefs(requireActivity())
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        imageButtonClose = view.findViewById(R.id.imageButtonClose)
        buttonUpdateAddress = view.findViewById(R.id.buttonUpdateAddress)
        tvActualDirection = view.findViewById(R.id.tvActualDirection)
        tvOldDirection1 = view.findViewById(R.id.tvOldDirection1)
        tvOldDirection2 = view.findViewById(R.id.tvOldDirection2)

        setSharedPreferencesAddress()

        imageButtonClose.setOnClickListener { dismiss() }
        buttonUpdateAddress.setOnClickListener { getLocation() }
    }

    private fun setSharedPreferencesAddress() {
        tvActualDirection.text = sharedPreferences.getString("USER_ACTUAL_ADDRESS", "")
        tvOldDirection1.text = sharedPreferences.getString("USER_OLD_ADDRESS1", "")
        tvOldDirection2.text = sharedPreferences.getString("USER_OLD_ADDRESS2", "")
    }


    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient
                    .lastLocation
                    .addOnSuccessListener(getLocationSuccessListener())
            }
        } else {
            requestPermissions()
        }
    }

    private fun getLocationSuccessListener(): ((Location) -> Unit) {
        return { location: Location ->
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = location.latitude.let {
                geocoder.getFromLocation(
                    it.toDouble(),
                    location.longitude.toDouble(),
                    1
                )
            }
            val actualAddress = addresses?.get(0)?.getAddressLine(0)
            if (tvActualDirection.text.toString() != actualAddress) {
                tvOldDirection2.text = tvOldDirection1.text.toString()
                tvOldDirection1.text = tvActualDirection.text.toString()
                tvActualDirection.text = actualAddress
                sharedPreferences.edit()
                    .putString("USER_ACTUAL_ADDRESS", tvActualDirection.text.toString())
                    .putString("USER_OLD_ADDRESS1", tvOldDirection1.text.toString())
                    .putString("USER_OLD_ADDRESS2", tvOldDirection2.text.toString())
                    .apply()
            }
        }
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = requireActivity()
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return isGpsEnabled || isNetworkEnabled
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    private fun checkPermissions(): Boolean {
        val isFineLocationGranted = checkGranted(Manifest.permission.ACCESS_FINE_LOCATION)
        val isCoarseLocationGranted = checkGranted(Manifest.permission.ACCESS_COARSE_LOCATION)
        return isFineLocationGranted || isCoarseLocationGranted
    }

    private fun checkGranted(permission: String): Boolean {
        val selfPermission = ActivityCompat.checkSelfPermission(requireContext(), permission)
        return selfPermission == PackageManager.PERMISSION_GRANTED
    }
}