package com.hyx.test

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hyx.test.db.DbManage.Companion.getInstance
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppTest {
    @Test
    fun testGetDatasByPage() {1
        val ctx: Context? = InstrumentationRegistry.getInstrumentation().context
        val list = getInstance(ctx!!)!!.getDatasByPage(0)
        if (list != null) {
            for (entity in list) {
                println(entity)
            }
        }
    }

    @Test
    fun testGetLastData() {
        val ctx: Context? = InstrumentationRegistry.getInstrumentation().context
        val entity = getInstance(ctx!!)!!.lastData
        println(entity)
    }
}