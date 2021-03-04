package com.soufianekre.redpass.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import com.soufianekre.redpass.R
import com.soufianekre.redpass.data.app_pref.PrefConst.PREF_ABOUT
import com.soufianekre.redpass.data.app_pref.PrefConst.PREF_SECURITY
import com.soufianekre.redpass.ui.settings.about.AboutActivity
import com.soufianekre.redpass.ui.base.BasePreferenceFragment
import com.soufianekre.redpass.ui.settings.security.SecurityPrefActivity

class SettingsFragment : BasePreferenceFragment() , Preference.OnPreferenceClickListener{

    companion object{
        fun newInstance(): SettingsFragment {
            val args = Bundle()

            val fragment = SettingsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_layout,"settings_layout")
        val securityPref = findPreference<Preference>(PREF_SECURITY)
        securityPref!!.onPreferenceClickListener = this
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when(preference!!.key){
            PREF_SECURITY -> {
                val intent = Intent(requireActivity(),SecurityPrefActivity::class.java)
                startActivity(intent)
            }
            PREF_ABOUT -> {
                val intent = Intent(requireActivity(),
                    AboutActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }


}