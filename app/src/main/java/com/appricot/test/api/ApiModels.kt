package com.appricot.test.api

import com.squareup.moshi.Json

data class Request(
    @Json(name = "status")
    var status: Boolean? = null,
    @Json(name = "data")
    var data: List<Datum>? = null
)

data class Datum(
    @Json(name = "id")
    var id: Int? = null,
    @Json(name = "title")
    var title: String? = null,
    @Json(name = "actual_time")
    var actualTime: Int? = null,
    @Json(name = "status")
    var status: String? = null,
    @Json(name = "location")
    var location: String? = null
)