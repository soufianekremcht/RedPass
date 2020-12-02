package com.soufianekre.redpass.ui.base

import androidx.fragment.app.DialogFragment
import com.soufianekre.redpass.ui.base.mvp.BaseMvp

open class BaseDialogFragment : DialogFragment(),BaseMvp.View{
    private val baseActivity : BaseActivity? = activity as? BaseActivity


    override fun showError(tag: String, resId: Int) {
        onError(tag ,baseActivity!!.getString(resId))
    }




    override fun showError(tag: String, message: String?) {
        baseActivity!!.showError(tag,message)
    }


    override fun showMessage(message: String?) {
        baseActivity!!.showMessage(message)
    }

    override fun showMessage(resId: Int) {
        baseActivity!!.showMessage(resId)
    }

    override fun hideKeyboard() {
        baseActivity!!.hideKeyboard()
    }

}