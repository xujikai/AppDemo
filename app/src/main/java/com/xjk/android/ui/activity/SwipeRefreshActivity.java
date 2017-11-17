package com.xjk.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.xjk.android.R;
import com.xjk.android.base.BaseActivity;
import com.xjk.android.utils.UIUtils;

import butterknife.BindView;

/**
 * Created by xxx on 2017/8/18.
 */

public class SwipeRefreshActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static void start(Context context){
        Intent intent = new Intent(context, SwipeRefreshActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_swipe_refresh;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(UIUtils.getColor(R.color.colorAccent));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {

    }
}
