package com.hiscene.architecturecomponent

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by hujun on 2019-11-02.
 */

// Copyright (c) 2019 hujun. All rights reserved.
@Entity(tableName = "")
class User {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "first_name")
    lateinit var firstName : String

    lateinit var lastName : String

    @Ignore
    lateinit var picture : Bitmap
}