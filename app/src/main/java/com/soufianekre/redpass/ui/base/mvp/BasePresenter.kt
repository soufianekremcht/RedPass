package com.soufianekre.redpass.ui.base.mvp

import com.soufianekre.redpass.data.AppDataManager
import com.soufianekre.redpass.data.DataManager
import com.soufianekre.redpass.helpers.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V:BaseMvp.View>() :BaseMvp.Presenter<V>{

    var dataManager: DataManager = AppDataManager()
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val schedulerProvider : SchedulerProvider = SchedulerProvider()

    private var mvpView: V? = null
    private var isViewAttached: Boolean = mvpView != null

    override fun onAttach(mvpView: V?) {
        this.mvpView = mvpView
    }

    override fun getMvpView(): V? = mvpView

    override fun onDetach() {
        compositeDisposable.dispose()
        mvpView = null
    }





}