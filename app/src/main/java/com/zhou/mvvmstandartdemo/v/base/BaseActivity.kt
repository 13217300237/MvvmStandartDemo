package com.zhou.mvvmstandartdemo.v.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

open abstract class BaseActivity<T : ViewModel> : AppCompatActivity() {
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
    abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        init()
    }
}