package com.example.kt_coroutines.user.ui

import android.app.ProgressDialog
import android.graphics.Color
import android.os.*
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kt_coroutines.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

// TODO 使用协程方式 解决完成三层回调带来的痛点

/**
 * 请求加载[用户数据]
 * suspend 就是一个提醒作用，提醒用户  当前的函数  是挂起函数，可能会执行异常操作
 */
private suspend fun requestLoadUser(): String {
    val isLoadSuccess = true // 加载成功的标记
    // 此协程能保持在异步线程而已
    withContext(Dispatchers.IO) {
        // 睡眠3秒
        delay(3000)
    }
    return if (isLoadSuccess) {
        "加载到[用户数据]信息集"
    } else {
        "加载到[用户数据]，"
    }
}

/**
 * 请求加载[用户资产数据]
 * suspend 就是一个提醒作用，提醒用户  当前的函数  是挂起函数，可能会执行异常操作
 */
private suspend fun requestLoadUserAssets(): String {
    val isLoadSuccess = true // 加载成功的标记
    // 此协程能保持在异步线程而已
    withContext(Dispatchers.IO) {
        // 睡眠3秒
        delay(3000)
    }
    return if (isLoadSuccess) {
        "加载到[用户资产数据]信息集"
    } else {
        "加载到[用户资产数据]，"
    }
}

/**
 * 请求加载[用户资产详情数据]
 * suspend 就是一个提醒作用，提醒用户  当前的函数  是挂起函数，可能会执行异常操作
 */
private suspend fun requestLoadUserAssetsDetails(): String {
    val isLoadSuccess = true // 加载成功的标记
    // 此协程能保持在异步线程而已
    withContext(Dispatchers.IO) {
        // 睡眠3秒
        delay(3000)
    }
    return if (isLoadSuccess) {
        "加载到[用户资产详情数据]信息集"
    } else {
        "加载到[用户资产详情数据]，"
    }
}

class MainActivity4 : AppCompatActivity() {
    var mProgressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // 点击事件
    fun startRequest(view: View) {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog?.setTitle("请求服务器中...")
        mProgressDialog?.show()

        GlobalScope.launch(Dispatchers.Main) {
            // 先异步请求1  更新UI
            val requestLoadUser = requestLoadUser()
            textView.text = requestLoadUser
            textView.setTextColor(Color.GREEN)

            // 先异步请求1  更新UI
            val requestLoadUser2 = requestLoadUserAssets()
            textView.text = requestLoadUser2
            textView.setTextColor(Color.BLUE)

            // 先异步请求1  更新UI
            val requestLoadUser3 = requestLoadUserAssetsDetails()
            textView.text = requestLoadUser3
            mProgressDialog?.dismiss()
            textView.setTextColor(Color.RED)
        }
    }
}