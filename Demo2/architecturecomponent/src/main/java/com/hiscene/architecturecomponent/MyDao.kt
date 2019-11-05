package com.hiscene.architecturecomponent

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by junhu on 2019-11-04
 */
@Dao
interface MyDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertUser(user: User)
//
//    @Insert
//    fun insertUsers(user1: User, user2: User)
//
//    @Insert
//    fun insertUsersAndFriends(user: User, friends: List<User>)
//
//    @Insert
//    fun insertUserss(vararg user: User)
//
//    @Update
//    fun updateUsers(vararg user: User)
//
//    @Delete
//    fun deleteUsers(vararg user: User)
//
//    @Query(value = "select * from user")
//    fun loadAllUsers(): Array<User>
//
//    @Query("select * from user where age>:age")
//    fun loadUser(age: Int): Array<User>
//
//    @Query("select * from user where first_name in (:firstName)")
//    fun loadUserFromNames(firstName: List<String>): Array<User>
//
//    @Query("select first_name from user")
//    fun loadAllUserNames(): Array<String>
//
//    @Query("select first_name,last_name from user")
//    fun loasNames(): Array<NameBean>
//
//    @Query("select first_name,last_name from user where first_name in (:firstName)")
//    fun loadAllUsersFromNameSync(firstName: List<String>):LiveData<List<User>>
}