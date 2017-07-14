package com.xjk.android.presenter.contract;

import com.xjk.android.base.BasePresenter;
import com.xjk.android.base.BaseView;

/**
 * 使用分页需实现的接口
 * Created by xxx on 2017/2/24.
 */

public interface IPagePresenter<T extends BaseView> extends BasePresenter<T> {

    boolean isInitialPage();

    void addPage();

    void resetPage();

}
