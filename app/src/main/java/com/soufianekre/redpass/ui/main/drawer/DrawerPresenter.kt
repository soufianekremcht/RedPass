package com.soufianekre.redpass.ui.main.drawer

import com.soufianekre.redpass.R
import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.ui.base.mvp.BasePresenter

class DrawerPresenter<V : DrawerMvp.View> : BasePresenter<V>() , DrawerMvp.Presenter<V>{


    override fun getLabels() {
        compositeDisposable.add(dataManager.getAppDatabase().labelDoa().getLabels()
            .compose(schedulerProvider.ioToMainFlowableScheduler())
            .subscribe {
                getMvpView()?.buildLabelsMenu(it)
            }

        )
    }
    override fun onLabelClicked(label: Label) {
        // load fragment
        getMvpView()!!.loadPasswordItemList(label)
    }

    override fun onLabelLongClicked(label : Label): Boolean {
        return false
    }
    override fun onDrawerItemClicked(item: Int) {
        when(item){
            R.id.drawer_item_settings -> return
            R.id.drawer_item_edit_labels-> getMvpView()?.openLabelsActivity()
            R.id.drawer_item_all -> getMvpView()?.showPasswordListFragment()
        }
    }

}