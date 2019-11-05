package com.hiscene.architecturecomponent

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by junhu on 2019-11-04
 */
@Database(entities = arrayOf(User::class, UserInfo::class), version = 1)
abstract class MyDataBase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao
    abstract fun userDao(): MyDao
}