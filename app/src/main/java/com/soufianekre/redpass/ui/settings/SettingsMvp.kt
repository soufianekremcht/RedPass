package com.soufianekre.redpass.ui.settings

import com.soufianekre.redpass.ui.base.mvp.BaseMvp

interface SettingsMvp{
    interface View : BaseMvp.View{

    }
    interface Presenter : BaseMvp.Presenter<View>{

    }
}