package com.hyx.test.ui

import android.os.Bundle
import androidx.core.widget.NestedScrollView
import androidx.core.widget.NestedScrollView.OnScrollChangeListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hyx.test.R.id
import com.hyx.test.R.layout
import com.hyx.test.db.DbManage
import com.hyx.test.db.DbManage.Companion.getInstance
import com.hyx.test.entity.TestEntity
import com.hyx.test.ui.adapter.HistoryDataAdapter
import kotlin.collections.ArrayList

class HistoryActivity : BaseActivity() {
    private var mRv: RecyclerView? = null
    private var mNsv: NestedScrollView? = null
    private var mSrl: SwipeRefreshLayout? = null
    private var mAdapter: HistoryDataAdapter? = null
    private var mDatas: ArrayList<TestEntity>? = null
    private var mDbManage: DbManage? = null
    private var mPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_history)
        mRv = findViewById<RecyclerView?>(id.rv)
        mNsv = findViewById<NestedScrollView?>(id.nsv)
        mSrl = findViewById<SwipeRefreshLayout?>(id.srl)
        mDatas = ArrayList()
        mDbManage = getInstance(this)
        initRecyclerView()

        //设置下拉刷新的监听
        mSrl!!.setOnRefreshListener {
            mPage = 0
            mDatas = ArrayList()
            requestDatas()
        }

        //设置上拉加载更多
        mNsv!!.setOnScrollChangeListener(OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (v.getChildAt(0).height <= scrollY + v.height) {// 如果满足就是到底部了
                mPage++
                requestDatas()
            }
        })
        requestDatas()
    }


    private fun initRecyclerView() {
        mRv!!.layoutManager = LinearLayoutManager(this)
        mRv!!.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        mAdapter = HistoryDataAdapter(mDatas)
        mRv!!.adapter = mAdapter
    }

    private fun requestDatas() {
        val list: Collection<TestEntity> = mDbManage!!.getDatasByPage(mPage)
        mDatas!!.addAll(list)
        mAdapter!!.notifyDataSetChanged()
        mSrl!!.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}