package com.abc360.tool.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.APIs.SoftwaveChanger;
import com.abc360.tool.userdeta.UserProfileManger;

/**
 * Created by roya on 14/11/21.
 */
public class CouresSoftwaveActivity extends Activity{


    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_softwave);
        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.reservation_button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        RelativeLayout relativeLayoutSkype = (RelativeLayout)findViewById(R.id.course_softwave_rl_skype);
        RelativeLayout relativeLayoutQQ = (RelativeLayout)findViewById(R.id.course_softwave_rl_qq);

        final ImageView imageViewSkype = (ImageView)findViewById(R.id.course_softwave_iv_skype);
        final ImageView imageViewQQ = (ImageView)findViewById(R.id.course_softwave_iv_qq);

        Intent intent = getIntent();
        String softwaveSet = intent.getStringExtra("softwave");
        id = intent.getStringExtra("id");

        if (softwaveSet.equals("1")){
            imageViewSkype.setVisibility(View.VISIBLE);
        }else if (softwaveSet.equals("2")){
            imageViewQQ.setVisibility(View.VISIBLE);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int viewId = view.getId();
                switch (viewId){
                    case R.id.course_softwave_rl_skype:{
                        imageViewSkype.setVisibility(View.VISIBLE);
                        imageViewQQ.setVisibility(View.INVISIBLE);
                        break;}
                    case R.id.course_softwave_rl_qq:{
                        imageViewSkype.setVisibility(View.INVISIBLE);
                        imageViewQQ.setVisibility(View.VISIBLE);
                        break;}
                }
            }
        };

        relativeLayoutSkype.setOnClickListener(onClickListener);
        relativeLayoutQQ.setOnClickListener(onClickListener);


        Button buttonSave = (Button)findViewById(R.id.course_softwave_btn_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageViewSkype.getVisibility() == View.VISIBLE) {

                    SoftwaveChanger softwaveChanger = new SoftwaveChanger(getApplicationContext());
                    softwaveChanger.saveSoftwave(
                            new UserProfileManger(getApplicationContext()).getId(),
                            id,
                            "1",
                            new SoftwaveChanger.OnfinishedListener() {
                                @Override
                                public void onSuccess() {
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("softwave", 1);
                                    setResult(2,resultIntent);
                                    finish();
                                }

                                @Override
                                public void onFailure(int errorCode) {
                                    Toast.makeText(getApplicationContext(),"修改失败,请稍后再试",Toast.LENGTH_SHORT).show();
                                }
                            }

                    );

                }else if (imageViewQQ.getVisibility() == View.VISIBLE){
                    SoftwaveChanger softwaveChanger = new SoftwaveChanger(getApplicationContext());
                    softwaveChanger.saveSoftwave(
                            new UserProfileManger(getApplicationContext()).getId(),
                            id,
                            "2",
                            new SoftwaveChanger.OnfinishedListener() {
                                @Override
                                public void onSuccess() {
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("softwave", 2);
                                    setResult(2,resultIntent);
                                    finish();
                                }

                                @Override
                                public void onFailure(int errorCode) {
                                    Toast.makeText(getApplicationContext(),"修改失败,请稍后再试",Toast.LENGTH_SHORT).show();
                                }
                            }

                    );
                }else {
                    Toast.makeText(getApplicationContext(),"请选择",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

}
