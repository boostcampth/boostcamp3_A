package com.aone.menurandomchoice.views.menuregister;


import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityMenuRegisterBinding;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.utils.GlideUtil;
import com.aone.menurandomchoice.views.MenuPreviewActivity;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapter;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapterContract;
import com.aone.menurandomchoice.views.menuregister.adapter.viewholder.OnMenuCategoryClickListener;
import com.yalantis.ucrop.UCrop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MenuRegisterActivity
        extends BaseActivity<ActivityMenuRegisterBinding, MenuRegisterContract.View, MenuRegisterContract.Presenter>
        implements MenuRegisterContract.View {

    private static final int REQUEST_CODE_OPEN_ALBUM = 1;
    private static final String EXTRA_MENU_DETAIL_ITEM = "EXTRA_MENU_DETAIL_ITEM";

    private MenuCategoryAdapterContract.View menuCategoryAdapterView;

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
        getPresenter().handlingBackKeyClick();
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
        MenuCategoryAdapter menuCategoryAdapter = new MenuCategoryAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        getDataBinding().activityMenuRegisterRecyclerView.setLayoutManager(layoutManager);
        getDataBinding().activityMenuRegisterRecyclerView.setAdapter(menuCategoryAdapter);

        setUpAdapterToPresenter(menuCategoryAdapter);
        setUpAdapterToThis(menuCategoryAdapter);
    }

    private void setUpAdapterToPresenter(MenuCategoryAdapter menuCategoryAdapter) {
        getPresenter().setAdapterModel(menuCategoryAdapter);
    }

    private void setUpAdapterToThis(MenuCategoryAdapter menuCategoryAdapter) {
        menuCategoryAdapterView = menuCategoryAdapter;
        menuCategoryAdapterView.setOnMenuCategoryClickListener(new OnMenuCategoryClickListener() {
            @Override
            public void onMenuCategoryItemClick(@NonNull View view, int position) {
                getPresenter().handlingMenuCategoryItemClick(position);
            }
        });
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
    public void moveToPreviewActivityWithItem(MenuDetail menuDetail) {
        Intent menuPreviewIntent = new Intent(this, MenuPreviewActivity.class);
        menuPreviewIntent.putExtra(EXTRA_MENU_DETAIL_ITEM, menuDetail);
        startActivity(menuPreviewIntent);
    }

    @Override
    public void moveToPreviousActivityWithItem(MenuDetail menuDetail) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_MENU_DETAIL_ITEM, menuDetail);
        setResult(RESULT_OK, resultIntent);

        finish();
    }

    @NonNull
    @Override
    public int[] getRegisterTargetImageSize() {
        return new int[]{getDataBinding().activityMenuRegisterIv.getWidth(),
                getDataBinding().activityMenuRegisterIv.getHeight()};
    }

    @NonNull
    @Override
    public String getInputtedMenuName() {
        return getDataBinding().activityMenuRegisterNameEt.getText().toString();
    }

    @NonNull
    @Override
    public String getInputtedMenuDescription() {
        return getDataBinding().activityMenuRegisterDescriptionEt.getText().toString();
    }

    @NonNull
    @Override
    public String getInputtedMenuPrice() {
        return getDataBinding().activityMenuRegisterPriceEt.getText().toString();
    }

    @Override
    public void finishView() {
        finish();
    }

    private void openAlbumOfDevice() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_CODE_OPEN_ALBUM);
    }

}
