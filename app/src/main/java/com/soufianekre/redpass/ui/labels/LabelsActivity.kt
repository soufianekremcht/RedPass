package com.soufianekre.redpass.ui.labels

import android.annotation.SuppressLint
import android.graphics.Color

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.colorChooser
import com.afollestad.materialdialogs.customview.customView
import com.soufianekre.redpass.R
import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.ui.base.BaseActivity
import com.soufianekre.redpass.ui.views.CustomDividerItemDecoration
import kotlinx.android.synthetic.main.dialog_add_new_label.*
import kotlinx.android.synthetic.main.dialog_add_new_label.view.*

@SuppressLint("NonConstantResourceId")
class LabelsActivity :BaseActivity(),LabelsMvp.View{


    private val newLabelColor: Int= Color.RED

    //views
    @BindView(R.id.app_toolbar)
    lateinit var appToolbar:Toolbar

    @BindView(R.id.labels_recycler_view)
    lateinit var labelsRecyclerView: RecyclerView

    @BindView(R.id.labels_empty_view)
    lateinit var labelEmptyView :RelativeLayout

    private  var labelAdapter: LabelsAdapter? = null

    private lateinit var mPresenter : LabelsPresenter<LabelsMvp.View>

    private var newLabelDialog : MaterialDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_labels)
        mPresenter = LabelsPresenter()
        ButterKnife.bind(this)
        mPresenter.onAttach(this)
        setupUi()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.labels,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_labels_add_new_label ->{
                showNewLabelDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    private fun setupUi(){
        //toolbar
        appToolbar.title = getString(R.string.edit_label)
        appToolbar.setOnClickListener{
            onBackPressed()
        }
        setSupportActionBar(appToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //recyclerView
        labelAdapter = LabelsAdapter(this,ArrayList(),mPresenter)
        labelsRecyclerView.itemAnimator = DefaultItemAnimator()
        labelsRecyclerView.layoutManager = LinearLayoutManager(this,VERTICAL,false)
        labelsRecyclerView.addItemDecoration(CustomDividerItemDecoration(this, VERTICAL))
        labelsRecyclerView.adapter = labelAdapter
        mPresenter.getLabels()
    }

    override fun updateAdapter(labels :List<Label>) {
        labelAdapter!!.addAll(labels)
        checkEmptyView()
    }

    private fun showColorPicker(){
        val colors = intArrayOf(Color.RED, Color.GREEN, Color.BLUE)

        MaterialDialog(this).show {
            title(R.string.colors)
            colorChooser(colors) { dialog, color ->
                // Use color integer

            }
            positiveButton(R.string.select)
        }
    }

    private fun showNewLabelDialog(){

        newLabelDialog = MaterialDialog(this)
        newLabelDialog!!.customView(R.layout.dialog_add_new_label)
        newLabelDialog!!.view.apply {
            add_label_color_btn.setOnClickListener{
                showColorPicker()
            }
        }
        newLabelDialog!!.positiveButton {
            val newLabelName = it.view.add_label_name_field.text?.trim().toString()
            if (newLabelName.isNotEmpty() && !labelAdapter!!.isLabelExisted(newLabelName))
                mPresenter.addLabel(newLabelName,newLabelColor)
            it.view.add_label_name_field.text?.clear()
        }
        newLabelDialog!!.negativeButton {
            it.dismiss()
        }
        newLabelDialog!!.title(null,"Add new label :")
        newLabelDialog!!.show {
        }
    }

    private fun checkEmptyView(){
        showMessage("${labelAdapter!!.itemCount}")
        if(labelAdapter!!.itemCount>0){
            labelsRecyclerView.visibility = View.VISIBLE
            labelEmptyView.visibility = View.GONE

        }else{
            labelsRecyclerView.visibility = View.GONE
            labelEmptyView.visibility = View.VISIBLE
        }
    }

    override fun onItemInserted(label : Label) {
        labelAdapter!!.add(label)
        newLabelDialog?.dismiss()
        checkEmptyView()

    }

    override fun onItemDeleted(label :Label) {
        labelAdapter!!.remove(label)
        checkEmptyView()
    }



}