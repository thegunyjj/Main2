package com.abc360.tool.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.abc360.tool.userdeta.UserIDManager;
import com.abc360.tool.userdeta.UserProfileManger;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChangePasswordActivity extends Activity {

    EditText editTextOld1;
    EditText editTextnew0;
    EditText editTextNew1;
    Button buttonChangePassword;

    private class BagOfError {
        String errorCode ;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        LinearLayout buttonBack = (LinearLayout) findViewById(R.id.changePassword_button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editTextOld1 = (EditText)findViewById(R.id.old1);
        editTextnew0 = (EditText)findViewById(R.id.new0);
        editTextNew1 = (EditText)findViewById(R.id.new1);
        buttonChangePassword = (Button)findViewById(R.id.button_changepassword);
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oldPassword1 = editTextOld1.getText().toString();
                final String newPassword0 = editTextnew0.getText().toString();
                String newPassword1 = editTextNew1.getText().toString();

                if (oldPassword1.equals("")){
                    Toast.makeText(getApplication(),"请输入旧密码",Toast.LENGTH_LONG).show();

                }else if (newPassword0.equals("") || newPassword1.equals("")){
                    Toast.makeText(getApplication(),"请输入新密码",Toast.LENGTH_LONG).show();

                }else if (!newPassword0.equals(newPassword1)){
                    Toast.makeText(getApplication(),"输入的密码不一致，请重新输入",Toast.LENGTH_LONG).show();

                }else {

                    RequestParams params = new RequestParams();
                    params.put("sid", new UserProfileManger(getApplicationContext()).getId());
                    params.put("oldpassword", md5(oldPassword1));
                    params.put("newpassword", newPassword1);
                    Log.e("密",params.toString());
int yl;
                    AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(getApplicationContext());

                    client.post(getApplicationContext(), getApplicationContext().getString(R.string.tool_api_link) + "setPassword", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                String json = new String(responseBody);
                                Log.e("密",json);
                                BagOfError bagOfError;
                                Gson gson = new Gson();
                                bagOfError = gson.fromJson(json, BagOfError.class);
                                if (bagOfError.errorCode.equals("0")) {
                                    Toast.makeText(getApplication(), "修改成功", Toast.LENGTH_LONG).show();
                                    Intent it = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(it);
                                } else {
                                    Toast.makeText(getApplication(), "密码修改失败，请确认输入的密码是否正确", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception ignored) {
                                Toast.makeText(getApplication(), "未知错误，请稍后再试", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(getApplication(), "网络连接失败，请稍后再试", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    public static String md5(String string) {

        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
