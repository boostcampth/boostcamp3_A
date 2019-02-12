package com.aone.menurandomchoice.views.menuregister;

import android.os.Bundle;
import android.view.MenuItem;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityMenuRegisterBinding;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapter;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MenuRegisterActivity
        extends BaseActivity<ActivityMenuRegisterBinding, MenuRegisterContract.View, MenuRegisterContract.Presenter>
        implements MenuRegisterContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpCategoryRecyclerView();
    }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.item_action_bar_next:
                moveToMenuConfirmActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    private void setUpCategoryRecyclerView() {

        MenuCategoryAdapter menuCategoryAdapter = new MenuCategoryAdapter();
        menuCategoryAdapter.setItems(createMenuCategoryItems());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        getDataBinding().activityMenuRegisterRecyclerView.setLayoutManager(layoutManager);
        getDataBinding().activityMenuRegisterRecyclerView.setAdapter(menuCategoryAdapter);
    }

    private List<MenuCategoryItem> createMenuCategoryItems() {
        String[] categories = getResources().getStringArray(R.array.category);
        ArrayList<MenuCategoryItem> menuCategoryItems = new ArrayList<>();
        for(String category : categories) {
            menuCategoryItems.add(new MenuCategoryItem(category));
        }

        return menuCategoryItems;
    }

    private void moveToMenuConfirmActivity() {

    }

}
