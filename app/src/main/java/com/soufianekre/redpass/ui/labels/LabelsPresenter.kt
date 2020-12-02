package com.soufianekre.redpass.ui.labels

import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.ui.base.mvp.BasePresenter

class LabelsPresenter<V : LabelsMvp.View> :BasePresenter<V>(),LabelsMvp.Presenter<V>{


    override fun addLabel(name:String) {
        val label = Label(name)
        compositeDisposable.add(dataManager.getAppDatabase().labelDoa().insertLabel(label)
            .compose(schedulerProvider.ioToMainCompletableScheduler())
            .subscribe ({
                getMvpView()?.onItemInserted(label)
            },{
                getMvpView()?.showError("LabelPresenter",it.localizedMessage)
            })

        )
    }

    override fun getLabels(){
        compositeDisposable.add(dataManager.getAppDatabase().labelDoa().getLabels()
            .compose(schedulerProvider.ioToMainFlowableScheduler())
            .subscribe ({
                getMvpView()?.updateAdapter(it)
            },{
                getMvpView()?.showError("LabelPresenter",it.localizedMessage)
            })

        )
    }

    override fun deleteLabel(label:Label) {
        compositeDisposable.add(dataManager.getAppDatabase().labelDoa().deleteLabel(label)
            .compose(schedulerProvider.ioToMainCompletableScheduler())
            .subscribe {
                getMvpView()?.onItemDeleted(label)
            }

        )
    }

    override fun onLabelClicked(label: Label) {
        getMvpView()?.showMessage("Label : " + label.name)
    }

    override fun onLabelLongClicked(label: Label): Boolean {
        return false
    }

    override fun onLabelDeleted(label: Label) {
        deleteLabel(label)
    }



}