package com.aone.menurandomchoice.views.menuregister;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.utils.ClickUtil;
import com.aone.menurandomchoice.utils.DateUtil;
import com.aone.menurandomchoice.utils.StringUtil;
import com.aone.menurandomchoice.views.base.BasePresenter;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapterContract;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;
import com.aone.menurandomchoice.views.menuregister.helper.UCropCreateHelper;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.yalantis.ucrop.UCrop;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import static android.app.Activity.RESULT_OK;

public class MenuRegisterPresenter extends BasePresenter<MenuRegisterContract.View>
        implements MenuRegisterContract.Presenter {

    private MenuCategoryAdapterContract.Model<MenuCategoryItem> menuCategoryAdapterModel;
    private UCropCreateHelper uCropCreateHelper;

    private enum RegisterState {

        SUCCESS(R.string.register_success),
        NO_IMAGE(R.string.register_no_image),
        NO_NAME(R.string.register_no_name),
        NO_DESCRIPTION(R.string.register_no_description),
        NO_PRICE(R.string.register_no_price),
        NO_CATEGORY(R.string.register_no_category);

        private int messageResourceId;

        RegisterState(int messageResourceId) {
            this.messageResourceId = messageResourceId;
        }

        public int getMessageResourceId() {
            return messageResourceId;
        }

    }

    MenuRegisterPresenter() {
        setUp();
    }

    private void setUp() {
        uCropCreateHelper = new UCropCreateHelper();
    }

    @Override
    public void setAdapterModel(@NonNull MenuCategoryAdapterContract.Model<MenuCategoryItem> menuCategoryAdapterModel) {
        this.menuCategoryAdapterModel = menuCategoryAdapterModel;
        this.menuCategoryAdapterModel.setItems(createMenuCategoryItems());
        this.menuCategoryAdapterModel.selectDefaultCategory();
    }

    @Override
    public void handlingPassedMenuDetailInfo(@Nullable MenuDetail menuDetail) {
        if(menuDetail != null) {
            handlingMenuDetailInfo(menuDetail);
            handlingImageRegisterButton(menuDetail.getPhotoUrl());
        } else {
            sendMessageToView(R.string.activity_menu_register_menu_passed_fail);
        }
    }

    @Override
    public void handlingMenuCategoryItemClick(int position) {
        menuCategoryAdapterModel.setSelectedPositionOfMenuCategories(position);
    }

    @Override
    public void handlingImageRegisterButtonClick() {
        checkPermissionWithTedPermission();
    }

    @Override
    public void handlingImageDeleteButtonClick() {
        if(isAttachView()) {
            getView().setRegisteredImage("");
            handlingImageRegisterButton("");
        }
    }

    @Override
    public void handlingPickPhotoResult(int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            handlingPickPhotoSuccess(data);
        } else {
            sendMessageToView(R.string.activity_menu_register_photo_fail);
        }
    }

    @Override
    public void handlingUCropResult(int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            handlingUCropSuccess(data);
        } else {
            sendMessageToView(R.string.activity_menu_register_photo_fail);
        }
    }

    @Override
    public void handlingPreviewButtonClick() {
        if(isAttachView()) {
            MenuDetail menuDetail = getView().getInputtedMenuDetail();
            RegisterState registerState = checkMenuDetailItem(menuDetail);
            if(registerState == RegisterState.SUCCESS) {
                getView().moveToPreviewActivityWithItem();
            } else {
                sendMessageToView(registerState.getMessageResourceId());
            }
        }
    }

    @Override
    public void handlingMenuDeleteButtonClick() {
        showMenuDeleteCheckDialog();
    }

    @Override
    public void handlingRegisterOkButtonClick() {
        if(isAttachView()) {
            MenuDetail menuDetail = getView().getInputtedMenuDetail();
            RegisterState registerState = checkMenuDetailItem(menuDetail);
            if (registerState == RegisterState.SUCCESS) {
                getView().moveToPreviousActivityWithItem();
            } else {
                sendMessageToView(registerState.getMessageResourceId());
            }
        }
    }

    @Override
    public void handlingBackKeyClick() {
        viewFinish();
    }

    @Override
    public void handlingInputtedMenuName(@NonNull String menuName) {
        if(isAttachView()) {
            getView().showChangedMenuName(menuName);
        }
    }

    @Override
    public void handlingInputtedDescription(@NonNull String description) {
        if(isAttachView()) {
            getView().showChangedDescription(description);
        }
    }

    @Override
    public void handlingInputtedPrice(@NonNull String priceStr) {
        if(isAttachView()) {
            if(priceStr.length() < 1) {
                getView().showChangedPrice(0);
            } else {
                getView().showChangedPrice(Integer.parseInt(priceStr));
            }

        }
    }

    private void checkPermissionWithTedPermission() {
        if(isAttachView()) {
            TedPermission.with(getView().getAppContext())
                    .setRationaleMessage(StringUtil.getString(R.string.permission_request_guide))
                    .setDeniedMessage(StringUtil.getString(R.string.permission_denied_guide))
                    .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .setPermissionListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            openAlbumToView();
                        }

                        @Override
                        public void onPermissionDenied(List<String> deniedPermissions) {
                        }
                    })
                    .check();
        }
    }

    private List<MenuCategoryItem> createMenuCategoryItems() {
        ArrayList<MenuCategoryItem> menuCategoryItems = new ArrayList<>();
        String[] categories = getView().getAppContext().getResources().getStringArray(R.array.category);

        String removeWord = StringUtil.getString(R.string.arrays_category_all_food);

        for(String category : categories) {
            if(!category.equals(removeWord)) {
                menuCategoryItems.add(new MenuCategoryItem(category));
            }
        }

        return menuCategoryItems;
    }

    private void openAlbumToView() {
        if(isAttachView()) {
            getView().executePickPhotoFromAlbum();
        }
    }

    private void handlingPickPhotoSuccess(Intent data) {
        if(hasData(data)) {
            UCrop uCrop = createUCop(data.getData());
            sendUCropLibraryToView(uCrop);
        } else {
            sendMessageToView(R.string.activity_menu_register_photo_fail);
        }
    }

    private void handlingUCropSuccess(Intent data) {
        if (hasUCropData(data)) {
            Uri uri = UCrop.getOutput(data);
            if(uri != null) {
                String imagePath = uri.getPath();
                if (imagePath != null) {
                    sendRegisteredImageUriToView(imagePath);
                } else {
                    sendMessageToView(R.string.activity_menu_register_photo_fail);
                }
            } else {
                sendMessageToView(R.string.activity_menu_register_photo_fail);
            }
        } else {
            sendMessageToView(R.string.activity_menu_register_photo_fail);
        }
    }

    private boolean hasData(Intent data) {
        return data != null && data.getData() != null;
    }

    private boolean hasUCropData(Intent data) {
        return data != null && UCrop.getOutput(data) != null;
    }

    private void sendUCropLibraryToView(UCrop uCrop) {
        if(isAttachView()) {
            getView().startToUCropLibrary(uCrop);
        }
    }

    private UCrop createUCop(Uri uri) {
        final float X_RATIO = 3;
        final float Y_RATIO = 4;
        int[] viewSize = getView().getRegisterTargetImageSize();

        return uCropCreateHelper.createUCop(createSaveFileName(), uri, X_RATIO, Y_RATIO, viewSize[0], viewSize[1]);
    }

    private String createSaveFileName() {
        MenuDetail menuDetail = getView().getInputtedMenuDetail();
        return menuDetail.getStoreIdx() + "_" + menuDetail.getSequence() + "_" +
                DateUtil.getNowDate() + ".jpg";
    }

    private void sendRegisteredImageUriToView(String imagePath) {
        if(isAttachView()) {
            getView().setRegisteredImage(imagePath);
        }
    }

    private RegisterState checkMenuDetailItem(MenuDetail menuDetail) {
        if(TextUtils.isEmpty(menuDetail.getPhotoUrl())) {
            return RegisterState.NO_IMAGE;
        }

        if(TextUtils.isEmpty(menuDetail.getName())) {
            return RegisterState.NO_NAME;
        }

        if(TextUtils.isEmpty(menuDetail.getDescription())) {
            return RegisterState.NO_DESCRIPTION;
        }

        if(menuDetail.getPrice() == 0) {
            return RegisterState.NO_PRICE;
        }

        menuDetail.setCategory(menuCategoryAdapterModel.getSelectedCategory());
        if(TextUtils.isEmpty(menuDetail.getCategory())) {
            return RegisterState.NO_CATEGORY;
        }

        return RegisterState.SUCCESS;
    }

    private void handlingMenuDetailInfo(MenuDetail menuDetail) {
        if(isAttachView()) {
            getView().setMenuDetailToDataBinding(menuDetail);
            if(menuCategoryAdapterModel != null) {
                menuCategoryAdapterModel.setCategory(menuDetail.getCategory());
            }
        }
    }

    private void handlingImageRegisterButton(String photoUrl) {
        if(isAttachView()) {
            if(TextUtils.isEmpty(photoUrl)) {
                getView().showMenuAddButton();
            } else {
                getView().showMenuDeleteButton();
            }
        }
    }

    private void showMenuDeleteCheckDialog(){
        if(isAttachView()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getView().getActivityContext());

            String guideMessage = StringUtil.getString(R.string.activity_menu_register_menu_delete_guide);

            builder.setMessage(guideMessage);

            String yesMessage = StringUtil.getString(R.string.activity_menu_register_menu_delete_yes);

            builder.setPositiveButton(yesMessage,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            initializeRegisteredMenu();
                            sendMessageToView(R.string.activity_menu_register_menu_delete_success);
                            getView().moveToPreviousActivityWithItem();

                        }
                    });

            String noMessage = StringUtil.getString(R.string.activity_menu_register_menu_delete_no);
            builder.setNegativeButton(noMessage,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendMessageToView(R.string.activity_menu_register_menu_delete_success);
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private void initializeRegisteredMenu() {
        if(isAttachView()) {
            MenuDetail menuDetail = getView().getInputtedMenuDetail();
            menuDetail.setName("");
            menuDetail.setDescription("");
            menuDetail.setPrice(0);
            menuDetail.setPhotoUrl("");
            menuDetail.setCategory("");

            getView().setMenuDetailToDataBinding(menuDetail);
        }
    }

    private void viewFinish() {
        if(isAttachView()) {
            getView().finishView();
        }
    }
}
