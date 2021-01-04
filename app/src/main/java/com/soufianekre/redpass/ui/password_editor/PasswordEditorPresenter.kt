package com.soufianekre.redpass.ui.password_editor

import com.soufianekre.redpass.data.db.models.PasswordItem
import com.soufianekre.redpass.ui.base.mvp.BasePresenter
import timber.log.Timber

class PasswordEditorPresenter<V : PasswordEditorMvp.View> :BasePresenter<V>(),PasswordEditorMvp.Presenter<V>{



    override fun addPasswordItem(passwordItem : PasswordItem) {
        compositeDisposable.add(dataManager.getAppDatabase()
            .passwordItemDoa()
            .insertPasswordItem(passwordItem)
            .compose(schedulerProvider.ioToMainCompletableScheduler())
            .subscribe({
                getMvpView()?.showMessage("You Add The Account Successfully")
            },{
                getMvpView()?.showMessage(it.localizedMessage)
                Timber.e(it.localizedMessage)
            })

        )
    }

    override fun updatePassword(passwordItem : PasswordItem) {
        compositeDisposable.add(dataManager.getAppDatabase()
            .passwordItemDoa()
            .updatePasswordItem(passwordItem).compose(schedulerProvider.ioToMainCompletableScheduler())
            .subscribe({
                getMvpView()?.showMessage("You Add The Account Successfully")
            },{
                getMvpView()?.showMessage(it.localizedMessage)
                Timber.e(it.localizedMessage)
            })

        )
    }


}