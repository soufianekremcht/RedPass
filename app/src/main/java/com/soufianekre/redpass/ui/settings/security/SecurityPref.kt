package com.soufianekre.redpass.ui.settings.security

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.soufianekre.redpass.R
import com.soufianekre.redpass.ui.settings.SettingsActivity
import com.soufianekre.redpass.ui.settings.SettingsFragment

class SecurityPref : PreferenceFragmentCompat() {

    companion object{
        fun newInstance(): SecurityPref {
            val args = Bundle()

            val fragment = SecurityPref()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_security_layout,"security_layout")
    }


    public fun onBackPressed(){
        getSettingsActivity().loadPrefFragment(SettingsFragment.newInstance())
    }


    private fun getSettingsActivity() : SettingsActivity {
        return activity as SettingsActivity;
    }
}