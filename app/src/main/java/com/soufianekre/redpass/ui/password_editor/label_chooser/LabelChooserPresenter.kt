package com.soufianekre.redpass.ui.password_editor.label_chooser

import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.ui.base.mvp.BasePresenter
import timber.log.Timber

class LabelChooserPresenter<V : LabelChooserMvp.View> : BasePresenter<V>(),LabelChooserMvp.Presenter<V>{

    override fun getLabels() {
        compositeDisposable.add(dataManager.getAppDatabase().labelDoa().getLabels()
            .compose(schedulerProvider.ioToMainFlowableScheduler())
            .subscribe ({
                getMvpView()?.showLabels(ArrayList(it))
            },{
                if (it.localizedMessage != null)
                Timber.e(it.localizedMessage)
            })

        )
    }

    override fun onLabelClicked(label: Label) {
        getMvpView()!!.setPasswordItemLabel(label)
    }





}