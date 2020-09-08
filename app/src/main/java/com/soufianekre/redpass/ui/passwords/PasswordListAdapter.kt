package com.soufianekre.redpass.ui.passwords

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soufianekre.redpass.data.db.models.PasswordItem
import com.soufianekre.redpass.helpers.AppHelper
import com.soufianekre.redpass.helpers.DateHelper
import com.soufianekre.redpass.helpers.FlipAnimator
import kotlinx.android.synthetic.main.card_password_item.view.*
import java.util.*


class PasswordListAdapter(context: Context, private var passwordList: ArrayList<PasswordItem>,private var listener :PasswordListAdapterListener) :
    RecyclerView.Adapter<PasswordListAdapter.PasswordListViewHolder>() {


    private var TIME_FORMAT: String = "hh:mm"
    var mContext: Context = context
    private var selectedItems : SparseBooleanArray = SparseBooleanArray(passwordList.size)
    private var animationItemsIndex : SparseBooleanArray = SparseBooleanArray(passwordList.size)

    var current_item_selected = -1
    var reverseAllAnimations = false
    var selection_mode :Boolean = false



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordListViewHolder {
        val view = LayoutInflater.from(mContext).inflate(com.soufianekre.redpass.R.layout.card_password_item,parent,false)
        return PasswordListViewHolder(view)
    }

    override fun getItemCount(): Int = passwordList.size

    override fun onBindViewHolder(holder: PasswordListViewHolder, position: Int) {
        var passwordItem  = passwordList[position]

        // change the row state to activated
        holder.itemView.isActivated = selectedItems.get(position, false);

        holder.passwordTitleText.text = passwordItem.title
        holder.passwordIconText.text = passwordItem.account_use.toUpperCase()[0].toString()
        holder.passwordIconImg.setColorFilter(AppHelper.getRandomMaterialColor(mContext,400))
        holder.passwordUseTitle.text = passwordItem.account_use

        holder.passwordCreationDateText.text = DateHelper.format(mContext, Date().time,TIME_FORMAT)


        setListeners(holder,position)
        applyIconAnimation(holder,position)
    }

    private fun setListeners(holder: PasswordListViewHolder, position:Int){


        holder.itemView.setOnClickListener{
            if (!selection_mode)
                listener.onItemClicked(passwordList[position],position)
            else
                toggleSelection(position)
        }
        holder.itemView.setOnLongClickListener{
            listener.onItemLongClicked(position)
            return@setOnLongClickListener true
        }
    }



    fun addAll(passwords : List<PasswordItem>){
        passwordList.clear()
        passwordList.addAll(passwords)
        notifyDataSetChanged()

    }

    fun remove(password: PasswordItem){
        for (i in 0..passwordList.size){
            if( password == passwordList[i]){
                passwordList.remove(password)
                notifyItemRemoved(i)
            }
        }
    }

    // Selection
    fun toggleSelection(pos: Int) {
        current_item_selected = pos
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos)

        } else {
            selectedItems.put(pos, true)
        }
        notifyItemChanged(pos)
    }

    fun selectAll() {
        if (passwordList.size > 1) {
            for (i in 0 until passwordList.size) {
                selectedItems.put(i, true)
            }
        }
        notifyDataSetChanged()


    }
    fun clearSelection() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun getSelectedItemCount(): Int {
        return selectedItems.size()
    }

    fun getSelectedItems(): List<Int> {
        val items = ArrayList<Int>(selectedItems.size())
        for (i in 0 until selectedItems.size()) {
            items.add(selectedItems.keyAt(i))
        }
        return items
    }


    private fun applyIconAnimation(holder: PasswordListViewHolder, position: Int) {
        if (selectedItems[position, false]) {
            holder.iconFront.visibility = GONE
            resetIconYAxis(holder.iconBack)
            holder.iconBack.visibility = VISIBLE
            holder.iconBack.alpha = 1f
            if (current_item_selected == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, true)
                resetCurrentIndex()
            }
        } else {
            holder.iconBack.visibility = GONE
            resetIconYAxis(holder.iconFront)
            holder.iconFront.visibility = VISIBLE
            holder.iconFront.alpha = 1f
            if (reverseAllAnimations && animationItemsIndex.get(position, false)
                || current_item_selected == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, false)
                resetCurrentIndex()
            }
        }
    }


    // As the views will be reused, sometimes the icon appears as
    // flipped because older view is reused. Reset the Y-axis to 0
    private fun resetIconYAxis(view: View) {
        if (view.rotationY != 0f) {
            view.rotationY = 0f
        }
    }

    fun resetAnimationIndex() {
        reverseAllAnimations = false
        animationItemsIndex.clear()
    }

    private fun  resetCurrentIndex(){
        current_item_selected = -1
    }


    inner class PasswordListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iconBack : RelativeLayout = itemView.icon_back

        var iconFront : RelativeLayout = itemView.icon_front

        var passwordIconImg : ImageView = itemView.password_icon_img

        var passwordIconText:TextView = itemView.password_icon_text

        var passwordTitleText :TextView = itemView.password_title

        var passwordUseTitle: TextView = itemView.password_account_use_title

        var passwordCreationDateText:TextView = itemView.password_creation_date_text

    }

    interface PasswordListAdapterListener{
        fun onItemClicked(passwordItem: PasswordItem,position: Int)
        fun onItemLongClicked(position : Int)

    }

}

