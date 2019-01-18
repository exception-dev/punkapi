package com.ex.punkapi.intro.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.ex.punkapi.R
import com.ex.punkapi.base.ui.BaseActivity
import com.ex.punkapi.main.ui.MainActivity

private const val TIMER:Long = 3000
private const val MSG_CHECK:Int = 1;


class IntroActivity : BaseActivity() {

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what){
                MSG_CHECK -> {
                    startApp()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        checkTime()
    }

    override fun onDestroy() {
        handler.removeMessages(MSG_CHECK)
        super.onDestroy()
    }

    private fun checkTime(){
        handler.sendEmptyMessageDelayed(MSG_CHECK, TIMER)
    }



    private fun startApp(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
