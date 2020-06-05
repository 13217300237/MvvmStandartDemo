package com.zhou.baselib.bus

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 如果希望创建一个消息通道，只允许通知一次，那就使用SingleLiveEvent
 *
 * @param <T> the data type
 */
class SingleLiveData<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(true)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.d(
                "SingleLiveEvent",
                "Multiple observers registered but only one will be notified of changes."
            )
        }
        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {// 获取bool值，并且在获取之后将它的值设置成false
                observer.onChanged(t)
            }
        })
    }

}