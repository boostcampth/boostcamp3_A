package com.aone.menurandomchoice.views.menuregister;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityMenuRegisterBinding;
import com.aone.menurandomchoice.views.base.BaseActivity;

import androidx.annotation.NonNull;

public class MenuRegisterActivity
        extends BaseActivity<ActivityMenuRegisterBinding, MenuRegisterContract.View, MenuRegisterContract.Presenter>
        implements MenuRegisterContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu_register;
    }

    @NonNull
    @Override
    protected MenuRegisterContract.Presenter setUpPresenter() {
        return new MenuRegisterPresenter();
    }

    @NonNull
    @Override
    protected MenuRegisterContract.View getView() {
        return this;
    }
}
