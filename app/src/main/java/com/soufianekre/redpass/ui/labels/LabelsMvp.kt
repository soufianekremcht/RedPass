package com.soufianekre.redpass.ui.labels

import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.ui.base.mvp.BaseMvp

class LabelsMvp{
    interface View :BaseMvp.View{
        fun updateAdapter(labels :List<Label>)
        fun onItemInserted(label :Label)
        fun onItemDeleted(label : Label)
    }
    interface Presenter<V : View> : BaseMvp.Presenter<V>,LabelsAdapter.LabelsAdapterListener{
        fun addLabel(name:String)
        fun getLabels()
        fun deleteLabel(label: Label)
    }
}