package com.aone.menurandomchoice.views.storeedit;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.aone.menurandomchoice.R;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private StoreEditContract.Presenter presenter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle timeArgs = getArguments();
        final String time = timeArgs.getString("time");
        final String type = timeArgs.getString("type");

        int idx = time.indexOf(":");
        final int hour = Integer.parseInt(time.substring(0, idx));
        int minute = Integer.parseInt(time.substring(idx+1));

        return new TimePickerDialog(getActivity(), R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String formatHour = "";
                String formatMinute = "";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hourOfDay = view.getHour();
                    minute = view.getMinute();
                } else {
                    hourOfDay = view.getCurrentHour();
                    minute = view.getCurrentMinute();
                }

                if(hourOfDay < 10){
                    formatHour = "0" + hourOfDay;
                } else {
                    formatHour = String.valueOf(hourOfDay);
                }

                if(minute < 10){
                    formatMinute = "0" + minute;
                } else {
                    formatMinute = String.valueOf(minute);
                }

                presenter.onTimeSet(type, formatHour, formatMinute);
            }
        }, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }


    public void onTimeSet(TimePicker view, int hourOfDay, int minute) { }


    public void setPresenter(StoreEditContract.Presenter presenter) {
        this.presenter = presenter;
    }
}