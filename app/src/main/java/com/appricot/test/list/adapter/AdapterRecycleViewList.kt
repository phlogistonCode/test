package com.appricot.test.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appricot.test.R
import com.appricot.test.list.models.RequestModel

class AdapterRecycleViewList: RecyclerView.Adapter<ViewHolderList>() {

    private var dataSet: List<RequestModel> = ArrayList()

    private var onHolderClickListener: View.OnClickListener? = null

    override fun getItemCount() = dataSet.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderList {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycleview_list, parent, false)
        return ViewHolderList(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderList, position: Int) {
        val item = dataSet[position]
        holder.tvHead?.text = item.head
        holder.tvDate?.text = item.date.toString()
        holder.tvLocation?.text = item.location
        holder.tvStatus?.text = item.status

        holder.setOnHolderClickListener(onHolderClickListener)
    }

    fun setOnCheckBoxCheckedChangeListener (checkedChangeListener: View.OnClickListener?) {
        onHolderClickListener = checkedChangeListener
    }

    fun clearData() {
        dataSet = emptyList()
        notifyDataSetChanged()
    }

    fun prependData(dataSet: List<RequestModel>) {
        this.dataSet = dataSet
        notifyItemRangeInserted(0, dataSet.size)
    }

    fun appendData(dataSet: List<RequestModel>) {
        val previousDataSetSize = this.dataSet.size
        this.dataSet += dataSet
        notifyItemRangeInserted(previousDataSetSize, dataSet.size)
    }

}