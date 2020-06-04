package com.zhou.mvvmstandartdemo.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhou.baselibrary.network.HttpCallback
import com.zhou.mvvmstandartdemo.m.UserModel
import com.zhou.mvvmstandartdemo.m.bean.user.UserBean


/**
 * 业务逻辑转移到这里
 *
 * 原则上，一个界面（Activity或者Fragment）对应一个 UserModel
 *
 */
class LoginActivityViewModel : ViewModel() {
    val userLiveData: MutableLiveData<UserBean> by lazy {
        MutableLiveData<UserBean>()
    }

    fun doLogin(userName: String, password: String) {

        // 发送网络请求，并且执行回调
        UserModel().login(userName, password, object : HttpCallback<UserBean> {
            override fun onSuccess(result: UserBean?) {
                userLiveData.postValue(result)
            }

            override fun onFailure(e: Exception?) {
                userLiveData.postValue(UserBean())// 失败，则手动构建一个userBean
            }
        })
    }

}