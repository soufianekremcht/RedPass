package com.soufianekre.redpass.ui.main


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.soufianekre.redpass.R
import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.ui.base.BaseActivity
import com.soufianekre.redpass.ui.main.drawer.NavDrawerFragment
import com.soufianekre.redpass.ui.password_editor.PasswordEditorActivity
import com.soufianekre.redpass.ui.passwords.PasswordListFragment
import com.soufianekre.redpass.ui.settings.SettingsActivity


class MainActivity :BaseActivity() ,MainMvp.View{


    private val FRAGMENT_DRAWER_TAG: String = "fragment_drawer_tag"


    @BindView(R.id.app_toolbar)
    lateinit var mainToolbar : Toolbar

    @BindView(R.id.password_add_fab)
    lateinit var passwordAddFab: FloatingActionButton

    @BindView(R.id.main_drawer)
    lateinit var mainDrawer : DrawerLayout

    private var searchView :SearchView? = null


    lateinit var mPresenter :MainPresenter<MainMvp.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter()
        ButterKnife.bind(this)
        mPresenter.onAttach(this)
        setupUi()
        loadPasswordListFragment(null)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onDetach()
    }
    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        val searchManager : SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu!!.findItem(R.id.menu_main_search).actionView as SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.maxWidth = Integer.MAX_VALUE;
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_main_settings -> {
                val intent = Intent(this,SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            /*.setCustomAnimations(
                R.anim.anim_in,
                R.anim.anim_out,
                R.anim.anim_in_pop,
                R.anim.anim_out_pop)*/
            .replace(R.id.fragment_container,fragment)
            .commit()
    }

    override fun openPasswordEditorActivity(){
        val passwordEditorIntent  = Intent(this@MainActivity,PasswordEditorActivity::class.java)
        startActivity( passwordEditorIntent)
    }



    fun setupUi(){
        mainToolbar.setOnClickListener{
            onBackPressed()
        }
        // drawer
        val toggle = ActionBarDrawerToggle(this,mainDrawer,mainToolbar
            , R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        mainDrawer.addDrawerListener(toggle)
        toggle.syncState()

        setSupportActionBar(mainToolbar)

        val mNavigationDrawerFragment = supportFragmentManager.findFragmentById(R.id.main_nav_view)
                as NavDrawerFragment?
        if (mNavigationDrawerFragment == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.main_nav_view, NavDrawerFragment.newInstance(),
                FRAGMENT_DRAWER_TAG
            ).commit()
        }

        passwordAddFab.setOnClickListener{
            openPasswordEditorActivity()
        }
    }


    private fun loadPasswordListFragment(label : Label?){
        loadFragment(PasswordListFragment.newInstance(label))
    }

    fun getDrawerLayout():DrawerLayout{
        return mainDrawer
    }
    fun getToolbar(): Toolbar{
        return mainToolbar
    }
    fun getFab():FloatingActionButton{
        return passwordAddFab
    }

    fun getSearchView() : SearchView?{
        return searchView
    }


    override fun onBackPressed() {
        if (searchView!!.isIconified){
            searchView!!.isIconified = false;
        }else{
            super.onBackPressed()
        }
    }
}