package com.soufianekre.redpass.ui.main

import androidx.fragment.app.Fragment
import com.soufianekre.redpass.ui.base.mvp.BaseMvp

interface MainMvp {

    interface  View :BaseMvp.View{
        fun openPasswordEditorActivity()
        fun loadFragment(fragment: Fragment)


    }

    interface Presenter<V :View> : BaseMvp.Presenter<V>{

    }
}