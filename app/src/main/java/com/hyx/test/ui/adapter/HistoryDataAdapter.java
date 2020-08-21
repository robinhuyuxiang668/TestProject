package com.hyx.test.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hyx.test.R;
import com.hyx.test.entity.TestEntity;
import com.hyx.test.util.TimeUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;


public class HistoryDataAdapter extends BaseQuickAdapter<TestEntity, BaseViewHolder> {

    public HistoryDataAdapter(@Nullable List<TestEntity> datas) {
        super(R.layout.item_history_data, datas);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, TestEntity entity) {
        String time = TimeUtils.INSTANCE.getTimeStr(entity.getTime(), TimeUtils.TIME_FORMAT_YYYYMMDDHHMMSS);
        helper.setText(R.id.tv_time, time);
        helper.setText(R.id.tv_content, entity.getContent());
    }
}