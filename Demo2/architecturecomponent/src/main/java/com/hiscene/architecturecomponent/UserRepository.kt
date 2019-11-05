package com.hiscene.architecturecomponent

import androidx.lifecycle.LiveData
import androidx.room.Room

/**
 * Created by junhu on 2019-11-05
 */
class UserRepository {
    private var userinfoDao: UserInfoDao

    init {
        val myDataBase = Room.databaseBuilder(AppApplication.instance,MyDataBase::class.java,"userinfo_database").build()
        userinfoDao = myDataBase.userInfoDao()
    }

    fun getAllUserInfo(): LiveData<List<UserInfo>> {
        return userinfoDao.getAllUser()
    }

    fun insert(userInfo: UserInfo) {
        userinfoDao.addUser(userInfo)
    }
}