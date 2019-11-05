package com.hiscene.architecturecomponent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Created by junhu on 2019-11-04
 */
@Entity(foreignKeys = arrayOf(ForeignKey(entity = User::class,parentColumns = arrayOf("id"),childColumns = arrayOf("userId"))))
data class Book(
    @PrimaryKey
    var bookId:Int=0,
    @ColumnInfo(name = "title")
    var title:String = ""
) {
    constructor():this(0)
}