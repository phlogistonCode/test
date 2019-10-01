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
    var actual_time: Long? = null,
    @Json(name = "status")
    var status: String? = null,
    @Json(name = "location")
    var location: String? = null
)

data class Details(
    @Json(name = "status")
    var status: Boolean? = null,
    @Json(name = "data")
    var data: Data? = null
)

data class Data(
    @Json(name = "id")
    var id: Int? = null,
    @Json(name = "title")
    var title: String? = null,
    @Json(name = "actual_time")
    var actual_time: Long? = null,
    @Json(name = "status")
    var status: String? = null,
    @Json(name = "location")
    var location: String? = null,
    @Json(name = "description")
    var description: String? = null,
    @Json(name = "specialist")
    var specialist: Specialist? = null
)

data class Specialist(
    @Json(name = "first_name")
    var first_name: String,
    @Json(name = "last_name")
    var last_name: String
)