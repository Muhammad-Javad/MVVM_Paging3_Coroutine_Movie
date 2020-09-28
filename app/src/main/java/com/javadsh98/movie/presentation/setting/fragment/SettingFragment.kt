package com.javadsh98.movie.presentation.setting.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.javadsh98.movie.R

class SettingFragment : PreferenceFragmentCompat() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        requireActivity().onBackPressedDispatcher.addCallback(this) {
//            requireActivity().onNavigateUp()
//        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)

        val display = findPreference<ListPreference>("theme")
        val category = findPreference<ListPreference>("category")
        val language = findPreference<ListPreference>("language")

        display?.setOnPreferenceChangeListener { _, newValue ->
            when (newValue) {
                "1" -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
            true
        }

        category?.setOnPreferenceChangeListener{_, newvalue->
            when(newvalue){
                "top-rated" -> {

                }
                else -> {

                }
            }
            true
        }

        language?.setOnPreferenceChangeListener { _, newValue ->
            when(newValue){
                "en-US" -> {

                }
            }

            true
        }

    }

}