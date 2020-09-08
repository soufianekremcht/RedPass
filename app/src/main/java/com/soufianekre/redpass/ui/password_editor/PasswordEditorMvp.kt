package com.soufianekre.redpass.ui.password_editor

import com.soufianekre.redpass.data.db.models.PasswordItem
import com.soufianekre.redpass.ui.base.mvp.BaseMvp

class PasswordEditorMvp{
    interface View : BaseMvp.View{
        fun saveAndFinish()
        fun openLabelChooser(){

        }
    }
    interface Presenter<V : View> : BaseMvp.Presenter<V>{

        fun addPasswordItem(passwordItem: PasswordItem) : Unit
        fun updatePassword(passwordItem: PasswordItem): Unit
    }
}