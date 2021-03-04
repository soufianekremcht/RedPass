package com.soufianekre.redpass.ui.intro

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.soufianekre.redpass.R
import com.soufianekre.redpass.RedPassApp
import com.soufianekre.redpass.data.app_pref.PrefConst
import com.soufianekre.redpass.helpers.KeyboardUtils
import com.soufianekre.redpass.ui.base.BaseActivity
import com.soufianekre.redpass.ui.main.MainActivity



class IntroActivity :BaseActivity(),IntroMvp.View{


    @BindView(R.id.user_password_submit_btn)
    lateinit var submitBtn : Button

    @BindView(R.id.user_password_field)
    lateinit var passwordField:EditText

    @BindView(R.id.user_password_show_hide_img)
    lateinit var passwordShowHideBtn : ImageView

    @BindView(R.id.app_toolbar)
    lateinit var introToolbar : Toolbar

    lateinit var mPresenter: IntroPresenter<IntroMvp.View>

    private var isHidden : Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // check first Launch
        setContentView(R.layout.activity_intro)
        ButterKnife.bind(this)
        mPresenter = IntroPresenter()
        mPresenter.onAttach(this)

        if (RedPassApp.getPref().getBoolean(PrefConst.PREF_IS_FIRST_LAUNCH ,true)){
            RedPassApp.getPref().set(PrefConst.PREF_IS_FIRST_LAUNCH,false)
            showAppPasswordActivity()
            finish()
        }

        setupUi()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    private fun setupUi() {
        setSupportActionBar(introToolbar)

        submitBtn.setOnClickListener{
            mPresenter.checkSecurityPassword(passwordField.text.toString())
        }

        passwordShowHideBtn.setOnClickListener{
            if(isHidden){
                passwordField.transformationMethod = HideReturnsTransformationMethod.getInstance()
                isHidden = false
            } else{
                passwordField.transformationMethod = PasswordTransformationMethod.getInstance()
                isHidden = true
            }
        }

    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.intro,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.intro_menu_keyboard ->{
                KeyboardUtils.switchToNumeric(this,passwordField){
                    showMessage("The keyboard switched to ${(if (it) "Numeric" else "AlpaNumeric")}")
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setFieldError(error: String) {
        passwordField.error = error
    }


    private fun showAppPasswordActivity() {
        val intent = Intent(this,AppPasswordActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun showMainActivity() {
        val intent : Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (passwordField.isFocused){
            KeyboardUtils.hideSoftInput(this)
        }else{
            super.onBackPressed()
        }


    }
}