package com.soufianekre.redpass.ui.settings.database

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import com.soufianekre.redpass.R
import com.soufianekre.redpass.ui.base.BaseActivity
import com.soufianekre.redpass.ui.settings.security.SecurityPrefFragment

class DatabasePrefActivity : BaseActivity() {

    @BindView(R.id.settings_toolbar)
    lateinit var databasePrefToolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        ButterKnife.bind(this)
        setupUI()
        loadPrefFragment(SecurityPrefFragment.newInstance())
    }

    private fun setupUI() {
        databasePrefToolbar.title = "DataBase"
        setSupportActionBar(databasePrefToolbar)
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
        finish();
    }

    fun loadPrefFragment(fragment : Fragment){
        supportFragmentManager
            .beginTransaction().replace(R.id.settings_container,fragment)
            .addToBackStack(null)
            .commit()
    }
}