package com.zhou.baselib.bus

import androidx.lifecycle.MutableLiveData

class LiveDataBus {

    /**
     * 只有当前存活的owner才能收到消息, 即，尚未创建成功的owner将不会收到消息 ，
     * 即使它进行了监听
     */
    private val mapOfAliveOwner: HashMap<String, AliveOwnerMutableLiveData<Any?>> = HashMap()

    /**
     * 全部owner都能收到消息，就算将要跳转的Activity并没创建成功的情况
     */
    private val mapOfAllOwner: HashMap<String, MutableLiveData<Any?>> = HashMap()

    /**
     *
     */
    private val mapOfSingleEventOwner: HashMap<String, SingleLiveData<Any?>> = HashMap()

    // 内部类的单例写法，静态成员天生就是线程安全的
    class SingleHolder {
        companion object {
            val DATA_BUS = LiveDataBus()
        }
    }

    companion object {
        // 提供给外界一个get方法来获取单例对象
        fun get(): LiveDataBus {
            return SingleHolder.DATA_BUS
        }
    }

    /**
     * 获取消息通道, 仅支持当前存活的 lifeCycleOwner
     *
     * @key 消息通道的key
     */
    fun getAliveOwnerChannel(key: String): MutableLiveData<Any?> {
        if (!mapOfAliveOwner.containsKey(key)) {
            mapOfAliveOwner[key] = AliveOwnerMutableLiveData()
        }
        return mapOfAliveOwner[key]!!
    }

    /**
     * 获取默认消息通道,  支持所有lifeCycleOwner，包括目前还没创建成功的
     */
    fun getDefaultChannel(key: String): MutableLiveData<Any?> {
        if (!mapOfAllOwner.containsKey(key)) {
            mapOfAllOwner[key] = MutableLiveData()
        }
        return mapOfAllOwner[key]!!
    }

    fun getSingleEventChannel(key: String): SingleLiveData<Any?> {
        if (!mapOfSingleEventOwner.containsKey(key)) {
            mapOfSingleEventOwner[key] = SingleLiveData()
        }
        return mapOfSingleEventOwner[key]!!
    }
}



