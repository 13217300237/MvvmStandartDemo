package com.zhou.baselib

import androidx.lifecycle.ViewModel
import com.zhou.baselib.bus.LiveDataHolder

abstract class BaseViewModel : ViewModel() {
    // 一个ViewModel可以存在多个LiveData，所以使用LiveDataHolder管理所有的LiveData
    val liveDataHolder = LiveDataHolder.get()
}