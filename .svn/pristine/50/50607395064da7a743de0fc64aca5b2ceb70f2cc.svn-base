package com.abc360.tool.Activity;


import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.BookableClassManager;
import com.abc360.tool.userdeta.SaveBookClassManager;
import com.abc360.tool.userdeta.UserIDManager;
import com.abc360.tool.userdeta.UserProfileManger;
import com.abc360.tool.widgets.BookListviewAdapter;
import com.abc360.tool.widgets.BookListviewAdapterItem;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoader;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoaderUtils;
import com.loopj.android.http.RequestParams;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationFormCidActivity extends ListActivity {

    List<BookListviewAdapterItem> listDatas ;
    ListView listview ;
    BookListviewAdapter adapter ;


    String id;
    class Teacher{
        String id;
        String pic;
        String name;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.reservation_button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Intent intent = getIntent();

        final Teacher teacher = new Teacher();
        id = intent.getStringExtra("id");
        teacher.id = intent.getStringExtra("teacherID");
        teacher.name = intent.getStringExtra("teacherName");
        teacher.pic = intent.getStringExtra("teacherSrcLink");

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
                //Log.e("确认", listDatas.get(0).selected + "" );


                List<String> classes = new ArrayList<String>();
                for(BookListviewAdapterItem i :listDatas){
                    if(i.selected == 1){
                        classes.add(i.id);
                    }
                }
                if(classes.size() <= 0 ){
                    Toast.makeText(getApplicationContext(),"请选择要预约的时间",Toast.LENGTH_SHORT).show();
                    return;}

                final ProgressDialog progressDialog = new ProgressDialog(ReservationFormCidActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("请稍后...");
                progressDialog.show();

                RequestParams params = new RequestParams();

                params.put("cid",classes);
                params.put("sid",new UserProfileManger(getApplicationContext()).getId());
                params.put("md5",new UserIDManager(getApplicationContext()).getPassword());
                //Log.e("ids",params.toString());
                SaveBookClassManager saveBookClassManager = new SaveBookClassManager(getApplicationContext());
                saveBookClassManager.saveBookClass(params,new SaveBookClassManager.onSavedListner() {
                    @Override
                    public void onSuccess() {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"预约成功！请在课程详情中添加教材",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        progressDialog.dismiss();
                        if(errorCode == 0){
                            Toast.makeText(getApplicationContext(),"网络连接失败，请稍后再试",Toast.LENGTH_SHORT).show();
                        }else {
                            flashPage();
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        flashPage();



    }


    private void flashPage(){
        BookListviewAdapterItem item = new BookListviewAdapterItem();
        item.begin_time = "载入中...";
        item.selected = 2;
        listDatas.clear();
        listDatas.add(item);
        adapter.notifyDataSetChanged();

        BookableClassManager bookableClassManager = new BookableClassManager(getApplicationContext());
        bookableClassManager.loadBookableClasses(id,new BookableClassManager.onLoadedListner() {
            @Override
            public void onLoaded(BookableClassManager.Result result) {

                listDatas.clear();
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                if(result.data.size() >=1){
                    for (int j =0; j < result.data.size(); j++){
                        BookableClassManager.Data i = result.data.get(j);
                        BookListviewAdapterItem item = new BookListviewAdapterItem();
                        item.selected = 0;
                        item.id=i.id;
                        item.begin_time =formatter.format(new Date(Long.parseLong(i.begin_time) * 1000))+" ~ "+
                                formatter.format(new Date(Long.parseLong(i.begin_time) * 1000+1020000));
                        listDatas.add(item);
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure() {
                BookListviewAdapterItem item = new BookListviewAdapterItem();
                item.begin_time = "没有可选择的课程";
                item.selected = 2;
                listDatas.clear();
                listDatas.add(item);
                adapter.notifyDataSetChanged();
            }
        });
    }




}
