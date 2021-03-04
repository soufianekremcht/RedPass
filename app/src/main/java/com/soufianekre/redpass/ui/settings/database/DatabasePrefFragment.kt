package com.soufianekre.redpass.ui.settings.database

import android.os.Bundle
import com.soufianekre.redpass.R
import com.soufianekre.redpass.ui.base.BasePreferenceFragment
import com.soufianekre.redpass.ui.settings.SettingsActivity
import com.soufianekre.redpass.ui.settings.SettingsFragment
import com.soufianekre.redpass.ui.settings.security.SecurityPrefFragment

class DatabasePrefFragment : BasePreferenceFragment(){


    companion object{
        fun newInstance(): SecurityPrefFragment {
            val args = Bundle()

            val fragment = SecurityPrefFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_database_layout,"database_layout")
    }

}