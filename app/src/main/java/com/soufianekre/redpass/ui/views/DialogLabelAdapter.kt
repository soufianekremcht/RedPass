package com.soufianekre.redpass.ui.views


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.helpers.AppHelper
import kotlinx.android.synthetic.main.item_drawer_label.view.*


class DialogLabelAdapter(var mActivity: Activity, var labels: ArrayList<Label>,
                         var navigationTmp: String? = null, var listener : DrawerLabelListener
) :
    BaseAdapter() {


    private var holder : LabelBaseViewHolder? = null


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val label :Label = labels[position]
        var view : View? = convertView
        if (view == null){
            val inflater = mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(com.soufianekre.redpass.R.layout.item_drawer_label,parent,false)
            holder = LabelBaseViewHolder(view)


        }else{
            holder = convertView!!.tag as LabelBaseViewHolder
        }
        holder!!.drawerLabelText.text = label.name
        holder!!.drawerLabelIcon.setColorFilter(AppHelper.getRandomMaterialColor(mActivity,400))

        view!!.setOnClickListener{
            listener.onItemClicked(label)
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return labels[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return labels.size
    }

    fun addAll(labelList:List<Label>){
        labels.clear()
        labels.addAll(labelList)
        notifyDataSetChanged()
    }


    inner class LabelBaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var drawerLabelText :TextView = itemView.drawer_item_label_text
        var drawerLabelIcon :ImageView = itemView.drawer_item_label_icon

    }

    interface DrawerLabelListener{
        fun onItemClicked(label:Label)
    }




}