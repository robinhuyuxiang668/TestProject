package com.hyx.test.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hyx.test.R;
import com.hyx.test.db.DbManage;
import com.hyx.test.entity.TestEntity;
import com.hyx.test.ui.adapter.HistoryDataAdapter;

import java.util.ArrayList;
import java.util.List;


public class HistoryActivity extends BaseActivity {
    private RecyclerView mRv;
    private NestedScrollView mNsv;
    private SwipeRefreshLayout mSrl;

    private HistoryDataAdapter mAdapter;
    private List<TestEntity> mDatas;
    private DbManage mDbManage;
    private int mPage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mRv = findViewById(R.id.rv);
        mNsv =  findViewById(R.id.nsv);
        mSrl =  findViewById(R.id.srl);
        mDatas = new ArrayList<>();
        mDbManage = DbManage.Companion.getInstance(this);
        initRecyclerView();

        //设置下拉刷新的监听
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
                mDatas.clear();
                requestDatas();
            }
        });

        //设置上拉加载更多
        mNsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(0).getHeight() <= scrollY + v.getHeight()) {// 如果满足就是到底部了
                    mPage++;
                    requestDatas();
                }
            }
        });
        requestDatas();
    }

    private void initRecyclerView(){
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new HistoryDataAdapter(mDatas);
        mRv.setAdapter(mAdapter);
    }

    private void requestDatas(){
        List<TestEntity> list = mDbManage.getDatasByPage(mPage);
        mDatas.addAll(list);
        mAdapter.notifyDataSetChanged();

        mSrl.setRefreshing(false);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
