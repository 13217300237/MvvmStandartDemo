package com.zhou.mvvmstandartdemo.m

import com.zhou.baselib.BaseModel
import com.zhou.baselib.network.HttpCallback
import com.zhou.mvvmstandartdemo.m.network.HttpRequestManager
import com.zhou.mvvmstandartdemo.m.bean.user.UserBean

/**
 * 约束业务数据的接口
 */
interface UserModelInterface : BaseModel {
    fun login(userName: String, password: String, callback: HttpCallback<UserBean>)
}

/**
 * 纯粹的M层, 只有数据获取的代码
 */
class UserModel : UserModelInterface {
    override fun login(userName: String, password: String, callback: HttpCallback<UserBean>) {
        HttpRequestManager.doLogin(userName, password, callback)
    }

}
