package com.appricot.test.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.appricot.test.R
import com.appricot.test.list.models.RequestModel

class AdapterRecycleViewList : RecyclerView.Adapter<ViewHolderList>(), Filterable {

    private var dataSet: List<RequestModel> = ArrayList()
    private var dataSetFilterable: List<RequestModel> = ArrayList()

    private var onHolderClickListener: View.OnClickListener? = null

    override fun getItemCount() = dataSetFilterable.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderList {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycleview_list, parent, false)
        return ViewHolderList(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderList, position: Int) {
        val item = dataSetFilterable[position]
        holder.tvHead?.text = item.head
        holder.tvDate?.text = item.date.toString()
        holder.tvLocation?.text = item.location
        holder.tvStatus?.text = item.status

        holder.itemView.setOnClickListener(onHolderClickListener)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                dataSetFilterable = if (charString == "Все") {
                    dataSet.toList()
                } else {
                    val filteredList: ArrayList<RequestModel> = ArrayList()
                    when (charString) {
                        "Открытые" -> dataSet.forEach { if (it.status == "открыта") filteredList.add(it) }
                        "Занятые" -> dataSet.forEach { if (it.status == "занята") filteredList.add(it) }
                        "Закрытые" -> dataSet.forEach { if (it.status == "закрыта") filteredList.add(it) }
                    }
                    filteredList.toList()
                }

                val filterResults = FilterResults()
                filterResults.count = dataSetFilterable.size
                filterResults.values = dataSetFilterable
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                clearData()
                dataSetFilterable = filterResults.values as List<RequestModel>
                notifyDataSetChanged()
            }
        }
    }

    fun setOnItemClickListener(checkedChangeListener: View.OnClickListener?) {
        onHolderClickListener = checkedChangeListener
    }

    fun clearData() {
        dataSetFilterable = emptyList()
        notifyDataSetChanged()
    }

    fun prependData(dataSet: List<RequestModel>) {
        this.dataSetFilterable = dataSet
        this.dataSet = dataSet
        notifyItemRangeInserted(0, dataSet.size)
    }

    fun appendData(dataSet: List<RequestModel>) {
        val previousDataSetSize = this.dataSetFilterable.size
        this.dataSetFilterable += dataSet
        this.dataSet += dataSet
        notifyItemRangeInserted(previousDataSetSize, dataSet.size)
    }

}