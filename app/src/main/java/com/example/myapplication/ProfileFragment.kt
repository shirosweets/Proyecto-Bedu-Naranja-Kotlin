package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.switchmaterial.SwitchMaterial

class ProfileFragment : Fragment() {
    private lateinit var themeSwitch: SwitchMaterial

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        themeSwitch = view.findViewById(R.id.theme_switch)
        themeSwitch.isChecked = UserConfig.isDarkTheme(requireContext())
        themeSwitch.setOnCheckedChangeListener { _, _ ->
            activity?.applicationContext?.let { UserConfig.switchTheme(it) }
            activity?.recreate()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

}