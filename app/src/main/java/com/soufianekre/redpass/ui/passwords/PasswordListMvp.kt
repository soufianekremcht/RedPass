package com.soufianekre.redpass.ui.passwords

import android.view.ActionMode
import androidx.appcompat.widget.SearchView


import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.data.db.models.PasswordItem
import com.soufianekre.redpass.ui.base.mvp.BaseMvp

class PasswordListMvp{
    interface View : BaseMvp.View{
        fun notifyAdapter(passwordList: List<PasswordItem>)
        fun openPasswordEditorActivity(passwordItem :PasswordItem)
        fun onItemDeleted(password:PasswordItem)
        fun getActionMode() : ActionMode?
        fun toggleItemSelection(position : Int)
        fun enableActionMode(position : Int)
        fun setToolbarTitle(label : Label?)

    }
    interface Presenter<V: BaseMvp.View> :BaseMvp.Presenter<V>,PasswordListAdapter.PasswordListAdapterListener{
        fun getPasswords(query : String,label : Label?)
        fun deletePassword(password:PasswordItem)
        fun getSearchResults(searchView : SearchView)
    }
}