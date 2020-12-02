package com.soufianekre.redpass.ui.passwords

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.soufianekre.redpass.R
import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.data.db.models.PasswordItem
import com.soufianekre.redpass.helpers.AppConst.FILTER_BY_LABEL
import com.soufianekre.redpass.helpers.AppConst.PASSWORD_TO_EDIT
import com.soufianekre.redpass.helpers.ColorsHelper
import com.soufianekre.redpass.ui.base.BaseFragment
import com.soufianekre.redpass.ui.main.MainActivity
import com.soufianekre.redpass.ui.password_editor.PasswordEditorActivity


class PasswordListFragment :BaseFragment(),PasswordListMvp.View{



    @BindView(R.id.passwords_empty_layout)
    lateinit var emptyLayout: LinearLayout
    @BindView(R.id.password_list_recycler_view)
    lateinit var passwordListRecyclerView :RecyclerView
    private lateinit var passwordAdapter : PasswordListAdapter

    private lateinit var actionModeCallBack: CustomActionModeCallBack
    private  var actionMode:ActionMode? = null

    private var chosenLabel : Label? = null


    private lateinit var mPresenter: PasswordListPresenter<PasswordListMvp.View>
    companion object{


        fun newInstance(label: Label?): PasswordListFragment {
            val args = Bundle()
            args.putSerializable(FILTER_BY_LABEL,label)
            val fragment = PasswordListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_password_list,container,false)
        mPresenter = PasswordListPresenter()
        ButterKnife.bind(this,view)
        mPresenter.onAttach(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionModeCallBack = CustomActionModeCallBack()
        passwordListRecyclerView.setHasFixedSize(true)
        passwordListRecyclerView.layoutManager = LinearLayoutManager(requireActivity(),VERTICAL,false)
        passwordListRecyclerView.itemAnimator = DefaultItemAnimator()
        passwordAdapter = PasswordListAdapter(requireActivity(),ArrayList(),mPresenter)
        passwordListRecyclerView.adapter = passwordAdapter

        if (getMainActivity()!!.getSearchView() != null)
            mPresenter.getSearchResults(getMainActivity()!!.getSearchView()!!)




    }

    override fun onResume() {
        super.onResume()
        mPresenter.getPasswords("",chosenLabel)
    }

    
    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }


    override fun notifyAdapter(passwordList: List<PasswordItem>) {
        passwordAdapter.addAll(passwordList)
        checkEmptyView()
    }

    // layout
    override fun openPasswordEditorActivity(passwordItem :PasswordItem) {
        val intent : Intent = Intent(activity,PasswordEditorActivity::class.java)
        intent.putExtra(PASSWORD_TO_EDIT,passwordItem)
        startActivity(intent)
    }


    override fun onItemDeleted(password:PasswordItem) {

    }


    override fun enableActionMode(position:Int){
        if (actionMode == null){
            actionMode = requireActivity().startActionMode(actionModeCallBack)
        }
        toggleItemSelection(position)
    }

    override fun toggleItemSelection(position:Int){
        passwordAdapter.toggleSelection(position)
        val count = passwordAdapter.getSelectedItemCount();
        if(count == 0){
            actionMode?.finish()
        }else{
            actionMode?.title = count.toString()
            actionMode?.invalidate()
        }
    }

    // Action Mode
    override fun getActionMode(): ActionMode? {
        return actionMode
    }



    override fun setToolbarTitle(label: Label?) {
        if (label != null ){
            getMainActivity()?.getToolbar()!!.title = "Label : " + label.name
        }else{
            getMainActivity()?.getToolbar()!!.title = "Label : All "
        }

    }




    private fun checkEmptyView() {
        if (passwordAdapter.itemCount >0){
            passwordListRecyclerView.visibility = View.VISIBLE
            emptyLayout.visibility = View.GONE
        }else{
            passwordListRecyclerView.visibility = View.GONE
            emptyLayout.visibility = View.VISIBLE
        }
    }

    private fun getMainActivity() : MainActivity?{
        return activity as MainActivity
    }


    private fun checkArgs() {
        chosenLabel = arguments?.getSerializable(FILTER_BY_LABEL) as? Label

    }




    inner class CustomActionModeCallBack : ActionMode.Callback {
        override fun onCreateActionMode(
            mode: ActionMode?,
            menu: Menu?
        ): Boolean {
            mode!!.menuInflater.inflate(R.menu.menu_action,menu)
            passwordAdapter.selection_mode =true
            changeStatusBarColor(ColorsHelper.getColor(getMainActivity()!!,R.color.holo_dark_action_mode))
            getMainActivity()?.getFab()!!.hide()
            passwordAdapter.notifyDataSetChanged()
            return true

        }

        override fun onPrepareActionMode(
            mode: ActionMode?,
            menu: Menu?
        ): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when(item!!.itemId){
                R.id.menu_action_select_all ->{
                    // select all
                    return true
                }
            }
            return false
        }


        override fun onDestroyActionMode(mode:ActionMode?) {
            passwordAdapter.clearSelection()
            mode!!.finish()
            passwordAdapter.selection_mode = false
            actionMode = null
            passwordListRecyclerView.post {
                passwordAdapter.resetAnimationIndex()
                // mAdapter.notifyDataSetChanged();
            }
            passwordAdapter.notifyDataSetChanged()
            changeStatusBarColor(ColorsHelper.getColor(getMainActivity()!!,R.color.colorPrimary))
            getMainActivity()?.getFab()!!.show()

        }


    }

    private fun changeStatusBarColor(color:Int) {
        ColorsHelper.changeStatusBarColor(requireActivity(),color)
    }


}
