package com.abc360.tool.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.APIs.LeaveCancel;
import com.abc360.tool.userdeta.APIs.LeaveRecords;
import com.abc360.tool.widgets.LeaveItem;

import java.text.SimpleDateFormat;

/**
 * Created by roya on 14/11/21.
 */
public class LeaveActivity extends Activity{

    LeaveRecords.Result locationResult;

    private SwipeRefreshLayout mSwipeLayout;
    private LinearLayout linearLayoutMain;

    private Button buttonAfl;
    private View.OnClickListener buttonAflListener;

    private TextView textViewPh;
    private TextView textViewEu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.leave_swipe_ly);
        mSwipeLayout.setColorSchemeResources(R.color.main_color);

        linearLayoutMain = (LinearLayout)findViewById(R.id.leave_ll_main);

        textViewPh = (TextView)findViewById(R.id.leave_tv_ph);
        textViewEu = (TextView)findViewById(R.id.leave_tv_eu);

        buttonAfl = (Button)findViewById(R.id.leave_afl);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                flashUserData();
            }
        });


        buttonAflListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((locationResult.leave_time.ph + locationResult.leave_time.eu) <= 0) {
                    Toast.makeText(getApplicationContext(), "您没有请假次数，不能请假", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Log.e("hpeu",locationResult.leave_time.ph+"|"+locationResult.leave_time.eu);
                    Intent intent = new Intent(getApplicationContext(), AflActivity.class);
                    intent.putExtra("ph", locationResult.leave_time.ph);
                    intent.putExtra("eu", locationResult.leave_time.eu);
                    startActivity(intent);
                }
            }
        };

        TypedValue typed_value = new TypedValue();
        getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typed_value, true);
        mSwipeLayout.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value.resourceId));
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSwipeLayout.setRefreshing(true);
        textViewPh.setText(0 + "次");
        textViewPh.setText(0 + "次");
        flashUserData();
    }

    private void flashUserData(){
        new LeaveRecords(getApplicationContext()).getLeaveRecords(new LeaveRecords.LoadedListener() {
            @Override
            public void onLoaded(LeaveRecords.Result result) {
                buttonAfl.setOnClickListener(buttonAflListener);

                mSwipeLayout.setRefreshing(false);
                textViewPh.setText(result.leave_time.ph + "次");
                textViewEu.setText(result.leave_time.eu+"次");
                locationResult = result;
                linearLayoutMain.removeAllViews();
                if (result.data != null){
                    for (final LeaveRecords.Data item : result.data){
                        LeaveItem view = new LeaveItem(getApplicationContext(), linearLayoutMain);
                        if (item.catalog.equals("1")){
                            view.setTitle("菲教套餐");
                        }else {
                            view.setTitle("欧美套餐");
                        }
                        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
                        String timeBegin = format.format(Long.decode(item.begin_time)*1000);
                        String timeEnd = format.format(Long.decode(item.end_time)*1000);

                        view.setSubtitle("请假日期: " + timeBegin + " ~ " + timeEnd);

                        view.setButtonListenter(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (item.cancel_able == 1) {
                                    AlertDialog.Builder a = new AlertDialog.Builder(LeaveActivity.this);
                                    a.setMessage("确认取消请假吗？");
                                    a.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            new LeaveCancel(getApplicationContext()).cancel(item.id, new LeaveCancel.FinishedListener() {
                                                @Override
                                                public void onSuccess() {
                                                    flashUserData();
                                                }

                                                @Override
                                                public void onFaiure() {
                                                    Toast.makeText(getApplicationContext(), "取消失败", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    });
                                    a.setNeutralButton("否", null);
                                    a.show();
                                }else {
                                    Toast.makeText(getApplicationContext(),"请假已经开始不能取消",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        linearLayoutMain.addView(view);
                    }
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                mSwipeLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(),errorMsg,Toast.LENGTH_LONG).show();
            }
        });
    }



}
