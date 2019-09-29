package com.appricot.test.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface  GlabstoreApi {

    @GET("/test/list.json")
    fun getDeferredListRquests(): Deferred<Response<Request>>
}