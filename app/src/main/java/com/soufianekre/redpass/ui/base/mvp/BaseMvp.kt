package com.soufianekre.redpass.ui.base.mvp

import androidx.annotation.StringRes


class BaseMvp {

    interface View{

        fun showError(message: String?)

        fun showMessage(message: String?)
        fun hideKeyboard()
    }
    interface Presenter<V :View> {
        fun getMvpView() :V?
        fun onAttach(mvpView: V?)
        fun onDetach()

    }
}