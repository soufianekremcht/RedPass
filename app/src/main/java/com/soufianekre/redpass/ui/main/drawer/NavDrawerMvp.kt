package com.soufianekre.redpass.ui.main.drawer

import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.ui.base.mvp.BaseMvp

class NavDrawerMvp{
    interface View :BaseMvp.View,NavDrawerLabelsAdapter.DrawerLabelsAdapterListener{
        fun buildLabelsMenu(labelList:List<Label>)
        fun showLabelsActivity()
        fun loadPasswordItemList(label:Label)
        fun showPasswordListFragment()
        fun showSettings()
    }
    interface Presenter<V : View>:BaseMvp.Presenter<V>{
        fun getLabels()
    }
}