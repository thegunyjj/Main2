package com.abc360.tool.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.widgets.CustomTimePickerDialog;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class SearchActivity extends Activity {

    private class BagForParameter{
        int timeHH = -2;
        int timeMM;
        String date;
        String dateSow;
        int catalog = 0;
        boolean option0=false;
        boolean option1=false;
        boolean option2=false;
        boolean option3=false;
        boolean option4=false;
        boolean option5=false;
        boolean option6=false;
        boolean option7=false;
        public String toString(){
            return "&date="+date +
                    "&timeHH="+timeHH +
                    "&timeMM="+timeMM +
                    "&option0="+option0 +
                    "&option1="+option1 +
                    "&option2="+option2 +
                    "&option3="+option3 +
                    "&option4="+option4 +
                    "&option5="+option5 +
                    "&option6="+option6 +
                    "&option7="+option7 ;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final BagForParameter bagForParameter = new BagForParameter();
        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.profile_button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayout linearLayoutTeacher = (LinearLayout)findViewById(R.id.searchActivity_ll_teacher);
        LinearLayout linearLayoutDate = (LinearLayout)findViewById(R.id.searchActivity_ll_date);
        LinearLayout linearLayoutTime = (LinearLayout)findViewById(R.id.searchActivity_ll_time);
        final TextView textViewDate = (TextView)findViewById(R.id.searchActivity_tv_date);
        final TextView textViewTime = (TextView)findViewById(R.id.searchActivity_tv_time);
        final TextView textViewcatalog = (TextView)findViewById(R.id.searchActivity_tv_teacher);
        textViewcatalog.setText("全部");


        Button buttonSearch = (Button)findViewById(R.id.searchactivity_bt_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),bagForParameter.toString(),Toast.LENGTH_SHORT).show();
                if (!(bagForParameter.option1 || bagForParameter.option2 || bagForParameter.option3|| bagForParameter.option4|| bagForParameter.option5|| bagForParameter.option6|| bagForParameter.option7)){
                    Toast.makeText(getApplicationContext(),"请选择老师擅长课程",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(bagForParameter.date == null){
                    Toast.makeText(getApplicationContext(),"请选择日期",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(bagForParameter.timeHH == -2){
                    Toast.makeText(getApplicationContext(),"请选择时间",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                intent.putExtra("date",   bagForParameter.date );
                intent.putExtra("dateSow",bagForParameter.dateSow);
                intent.putExtra("timeHH", bagForParameter.timeHH );
                intent.putExtra("timeMM", bagForParameter.timeMM );
                intent.putExtra("catalog",bagForParameter.catalog);
                ArrayList<String> options = new ArrayList<String>();
                if(bagForParameter.option1) {
                    options.add("1");
                }
                if(bagForParameter.option2) {
                    options.add("2");
                }
                if(bagForParameter.option3) {
                    options.add("3");
                }
                if(bagForParameter.option4) {
                    options.add("4");
                }
                if(bagForParameter.option5) {
                    options.add("5");
                }
                if(bagForParameter.option6) {
                    options.add("6");
                }
                if(bagForParameter.option7) {
                    options.add("7");
                }
                intent.putStringArrayListExtra("option", options);
                startActivity(intent);
            }
        });

        final CheckBox checkBoxOption0 = (CheckBox)findViewById(R.id.searchActivity_cb_option0);
        final CheckBox checkBoxOption1 = (CheckBox)findViewById(R.id.searchActivity_cb_option1);
        final CheckBox checkBoxOption2 = (CheckBox)findViewById(R.id.searchActivity_cb_option2);
        final CheckBox checkBoxOption3 = (CheckBox)findViewById(R.id.searchActivity_cb_option3);
        final CheckBox checkBoxOption4 = (CheckBox)findViewById(R.id.searchActivity_cb_option4);
        final CheckBox checkBoxOption5 = (CheckBox)findViewById(R.id.searchActivity_cb_option5);
        final CheckBox checkBoxOption6 = (CheckBox)findViewById(R.id.searchActivity_cb_option6);
        final CheckBox checkBoxOption7 = (CheckBox)findViewById(R.id.searchActivity_cb_option7);

        final boolean[] CheckAll = {true,true};
        CheckBox.OnCheckedChangeListener checkedChangeListener = new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch (compoundButton.getId()) {
                    case R.id.searchActivity_cb_option0:
                        CheckAll[0] = false;
                        if (CheckAll[1]) {
                            checkBoxOption1.setChecked(b);
                            checkBoxOption2.setChecked(b);
                            checkBoxOption3.setChecked(b);
                            checkBoxOption4.setChecked(b);
                            checkBoxOption5.setChecked(b);
                            checkBoxOption6.setChecked(b);
                            checkBoxOption7.setChecked(b);
                        }
                        CheckAll[0] = true;
                        break;
                    case R.id.searchActivity_cb_option1:
                        bagForParameter.option1 = b;
                        if (CheckAll[0]){
                            CheckAll[1] = false;
                            checkBoxOption0.setChecked(false);
                            CheckAll[1] = true;}
                        break;
                    case R.id.searchActivity_cb_option2:
                        bagForParameter.option2 = b;
                        if (CheckAll[0]){
                            CheckAll[1] = false;
                            checkBoxOption0.setChecked(false);
                            CheckAll[1] = true;}
                        break;
                    case R.id.searchActivity_cb_option3:
                        bagForParameter.option3 = b;
                        if (CheckAll[0]){
                            CheckAll[1] = false;
                            checkBoxOption0.setChecked(false);
                            CheckAll[1] = true;}
                        break;
                    case R.id.searchActivity_cb_option4:
                        bagForParameter.option4 = b;
                        if (CheckAll[0]){
                            CheckAll[1] = false;
                            checkBoxOption0.setChecked(false);
                            CheckAll[1] = true;}
                        break;
                    case R.id.searchActivity_cb_option5:
                        bagForParameter.option5 = b;
                        if (CheckAll[0]){
                            CheckAll[1] = false;
                            checkBoxOption0.setChecked(false);
                            CheckAll[1] = true;}
                        break;
                    case R.id.searchActivity_cb_option6:
                        bagForParameter.option6 = b;
                        if (CheckAll[0]){
                            CheckAll[1] = false;
                            checkBoxOption0.setChecked(false);
                            CheckAll[1] = true;}
                        break;
                    case R.id.searchActivity_cb_option7:
                        bagForParameter.option7 = b;
                        if (CheckAll[0]){
                            CheckAll[1] = false;
                            checkBoxOption0.setChecked(false);
                            CheckAll[1] = true;}
                        break;
                    default:
                        break;
                }

            }
        };

        checkBoxOption0.setOnCheckedChangeListener(checkedChangeListener);
        checkBoxOption1.setOnCheckedChangeListener(checkedChangeListener);
        checkBoxOption2.setOnCheckedChangeListener(checkedChangeListener);
        checkBoxOption3.setOnCheckedChangeListener(checkedChangeListener);
        checkBoxOption4.setOnCheckedChangeListener(checkedChangeListener);
        checkBoxOption5.setOnCheckedChangeListener(checkedChangeListener);
        checkBoxOption6.setOnCheckedChangeListener(checkedChangeListener);
        checkBoxOption7.setOnCheckedChangeListener(checkedChangeListener);

        linearLayoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Long newUnix = System.currentTimeMillis();
                SimpleDateFormat dateShowFormat = new SimpleDateFormat("EE (yyyy/MM/dd)");
                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

                Calendar c = Calendar.getInstance();

                final String[] items;
                if (c.get(Calendar.HOUR_OF_DAY) <= 22){
                    items = new String[]{
                            dateShowFormat.format(newUnix),
                            dateShowFormat.format(newUnix + 86400000),
                            dateShowFormat.format(newUnix + 86400000 * 2 ),
                            dateShowFormat.format(newUnix + 86400000 * 3 )
                    };
                }else {
                    items = new String[]{
                            dateShowFormat.format(newUnix),
                            dateShowFormat.format(newUnix + 86400000),
                            dateShowFormat.format(newUnix + 86400000 * 2 ),
                            dateShowFormat.format(newUnix + 86400000 * 3 ),
                            dateShowFormat.format(newUnix + 86400000 * 4 )
                    };
                }


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchActivity.this);
                alertDialog.setTitle("上课日期");
                alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(getApplicationContext(),i+"",Toast.LENGTH_SHORT).show();
                        bagForParameter.date = dateFormat.format(newUnix + 86400000 * i);
                        bagForParameter.dateSow = items[i];
                        textViewDate.setText(items[i]);
                    }
                });

                alertDialog.show();

            }
        });

        linearLayoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter = new SimpleDateFormat("HH");
                String a = formatter.format(new Date(System.currentTimeMillis()));
                Integer b = Integer.valueOf(a);

                final CustomTimePickerDialog pickerDialog = new CustomTimePickerDialog(SearchActivity.this,null,b,0,true);

                pickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String a;
                        String b;
                        Class<?> classForid = null;
                        try {
                            classForid = Class.forName("com.android.internal.R$id");
                            Field timePickerField = classForid.getField("timePicker");
                            TimePicker timePicker = (TimePicker)pickerDialog.findViewById(timePickerField.getInt(null));

                            if(timePicker != null){
                                bagForParameter.timeHH = timePicker.getCurrentHour();
                                bagForParameter.timeMM = timePicker.getCurrentMinute();
                                if ( (bagForParameter.timeMM) != 0 && (bagForParameter.timeMM <= 2)) {
                                    bagForParameter.timeMM = bagForParameter.timeMM * 20;
                                }
                                if(bagForParameter.timeHH < 10){
                                    a = "0";
                                }else {a = "";}
                                if(bagForParameter.timeMM < 10){
                                    b = "0";
                                }else {b = "";}

                                textViewTime.setText(a+bagForParameter.timeHH+":"+b+bagForParameter.timeMM);
                            }else {
                                Log.e("timePicker","找不到timepicker");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                pickerDialog.show();
            }
        });

        linearLayoutTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String[] showString = new String[]{"全部","菲律宾","欧美"};

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchActivity.this);
                alertDialog.setTitle("老师类型");
                alertDialog.setItems(
                        showString,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bagForParameter.catalog = i ;
                        textViewcatalog.setText(showString[i]);

                    }
                });
                alertDialog.show();
            }
        });

    }


}
