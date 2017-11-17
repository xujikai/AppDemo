package com.xjk.android.ui.coor_behavior;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xjk.android.R;
import com.xjk.android.base.BaseActivity;
import com.xjk.android.utils.DataUtils;
import com.xjk.android.utils.L;

import butterknife.BindView;

/**
 * Created by xxx on 2017/9/21.
 */

public class CoorDemo03Activity extends BaseActivity {

    @BindView(R.id.stickLayout)
    StickLayout mStickLayout;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, CoorDemo03Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_coor_03;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initListener() {
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                L.e(TAG, "scrollY: " + dy);
//            }
//        });
    }

    @Override
    protected void initData() {
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_text, DataUtils.getData(50)) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.item_text, "xxx");
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(position %2 == 0) {
                    mStickLayout.scrollBy(0, 10);
                    L.e(TAG, "scrollY: 10  getScrollY(): " + mStickLayout.getScrollY());
                }else {
                    L.e(TAG, "scrollY: -10 getScrollY(): " + mStickLayout.getScrollY());
                    mStickLayout.scrollBy(0, 10 * -1);
                }
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

}
