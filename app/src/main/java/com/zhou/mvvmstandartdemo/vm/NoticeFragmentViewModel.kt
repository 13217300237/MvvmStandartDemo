package com.zhou.mvvmstandartdemo.vm

import androidx.lifecycle.ViewModel
import com.zhou.baselib.bus.SingleLiveData
import com.zhou.mvvmstandartdemo.m.NoticeModel

class NoticeFragmentViewModel : ViewModel() {
    val noticeLiveData: SingleLiveData<String> by lazy { SingleLiveData<String>() }

    fun getMsg() {
        noticeLiveData.postValue(NoticeModel().getNotice())
    }
}