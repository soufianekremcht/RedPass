package com.soufianekre.redpass.ui.labels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soufianekre.redpass.R
import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.helpers.AppHelper
import kotlinx.android.synthetic.main.item_label.view.*

class LabelsAdapter(private var mContext : Context, private var labels : ArrayList<Label>,var listener: LabelsAdapterListener?) : RecyclerView.Adapter<LabelsAdapter.LabelViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_label,parent,false)
        return LabelViewHolder(view)
    }

    override fun getItemCount(): Int {
        return labels.size
    }

    override fun onBindViewHolder(holder: LabelViewHolder, position: Int) {
        val label = labels[position]
        holder.labelText.text = label.name
        holder.labelIcon.setColorFilter(AppHelper.getRandomMaterialColor(mContext,400))

        setListeners(holder,position)
    }

    private fun setListeners(holder: LabelViewHolder, position: Int){
        holder.itemView.setOnClickListener{
            listener?.onLabelClicked(labels[position])
        }
        holder.itemView.setOnLongClickListener{
            if (listener == null)
                return@setOnLongClickListener false
            else
                listener!!.onLabelLongClicked(labels[position])

        }
        holder.deleteLabelBtn.setOnClickListener{
            listener?.onLabelDeleted(labels[position])
        }

    }

    fun addAll(labelList: List<Label>){
        labels.clear()
        labels.addAll(labelList)
        notifyDataSetChanged()
    }
    fun remove(label :Label){
        for ( i in 0 until labels.size){
            if (labels[i] == label)
                labels.remove(label)
                notifyItemRemoved(i)
        }

    }
    fun add(label :Label){
        labels.add(label)
        notifyItemInserted(labels.size- 1)
    }



    inner class LabelViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var labelText: TextView = itemView.label_text
        var labelIcon: ImageView = itemView.label_icon
        var deleteLabelBtn:ImageView = itemView.label_delete_img
    }

    interface LabelsAdapterListener{
        fun onLabelClicked(label :Label)
        fun onLabelLongClicked(label:Label) : Boolean
        fun onLabelDeleted(label :Label)
    }
}