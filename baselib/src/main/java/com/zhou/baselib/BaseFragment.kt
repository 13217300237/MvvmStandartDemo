package com.zhou.baselib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

open abstract class BaseFragment<T : ViewModel> : Fragment() {
    /**
     * 布局ID
     */
    abstract fun getLayoutId(): Int

    private fun getViewModelClz(): Class<T> {
        return analysisClassInfo(this)
    }

    fun getViewModel(): T {
        return ViewModelProvider(this).get(getViewModelClz())
    }

    /**
     * 获取对象的参数化类型,并且把第一个参数化类型的class对象返回出去
     *
     * @param obj
     * @return
     */
    private fun analysisClassInfo(obj: Any): Class<T> {
        val type = obj.javaClass.genericSuperclass //
        val param = (type as ParameterizedType?)!!.actualTypeArguments //  获取参数化类型
        return param[0] as Class<T>
    }

    /**
     * 界面元素初始化
     */
    abstract fun initView()

    abstract fun initObserber()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }


    /**
     * onViewCreated之后，才能用kotlin的viewId去操作view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initObserber()
    }
}