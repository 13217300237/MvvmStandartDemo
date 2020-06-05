package com.zhou.mvvmstandartdemo.vm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.zhou.baselib.BaseViewModel
import com.zhou.baselib.bus.EventType
import com.zhou.mvvmstandartdemo.m.NoticeModel

class NoticeFragmentViewModel : BaseViewModel() {

    fun getMsg() {
        liveDataHolder.getLiveData(String::class.java).postValue(NoticeModel().getNotice())
    }

    /**
     * 监听登录业务
     */
    fun observerGetMsg(lifecycleOwner: LifecycleOwner, observer: Observer<String>) {
        liveDataHolder.getLiveData(String::class.java, EventType.ALIVE)
            .observe(lifecycleOwner, observer)
    }
}