package com.soufianekre.redpass.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.soufianekre.redpass.helpers.KeyboardUtils

import com.soufianekre.redpass.ui.base.mvp.BaseMvp
import es.dmoral.toasty.Toasty

@SuppressLint("Registered")
open class BaseActivity :AppCompatActivity(),BaseMvp.View{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun showError(message: String?) {
        Toasty.error(this,message!!,Toasty.LENGTH_LONG).show()
    }

    override fun showMessage(message: String?) {
        Toasty.info(this,message!!,Toasty.LENGTH_SHORT).show()
    }

    override fun hideKeyboard() {
        KeyboardUtils.hideSoftInput(this)
    }
}