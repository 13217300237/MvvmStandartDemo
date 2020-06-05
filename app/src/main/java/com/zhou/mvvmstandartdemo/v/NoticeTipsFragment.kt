package com.zhou.mvvmstandartdemo.v

import androidx.lifecycle.Observer
import com.zhou.baselib.BaseFragment
import com.zhou.mvvmstandartdemo.R
import com.zhou.mvvmstandartdemo.vm.NoticeFragmentViewModel
import kotlinx.android.synthetic.main.notice_fragment.*

class NoticeTipsFragment : BaseFragment<NoticeFragmentViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.notice_fragment
    }

    override fun initView() {
        tvMsg.setOnClickListener {
            getViewModel().getMsg()
        }
    }

    override fun initObserber() {
        getViewModel().noticeLiveData.observe(this, Observer {
            tvMsg.text = it
        })
    }


}