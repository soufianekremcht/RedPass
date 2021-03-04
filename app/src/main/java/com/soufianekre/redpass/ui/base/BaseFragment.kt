package com.soufianekre.redpass.ui.base

import androidx.fragment.app.Fragment
import com.soufianekre.redpass.ui.base.mvp.BaseMvp

open class BaseFragment : Fragment(),BaseMvp.View{

    private val baseActivity : BaseActivity? = activity as? BaseActivity



    override fun showError(message: String?) {
        baseActivity!!.showError(message)
    }


    override fun showMessage(message: String?) {
        baseActivity!!.showMessage(message)
    }

    override fun hideKeyboard() {
        baseActivity!!.hideKeyboard()
    }




}