package com.appricot.test.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.appricot.test.R.layout
import com.appricot.test.api.GlabstoreApi
import com.appricot.test.api.Request
import com.appricot.test.db.RequestModelDao
import com.appricot.test.db.RequestModelDataBase
import com.appricot.test.list.adapter.AdapterRecycleViewList
import com.appricot.test.list.models.RequestModel
import com.appricot.test.utils.normalizeTime
import com.appricot.test.utils.translateStatus
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class ListFragment @Inject constructor() : DaggerFragment() {

    @Inject
    lateinit var service: GlabstoreApi

    @Inject
    lateinit var db: RequestModelDao

    private var adapterRecView: AdapterRecycleViewList? = null

    private var sortType: Int = 1

    private val onItemClickListener = View.OnClickListener {

    }

    // TODO: https://github.com/guenodz/livedata-recyclerview-sample

    private val onItemSpinnerSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            itemSelected: View?,
            selectedItemPosition: Int,
            selectedId: Long
        ) {
            val types = resources.getStringArray(com.appricot.test.R.array.sortList)
            adapterRecView?.filter?.filter(types[selectedItemPosition])
            sortType = selectedItemPosition
            Log.d("sort", sortType.toString())
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ListFragment", "create List")
        sortType = savedInstanceState?.getInt("sort") ?: 1
        adapterRecView = AdapterRecycleViewList()
        downloadListToDB(sortType)
        presentListFromDB()

        return inflater.inflate(layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rvList.layoutManager = LinearLayoutManager(activity)
        rvList.adapter = adapterRecView

        spinner.onItemSelectedListener = onItemSpinnerSelectedListener

        adapterRecView?.setOnItemClickListener(onItemClickListener)

        Log.d("ListFragment", "after activity created")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("sort", sortType)
        super.onSaveInstanceState(outState)
    }

    private fun downloadListToDB(sortType: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val requestMain: Request?
            val listRequestModel: MutableList<RequestModel> = mutableListOf()

            val listRequest = service.getDeferredListRquests()
            try {
                val response = listRequest.await()
                if (response.isSuccessful) {
                    requestMain = response.body()
                    if (!requestMain?.data.isNullOrEmpty()) {
                        requestMain?.data?.forEach {
                            listRequestModel.add(
                                RequestModel(
                                    id = it.id,
                                    head = it.title,
                                    date = it.actual_time?.normalizeTime(),
                                    location = it.location,
                                    status = it.status?.translateStatus()
                                )
                            )
                        }
                        Log.d("listRequestModel ", listRequestModel.toString())
                        spinner.setSelection(sortType)
                        db.deleteAll()
                        db.insertList(listRequestModel)
//                        adapterRecView?.prependData(listRequestModel.sortedWith(compareBy { it.date }))
                    }
                } else {
                    Log.d("MainActivity ", response.errorBody().toString())
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun presentListFromDB() {
        GlobalScope.launch(Dispatchers.Main) {
            adapterRecView?.prependData(db.getAll())
        }
    }

}