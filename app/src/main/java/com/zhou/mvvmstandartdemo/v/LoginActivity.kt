package com.zhou.mvvmstandartdemo.v

import android.animation.ValueAnimator
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.zhou.mvvmstandartdemo.R
import com.zhou.baselib.BaseActivity
import com.zhou.baselib.BaseView
import com.zhou.mvvmstandartdemo.vm.LoginActivityViewModel
import kotlinx.android.synthetic.main.activity_login.*

interface LoginView : BaseView {
    fun getUserName(): String
    fun getPassword(): String
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
class LoginActivity : BaseActivity<LoginActivityViewModel>(), LoginView {

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        // 获取ViewModel
        // 点击事件触发，出发VM的业务逻辑
        btnLogin.setOnClickListener {
            showLoading() // 开始请求数据显示加载中
            getViewModel().doLogin(getUserName(), getPassword())
        }
        tvMsg.setOnClickListener {
            getViewModel().getMsg()
        }

        addFragment()
        addFragment2()

        val ani = ValueAnimator.ofFloat(0f,1f)
    }

    private lateinit var fragment: Fragment
    private fun addFragment() {
        fragment = NoticeTipsFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContent, fragment, NoticeTipsFragment::class.java.simpleName)
            .show(fragment)
            .commitAllowingStateLoss()
    }

    private fun addFragment2() {
        fragment = NoticeTipsFragment2()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContent2, fragment, NoticeTipsFragment2::class.java.simpleName)
            .show(fragment)
            .commitAllowingStateLoss()
    }

    override fun initObserver() {
        // 定义数据回调逻辑
        getViewModel().observerDoLogin(this, Observer {
            hideLoading() // 得到数据返回，隐藏加载中
            showResult(it.toString())// 处理数据
        })
        getViewModel().observerGetMsg(this, Observer {
            tvMsg.text = it.toString()
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
