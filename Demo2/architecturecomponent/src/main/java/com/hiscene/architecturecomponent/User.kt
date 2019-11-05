package com.hiscene.architecturecomponent

import android.graphics.Bitmap
import androidx.room.*

/**
 * Created by hujun on 2019-11-02.
 */

// Copyright (c) 2019 hujun. All rights reserved.
@Entity(tableName = "user", indices = arrayOf(Index("first_name")))
class User {
    @PrimaryKey
    var id: Int = 0

    @ColumnInfo(name = "first_name")
    var firstName: String = ""

    @ColumnInfo(name = "last_name")
    var lastName: String = ""

    var age: Int = 0

    @Ignore
    var picture: Bitmap? = null

//    @Embedded(prefix = "book_")
//    lateinit var book: Book
}