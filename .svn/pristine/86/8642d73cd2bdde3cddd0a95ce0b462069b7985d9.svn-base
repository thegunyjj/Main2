package com.abc360.tool.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.APIs.VersionChecker;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoader;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoaderUtils;
import com.abc360.tool.widgets.UpdataDownloader;
import com.abc360.tool.widgets.LazyImageLoader.FileCache;
import com.igexin.sdk.PushManager;

/**
 * Created by roya on 14/11/21.
 */
public class SettingActivity extends Activity{

    SharedPreferences preferences;

    int loctionVersionCode =0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        preferences = getSharedPreferences("setting",MODE_PRIVATE);
        final SharedPreferences.Editor edit = preferences.edit();


        LinearLayout linearLayoutAbout = (LinearLayout)findViewById(R.id.setting_ll_about);
        linearLayoutAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout linearLayoutFeedback = (LinearLayout)findViewById(R.id.setting_ll_feedback);
        linearLayoutFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FeedbackActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout linearLayoutCheckNew = (LinearLayout)findViewById(R.id.setting_ll_check_new);
        linearLayoutCheckNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog dialog = new ProgressDialog(SettingActivity.this);
                dialog.setMessage("请稍候...");
                dialog.show();

                PackageManager packageManager = getPackageManager();
                try {
                    loctionVersionCode = packageManager.getPackageInfo(getPackageName(), 0).versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                VersionChecker versionChecker = new VersionChecker(getApplicationContext());
                versionChecker.check(new VersionChecker.OnFinishedListener() {
                    @Override
                    public void onSuccess(int versionCode, String versionName, final String link) {
                        dialog.dismiss();
                        if (versionCode > loctionVersionCode){
                            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                            builder.setTitle("发现新版本");
                            builder.setMessage(versionName + "\n是否更新？");
                            builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    new UpdataDownloader(getApplicationContext(),link);
                                }
                            });
                            builder.setNeutralButton("取消",null);
                            builder.show();

                        }else {
                            Toast.makeText(getApplicationContext(),"已是最新版本",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int error) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"检查失败，请稍后再试",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        RelativeLayout relativeLayoutClean = (RelativeLayout)findViewById(R.id.setting_rl_clean);
        relativeLayoutClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageLoader imageLoader = ImageLoaderUtils.getImageLoader(getApplicationContext());
                imageLoader.clearCacheAll();
                Toast.makeText(getApplicationContext(),"清理完成!",Toast.LENGTH_SHORT).show();
            }
        });

        final CheckBox swich = (CheckBox)findViewById(R.id.setting_sw_push);

        if (preferences.getInt("push",1) == 1){
            swich.setChecked(true);
        }else {
            swich.setChecked(false);
        }

        swich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    edit.putInt("push",1);
                    edit.apply();
                    PushManager.getInstance().turnOnPush(getApplicationContext());
                }else {
                    edit.putInt("push",0);
                    edit.apply();
                    PushManager.getInstance().turnOnPush(getApplicationContext());
                }
            }
        });



    }

}
