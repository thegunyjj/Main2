package com.abc360.tool.Activity;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.APIs.SaveBind;
import com.abc360.tool.userdeta.APIs.TeacherBindDetail;
import com.abc360.tool.widgets.BookListviewAdapter;
import com.abc360.tool.widgets.BookListviewAdapterItem;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoader;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoaderUtils;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReservationForBindingActivity extends ListActivity {

    List<BookListviewAdapterItem> listDatas ;
    ListView listview ;
    BookListviewAdapter adapter ;

    TextView textViewAcoin;
    TextView textViewWeekday;

    String id;
    String begin_time;

    Teacher teacher;

    class Teacher{
        String id;
        String pic;
        String name;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_binding);
        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.reservation_button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textViewAcoin = (TextView)findViewById(R.id.reservation_tv_teacher_acoin);
        textViewWeekday = (TextView)findViewById(R.id.reservation_tv_teacher_weekday);

        final Intent intent = getIntent();

        teacher = new Teacher();
        id = intent.getStringExtra("id");
        teacher.id = intent.getStringExtra("teacherID");
        teacher.name = intent.getStringExtra("teacherName");
        teacher.pic = intent.getStringExtra("teacherSrcLink");
        begin_time = intent.getStringExtra("begin_time");

        LinearLayout buttonCheckTeacher = (LinearLayout)findViewById(R.id.reservation_button_checkTeacher);
        buttonCheckTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CheckTeacherActivity.class);
                intent.putExtra("teacherID",teacher.id);
                intent.putExtra("teacherName",teacher.name);
                intent.putExtra("teacherSrcLink",teacher.pic);
                startActivity(intent);
            }
        });

        TextView tv = (TextView) findViewById(R.id.reservation_tv_teacher_name);
        tv.setText(teacher.name);

        ImageView imageViewTeacherPicture = (ImageView)findViewById(R.id.reservation_iv_teacher);
        ImageLoader imageLoader = ImageLoaderUtils.getImageLoader(getApplicationContext());
        imageLoader.DisplayImage(teacher.pic, imageViewTeacherPicture);


        listDatas = new ArrayList<BookListviewAdapterItem>();
        listview = getListView();
        adapter = new BookListviewAdapter(getApplicationContext(),listDatas);

        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapter);
        alphaInAnimationAdapter.setAbsListView(listview);

        listview.setAdapter(alphaInAnimationAdapter);

        Button buttonOK = (Button)findViewById(R.id.reservation_button_ok);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> classes = new ArrayList<String>();
                for(BookListviewAdapterItem i :listDatas){
                    if(i.selected == 1){
                        classes.add(i.begin_time);
                    }
                }
                if (classes.size() > 0) {
                    final ProgressDialog progressDialog = new ProgressDialog(ReservationForBindingActivity.this);
                    progressDialog.setMessage("请稍候...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    SaveBind saveBind = new SaveBind(getApplicationContext());
                    saveBind.save(teacher.id, classes, new SaveBind.OnSavedListener() {
                        @Override
                        public void onSuccess() {
                            progressDialog.dismiss();
                            Intent intent1 = new Intent(getApplicationContext(),BindingActivity.class);
                            startActivity(intent1);
                        }

                        @Override
                        public void onFailure(int errorCode, String errorMsg) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),errorMsg,Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(),"请选择课程",Toast.LENGTH_LONG).show();
                }
            }
        });

        flashPage();



    }


    private void flashPage(){
        TeacherBindDetail teacherBindDetail = new TeacherBindDetail(getApplicationContext());
        teacherBindDetail.load(teacher.id, begin_time, new TeacherBindDetail.FinishedListener() {
            @Override
            public void onSuccess(TeacherBindDetail.Data data) {
                textViewAcoin.setText(data.acoin+"A币/节");
                data.weekday = data.weekday.replace("星期","周").replace("天","日");
                textViewWeekday.setText(data.weekday);
                for (TeacherBindDetail.LSN i : data.lsn){
                    BookListviewAdapterItem item = new BookListviewAdapterItem();
                    item.selected = 0;
                    item.id=i.id;
                    item.begin_time = i.just_time;
                    listDatas.add(item);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFaiure(int errorCode, String errorMsg) {
                Toast.makeText(getApplicationContext(),errorMsg,Toast.LENGTH_LONG).show();
            }
        });

    }




}
