package com.soufianekre.redpass.ui.base.mvp

import androidx.annotation.StringRes


class BaseMvp {

    interface View{
        fun onError(tag:String,@StringRes resId: Int)

        fun onError(tag:String,message: String?)

        fun showMessage(message: String?)

        fun showMessage(@StringRes resId: Int)

        fun hideKeyboard()
    }
    interface Presenter<V :View> {
        fun getMvpView() :V?
        fun onAttach(mvpView: V?)
        fun onDetach()

    }
}