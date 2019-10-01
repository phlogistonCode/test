package com.appricot.test.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appricot.test.R
import com.appricot.test.api.Details
import com.appricot.test.api.GlabstoreApi
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

class DetailsFragment @Inject constructor() : DaggerFragment() {

    @Inject
    lateinit var service: GlabstoreApi

    private lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        id = arguments?.getString("id")!!

        downloadDetails(id)

        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        empty_button.setOnClickListener { showSnackbarText("Извините, данный функционал еще в разработке") }
    }

    private fun downloadDetails(id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val details: Details?
            val detailsResult = service.getDeferredDetails(id)
            try {
                val response = detailsResult.await()
                if (response.isSuccessful) {
                    details = response.body()
                    if (details?.status!!) {
                        with(details.data) {
                            descr_text.text = this?.description
                            if (this?.status == "closed" || this?.status == "in_progress") {
                                fio_group.visibility = View.VISIBLE
                                val fio = "${this.specialist?.first_name} ${this.specialist?.last_name}"
                                fio_text.text = fio
                            } else if (this?.status == "open") {
                                empty_button.visibility = View.VISIBLE
                            }
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
            } catch (e: Exception) {
            }
        }
    }

    private fun showSnackbarRetry(text: String) {
        Snackbar.make(activity!!.container, text, Snackbar.LENGTH_LONG)
            .setAction("RETRY") { downloadDetails(id) }
            .show()
    }

    private fun showSnackbarText(text: String) {
        Snackbar.make(activity!!.container, text, Snackbar.LENGTH_SHORT).show()
    }


}