package com.soufianekre.redpass.ui.main.drawer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.soufianekre.redpass.R
import com.soufianekre.redpass.R.string
import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.ui.base.BaseFragment
import com.soufianekre.redpass.ui.labels.LabelsActivity
import com.soufianekre.redpass.ui.main.MainActivity
import com.soufianekre.redpass.ui.passwords.PasswordListFragment
import com.soufianekre.redpass.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.fragment_nav_drawer.*
import kotlinx.android.synthetic.main.fragment_nav_drawer.view.*
import timber.log.Timber


@SuppressLint("NonConstantResourceId")
class NavDrawerFragment: BaseFragment(),NavDrawerMvp.View{


    private var expanded: Boolean = false

    private lateinit var drawerView :View


    @BindView(R.id.drawer_item_all)
    lateinit var showAll : LinearLayout


    @BindView(R.id.drawer_item_settings)
    lateinit var settings:View
    @BindView(R.id.drawer_item_edit_labels)
    lateinit var editLabels : View
    @BindView(R.id.drawer_item_labels)
    lateinit var labelsView:View
    @BindView(R.id.left_drawer)
    lateinit var leftDrawer:View
    @BindView(R.id.drawer_labels_list_view)
    lateinit var drawerLabelsListView : RecyclerView

    lateinit var toggle : Transition

    private lateinit var drawerLabelAdapter : NavDrawerLabelsAdapter
    var mDrawerToggle: ActionBarDrawerToggle? = null
    var mDrawerLayout: DrawerLayout? = null
    private var mActivity: MainActivity? = null
    lateinit var mPresenter: NavDrawerPresenter<NavDrawerMvp.View>

    companion object{
        fun newInstance(): NavDrawerFragment{
            val fragment = NavDrawerFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        drawerView = inflater.inflate(R.layout.fragment_nav_drawer,container,false)
        mPresenter = NavDrawerPresenter()
        mPresenter.onAttach(this)
        ButterKnife.bind(this,drawerView)
        return drawerView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toggle = TransitionInflater.from(context)
            .inflateTransition(R.transition.drawer_labels_card_toggle)
        drawerLabelAdapter =
            NavDrawerLabelsAdapter(
                requireActivity(),
                ArrayList(),
                this
            )
        drawerLabelsListView.setHasFixedSize(true)
        drawerLabelsListView.layoutManager = LinearLayoutManager(requireActivity(),VERTICAL,false)
        drawerLabelsListView.adapter = drawerLabelAdapter
        setListeners()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as MainActivity?
        setupLayout()
    }

    override fun onResume() {
        super.onResume()
        refreshMenus()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }


    private fun setupLayout(){
        mDrawerLayout = getMainActivity().getDrawerLayout()
        mDrawerLayout!!.isFocusableInTouchMode = false

        leftDrawer.setPadding(
            leftDrawer.paddingLeft, leftDrawer.paddingTop, leftDrawer.paddingRight,
            10
        )
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = object : ActionBarDrawerToggle(
            mActivity,
            mDrawerLayout,
            getMainActivity().getToolbar(),
            string.navigation_drawer_open,
            string.navigation_drawer_close) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                getMainActivity().invalidateOptionsMenu()
            }
        }
        mDrawerLayout!!.addDrawerListener(mDrawerToggle as ActionBarDrawerToggle)
        (mDrawerToggle as ActionBarDrawerToggle).isDrawerIndicatorEnabled = true

    }


    private fun refreshMenus(){
        buildMainMenu()
        Timber.v("Finished main menu initialization")
        mPresenter.getLabels()
        Timber.v("Finished categories menu initialization")

    }

    private fun buildMainMenu() {
        drawerView.drawer_item_settings.setOnClickListener{
            showSettings()
        }
    }


    private fun setListeners(){

        editLabels.setOnClickListener{
            showLabelsActivity()
        }

        drawer_labels_title_container.setOnClickListener{
            expanded = !expanded
            toggle.duration = if (expanded) 300L else 200L
            TransitionManager.beginDelayedTransition(drawerLabelsListView as ViewGroup, toggle)
            drawer_labels_expand_icon.rotationX = if (expanded) 180f else 0f

            drawerLabelsListView.visibility = if (expanded) View.VISIBLE else View.GONE
        }


    }
    private fun getMainActivity(): MainActivity {
        return (activity as MainActivity?)!!
    }

    override fun buildLabelsMenu(labelList : List<Label>) {
        drawerLabelAdapter.addAll(labelList)
        drawerLabelsListView.adapter = drawerLabelAdapter
        mDrawerToggle!!.syncState()
    }


    override fun loadPasswordItemList(label: Label) {
        getMainActivity().loadFragment(PasswordListFragment.newInstance(label))
        getMainActivity().getDrawerLayout().closeDrawer(GravityCompat.START)
    }



    override fun showLabelsActivity(){
        val intent = Intent(requireContext(),LabelsActivity::class.java)
        startActivity(intent)
        getMainActivity().getDrawerLayout().closeDrawer(GravityCompat.START)

    }

    override fun showPasswordListFragment() {
        drawerLabelAdapter.selectedItemPosition = -1
        drawerLabelAdapter.notifyDataSetChanged()
        getMainActivity().loadFragment(PasswordListFragment.newInstance(null))
        getMainActivity().getDrawerLayout().closeDrawer(GravityCompat.START)

    }

    override fun showSettings() {
        val intent = Intent(activity,SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun onLabelClicked(label: Label) {
        getMainActivity().loadFragment(PasswordListFragment.newInstance(label))
        Handler().postDelayed({
            getMainActivity().mainDrawer.closeDrawer(GravityCompat.START)
        },250)
    }



}