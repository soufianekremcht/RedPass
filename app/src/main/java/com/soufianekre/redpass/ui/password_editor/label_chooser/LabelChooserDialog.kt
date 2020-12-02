package com.soufianekre.redpass.ui.password_editor.label_chooser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.soufianekre.redpass.R
import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.ui.base.BaseDialogFragment
import com.soufianekre.redpass.ui.main.drawer.NavDrawerLabelsAdapter
import com.soufianekre.redpass.ui.password_editor.PasswordEditorActivity

class LabelChooserDialog : BaseDialogFragment(),LabelChooserMvp.View {


    @BindView(R.id.label_chooser_recycler_view)
    lateinit var labelChooserRecyclerView : RecyclerView

    @BindView(R.id.label_chooser_add_btn)
    lateinit var labelChooserBtn : Button

    private lateinit var passwordEditorActivity: PasswordEditorActivity
    private var mPresenter : LabelChooserPresenter<LabelChooserMvp.View>? = null
    private lateinit var labelsAdapter : NavDrawerLabelsAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        passwordEditorActivity = activity as PasswordEditorActivity


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View  = inflater.inflate(R.layout.dialog_label_chooser,container,false)
        mPresenter = LabelChooserPresenter()
        ButterKnife.bind(this,view)
        mPresenter!!.onAttach(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("Label Dialog","Is this visible?")

        labelChooserRecyclerView.itemAnimator = DefaultItemAnimator()
        labelChooserRecyclerView.layoutManager = LinearLayoutManager(requireContext()
                ,VERTICAL,false)

        labelsAdapter =
            NavDrawerLabelsAdapter(
                requireContext(),
                ArrayList(),
                mPresenter
            )
        labelChooserRecyclerView.adapter = labelsAdapter
        mPresenter!!.getLabels()


        labelChooserBtn.setOnClickListener{
            dismiss()
        }



    }
    
    override fun onDestroyView() {
        mPresenter!!.onDetach()
        super.onDestroyView()
    }

    override fun showLabels(labels:ArrayList<Label>){
        labelsAdapter.addAll(labels)
        showMessage("Labels : " +labels.size)
    }

    override fun setPasswordItemLabel(label: Label) {
        passwordEditorActivity.setLabelFromDialog(label)
        dismiss()
    }




}