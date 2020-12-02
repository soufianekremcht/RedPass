package com.soufianekre.redpass.ui.main.drawer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.soufianekre.redpass.R
import com.soufianekre.redpass.data.db.models.Label
import kotlinx.android.synthetic.main.item_drawer_label.view.*

class NavDrawerLabelsAdapter (private var mContext : Context,
                              private var labels : ArrayList<Label>,
                              var listener: DrawerLabelsAdapterListener?)
    : RecyclerView.Adapter<NavDrawerLabelsAdapter.DrawerLabelViewHolder>() {

    var selectedItemPosition : Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerLabelViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.item_drawer_label, parent, false)

        return DrawerLabelViewHolder(view)
    }

    override fun getItemCount(): Int {
        return labels.size
    }

    override fun onBindViewHolder(holder: DrawerLabelViewHolder, position: Int) {
        val label = labels[position]
        holder.labelText.text = label.name
        if (selectedItemPosition == position){
            holder.itemView.setBackgroundResource(R.color.drawer_selection_color)
            holder.labelIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.accent_red))
        }else{
            holder.itemView.setBackgroundColor(android.R.attr.selectableItemBackground)

        }

        setListeners(holder, position)
    }

    private fun setListeners(holder: DrawerLabelViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            selectedItemPosition = position
            listener?.onLabelClicked(labels[position])
            notifyDataSetChanged()
        }
        holder.itemView.setOnLongClickListener {
            if (listener == null)
                return@setOnLongClickListener false
            else
                listener!!.onLabelLongClicked(labels[position])

        }

    }

    fun addAll(labelList: List<Label>) {
        labels.clear()
        labels.addAll(labelList)
        notifyDataSetChanged()
    }

    fun remove(label: Label) {
        for (i in 0..labels.size) {
            if (labels[i] == label)
                labels.remove(label)
            notifyItemRemoved(i)
        }

    }

    fun add(label: Label) {
        labels.add(label)
        notifyItemInserted(labels.size - 1)
    }


    inner class DrawerLabelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var labelText: TextView = itemView.drawer_item_label_text
        var labelIcon: ImageView = itemView.drawer_item_label_icon
    }

    interface DrawerLabelsAdapterListener {
        fun onLabelClicked(label:Label)
        fun onLabelLongClicked(label :Label): Boolean
    }
}