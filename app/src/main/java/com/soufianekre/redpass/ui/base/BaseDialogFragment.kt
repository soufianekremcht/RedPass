package com.soufianekre.redpass.ui.base

import androidx.fragment.app.DialogFragment
import com.soufianekre.redpass.ui.base.mvp.BaseMvp

open class BaseDialogFragment : DialogFragment(),BaseMvp.View{
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