package com.passerby.cfttestproject.bussiness.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Requests", indices = [Index(value = ["request"], unique = true)])
class RequestsEntity(
    @ColumnInfo(name = "request") val request: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}