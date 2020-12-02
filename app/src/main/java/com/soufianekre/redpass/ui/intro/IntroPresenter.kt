package com.soufianekre.redpass.ui.intro

import com.soufianekre.redpass.MyApp
import com.soufianekre.redpass.data.app_pref.PrefConst
import com.soufianekre.redpass.ui.base.mvp.BasePresenter

class IntroPresenter <V :IntroMvp.View> :BasePresenter<V>(),IntroMvp.Presenter<V>{




    override fun setSecurityPassword(passwordSubmitted: String,confirmedPassword : String) {
        if (passwordSubmitted == confirmedPassword){
            MyApp.getPref().set(PrefConst.PREF_APP_PASSWORD,passwordSubmitted)
            getMvpView()?.showMainActivity()

        }else{
            getMvpView()?.setFieldError("The Passwords are  Not Matched")
        }


    }

    override fun checkSecurityPassword(passwordSubmitted:String) {
        val userPassword :String ?= MyApp.getPref()
            .getString(PrefConst.PREF_APP_PASSWORD,PrefConst.EMPTY_APP_PASSWORD)
        if (passwordSubmitted == userPassword){
            getMvpView()?.showMainActivity()
        }else{
            getMvpView()?.setFieldError("The Password Is Incorrect")
        }

    }

}