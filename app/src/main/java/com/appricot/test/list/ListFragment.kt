package com.appricot.test.list

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.appricot.test.R
import com.appricot.test.R.layout
import com.appricot.test.api.GlabstoreApi
import com.appricot.test.api.Request
import com.appricot.test.db.RequestModelDao
import com.appricot.test.list.adapter.AdapterRecycleViewList
import com.appricot.test.list.adapter.Selector
import com.appricot.test.list.models.RequestModel
import com.appricot.test.main.MainActivity
import com.appricot.test.utils.normalizeTime
import com.appricot.test.utils.translateStatus
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject


class ListFragment @Inject constructor() : DaggerFragment() {

    @Inject
    lateinit var service: GlabstoreApi
    @Inject
    lateinit var db: RequestModelDao

    private val adapterRecView = AdapterRecycleViewList(object: Selector {
        override fun onItemSelected(id: Int) {
            toDetails(id.toString())
        }
    })

    private var sortType: Int = 1
    var sPref: SharedPreferences? = null

    private val onItemSpinnerSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            itemSelected: View?,
            selectedItemPosition: Int,
            selectedId: Long
        ) {
            val types = resources.getStringArray(R.array.sortList)
            adapterRecView.filter.filter(types[selectedItemPosition])
            sortType = selectedItemPosition
            saveSort()
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
        loadSort()
        downloadListToDB(sortType)

        return inflater.inflate(layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rvList.layoutManager = LinearLayoutManager(activity)
        rvList.adapter = adapterRecView

        spinner.onItemSelectedListener = onItemSpinnerSelectedListener

        Log.d("ListFragment", "after activity created")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        saveSort()
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
                    if (requestMain?.status!!) {
                        if (!requestMain.data.isNullOrEmpty()) {
                            requestMain.data?.forEach {
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
                            adapterRecView.prependData(listRequestModel.sortedWith(compareBy { it.date }))
                            spinner.setSelection(sortType)
                        }
                    } else {
//                        showSnackbarText(тут текст из поля error, которого нет)
                    }

                } else {
                    showSnackbarRetry(response.errorBody().toString())
                    Log.d("MainActivity ", response.errorBody().toString())
                }
            } catch (e: UnknownHostException) {
                showSnackbarRetry("Ошибка подключения!")
            } catch (e: Exception) {}
        }
    }

    private fun saveSort() {
        sPref = activity?.getPreferences(MODE_PRIVATE)
        val ed = sPref?.edit()
        ed?.putInt("sort", sortType)
        ed?.apply()
    }

    private fun loadSort() {
        sPref = activity?.getPreferences(MODE_PRIVATE)
        val savedSort = sPref?.getInt("sort", 1)
        sortType = savedSort!!
    }

    private fun showSnackbarRetry(text: String) {
        Snackbar.make(activity!!.container, text, Snackbar.LENGTH_LONG)
            .setAction("RETRY") { downloadListToDB(sortType) }
            .show()
    }

    private fun showSnackbarText(text: String) {
        Snackbar.make(activity!!.container, text, Snackbar.LENGTH_SHORT).show()
    }

    private fun toDetails(infoToFragment: String?) {
        (activity as MainActivity).presenter.toDetailsFragment(infoToFragment)
    }

}