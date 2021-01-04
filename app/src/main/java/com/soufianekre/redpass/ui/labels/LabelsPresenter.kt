package com.soufianekre.redpass.ui.labels

import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.ui.base.mvp.BasePresenter
import timber.log.Timber

class LabelsPresenter<V : LabelsMvp.View> :BasePresenter<V>(),LabelsMvp.Presenter<V>{

    override fun addLabel(name:String,color:Int) {
        val label = Label(name,color.toString())
        compositeDisposable.add(dataManager.getAppDatabase().labelDoa().insertLabel(label)
            .compose(schedulerProvider.ioToMainCompletableScheduler())
            .subscribe ({
                getMvpView()?.onItemInserted(label)
            },{
                getMvpView()?.showError(it.localizedMessage)
                Timber.e(it.localizedMessage)
            })

        )
    }

    override fun getLabels(){
        compositeDisposable.add(dataManager.getAppDatabase().labelDoa().getLabels()
            .compose(schedulerProvider.ioToMainFlowableScheduler())
            .subscribe ({
                getMvpView()?.updateAdapter(it)
            },{

                Timber.e(it.localizedMessage)
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