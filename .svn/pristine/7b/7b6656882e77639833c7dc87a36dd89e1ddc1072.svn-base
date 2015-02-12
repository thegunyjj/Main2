package com.abc360.tool.Activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.UserCourseBookedManager;
import com.abc360.tool.userdeta.UserIDManager;
import com.abc360.tool.userdeta.UserLoginManager;
import com.abc360.tool.userdeta.UserProfileManger;
import com.abc360.tool.widgets.LazyImageLoader.FileCache;

public class LoginActivity extends Activity {

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final UserLoginManager loginManager = new UserLoginManager(getApplicationContext());
        final UserIDManager idManager = new UserIDManager(getApplicationContext());

        username = (EditText)findViewById(R.id.editUsername);
        password = (EditText)findViewById(R.id.editPassword);
        username.setText(idManager.getUserName());

        new UserCourseBookedManager(getApplicationContext()).clear();
        idManager.clear();

        Button buttonLogin = (Button)findViewById(R.id.button_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idManager.clear();
                idManager.setUserNameANDPassword(username.getText().toString(), password.getText().toString());

                final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("正在登陆");
                pd.show();

                loginManager.setLoginListener(new UserLoginManager.LoginListener() {
                    @Override
                    public void onSuccess(byte[] response) {
                        pd.cancel();
                        Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_LONG).show();
                        UserProfileManger profileManger = new UserProfileManger(getApplicationContext());
                        profileManger.saveProfileFromJson(new String(response));
                        new FileCache(getApplicationContext()).clearAll();
                        Intent mainActivityClass = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainActivityClass);
                    }

                    @Override
                    public void onFailure(int ERROR_TYPE) {
                        pd.cancel();
                        if (ERROR_TYPE == UserLoginManager.ERROR_ID) {
                            Toast.makeText(getApplicationContext(), "账号或密码验证失败", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "网络链接失败，请稍后再试", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                loginManager.login(idManager.getLoginParams());
            }
        });

        Button buttonForget = (Button)findViewById(R.id.button_forget);
        buttonForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ForgetActivity.class);
                intent.putExtra("id",username.getText().toString());
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent MyIntent = new Intent(Intent.ACTION_MAIN);
            MyIntent.addCategory(Intent.CATEGORY_HOME);
            startActivity(MyIntent);
            finish();
        }
        return keyCode != KeyEvent.KEYCODE_BACK;
    }


}
