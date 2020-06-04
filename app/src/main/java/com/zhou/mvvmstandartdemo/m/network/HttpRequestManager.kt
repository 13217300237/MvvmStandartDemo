package com.zhou.mvvmstandartdemo.m.network

import com.zhou.baselibrary.network.HttpCallback
import com.zhou.baselibrary.network.Https
import com.zhou.mvvmstandartdemo.m.bean.user.UserBean
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * 集中管理网络请求
 *
 * PS:偷个懒，直接用共生体写静态方法了，本来应该写成单例。
 */
class HttpRequestManager {

    companion object {
        fun doLogin(
            username: String,
            password: String,
            httpCallback: HttpCallback<UserBean>
        ) {
            val url = "https://www.wanandroid.com/user/login"
            Https(url)
                .addParam("username", username)
                .addParam("password", password)
                .post(object : Https.ResponseCallback<UserBean?> {
                    override fun onSuccess(
                        request: Request?,
                        response: Response?,
                        result: UserBean?,
                        code: Int
                    ) {
                        httpCallback.onSuccess(result)
                    }

                    override fun onFailure(request: Request?, e: IOException?) {
                        httpCallback.onFailure(e)
                    }
                })
        }


    }
}