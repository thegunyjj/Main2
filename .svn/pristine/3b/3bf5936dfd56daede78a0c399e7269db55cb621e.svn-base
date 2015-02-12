package com.abc360.tool.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.APIs.ClassCanceler;
import com.abc360.tool.userdeta.UserCoursesDetailManager;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoader;
import com.abc360.tool.widgets.PdfDownloader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CouresDetailActivity extends Activity {

    String id;
    UserCoursesDetailManager.CourseDetailsData courseDetail;

    ProgressDialog progressDialog;
    TextView textViewTeacherName;
    TextView textViewTime;
    TextView textViewQQ;
    TextView textViewSkpye;
    TextView textViewPay;



    TextView textViewtextbook;
    TextView textViewToolId;
    ImageView imageViewToolSrc;

    RelativeLayout relativeLayoutDownload;
    Button buttonDownload;

    LinearLayout linearLayoutTextbook;
    LinearLayout linearLayoutSoftwave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coures_detail);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.couresdetail_button_back);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        relativeLayoutDownload = (RelativeLayout)findViewById(R.id.couresdetail_rl_download);
        buttonDownload = (Button)findViewById(R.id.couresdetail_btn_download);

        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                https://github.com/JoanZapata/android-pdfview
                PdfDownloader pdfDownloader = new PdfDownloader(getApplicationContext(),courseDetail.pdf);
                pdfDownloader.setListener(new PdfDownloader.OnLoaded() {
                    @Override
                    public void onLoaded(File file) {

                        try {
                            Intent intent1= new Intent(Intent.ACTION_VIEW);
                            intent1.setDataAndType(Uri.fromFile(file),"application/pdf");
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent1);
                        }catch ( Exception e ){
                            AlertDialog.Builder builder = new AlertDialog.Builder(CouresDetailActivity.this);
                            builder.setMessage("请安装PDF阅读器以打开教材(pdf)!");
                            builder.setPositiveButton("确认",null);
                            builder.show();
                        }

                    }
                });
                pdfDownloader.loadPdf();
            }
        });











        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("请稍后...");
        progressDialog.show();

        textViewTeacherName = (TextView)findViewById(R.id.teacherName);
        textViewToolId = (TextView)findViewById(R.id.toolId);
        textViewTime = (TextView)findViewById(R.id.time);
        textViewQQ = (TextView)findViewById(R.id.qq);
        textViewSkpye = (TextView)findViewById(R.id.skpye);
        imageViewToolSrc = (ImageView)findViewById(R.id.toolSrc);
        textViewtextbook = (TextView)findViewById(R.id.couresdetail_tv_textbook);

        textViewPay = (TextView)findViewById(R.id.pay);

        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();

        linearLayoutTextbook = (LinearLayout)findViewById(R.id.couresdetail_ll_textbook);
        linearLayoutTextbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CourseSelectionActivity.class);
                intent.putExtra("hasTextbook",courseDetail.hasTextbook);
                if(courseDetail.hasTextbook){
                    intent.putExtra("cname",courseDetail.cname);
                    intent.putExtra("tname",courseDetail.tname);
                    intent.putExtra("mname",courseDetail.mname);
                    intent.putExtra("cid",courseDetail.cid);
                    intent.putExtra("tid",courseDetail.tid);
                    intent.putExtra("mid",courseDetail.mid);
                }
                intent.putExtra("id",id);
                startActivityForResult(intent, 1);
            }
        });


        linearLayoutSoftwave = (LinearLayout)findViewById(R.id.couresdetail_ll_softwave);
        linearLayoutSoftwave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CouresSoftwaveActivity.class);
                intent.putExtra("softwave",courseDetail.use_tool);
                intent.putExtra("id",courseDetail.id);
                startActivityForResult(intent,2);
            }
        });


       load();

    }

    private void load(){
        final UserCoursesDetailManager coursesDetailManager = new UserCoursesDetailManager(getApplicationContext());
        coursesDetailManager.loadCourseDetail(id,new UserCoursesDetailManager.OnDetailsDataLoadedListener() {
            @Override
            public void onDetailsDataLoaded() {
                courseDetail = coursesDetailManager.getCourseDetail(id);
                ImageView intoIcon = (ImageView) findViewById(R.id.couresdetail_iv_textbook_into);
                if(courseDetail.nickname != null){

                    progressDialog.dismiss();

                    if (courseDetail.pdf != null){
                        relativeLayoutDownload.setVisibility(View.VISIBLE);
                    }
                    if(courseDetail.hasTextbook){
                        if (courseDetail.videoName != null){
                            textViewtextbook.setText(courseDetail.videoName);
                            intoIcon.setVisibility(View.GONE);
                            linearLayoutTextbook.setOnClickListener(null);
                        }else {
                                textViewtextbook.setText(courseDetail.cname + "\n" + courseDetail.tname + "\n" + courseDetail.mname);
                        }
                    }else {
                        if (courseDetail.free_try.equals("0")) {
                            textViewtextbook.setText("请添加教材");
                        }else {
                            textViewtextbook.setText("测评课");
                            intoIcon.setVisibility(View.GONE);
                            linearLayoutTextbook.setOnClickListener(null);
                        }
                    }
                    textViewTeacherName.setText(courseDetail.nickname);
                    SimpleDateFormat formatter = new SimpleDateFormat("H:mm, EEEE, yyyy/M/d");
                    textViewTime.setText(formatter.format(new Date(Long.parseLong(courseDetail.begin_time)*1000)));

                    textViewQQ.setText(courseDetail.qq);
                    textViewSkpye.setText(courseDetail.skype);

                    if (courseDetail.use_free.equals("1")){
                        textViewPay.setText(courseDetail.pay + "A币 (自由课时)");
                    }else {
                        textViewPay.setText(courseDetail.pay + "A币 (包月课时)");
                    }

                    if(courseDetail.use_tool.equals("2")) {
                        textViewToolId.setText("QQ授课");
                        imageViewToolSrc.setImageResource(R.drawable.ic_qq);
                    }else if(courseDetail.use_tool.equals("1")){
                        textViewToolId.setText("Skype授课");
                        imageViewToolSrc.setImageResource(R.drawable.ic_skype);
                    }else {
                        textViewToolId.setText("未知工具授课");
                        imageViewToolSrc.setImageResource(R.drawable.ic_skype);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "找不到课程详情",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onDetailsDataFailure() {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        "课程详情查询失败",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1:
                textViewtextbook.setText(data.getStringExtra("cname")+"\n"+
                    data.getStringExtra("tname")+"\n"+
                    data.getStringExtra("mname"));
                courseDetail.hasTextbook = true;
                courseDetail.cid = data.getStringExtra("cid");
                courseDetail.tid = data.getStringExtra("tid");
                courseDetail.mid = data.getStringExtra("mid");
                courseDetail.cname = data.getStringExtra("cname");
                courseDetail.tname = data.getStringExtra("tname");
                courseDetail.mname = data.getStringExtra("mname");
                break;
            case 2:
                if (data.getIntExtra("softwave",0) == 1){
                    courseDetail.use_tool = "1";
                    textViewToolId.setText("Skype授课");
                    imageViewToolSrc.setImageResource(R.drawable.ic_skype);
                } else if (data.getIntExtra("softwave",0) == 2){
                    courseDetail.use_tool = "2";
                    textViewToolId.setText("QQ授课");
                    imageViewToolSrc.setImageResource(R.drawable.ic_qq);
                }
                break;
            default:
                break;
        }
        load();
    }

}
