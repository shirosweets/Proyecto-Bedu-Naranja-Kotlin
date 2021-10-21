package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {
    private lateinit var themeSwitch: SwitchMaterial
    private lateinit var returnToProfile: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        themeSwitch = view.findViewById(R.id.theme_switch)
        themeSwitch.isChecked = UserConfig.isDarkTheme(requireContext())

        returnToProfile = view.findViewById(R.id.return_to_profile_bt)

        themeSwitch.setOnCheckedChangeListener { _, _ ->
            activity?.applicationContext?.let { UserConfig.switchTheme(it) }
            activity?.recreate()
        }

        returnToProfile.setOnClickListener {
            findNavController().navigate(
                R.id.action_settingsFragment_to_profileFragment,
                null
            )
        }
    }
}