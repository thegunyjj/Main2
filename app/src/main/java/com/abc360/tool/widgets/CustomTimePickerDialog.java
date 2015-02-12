package com.abc360.tool.widgets;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class CustomTimePickerDialog extends TimePickerDialog {

    private final static int TIME_PICKER_INTERVAL = 20;
    private TimePicker timePicker;
    private final OnTimeSetListener callback;
    int hour;
    public CustomTimePickerDialog(Context context, OnTimeSetListener callBack,
                                  int hourOfDay, int minute, boolean is24HourView) {
        super(context, callBack, hourOfDay, minute / TIME_PICKER_INTERVAL,
                is24HourView);
        this.hour = hourOfDay;
        this.callback = callBack;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (callback != null && timePicker != null) {
            timePicker.clearFocus();
            callback.onTimeSet(timePicker, timePicker.getCurrentHour(),
                    timePicker.getCurrentMinute() * TIME_PICKER_INTERVAL);
        }
    }

    @Override
    protected void onStop() {
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();


        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
        if (version <= 11) {return;}

        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            Field timePickerField = classForid.getField("timePicker");

            this.timePicker = (TimePicker) findViewById(timePickerField.getInt(null));

            Field numberPickerField = classForid.getField("hour");
            NumberPicker numberPickerHour = null;
            List<NumberPicker> numberPickerList = findNumberPicker(timePicker);
            for (NumberPicker mNumberPicker: numberPickerList){
               if (mNumberPicker.getId() == numberPickerField.getInt(null)){
                   numberPickerHour = mNumberPicker;
                   numberPickerHour.setMinValue(23);
                   numberPickerHour.setMinValue(6);
                   numberPickerHour.setValue(this.hour);
               }
            }

            Field field = classForid.getField("minute");

            NumberPicker mMinuteSpinner = (NumberPicker) timePicker
                    .findViewById(field.getInt(null));
            mMinuteSpinner.setMinValue(0);
            mMinuteSpinner.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
            List<String> displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            mMinuteSpinner.setDisplayedValues(displayedValues
                    .toArray(new String[0]));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 得到viewGroup里面的numberpicker组件
     * @param viewGroup
     * @return
     */
    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup){
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;
        if(null != viewGroup){
            for(int i = 0;i<viewGroup.getChildCount();i++){
                child = viewGroup.getChildAt(i);
                if(child instanceof NumberPicker){
                    npList.add((NumberPicker)child);
                }
                else if(child instanceof LinearLayout){
                    List<NumberPicker> result = findNumberPicker((ViewGroup)child);
                    if(result.size()>0){
                        return result;
                    }
                }
            }
        }
        return npList;
    }

}
