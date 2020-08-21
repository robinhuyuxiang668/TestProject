package com.hyx.test.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lzy.okgo.OkGo

abstract class BaseActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        OkGo.getInstance().cancelAll()
    }
}