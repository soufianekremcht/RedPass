package com.soufianekre.redpass.ui.main.drawer

import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.ui.base.mvp.BasePresenter

class NavDrawerPresenter<V : NavDrawerMvp.View> : BasePresenter<V>() , NavDrawerMvp.Presenter<V>{


    override fun getLabels() {
        compositeDisposable.add(dataManager.getAppDatabase().labelDoa().getLabels()
            .compose(schedulerProvider.ioToMainFlowableScheduler())
            .subscribe {
                getMvpView()?.buildLabelsMenu(it)
            }
        )
    }


}