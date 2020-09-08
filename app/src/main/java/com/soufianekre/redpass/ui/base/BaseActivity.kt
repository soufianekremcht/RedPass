package com.soufianekre.redpass.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.soufianekre.redpass.helpers.KeyboardUtils

import com.soufianekre.redpass.ui.base.mvp.BaseMvp

@SuppressLint("Registered")
open class BaseActivity :AppCompatActivity(),BaseMvp.View{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onError(tag : String,resId: Int) {
        onError(tag,getString(resId))
    }

    override fun onError(tag:String,message: String?) {
        Log.e(tag,message)
    }

    override fun showMessage(message: String?) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(resId: Int) {
        showMessage(getString(resId))
    }

    override fun hideKeyboard() {
        KeyboardUtils.hideSoftInput(this)
    }
}