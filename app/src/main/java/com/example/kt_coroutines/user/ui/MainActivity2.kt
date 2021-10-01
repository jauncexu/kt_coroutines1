package com.example.kt_coroutines.user.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kt_coroutines.R
import com.example.kt_coroutines.user.api.APIClient
import com.example.kt_coroutines.user.api.WanAndroidAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// 使用协程的思维  同步的代码写出异步的效果
class MainActivity2 : AppCompatActivity() {
    private val TAG = "MainActivity"
    var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // 按钮点击事件
    fun startRequest(view: View) {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog?.setTitle("请求服务器中..")
        mProgressDialog?.show()


        // Android中使用  默认是IO线程 ,所以需要修改成Main线程
        // 协程
        GlobalScope.launch(Dispatchers.Main) {
            // 更新UI
            val loginResult = // 在主线程
                APIClient.instance.instanceRetrofit(WanAndroidAPI::class.java)
                    .loginActionCoroutine("Derry-vip", "123456") // 1. 挂起出去执行异步操作 2. 操作完成后恢复主线程
            Log.d(TAG, "errorMsg: ${loginResult.data}")
            textView.text = loginResult.data.toString() // 更新控件 UI

            mProgressDialog?.dismiss() // 隐藏加载框
        }
    }
}