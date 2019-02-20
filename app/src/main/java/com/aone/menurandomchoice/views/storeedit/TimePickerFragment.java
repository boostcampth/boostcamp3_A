package com.aone.menurandomchoice.views.storeedit;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.aone.menurandomchoice.R;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public static final int OPEN = 0;
    public static final int CLOSE = 1;

    public static final String BUNDLE_PICKER_TIME = "BUNDLE_PICKER_TIME";
    public static final String BUNDLE_PICKER_TYPE = "BUNDLE_PICKER_TYPE";

    private StoreEditContract.Presenter presenter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle timeArgs = getArguments();
        String time = timeArgs.getString(BUNDLE_PICKER_TIME);
        final int type = timeArgs.getInt(BUNDLE_PICKER_TYPE);
        int idx = time.indexOf(":");

        int hour = 0;
        int minute = 0;
        if(idx != -1) {
            hour = Integer.parseInt(time.substring(0, idx));
            minute = Integer.parseInt(time.substring(idx + 1));
        }

        return new TimePickerDialog(getActivity(), R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hourOfDay = view.getHour();
                    minute = view.getMinute();
                } else {
                    hourOfDay = view.getCurrentHour();
                    minute = view.getCurrentMinute();
                }

                String formatHour;
                String formatMinute;
                if (hourOfDay < 10) {
                    formatHour = "0" + hourOfDay;
                } else {
                    formatHour = String.valueOf(hourOfDay);
                }

                if (minute < 10) {
                    formatMinute = "0" + minute;
                } else {
                    formatMinute = String.valueOf(minute);
                }

                presenter.onTimeSet(type, formatHour, formatMinute);
            }
        }, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    public void setPresenter(StoreEditContract.Presenter presenter) {
        this.presenter = presenter;
    }
}