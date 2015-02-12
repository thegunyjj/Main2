package com.abc360.tool.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.APIs.FavoriteSaver;
import com.abc360.tool.userdeta.APIs.TeacherChecker;
import com.abc360.tool.userdeta.UserProfileManger;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoader;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoaderUtils;
import com.abc360.tool.widgets.VoicePlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CheckTeacherActivity extends Activity {

    class Teacher{
        String id;
        String pic;
        String name;
    }

    VoicePlayer player;

    TeacherChecker.Teacher teacherChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_teacher);
        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.checkTeacher_button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();

        final Teacher teacher = new Teacher();
        teacher.id = intent.getStringExtra("teacherID");
        teacher.name = intent.getStringExtra("teacherName");
        teacher.pic = intent.getStringExtra("teacherSrcLink");

        ImageView imageViewTeacherPic = (ImageView)findViewById(R.id.checkTeacher_iv_teacher);
        TextView textViewTeacherName = (TextView)findViewById(R.id.checkTeacher_tv_teacher_name);

        textViewTeacherName.setText(teacher.name);

        ImageLoader imageLoader = ImageLoaderUtils.getImageLoader(getApplicationContext());
        imageLoader.DisplayImage(teacher.pic, imageViewTeacherPic);

        final Button buttonStar = (Button)findViewById(R.id.checkTeacher_bt_star);
        final TextView textViewFavorites = (TextView)findViewById(R.id.checkTeacher_tv_favorites);
        final TextView textViewCourses   = (TextView)findViewById(R.id.checkTeacher_tv_courses);
        final TextView textViewChinese   = (TextView)findViewById(R.id.checkTeacher_tv_chinese);
        final TextView textViewOthers    = (TextView)findViewById(R.id.checkTeacher_tv_others );
        final ImageView imageViewPlyaVoice=(ImageView)findViewById(R.id.checkTeacher_iv_playVoice);

        imageViewPlyaVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (teacherChecker.voice != null) {
                    if (player == null){
                        try {
                            player = new VoicePlayer(teacherChecker.voice);
                            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    imageViewPlyaVoice.setImageResource(R.drawable.ic_play);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"找不到音频",Toast.LENGTH_LONG).show();
                        }
                    }
                    if (player != null){
                       if (player.isPlaying()) {
                           player.pause();
                           imageViewPlyaVoice.setImageResource(R.drawable.ic_play);
                       }else {
                           player.play();
                           imageViewPlyaVoice.setImageResource(R.drawable.ic_pause);
                       }


                      }else {
                        Toast.makeText(getApplicationContext(),"找不到音频",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"找不到音频",Toast.LENGTH_LONG).show();
                }
            }
        });

        final ProgressDialog progressDialog = new ProgressDialog(CheckTeacherActivity.this);
        progressDialog.setMessage("载入中...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        TeacherChecker checker = new TeacherChecker(getApplicationContext());
        checker.getTeacherDetail(teacher.id,new TeacherChecker.OnLoadedListner() {
            @Override
            public void onSuccess(final TeacherChecker.Teacher teacher) {
                teacherChecker = teacher;
                if (teacher.myfav){
                    buttonStar.setText("已收藏");
                }else {
                    buttonStar.setText("收藏");
                }
                textViewFavorites.setText(teacher.fav);
                textViewChinese .setText(teacher.chinese);
                if (teacher.courses != null) {
                    for (int i = 0; i < teacher.courses.size(); i++) {
                        textViewCourses.append(teacher.courses.get(i));
                        if ((i + 1) != teacher.courses.size()) {
                            textViewCourses.append("\n");
                        }
                    }
                }
                if (teacher.marks != null) {
                    for (int i = 0; i < teacher.marks.size(); i++) {
                        if (teacher.marks.get(i).label != null) {
                            textViewOthers.append(teacher.marks.get(i).label);
                            if ((i + 1) != teacher.courses.size()) {
                                textViewOthers.append("\n");
                            }
                        }
                    }
                }
                if (textViewOthers.getText() == ""){
                    textViewOthers.setText("无");
                }

                progressDialog.cancel();

                buttonStar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String sid = new UserProfileManger(getApplicationContext()).getId();
                        String tid = teacher.id;
                        if (buttonStar.getText().equals("收藏")){
                            new FavoriteSaver(getApplicationContext()).saveFavorite(tid,sid,new FavoriteSaver.OnFinishedListner() {
                                @Override
                                public void onSuccess() {
                                    buttonStar.setText("已收藏");
                                    textViewFavorites.setText((Integer.parseInt(textViewFavorites.getText().toString())+1)+"");
                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                        }else {
                            List<String> tids = new ArrayList<String>();
                            tids.add(tid);
                            new FavoriteSaver(getApplicationContext()).delFavorite(sid,tids,new FavoriteSaver.OnFinishedListner() {
                                @Override
                                public void onSuccess() {
                                    buttonStar.setText("收藏");
                                    textViewFavorites.setText((Integer.parseInt(textViewFavorites.getText().toString())-1)+"");
                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                        }
                    }
                });

            }

            @Override
            public void onFailure(int errorCode) {
                progressDialog.cancel();
                Toast.makeText(getApplicationContext(),"查询失败",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();

    }


    @Override
    protected void onPause(){
        super.onPause();
        if (player != null) {
            player.pause();
        }
    }



}
