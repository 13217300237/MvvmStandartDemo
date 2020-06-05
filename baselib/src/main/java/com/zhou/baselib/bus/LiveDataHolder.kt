package com.zhou.baselib.bus

import androidx.lifecycle.MutableLiveData

enum class EventType {
    SINGLE,
    ALIVE,
    DEFAULT
}

/**
 * 这个函数是为了简化一个ViewModel中存在N个LiveData定义的情况，其实只需要一个类，去获取就行了
 *
 * 每个ViewModel应该有自己的LiveDataHolder，应该是每一个ViewModel都有一个LiveDataHolder, 用来获取LiveData
 */
class LiveDataHolder {

    companion object {
        fun get(): LiveDataHolder {
            return LiveDataHolder()
        }
    }

    private val map: HashMap<Class<out Any?>, MutableList<MutableLiveData<out Any?>>> = HashMap()


    fun <T> getLiveData(key: Class<T>): MutableLiveData<T> {
        return getLiveData(key, EventType.DEFAULT)
    }

    /**
     *
     * 获得一个指定的LiveData
     *
     * @param key 指定返回值类型
     * @param eventType 消息通道的类别
     * @see  EventType
     */
    fun <T> getLiveData(key: Class<T>, eventType: EventType): MutableLiveData<T> {
        val liveDataClz: Class<MutableLiveData<T>> = when (eventType) {
            EventType.SINGLE -> {
                SingleLiveData<T>().javaClass
            }
            EventType.ALIVE -> {
                AliveOwnerMutableLiveData<T>().javaClass
            }
            else -> {
                MutableLiveData<T>().javaClass
            }
        }

        if (map[key] == null) {
            map[key] = ArrayList()
        }
        val currentList = map[key]!!
        for (a in currentList) {
            if (liveDataClz.isInstance(a)) {
                return a as MutableLiveData<T>
            }
        }
        val newLiveData = liveDataClz.getConstructor().newInstance()
        currentList.add(newLiveData)
        return newLiveData
    }


}
