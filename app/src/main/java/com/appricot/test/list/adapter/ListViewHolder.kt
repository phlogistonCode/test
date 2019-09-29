package com.appricot.test.list.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appricot.test.R

class ViewHolderList(itemView: View): RecyclerView.ViewHolder(itemView) {
    var tvHead: TextView? = null
    var tvDate: TextView? = null
    var tvLocation: TextView? = null
    var tvStatus: TextView? = null
    init {
        tvHead = itemView.findViewById(R.id.text_head_request)
        tvDate = itemView.findViewById(R.id.text_date_request)
        tvLocation = itemView.findViewById(R.id.text_location_request)
        tvStatus = itemView.findViewById(R.id.text_status_request)
    }

    fun setOnHolderClickListener(clickListener: View.OnClickListener?) {
        itemView.setOnClickListener(clickListener)
    }
}