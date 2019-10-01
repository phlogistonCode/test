package com.appricot.test.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface  GlabstoreApi {

    @GET("/test/list.json")
    fun getDeferredListRquests(): Deferred<Response<Request>>

    @GET("/test/{id}.json")
    fun getDeferredDetails(@Path("id") id: String): Deferred<Response<Details>>
}