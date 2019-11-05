package com.hiscene.architecturecomponent

import androidx.room.ColumnInfo

/**
 * Created by junhu on 2019-11-04
 */
data class NameBean(
        @ColumnInfo(name = "first_name")
        var firstName:String="",
        @ColumnInfo(name = "last_name")
        var lastName:String=""

) {
    constructor():this("")
}