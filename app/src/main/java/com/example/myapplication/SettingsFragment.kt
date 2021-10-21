package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {
    private lateinit var themeSwitch: SwitchMaterial
    private lateinit var returnToProfile: Button
    private lateinit var spinnerChangeLanguage: Spinner

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
        spinnerChangeLanguage = view.findViewById(R.id.change_language)

        var languageList = ArrayList<String>()
        languageList.add("Español")
        languageList.add("English")


        var adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_text_view,
            languageList
        )

        spinnerChangeLanguage.adapter = adapter

        returnToProfile = view.findViewById(R.id.return_to_profile_bt)

        val current_lan = UserConfig.getLanguage(requireContext())
        when(current_lan){
            "en" -> spinnerChangeLanguage.setSelection(1)
            else -> spinnerChangeLanguage.setSelection(0)
        }

        spinnerChangeLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var cur_select_lan = UserConfig.getLanguage(requireContext())
                val iso_lan = when(languageList[position]){
                    "English" -> "en"
                    else -> "es"
                }
                if(iso_lan != cur_select_lan){
                    UserConfig.setLanguage(requireContext(), iso_lan)
                    activity?.recreate()
                }
            }
        }

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