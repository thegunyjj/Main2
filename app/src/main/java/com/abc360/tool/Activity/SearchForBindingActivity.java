package com.abc360.tool.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.widgets.CustomTimePickerDialog;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by roya on 14/11/21.
 */
public class SearchForBindingActivity extends Activity{

    LinearLayout linearLayoutTeacher;
    LinearLayout linearLayoutTimes;
    LinearLayout linearLayoutName;

    Button buttonSearch;

    TextView textViewTeacher;
    TextView textViewTimes;
    TextView textViewName;

    BagForSeacher bagForSeacher;

    private class BagForSeacher{
        int catalog = 0;
        String times;
        String name = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_binding);
        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        linearLayoutTeacher = (LinearLayout)findViewById(R.id.searchActivity_ll_teacher);
        linearLayoutTimes   = (LinearLayout)findViewById(R.id.searchActivity_ll_times);
        linearLayoutName    = (LinearLayout)findViewById(R.id.searchActivity_ll_name);

        buttonSearch        = (Button)findViewById(R.id.searchactivity_bt_search);

        textViewTeacher     = (TextView)findViewById(R.id.searchActivity_tv_teacher);
        textViewTimes       = (TextView)findViewById(R.id.searchActivity_tv_times);
        textViewName        = (TextView)findViewById(R.id.searchActivity_tv_name);

        bagForSeacher = new BagForSeacher();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bagForSeacher.catalog == 0){
                    Toast.makeText(getApplicationContext(),"请选择老师类型",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(), ResultsForBindingActivity.class);
                    intent.putExtra("catalog", bagForSeacher.catalog);
                    intent.putExtra("begin_time", bagForSeacher.times);
                    intent.putExtra("first_letter", bagForSeacher.name);
                    startActivity(intent);
                }
            }
        });


        linearLayoutTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] showString = new String[]{"菲律宾","欧美"};

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchForBindingActivity.this);
                alertDialog.setTitle("老师类型");
                alertDialog.setItems(
                        showString,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                bagForSeacher.catalog = i+1 ;
                                textViewTeacher.setText(showString[i]);

                            }
                        });
                alertDialog.show();
            }
        });

        linearLayoutTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat formatter = new SimpleDateFormat("HH");
                final String a = formatter.format(new Date(System.currentTimeMillis()));
                Integer b = Integer.valueOf(a);

                final CustomTimePickerDialog pickerDialog = new CustomTimePickerDialog(SearchForBindingActivity.this,null,b,0,true);

                pickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Integer timeHH;
                        Integer timeMM;
                        String a;
                        String b;
                        Class<?> classForid = null;
                        try {
                            classForid = Class.forName("com.android.internal.R$id");
                            Field timePickerField = classForid.getField("timePicker");
                            TimePicker timePicker = (TimePicker)pickerDialog.findViewById(timePickerField.getInt(null));

                            if(timePicker != null){
                                timeHH = timePicker.getCurrentHour();
                                timeMM = timePicker.getCurrentMinute();
                                if ( (timeMM) != 0 && (timeMM <= 2)) {
                                    timeMM = timeMM * 20;
                                }
                                if(timeHH < 10){
                                    a = "0";
                                }else {a = "";}
                                if(timeMM < 10){
                                    b = "0";
                                }else {b = "";}
                                bagForSeacher.times = a+timeHH+":"+b+timeMM;
                                textViewTimes.setText(a+timeHH+":"+b+timeMM);
                            }else {
                                Log.e("timePicker", "找不到timepicker");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                pickerDialog.show();
            }
        });

        linearLayoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] showString = new String[]{"全部","A","B","C","D","E",
                    "F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchForBindingActivity.this);
                alertDialog.setTitle("教师名首字母");
                alertDialog.setItems(
                        showString,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                bagForSeacher.name = i != 0 ? showString[i] : null;
                                textViewName.setText(showString[i]);

                            }
                        });
                alertDialog.show();
            }
        });



    }

}
