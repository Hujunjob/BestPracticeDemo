package com.hiscene.architecturecomponent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by junhu on 2019-11-04
 */
class NameViewModel : ViewModel() {
    private var repository: UserRepository

    init {
        repository = UserRepository()
    }

    fun getUserInfo():LiveData<List<UserInfo>>{
        return repository.getAllUserInfo()
    }

    fun insert(userInfo: UserInfo) {
        GlobalScope.launch {
            repository.insert(userInfo)
        }
    }
}