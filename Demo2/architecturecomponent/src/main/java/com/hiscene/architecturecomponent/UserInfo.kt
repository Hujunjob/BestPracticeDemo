package com.hiscene.architecturecomponent

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by junhu on 2019-11-04
 */
@Entity
data class UserInfo(
        @PrimaryKey
        var id: Int = 0,
        var name: String = "",
        var age: Int = 0
)