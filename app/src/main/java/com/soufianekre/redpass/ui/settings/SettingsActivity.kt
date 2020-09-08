package com.soufianekre.redpass.ui.settings

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import com.soufianekre.redpass.R
import com.soufianekre.redpass.ui.base.BaseActivity
import com.soufianekre.redpass.ui.settings.security.SecurityPref

class SettingsActivity : BaseActivity(),SettingsMvp.View{

    @BindView(R.id.settings_toolbar)
    lateinit var settingsToolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        ButterKnife.bind(this)

        setupUI()
        loadPrefFragment(SettingsFragment.newInstance())
    }

    private fun setupUI() {
        setSupportActionBar(settingsToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (getCurrentFragment() is SecurityPref){
            showMessage("Security Pref")
            (getCurrentFragment() as SecurityPref).onBackPressed()
        }else{
            super.onBackPressed()
        }

    }

    public fun loadPrefFragment(fragment : Fragment){
        supportFragmentManager
            .beginTransaction().replace(R.id.settings_container,fragment)
            .commit()
    }
    private fun getCurrentFragment() : Fragment? {
        return supportFragmentManager.findFragmentById(R.id.settings_container);
    }
}