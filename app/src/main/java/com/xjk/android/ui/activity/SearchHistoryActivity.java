package com.xjk.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xjk.android.R;
import com.xjk.android.base.BaseActivity;
import com.xjk.android.constant.Constants;
import com.xjk.android.data.sp.SpHelper;
import com.xjk.android.utils.L;
import com.xjk.android.utils.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xxx on 2017/10/11.
 */

public class SearchHistoryActivity extends BaseActivity{

    @BindView(R.id.et_input)
    EditText etInput;

    private List<String> mHistoryList;

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchHistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search_history;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        String history = SpHelper.getString(mContext, "search", Constants.DEFAULT_NULL);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        mHistoryList = gson.fromJson(history, listType);
        if(mHistoryList == null) mHistoryList = new ArrayList<>();

        L.e(mHistoryList.toString());
    }

    @OnClick(R.id.btn_add)
    public void clickAdd() {
        String content = etInput.getText().toString();
        if(StringUtils.isEmpty(content)) return;

        int index = mHistoryList.indexOf(content);
        if(index > -1) mHistoryList.remove(content);
        mHistoryList.add(0, content);
        SpHelper.putString(mContext, "search", new Gson().toJson(mHistoryList));
        L.e(mHistoryList.toString());
    }

}
