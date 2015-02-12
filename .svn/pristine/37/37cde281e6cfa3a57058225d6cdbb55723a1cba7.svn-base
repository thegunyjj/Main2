package com.abc360.tool.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.APIs.PasswordFinder;

/**
 * Created by roya on 14/11/21.
 */
public class ForgetActivity extends Activity{

    EditText editTextPhone;
    EditText editTextCode;
    EditText editTextNewPasswold0;
    EditText editTextNewPasswold1;
    Button   buttonGetCode;
    Button   buttonSent;

    String id;

    PasswordFinder finder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editTextPhone = (EditText)findViewById(R.id.forget_activity_et_phone);
        editTextCode  = (EditText)findViewById(R.id.forget_activity_et_code);
        editTextNewPasswold0 = (EditText)findViewById(R.id.forget_activity_et_npsw0);
        editTextNewPasswold1 = (EditText)findViewById(R.id.forget_activity_et_npsw1);
        buttonGetCode = (Button)findViewById(R.id.forget_activity_bt_getcode);
        buttonSent = (Button)findViewById(R.id.forget_activity_bt_sent);

        finder = new PasswordFinder(getApplicationContext());

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        editTextPhone.setText(id);

        buttonGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finder.getCode(id,new PasswordFinder.OnFinishedListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(),"已发送",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        buttonSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editTextPhone.getText().toString();
                String code=editTextCode.getText().toString();
                String p0 = editTextNewPasswold0.getText().toString();
                String p1 = editTextNewPasswold1.getText().toString();
                if (code.length() != 6){
                    Toast.makeText(getApplicationContext(),"验证码错误",Toast.LENGTH_LONG).show();
                }else if (p0.length() < 6){
                    Toast.makeText(getApplicationContext(),"新密码长度不可小于六位",Toast.LENGTH_LONG).show();
                }else if (!p0.equals(p1)){
                    Toast.makeText(getApplicationContext(),"两次输入不一致",Toast.LENGTH_LONG).show();
                }else {
                    finder.setPasswold(id,code,p0,new PasswordFinder.OnFinishedListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onFailure(int errorCode, String msg) {
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

}
