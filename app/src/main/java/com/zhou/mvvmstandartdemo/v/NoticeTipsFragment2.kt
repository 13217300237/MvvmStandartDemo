package com.zhou.mvvmstandartdemo.v

import android.app.Activity
import androidx.lifecycle.Observer
import com.zhou.baselib.BaseFragment
import com.zhou.mvvmstandartdemo.R
import com.zhou.mvvmstandartdemo.vm.LoginActivityViewModel
import com.zhou.mvvmstandartdemo.vm.NoticeFragmentViewModel
import kotlinx.android.synthetic.main.notice_fragment.*

class NoticeTipsFragment2 : BaseFragment<NoticeFragmentViewModel, LoginActivityViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.notice_fragment
    }

    override fun initView() {
        tvMsg.setOnClickListener {
            getActivityViewModel().getMsg() //泛型提取之后
        }
    }

    override fun initObserber() {
        getActivityViewModel().observerGetMsg(this, Observer {
            tvMsg.text = it.toString()
        })
    }


}