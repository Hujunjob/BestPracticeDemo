package com.hiscene.architecturecomponent

import androidx.lifecycle.LiveData
import androidx.room.*
import java.security.Policy

/**
 * Created by junhu on 2019-11-04
 */
@Dao
interface UserInfoDao {
    @Query("select * from userinfo")
    fun getAllUser(): LiveData<List<UserInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(userInfo: UserInfo)

    @Update
    fun updateUser(userInfo: UserInfo)

    @Delete
    fun deleteUser(userInfo: UserInfo)

    @Query("select * from userinfo where id=:id")
    fun getUser(id: Int):LiveData<UserInfo>
}