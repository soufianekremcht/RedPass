package com.soufianekre.redpass.ui.settings.security

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.github.ajalt.reprint.core.Reprint
import com.soufianekre.redpass.R
import com.soufianekre.redpass.data.app_pref.PrefConst.PREF_SECURITY_CHANGE_APP_PASSWORD
import com.soufianekre.redpass.data.app_pref.PrefConst.PREF_SECURITY_FAILED_PASS_ATTEMPS
import com.soufianekre.redpass.data.app_pref.PrefConst.PREF_SECURITY_FINGER_PRINT_LOCK
import com.soufianekre.redpass.ui.IntroFingerprintActivity
import com.soufianekre.redpass.ui.settings.change_password.ChangePasswordActivity

class SecurityPrefFragment : PreferenceFragmentCompat(),Preference.OnPreferenceClickListener {
    var fingerPrintPref : SwitchPreference? = null
    companion object{
        fun newInstance(): SecurityPrefFragment {
            val args = Bundle()

            val fragment = SecurityPrefFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_security_layout,"security_layout")
        fingerPrintPref = findPreference<SwitchPreference>(PREF_SECURITY_FINGER_PRINT_LOCK)
        fingerPrintPref?.isEnabled = Reprint.isHardwarePresent()


    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when(preference!!.key){
            PREF_SECURITY_CHANGE_APP_PASSWORD ->{
                val intent = Intent(activity, ChangePasswordActivity::class.java);
                requireActivity().startActivity(intent)
                return true
            }
            PREF_SECURITY_FAILED_PASS_ATTEMPS ->{
                // show dialog
            }
        }
        return false
    }


}