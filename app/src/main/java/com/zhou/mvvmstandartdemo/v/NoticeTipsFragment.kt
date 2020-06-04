package com.zhou.mvvmstandartdemo.v

import androidx.lifecycle.Observer
import com.zhou.mvvmstandartdemo.R
import com.zhou.mvvmstandartdemo.v.base.BaseFragment
import com.zhou.mvvmstandartdemo.vm.NoticeFragmentViewModel
import kotlinx.android.synthetic.main.notice_fragment.*

class NoticeTipsFragment : BaseFragment<NoticeFragmentViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.notice_fragment
    }

    override fun init() {
        getViewModel().noticeLiveData.observe(this, Observer {
            tvMsg.text = it
        })

        tvMsg.setOnClickListener {
            getViewModel().getMsg()
        }
    }


}