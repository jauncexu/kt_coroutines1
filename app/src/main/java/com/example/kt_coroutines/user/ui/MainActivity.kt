package com.example.kt_coroutines.user.ui

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import com.example.kt_coroutines.R
import com.example.kt_coroutines.user.api.APIClient
import com.example.kt_coroutines.user.api.WanAndroidAPI
import com.example.kt_coroutines.user.entity.LoginRegisterResponse
import com.example.kt_coroutines.user.entity.LoginRegisterResponseWrapper
import kotlinx.android.synthetic.main.activity_main.*

// 常规没有协程的写法
// 串行思维
class MainActivity : AppCompatActivity() {
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

        // 第一个步骤  开启异步线程 请求服务器
        object : Thread() {
            override fun run() {
                super.run()
                Thread.sleep(2000)
                val loginResult = APIClient.instance.instanceRetrofit(WanAndroidAPI::class.java)
                    .loginAction("Derry-vip", "123456")

                val result: LoginRegisterResponseWrapper<LoginRegisterResponse>? =
                    loginResult.execute().body()

                // 切换android 主线程，更新UI  把最终登录成功的JavaBean（KT数据类） 发送给  Handler
                val msg = mHandler.obtainMessage()
                msg.obj = result
                mHandler.sendMessage(msg)
            }
        }.start()
    }

    //  第二大步骤：主线程 更新UI
    val mHandler = Handler(Looper.getMainLooper()) {
        // as xx 转换成xx类型
        val result = it.obj as LoginRegisterResponseWrapper<LoginRegisterResponse>
        Log.d(TAG, "errorMsg: ${result.data}")
        textView.text = result.data.toString() // 更新控件 UI

        mProgressDialog?.dismiss() // 隐藏加载框

        false
    }
}