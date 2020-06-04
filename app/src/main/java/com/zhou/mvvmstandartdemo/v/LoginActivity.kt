package com.zhou.mvvmstandartdemo.v

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zhou.base.m.BaseView
import com.zhou.mvvmstandartdemo.R
import com.zhou.mvvmstandartdemo.vm.LoginActivityViewModel
import kotlinx.android.synthetic.main.activity_login.*

interface LoginView : BaseView {
    fun getUserName(): String
    fun getPassword(): String

    fun showLoading()
    fun hideLoading()

    fun showResult(res: String)
}

/**
 * MVVM中View层依然是纯粹的视图层，不要涉及到任何业务逻辑，它只负责调用VM层的业务，
 * 与MVP相比，P必须持有V的引用，然后驱动UI的更新,最后还要写释放引用的代码。
 *
 * 但是MVVM中，可以直接用 观察者回调来实现
 *
 *
 */
class LoginActivity : AppCompatActivity(), LoginView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 获取ViewModel
        val userModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)

        // 点击事件触发，出发VM的业务逻辑
        btnLogin.setOnClickListener {
            showLoading() // 开始请求数据显示加载中
            userModel.doLogin(getUserName(), getPassword())
        }

        // 定义数据回调逻辑
        userModel.userLiveData.observe(this, Observer {
            hideLoading() // 得到数据返回，隐藏加载中
            showResult(it.toString())// 处理数据
        })
    }

    override fun getUserName(): String {
        return tvUsername.text.toString()
    }

    override fun getPassword(): String {
        return tvPassword.text.toString()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun showResult(res: String) {
        dataView.text = res
    }
}
