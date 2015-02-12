package com.abc360.tool.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.APIs.SaveLeave;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by roya on 14/11/21.
 */
public class AflActivity extends Activity{

    private CheckBox checkBoxPh;
    private CheckBox checkBoxEu;
    private ImageView imageViewPh;
    private ImageView imageViewEu;

    private RelativeLayout relativeLayoutDate;
    private RelativeLayout relativeLayoutDays;
    private TextView textViewDate;
    private TextView textViewDays;

    private TextView textViewSave;

    private Data AflData;

    private class Data {
        boolean ph = false;
        boolean eu = false;
        String date;
        int days = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afl);
        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AflData = new Data();
        Intent intent = getIntent();

        checkBoxPh = (CheckBox)findViewById(R.id.afl_cb_ph);
        checkBoxEu = (CheckBox)findViewById(R.id.afl_cb_eu);
        imageViewPh = (ImageView)findViewById(R.id.alf_iv_ph);
        imageViewEu = (ImageView)findViewById(R.id.alf_iv_eu);

        relativeLayoutDate = (RelativeLayout)findViewById(R.id.afl_rl_date);
        relativeLayoutDays = (RelativeLayout)findViewById(R.id.afl_rl_days);
        textViewDate = (TextView)findViewById(R.id.afl_tv_date);
        textViewDays = (TextView)findViewById(R.id.afl_tv_days);

        textViewSave = (TextView)findViewById(R.id.afl_tv_save);
        textViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(AflData.eu || AflData.ph)){
                    Toast.makeText(getApplicationContext(),"请选择课程类型",Toast.LENGTH_LONG).show();
                    //Log.e("p",AflData.eu + "|"+AflData.ph);
                    return;
                }
                if (AflData.date == null){
                    Toast.makeText(getApplicationContext(),"请选择请假日期",Toast.LENGTH_LONG).show();
                    return;
                }
                if (AflData.days == 0){
                    Toast.makeText(getApplicationContext(),"请选择请假天数",Toast.LENGTH_LONG).show();
                    return;
                }
                List<Integer> catalog = new ArrayList<Integer>();
                if (AflData.ph)
                    catalog.add(1);
                if (AflData.eu)
                    catalog.add(2);

                final ProgressDialog progressDialog = new ProgressDialog(AflActivity.this);
                progressDialog.setMessage("请稍后...");
                progressDialog.show();
                SaveLeave saveLeave = new SaveLeave(getApplicationContext());
                saveLeave.save(catalog, AflData.date, AflData.days, new SaveLeave.FinishedListener() {
                    @Override
                    public void onSuccess() {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"请假成功",Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMsg) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),errorMsg,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        relativeLayoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(AflActivity.this ,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                        String date = (i+"-")+
                                ((i2+1) >= 10 ? i2+1 : "0"+(i2+1))+"-"+
                                (i3 >=10 ? i3 : "0"+i3);
                        AflData.date = date;
                        textViewDate.setText(date);
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        relativeLayoutDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String days[] = {"1天","2天","3天","4天","5天","6天","7天"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AflActivity.this);
                builder.setItems(days,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AflData.days = i+1;
                        textViewDays.setText(AflData.days+"天");
                    }
                });
                builder.show();
            }
        });

        checkBoxPh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AflData.ph = b;
                if (b){
                    imageViewPh.setVisibility(View.VISIBLE);
                }else {
                    imageViewPh.setVisibility(View.INVISIBLE);
                }
            }
        });

        checkBoxEu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AflData.eu = b;
                if (b){
                    imageViewEu.setVisibility(View.VISIBLE);
                }else {
                    imageViewEu.setVisibility(View.INVISIBLE);
                }
            }
        });


        int ph = intent.getIntExtra("ph",0);
        int eu = intent.getIntExtra("eu",0);

        if (ph == 0){
            checkBoxPh.setChecked(false);
        }else {
            checkBoxPh.setChecked(true);
        }
        if (eu == 0) {
            checkBoxEu.setChecked(false);
        }else {
            checkBoxEu.setChecked(true);
        }

    }

}
