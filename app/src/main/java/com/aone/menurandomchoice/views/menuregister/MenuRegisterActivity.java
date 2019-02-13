package com.aone.menurandomchoice.views.menuregister;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityMenuRegisterBinding;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.utils.GlideUtil;
import com.aone.menurandomchoice.views.MenuPreviewActivity;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapter;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;
import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MenuRegisterActivity
        extends BaseActivity<ActivityMenuRegisterBinding, MenuRegisterContract.View, MenuRegisterContract.Presenter>
        implements MenuRegisterContract.View {

    public static final int REQUEST_CODE_OPEN_ALBUM = 1;
    MenuCategoryAdapter menuCategoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpPresenterToDataBinding();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu_owner_menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.item_menu_owner_register_preview:
                getPresenter().handlingPreviewButtonClick();
                return true;
            case R.id.item_menu_owner_register_ok:
                getPresenter().handlingRegisterOkButtonClick();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_OPEN_ALBUM:
                attachViewToPresenter();
                getPresenter().handlingPickPhotoResult(resultCode, data);
                break;
            case UCrop.REQUEST_CROP:
                attachViewToPresenter();
                getPresenter().handlingUCropResult(resultCode, data);
                break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setUpPresenterToDataBinding() {
        getDataBinding().setPresenter(getPresenter());
    }

    private void setUpCategoryRecyclerView() {
        menuCategoryAdapter = new MenuCategoryAdapter();
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

    @Override
    public void executePickPhotoFromAlbum() {
        openAlbumOfDevice();
    }

    @Override
    public void startToUCropLibrary(UCrop uCrop) {
        uCrop.start(this);
    }

    @Override
    public void showRegisteredImage(String imagePath) {
        GlideUtil.loadImageWithSkipCache(getDataBinding().activityMenuRegisterIv, imagePath);
    }

    @Override
    public void moveToPreviewActivity() {
        MenuDetail menuDetail = createMenuDetailItem();
        startToMenuPreviewActivity(menuDetail);
    }

    @Override
    public void moveToPreviousActivityWithOk() {

    }

    @NonNull
    @Override
    public int[] getRegisterTargetImageSize() {
        return new int[]{getDataBinding().activityMenuRegisterIv.getWidth(),
                getDataBinding().activityMenuRegisterIv.getHeight()};
    }

    private void openAlbumOfDevice() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_CODE_OPEN_ALBUM);
    }

    private MenuDetail createMenuDetailItem() {
        String name = getDataBinding().activityMenuRegisterTitleEt.getText().toString();
        String price = getDataBinding().activityMenuRegisterPriceEt.getText().toString();
        String imagePath = getPresenter().getRegisteredImagePath();
        String description = getDataBinding().activityMenuRegisterGuideEt.getText().toString();
//        String category = getPresenter().getSelectedCategory();
        String category = menuCategoryAdapter.getSelectedCategory();

        MenuDetail menuDetail = new MenuDetail();
        menuDetail.setName(name);
        menuDetail.setPrice(Integer.parseInt(price));
        menuDetail.setPhotoUrl(imagePath);
        menuDetail.setDescription(description);
        menuDetail.setCategory(category);

        return menuDetail;
    }

    private void startToMenuPreviewActivity(MenuDetail menuDetail) {
        Intent menuPreviewIntent = new Intent(this, MenuPreviewActivity.class);
        menuPreviewIntent.putExtra("dd", menuDetail);
        startActivity(menuPreviewIntent);
    }
}
