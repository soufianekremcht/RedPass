package com.soufianekre.redpass.ui.password_editor.label_chooser

import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.ui.base.mvp.BaseMvp
import com.soufianekre.redpass.ui.main.drawer.NavDrawerLabelsAdapter

class LabelChooserMvp{
    interface View : BaseMvp.View{
        fun showLabels(labels : ArrayList<Label>)
        fun setPasswordItemLabel(label : Label)

    }
    interface Presenter<V : View> : BaseMvp.Presenter<V>,
        NavDrawerLabelsAdapter.DrawerLabelsAdapterListener{
        fun getLabels()

    }
}