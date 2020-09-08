package com.soufianekre.redpass.ui.labels

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.soufianekre.redpass.R
import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.ui.base.BaseActivity
import com.soufianekre.redpass.ui.views.CustomDividerItemDecoration

class LabelsActivity :BaseActivity(),LabelsMvp.View{


    //views
    @BindView(R.id.app_toolbar)
    lateinit var appToolbar:Toolbar

    @BindView(R.id.labels_add_label_field)
    lateinit var addLabelField: EditText

    @BindView(R.id.labels_add_label_btn)
    lateinit var addLabelImgBtn : ImageView


    @BindView(R.id.labels_recycler_view)
    lateinit var labelsRecyclerView: RecyclerView

    @BindView(R.id.labels_empty_view)
    lateinit var labelEmptyView :RelativeLayout



    private  var labelAdapter: LabelsAdapter? = null

    private lateinit var mPresenter : LabelsPresenter<LabelsMvp.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_labels)
        mPresenter = LabelsPresenter()
        ButterKnife.bind(this)
        mPresenter.onAttach(this)
        setupUi()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    private fun setupUi(){
        //toolbar
        appToolbar.title = "Edit Label"
        appToolbar.setOnClickListener{
            onBackPressed()
        }
        setSupportActionBar(appToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        addLabelImgBtn.setOnClickListener{
            mPresenter.addLabel(addLabelField.text.toString())
            addLabelField.text.clear()

        }

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

    private fun checkEmptyView(){
        showMessage(""+labelAdapter!!.itemCount)
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
        checkEmptyView()

    }

    override fun onItemDeleted(label :Label) {
        labelAdapter!!.remove(label)
        checkEmptyView()
    }



}