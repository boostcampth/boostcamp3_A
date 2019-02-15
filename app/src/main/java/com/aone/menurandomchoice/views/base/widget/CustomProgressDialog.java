package com.aone.menurandomchoice.views.base.widget;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;

import androidx.appcompat.app.AppCompatDialog;

public class CustomProgressDialog {

    private AppCompatDialog progressDialog;

    public void show(Activity activity) {
        if(isShowing()) {
            dialogHide();
        }

        createDialog(activity);
        dialogOpen();
    }

    public void hide() {
        if(isShowing()) {
            dialogHide();
        }
    }

    private boolean isShowing() {
        return progressDialog != null && progressDialog.isShowing();
    }

    private void dialogOpen() {
        progressDialog.show();
    }

    private void dialogHide() {
        progressDialog.dismiss();
        progressDialog = null;
    }

    private void createDialog(Activity activity) {
        if(activity != null && !activity.isFinishing()) {
            progressDialog = new AppCompatDialog(activity);
            progressDialog.setContentView(R.layout.custom_progress_dialog);
            Window progressWindow = progressDialog.getWindow();
            if (progressWindow != null) {
                progressWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
    }

}