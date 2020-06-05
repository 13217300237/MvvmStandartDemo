package com.zhou.baselib.network

interface HttpCallback<T> {
    fun onSuccess(result: T?)
    fun onFailure(e: Exception?)
}