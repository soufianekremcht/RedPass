package com.soufianekre.redpass.ui.intro

import com.soufianekre.redpass.ui.base.mvp.BaseMvp


class IntroMvp {
    interface View : BaseMvp.View{
        fun showMainActivity()
        fun setFieldError(error : String)

    }
    interface Presenter<V:View>: BaseMvp.Presenter<V>{
        fun checkSecurityPassword(passwordSubmitted : String)
        fun setSecurityPassword(passwordSubmitted: String,confirmedPassword : String)

    }
}