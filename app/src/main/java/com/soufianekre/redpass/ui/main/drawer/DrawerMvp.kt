package com.soufianekre.redpass.ui.main.drawer

import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.ui.base.mvp.BaseMvp
import com.soufianekre.redpass.ui.main.drawer.adapters.DrawerLabelsAdapter

class DrawerMvp{
    interface View :BaseMvp.View{
        fun buildLabelsMenu(labelList:List<Label>)
        fun openLabelsActivity()
        fun loadPasswordItemList(label:Label)
        fun showPasswordListFragment()
    }
    interface Presenter<V : View>:BaseMvp.Presenter<V>,DrawerLabelsAdapter.DrawerLabelsAdapterListener{
        fun getLabels()
        fun onDrawerItemClicked(item : Int)


    }
}