package com.abc360.tool.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.APIs.AvatarSaver;
import com.abc360.tool.userdeta.APIs.PhoneChanger;
import com.abc360.tool.userdeta.APIs.ProfileChanger;
import com.abc360.tool.userdeta.UserIDManager;
import com.abc360.tool.userdeta.UserProfileManger;
import com.abc360.tool.widgets.CircleBitmap;
import com.abc360.tool.widgets.LazyImageLoader.FileCache;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoader;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoaderUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class ProfileActivity extends Activity {
    ImageLoader imageLoader;
    ImageView imageViewAvater;

    float dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dp = getResources().getDisplayMetrics().density;
        final LinearLayout buttonBack = (LinearLayout)findViewById(R.id.profile_button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        UserProfileManger profileManger = new UserProfileManger(getApplicationContext());

        final TextView textViewName = (TextView)findViewById(R.id.profileActivity_tv_name);
        final TextView textViewMail = (TextView)findViewById(R.id.profileActivity_tv_mail);
        final TextView textViewSkype =(TextView)findViewById(R.id.profileActivity_tv_skype);
        final TextView textViewQQ =   (TextView)findViewById(R.id.profileActivity_tv_qq);
        final TextView textViewPhone =(TextView)findViewById(R.id.profileActivity_tv_phone);

        RelativeLayout relativeLayoutName = (RelativeLayout)findViewById(R.id.profileActivity_rl_name);
        RelativeLayout relativeLayoutMail = (RelativeLayout)findViewById(R.id.profileActivity_rl_mail);
        RelativeLayout relativeLayoutSkype= (RelativeLayout)findViewById(R.id.profileActivity_rl_skype);
        RelativeLayout relativeLayoutQQ   = (RelativeLayout)findViewById(R.id.profileActivity_rl_qq);
        RelativeLayout relativeLayoutPhone= (RelativeLayout)findViewById(R.id.profileActivity_rl_phone);
        RelativeLayout relativeLayoutPassword = (RelativeLayout)findViewById(R.id.profileActivity_rl_password);

        FrameLayout frameLayoutAvatar = (FrameLayout)findViewById(R.id.profileActivity_fl_avatar);
        frameLayoutAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });

        imageViewAvater = (ImageView)findViewById(R.id.reservation_profile_iv_avatar);

        UserProfileManger userProfileManger = new UserProfileManger(getApplicationContext());

        if (userProfileManger.getAvatar() != null) {
            ImageLoader imageLoader = ImageLoaderUtils.getImageLoader(getApplicationContext());
            imageLoader.DisplayImage(userProfileManger.getAvatar(), imageViewAvater);
        }

        textViewName.setText(profileManger.getNickname());
        textViewMail.setText(profileManger.getEmail());
        textViewSkype.setText(profileManger.getSkype());
        textViewQQ.setText(profileManger.getQq());
        textViewPhone.setText(profileManger.getMobile());

        relativeLayoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(ProfileActivity.this);
                editText.setPadding((int)(24*dp),(int)(24*dp),0,(int)(18*dp));
                editText.setBackgroundColor(0xffffff);
                editText.setText(textViewName.getText());
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("修改昵称");
                builder.setView(editText);
                builder.setPositiveButton("保存",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProfileChanger profileChanger = new ProfileChanger(getApplicationContext());
                        profileChanger.changeProfile(
                                new UserProfileManger(getApplicationContext()).getId(),
                                new UserIDManager(getApplicationContext()).getPassword(),
                                editText.getText().toString(),
                                null,
                                null,
                                null,
                                new ProfileChanger.OnFinishedListener() {
                                    @Override
                                    public void onSuccess() {
                                        Toast.makeText(getApplicationContext(),"已保存",Toast.LENGTH_SHORT).show();
                                        textViewName.setText( editText.getText().toString());
                                    }

                                    @Override
                                    public void onFailure(int errorCode) {
                                        if (errorCode == 0){
                                            Toast.makeText(getApplicationContext(),"网络连接失败，请稍后再试",Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(),"未知错误无法修改",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
                builder.setNegativeButton("取消",null);
                AlertDialog a = builder.create();
                a.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    }
                });

                a.show();

            }
        });

        relativeLayoutPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(),ChangePasswordActivity.class);
                startActivity(it);
            }
        });

        relativeLayoutMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(ProfileActivity.this);
                editText.setPadding((int)(24*dp),(int)(24*dp),0,(int)(18*dp));
                editText.setBackgroundColor(0xffffff);
                editText.setText(textViewMail.getText());
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("修改邮箱");
                builder.setView(editText);
                builder.setPositiveButton("保存",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProfileChanger profileChanger = new ProfileChanger(getApplicationContext());
                        profileChanger.changeProfile(
                                new UserProfileManger(getApplicationContext()).getId(),
                                new UserIDManager(getApplicationContext()).getPassword(),
                                null,
                                editText.getText().toString(),
                                null,
                                null,
                                new ProfileChanger.OnFinishedListener() {
                                    @Override
                                    public void onSuccess() {
                                        Toast.makeText(getApplicationContext(),"已保存",Toast.LENGTH_SHORT).show();
                                        textViewMail.setText( editText.getText().toString());
                                    }

                                    @Override
                                    public void onFailure(int errorCode) {
                                        if (errorCode == 0){
                                            Toast.makeText(getApplicationContext(),"网络连接失败，请稍后再试",Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(),"未知错误无法修改",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
                builder.setNegativeButton("取消",null);
                AlertDialog a = builder.create();
                a.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    }
                });

                a.show();

            }
        });

        relativeLayoutPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(),ChangePasswordActivity.class);
                startActivity(it);
            }
        });

        relativeLayoutSkype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(ProfileActivity.this);
                editText.setPadding((int)(24*dp),(int)(24*dp),0,(int)(18*dp));
                editText.setBackgroundColor(0xffffff);
                editText.setText(textViewSkype.getText());
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("修改Skype");
                builder.setView(editText);
                builder.setPositiveButton("保存",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProfileChanger profileChanger = new ProfileChanger(getApplicationContext());
                        profileChanger.changeProfile(
                                new UserProfileManger(getApplicationContext()).getId(),
                                new UserIDManager(getApplicationContext()).getPassword(),
                                null,
                                null,
                                editText.getText().toString(),
                                null,
                                new ProfileChanger.OnFinishedListener() {
                                    @Override
                                    public void onSuccess() {
                                        Toast.makeText(getApplicationContext(),"已保存",Toast.LENGTH_SHORT).show();
                                        textViewSkype.setText( editText.getText().toString());
                                    }

                                    @Override
                                    public void onFailure(int errorCode) {
                                        if (errorCode == 0){
                                            Toast.makeText(getApplicationContext(),"网络连接失败，请稍后再试",Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(),"未知错误无法修改",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
                builder.setNegativeButton("取消",null);
                AlertDialog a = builder.create();
                a.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    }
                });

                a.show();

            }
        });


        relativeLayoutPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(),ChangePasswordActivity.class);
                startActivity(it);
            }
        });

        relativeLayoutQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(ProfileActivity.this);
                editText.setPadding((int)(24*dp),(int)(24*dp),0,(int)(18*dp));
                editText.setBackgroundColor(0xffffff);
                editText.setText(textViewQQ.getText());
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("修改QQ");
                builder.setView(editText);
                builder.setPositiveButton("保存",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProfileChanger profileChanger = new ProfileChanger(getApplicationContext());
                        profileChanger.changeProfile(
                                new UserProfileManger(getApplicationContext()).getId(),
                                new UserIDManager(getApplicationContext()).getPassword(),
                                null,
                                null,
                                null,
                                editText.getText().toString(),
                                new ProfileChanger.OnFinishedListener() {
                                    @Override
                                    public void onSuccess() {
                                        Toast.makeText(getApplicationContext(),"已保存",Toast.LENGTH_SHORT).show();
                                        textViewQQ.setText( editText.getText().toString());
                                    }

                                    @Override
                                    public void onFailure(int errorCode) {
                                        if (errorCode == 0){
                                            Toast.makeText(getApplicationContext(),"网络连接失败，请稍后再试",Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(),"未知错误无法修改",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
                builder.setNegativeButton("取消",null);
                AlertDialog a = builder.create();
                a.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    }
                });

                a.show();

            }
        });

        relativeLayoutPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(),ChangePasswordActivity.class);
                startActivity(it);
            }
        });

        relativeLayoutPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                View layoutView = getLayoutInflater().inflate(R.layout.layout_change_phone,null,false);
                final EditText editTextPhone = (EditText)layoutView.findViewById(R.id.layout_change_phone_et_phone);
                final EditText editTextCode = (EditText)layoutView.findViewById(R.id.layout_change_phone_et_code);
                editTextPhone.setPadding((int)(12*dp),(int)(16*dp),0,0);
                editTextPhone.setBackgroundColor(0xffffff);
                editTextCode.setPadding((int)(12*dp),(int)(16*dp),0,(int)(16*dp));
                editTextCode.setBackgroundColor(0xffffff);
                final Button buttonGetCode = (Button)layoutView.findViewById(R.id.layout_change_phone_bt_getCode);
                buttonGetCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!editTextPhone.getText().toString().equals("")) {
                            PhoneChanger phoneChanger = new PhoneChanger(getApplicationContext());
                            phoneChanger.sendCode(new UserProfileManger(getApplicationContext()).getId(), editTextPhone.getText().toString(),new PhoneChanger.OnfinishedListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(getApplicationContext(),"发送成功！",Toast.LENGTH_SHORT).show();
                                    buttonGetCode.setText("已发送");
                                    buttonGetCode.setClickable(false);
                                    buttonGetCode.setBackgroundResource(R.drawable.btn_blue_unclickable);
                                }

                                @Override
                                public void onFailure(int errorCode, String msg) {
                                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            Toast.makeText(getApplicationContext(),"请输入手机号",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                editTextPhone.setText(textViewPhone.getText());
                builder.setView(layoutView);
                builder.setTitle("修改手机");
                builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PhoneChanger phoneChanger = new PhoneChanger(getApplicationContext());
                        phoneChanger.changePhone(new UserProfileManger(getApplicationContext()).getId(),
                                editTextPhone.getText().toString(),
                                editTextCode.getText().toString(),
                                new PhoneChanger.OnfinishedListener() {
                                    @Override
                                    public void onSuccess() {
                                        Toast.makeText(getApplicationContext(),"修改成功！",Toast.LENGTH_SHORT).show();
                                        textViewPhone.setText(editTextPhone.getText().toString());
                                    }

                                    @Override
                                    public void onFailure(int errorCode, String msg) {
                                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                builder.setNeutralButton("取消", null);
                AlertDialog a = builder.create();
                a.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(editTextPhone, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
                a.show();
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        //new setImage().execute((Integer[]) null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            try {
                if (data == null) return;
                Uri selectedImage = data.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                //Cursor actualimagecursor = managedQuery(selectedImage,proj,null,null,null);
                Cursor actualimagecursor = getContentResolver().query(selectedImage, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                String img_path = actualimagecursor.getString(actual_image_column_index);
                //File file = new File(img_path);


                Bitmap bitmap = BitmapFactory.decodeFile(img_path);
                Bitmap a;
                if ((bitmap.getWidth() <= 250) || (bitmap.getHeight() <= 250)) {
                    a = bitmap;
                } else {
                    int width = bitmap.getWidth();
                    int hight = bitmap.getHeight();
                    int k;
                    if (bitmap.getWidth() <= bitmap.getHeight()) {
                        k = width / 250;
                    } else {
                        k = hight / 250;
                    }
                    a = Bitmap.createScaledBitmap(bitmap, width / k, hight / k, false);
                }
                Bitmap b = new CircleBitmap().getSquareBitmap(a, 0);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                InputStream fileStream = new ByteArrayInputStream(baos.toByteArray());


                AvatarSaver avatarSaver = new AvatarSaver(getApplicationContext());
                avatarSaver.saveAvater(new UserProfileManger(getApplicationContext()).getId(), fileStream, new AvatarSaver.onFinishedListener() {
                    @Override
                    public void onSuccess(String avater) {
                        imageLoader = ImageLoaderUtils.getImageLoader(getApplicationContext());
                        imageLoader.clearCacheAll();
                        imageLoader.clearCache(avater);
                        imageLoader.DisplayImage(avater, imageViewAvater);
                        Toast.makeText(getApplicationContext(), "设置成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
