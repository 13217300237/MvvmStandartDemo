package com.zhou.baselib.bus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * 只有当前存活的lifeCycleOwner才会收到 消息, 重写它的observer的mLastVersion
 */
class AliveOwnerMutableLiveData<T> : MutableLiveData<T>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        hook(observer)
    }

    private fun hook(observer: Observer<in T>) {
        val classLiveData: Class<LiveData<*>> = LiveData::class.java
        val fieldObservers = classLiveData.getDeclaredField("mObservers")
        fieldObservers.isAccessible = true
        val mObservers = fieldObservers[this]
        val classObservers: Class<*> = mObservers.javaClass

        val methodGet = classObservers.getDeclaredMethod("get", Any::class.java)
        methodGet.isAccessible = true
        val objectWrapperEntry = methodGet.invoke(mObservers, observer)
        val objectWrapper =
            (objectWrapperEntry as Map.Entry<*, *>).value!!
        val classObserverWrapper: Class<*>? = objectWrapper.javaClass.superclass

        val fieldLastVersion =
            classObserverWrapper!!.getDeclaredField("mLastVersion")
        fieldLastVersion.isAccessible = true
        val fieldVersion = classLiveData.getDeclaredField("mVersion")
        fieldVersion.isAccessible = true
        val objectVersion = fieldVersion[this]
        fieldLastVersion[objectWrapper] = objectVersion
    }

}