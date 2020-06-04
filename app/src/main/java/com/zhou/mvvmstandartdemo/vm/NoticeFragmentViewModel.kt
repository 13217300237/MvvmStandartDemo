package com.zhou.mvvmstandartdemo.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhou.mvvmstandartdemo.m.NoticeModel

class NoticeFragmentViewModel : ViewModel() {
     val noticeLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    fun getMsg() {
        noticeLiveData.postValue(NoticeModel().getNotice())
    }
}