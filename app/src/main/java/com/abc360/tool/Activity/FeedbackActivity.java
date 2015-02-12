package com.abc360.tool.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.APIs.Feedbacker;
import com.abc360.tool.userdeta.UserProfileManger;

/**
 * Created by roya on 14/11/21.
 */
public class FeedbackActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        TextView textViewSent = (TextView)findViewById(R.id.feedback_tv_sent);
        final EditText editTextInput= (EditText)findViewById(R.id.feedback_et_input);


        textViewSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comm = editTextInput.getText().toString();
                if (comm.equals("")){
                    Toast.makeText(getApplicationContext(),"请输入文字后发送",Toast.LENGTH_SHORT).show();
                }else if (comm.length() <= 5){
                    Toast.makeText(getApplicationContext(),"请输入至少五个字",Toast.LENGTH_SHORT).show();
                }else {
                    Feedbacker feedbacker = new Feedbacker(getApplicationContext());
                    feedbacker.feedback(new UserProfileManger(getApplicationContext()).getId(),
                            comm,
                            new Feedbacker.OnFinishedListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(getApplicationContext(),"已发送",Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onFailure(int error) {
                                    Toast.makeText(getApplicationContext(),"发送失败,请稍后再试",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

}
