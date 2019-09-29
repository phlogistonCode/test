package com.appricot.test.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.appricot.test.R.layout
import com.appricot.test.api.GlabstoreApi
import com.appricot.test.api.Request
import com.appricot.test.list.adapter.AdapterRecycleViewList
import com.appricot.test.list.models.RequestModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListFragment @Inject constructor() : DaggerFragment() {

    @Inject
    lateinit var service: GlabstoreApi

    private var values: MutableList<RequestModel> = mutableListOf()
    private var mAdapter: AdapterRecycleViewList? = null

    private val onListClickListener = View.OnClickListener {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ListFragment", "create List")
        loadRequests()
        mAdapter = AdapterRecycleViewList()

        return inflater.inflate(layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rvList.layoutManager = LinearLayoutManager(activity)
        rvList.adapter = mAdapter

        mAdapter?.setOnCheckBoxCheckedChangeListener(onListClickListener)

        Log.d("ListFragment", "after activity created")
    }

    private fun loadRequests() {
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
                                    date = it.actualTime,
                                    location = it.location,
                                    status = it.status
                                )
                            )
                        }
                        mAdapter?.prependData(listRequestModel)
                    }
                } else {
                    Log.d("MainActivity ", response.errorBody().toString())
                }

            } catch (e: Exception) {
            }
        }
    }

}