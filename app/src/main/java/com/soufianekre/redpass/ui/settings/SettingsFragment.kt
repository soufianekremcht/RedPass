package com.soufianekre.redpass.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import com.soufianekre.redpass.R
import com.soufianekre.redpass.data.app_pref.PrefConst.PREF_ABOUT
import com.soufianekre.redpass.data.app_pref.PrefConst.PREF_SECURITY
import com.soufianekre.redpass.ui.about.AboutActivity
import com.soufianekre.redpass.ui.base.BasePreferenceFragment
import com.soufianekre.redpass.ui.settings.security.SecurityPref

class SettingsFragment : BasePreferenceFragment() , Preference.OnPreferenceClickListener{

    lateinit var securityPref: SecurityPref
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


    private fun getSettingsActivity() : SettingsActivity{
        return activity as SettingsActivity;
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when(preference!!.key){
            PREF_SECURITY -> getSettingsActivity().loadPrefFragment(SecurityPref.newInstance())
            PREF_ABOUT -> getSettingsActivity().startActivity(Intent(activity,AboutActivity::class.java))
        }
        return true
    }


}