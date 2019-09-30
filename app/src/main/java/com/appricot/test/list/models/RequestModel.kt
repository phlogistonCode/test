package com.appricot.test.list.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RequestModel(
    @PrimaryKey var id: Int?,
    var head: String?,
    var date: String?,
    var location: String?,
    var status: String?
)