package com.hyx.test;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.hyx.test.ui.HistoryActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class HistoryActivityTest {

    @Rule
    public ActivityTestRule<HistoryActivity> mActivityRule = new ActivityTestRule<>(HistoryActivity.class);


    /**
     * 下拉刷新获取最新数据
     */
    @Test
    public void testRefreshNewData(){
       onView(withId(R.id.rv)).perform(swipeDown());
    }


    /**
     * 上拉获取下一页数据
     */
    @Test
    public void testGetMoreData(){
        onView(withId(R.id.rv)).perform(swipeUp());
    }


}
