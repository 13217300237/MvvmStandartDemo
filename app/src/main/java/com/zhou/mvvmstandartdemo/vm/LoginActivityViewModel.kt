package com.zhou.mvvmstandartdemo.vm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.zhou.baselib.BaseViewModel
import com.zhou.baselib.bus.EventType
import com.zhou.baselib.network.HttpCallback
import com.zhou.mvvmstandartdemo.m.NoticeModel
import com.zhou.mvvmstandartdemo.m.UserModel
import com.zhou.mvvmstandartdemo.m.bean.user.UserBean


/**
 * 业务逻辑转移到这里
 *
 * 原则上，一个界面（Activity或者Fragment）对应一个 UserModel
 *
 */
class LoginActivityViewModel : BaseViewModel() {

    /**
     * 触发业务
     */
    fun getMsg() {
        val noticeLiveData =
            liveDataHolder.getLiveData(String::class.java)
        noticeLiveData.postValue(NoticeModel().getNotice())
    }

    /**
     * 监听业务
     *
     * 为了不向外界暴露LiveData成员,提供一个注册监听的函数
     */
    fun observerGetMsg(lifecycleOwner: LifecycleOwner, observer: Observer<String>) {
        val noticeLiveData =
            liveDataHolder.getLiveData(String::class.java)
        noticeLiveData.observe(lifecycleOwner, observer)
    }

    /**
     * 触发登录业务
     */
    fun doLogin(userName: String, password: String) {
        // 发送网络请求，并且执行回调
        UserModel().login(userName, password, object : HttpCallback<UserBean> {
            override fun onSuccess(result: UserBean?) {
                liveDataHolder.getLiveData(UserBean::class.java, EventType.ALIVE).postValue(result)
            }

            override fun onFailure(e: Exception?) {
                liveDataHolder.getLiveData(UserBean::class.java, EventType.ALIVE)
                    .postValue(UserBean())
            }
        })
    }

    /**
     * 监听登录业务
     */
    fun observerDoLogin(lifecycleOwner: LifecycleOwner, observer: Observer<UserBean>) {
        liveDataHolder.getLiveData(UserBean::class.java, EventType.ALIVE)
            .observe(lifecycleOwner, observer)
    }

}