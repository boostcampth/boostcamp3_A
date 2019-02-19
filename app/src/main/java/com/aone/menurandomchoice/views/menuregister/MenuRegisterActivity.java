package com.aone.menurandomchoice.views.menuregister;


import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityMenuRegisterBinding;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.views.base.widget.RappingTextWatcher;
import com.aone.menurandomchoice.views.menupreview.MenuPreviewActivity;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapter;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapterContract;
import com.aone.menurandomchoice.views.menuregister.adapter.viewholder.OnMenuCategoryClickListener;
import com.aone.menurandomchoice.views.storeedit.StoreEditActivity;
import com.yalantis.ucrop.UCrop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MenuRegisterActivity
        extends BaseActivity<ActivityMenuRegisterBinding, MenuRegisterContract.View, MenuRegisterContract.Presenter>
        implements MenuRegisterContract.View {

    public static final String EXTRA_MENU_DETAIL_ITEM = "EXTRA_MENU_DETAIL_ITEM";
    private static final int REQUEST_CODE_OPEN_ALBUM = 1;

    private MenuCategoryAdapterContract.View menuCategoryAdapterView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpPresenterToDataBinding();
        setUpCategoryRecyclerView();
        setUpEditTextChangeListener();
        passedIntentToPresenter();
    }

    @Override
    protected int getLayoutId() { return R.layout.activity_menu_register; }

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
            case R.id.item_menu_owner_delete:
                getPresenter().handlingMenuDeleteButtonClick();
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

    private void setUpEditTextChangeListener() {
        getDataBinding().activityMenuRegisterNameEt.addTextChangedListener(new RappingTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                MenuDetail menuDetail = getDataBinding().getMenuDetail();
                if(menuDetail != null) {
                    menuDetail.setName(editable.toString());
                }
            }
        });

        getDataBinding().activityMenuRegisterDescriptionEt.addTextChangedListener(new RappingTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                MenuDetail menuDetail = getDataBinding().getMenuDetail();
                if(menuDetail != null) {
                    menuDetail.setDescription(editable.toString());
                }
            }
        });

        getDataBinding().activityMenuRegisterPriceEt.addTextChangedListener(new RappingTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                MenuDetail menuDetail = getDataBinding().getMenuDetail();
                if(menuDetail != null) {
                    String price = editable.toString();
                    if(price.length() < 1) {
                        menuDetail.setPrice(0);
                    } else {
                        menuDetail.setPrice(Integer.parseInt(price));
                    }
                }
            }
        });

    }

    private void passedIntentToPresenter() {
        Intent passedIntent = getIntent();
        MenuDetail menuDetail = passedIntent.getParcelableExtra(StoreEditActivity.EXTRA_MENU_DETAIL_INFO);
        getPresenter().handlingPassedMenuDetailInfo(menuDetail);
    }

    @Override
    public void executePickPhotoFromAlbum() {
        openAlbumOfDevice();
    }

    @Override
    public void startToUCropLibrary(@NonNull UCrop uCrop) {
        uCrop.start(this);
    }

    @Override
    public void setMenuDetailToDataBinding(@NonNull MenuDetail menuDetail) {
        getDataBinding().setMenuDetail(menuDetail);
    }

    @Override
    public void setRegisteredImage(@NonNull String imagePath) {
        getDataBinding().getMenuDetail().setPhotoUrl(imagePath);
    }


    @Override
    public void showMenuAddButton() {
        getDataBinding().activityMenuRegisterAddBtn.setVisibility(View.VISIBLE);
        getDataBinding().activityMenuRegisterDeleteBtn.setVisibility(View.GONE);
    }

    @Override
    public void showMenuDeleteButton() {
        getDataBinding().activityMenuRegisterDeleteBtn.setVisibility(View.VISIBLE);
        getDataBinding().activityMenuRegisterAddBtn.setVisibility(View.GONE);
    }

    @Override
    public void moveToPreviewActivityWithItem() {
        Intent menuPreviewIntent = new Intent(this, MenuPreviewActivity.class);
        menuPreviewIntent.putExtra(MenuPreviewActivity.EXTRA_MENU_DETAIL_ITEM, getDataBinding().getMenuDetail());
        startActivity(menuPreviewIntent);
    }

    @Override
    public void moveToPreviousActivityWithItem() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_MENU_DETAIL_ITEM, getDataBinding().getMenuDetail());
        setResult(RESULT_OK, resultIntent);

        finish();
    }

    @NonNull
    @Override
    public MenuDetail getInputtedMenuDetail() {
        MenuDetail menuDetail = getDataBinding().getMenuDetail();
        if(menuDetail == null) {
            menuDetail = new MenuDetail();
        }
        return menuDetail;
    }

    @NonNull
    @Override
    public int[] getRegisterTargetImageSize() {
        return new int[]{getDataBinding().activityMenuRegisterIv.getWidth(),
                getDataBinding().activityMenuRegisterIv.getHeight()};
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
