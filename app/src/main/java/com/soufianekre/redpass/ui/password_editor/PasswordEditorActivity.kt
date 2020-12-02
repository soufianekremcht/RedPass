package com.soufianekre.redpass.ui.password_editor

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.chip.Chip
import com.soufianekre.redpass.R
import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.data.db.models.PasswordItem
import com.soufianekre.redpass.helpers.AppConst.PASSWORD_TO_EDIT
import com.soufianekre.redpass.helpers.InputHelper
import com.soufianekre.redpass.helpers.PasswordUtils
import com.soufianekre.redpass.ui.base.BaseActivity
import com.soufianekre.redpass.ui.password_editor.label_chooser.LabelChooserDialog

class PasswordEditorActivity :BaseActivity(),PasswordEditorMvp.View{


    private var LABEL_CHOOSER_DIALOG_TAG : String = "label_chooser_dialog"
    // widgets

    @BindView(R.id.app_toolbar)
    lateinit var toolbar : Toolbar


    @BindView(R.id.chip_password_label)
    lateinit var passwordEditorChosenLabel : Chip

    @BindView(R.id.pass_title_field)
    lateinit var passwordEditorTitleField:EditText



    @BindView(R.id.pass_username_field)
    lateinit var passwordEditorUsernameField:EditText

    @BindView(R.id.pass_password_field)
    lateinit var passwordField:EditText

    @BindView(R.id.pass_account_use_field)
    lateinit var passwordEditorAccountUseField:EditText

    @BindView(R.id.pass_notes_field)
    lateinit var passwordEditorNotesField:EditText

    @BindView(R.id.pass_label_btn)
    lateinit var setLabelBtn : Button

    @BindView(R.id.password_show_hide_img)
    lateinit var passwordShowHideBtn : ImageView

    @BindView(R.id.password_generate_img)
    lateinit var passwordGenerationBtn : ImageView

    private  var currentPasswordItem : PasswordItem? = null
    private var passwordLabel : Label? = null

    lateinit var mPresenter : PasswordEditorMvp.Presenter<PasswordEditorMvp.View>

    private var isPasswordHidden = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_editor)
        ButterKnife.bind(this)
        mPresenter = PasswordEditorPresenter()
        mPresenter.onAttach(this)
        if (savedInstanceState == null)
            checkIntent()
        else
            currentPasswordItem = savedInstanceState.getSerializable(PASSWORD_ITEM) as PasswordItem
        setupUi()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_password_editor,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_password_editor_save -> {
                savePassword()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(PASSWORD_ITEM,currentPasswordItem)
    }

    private fun checkIntent() {
        currentPasswordItem= intent.getSerializableExtra(PASSWORD_TO_EDIT) as? PasswordItem
        if (currentPasswordItem!= null){

            getAccountData()
        }

    }

    private fun getAccountData() {
        passwordEditorTitleField.setText(currentPasswordItem!!.title)
        passwordEditorAccountUseField.setText( currentPasswordItem!!.account_use)
        passwordEditorUsernameField.setText( currentPasswordItem!!.username)
        passwordField.setText( currentPasswordItem!!.password)

        passwordEditorNotesField.setText( currentPasswordItem!!.notes)
        passwordLabel = currentPasswordItem!!.label



    }



    private fun setupUi(){

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        passwordGenerationBtn.setOnClickListener{
            passwordField.setText(PasswordUtils.generateRandomPassword(8))
        }
        passwordShowHideBtn.setOnClickListener{
            if(isPasswordHidden){
                passwordField.transformationMethod = HideReturnsTransformationMethod.getInstance()
                isPasswordHidden = false
            } else{
                passwordField.transformationMethod = PasswordTransformationMethod.getInstance()
                isPasswordHidden = true
            }
        }
        setLabelBtn.setOnClickListener{
            val dialog = LabelChooserDialog()
            dialog.show(this@PasswordEditorActivity.supportFragmentManager,"label_dialog")
        }
    }




    private fun checkSubmittedData(): Boolean {
        var result =true
        if (InputHelper.isEmpty(passwordEditorTitleField)){
            passwordEditorTitleField.error = "You Should Set A Title"
            result =false
        } else if (InputHelper.isEmpty(passwordEditorUsernameField)){
            passwordEditorUsernameField.error = "You Should Set A UserName"
            result =false
        }else if (InputHelper.isEmpty(passwordEditorAccountUseField)){
            passwordEditorAccountUseField.error = "You Should Set A The Use of The Account"
            result =false
        }else if(InputHelper.isEmpty(passwordField)){
            passwordField.error = "You Should Set A Password"
            result =false

        }
        return result
    }

    override fun saveAndFinish() {
        finish()
    }

    fun setLabelFromDialog(label : Label){
        passwordLabel = label
        passwordEditorChosenLabel.text = label.name
    }



    private fun savePassword(){
        if (checkSubmittedData()){
            if (currentPasswordItem == null){
                currentPasswordItem = PasswordItem()
                currentPasswordItem!!.title = passwordEditorTitleField.text.toString()
                currentPasswordItem!!.username = passwordEditorUsernameField.text.toString()
                currentPasswordItem!!.password = passwordField.text.toString()
                currentPasswordItem!!.account_use = passwordEditorAccountUseField.text.toString()
                currentPasswordItem!!.notes = passwordEditorNotesField.text.toString()
                if (passwordLabel != null)
                    currentPasswordItem!!.label = passwordLabel as Label
                mPresenter.addPasswordItem(currentPasswordItem!!)
            }else{
                currentPasswordItem!!.title = passwordEditorTitleField.text.toString()
                currentPasswordItem!!.password = passwordField.text.toString()
                currentPasswordItem!!.username = passwordEditorUsernameField.text.toString()
                currentPasswordItem!!.account_use = passwordEditorAccountUseField.text.toString()
                currentPasswordItem!!.notes = passwordEditorNotesField.text.toString()
                if (passwordLabel != null)
                    currentPasswordItem!!.label = passwordLabel as Label
                mPresenter.updatePassword(currentPasswordItem!!)
            }
            saveAndFinish()
        }
    }

    companion object {
        //const
        const val PASSWORD_ITEM = "password_item"
    }


}