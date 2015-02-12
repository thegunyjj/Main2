package com.abc360.tool.userdeta;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class UserProfileManger {

    private SharedPreferences preferences ;

    //信息
    private class BagOfProfile {
        private String id ;                    //学生ID
        private String nickname ;               //昵称
        private String avatar ;                 //头像链接
        private int level;                      //级别
        private int acoin;                      //剩余coin
        private String skype ;                  //Skype id
        private String qq ;                     //QQ id
        private String mobile ;                  //电话号码
        private String email ;                  //email地址
        private String gtid;

        public String getId(){return id;}
        public String getNickname(){return nickname;}
        public String getAvatar(){return avatar;}
        public String getSkype(){return skype;}
        public String getQq(){return qq;}
        public String getMobile(){return mobile;}
        public String getEmail(){return email;}
        public String getGtid(){return gtid;}
        public int getLevel(){return level;}
        public int getAcoin(){return acoin;}

        public String toString(){
            return "id:"+id +
                    "nickname:"+nickname +
                    "avatar:"+avatar +
                    "skype:"+skype +
                    "qq:"+qq +
                    "mobile:"+mobile +
                    "email:"+email+
                    "level:"+level+
                    "acoin:"+acoin+
                    "gtid:"+gtid;
        }
    }

    public UserProfileManger(Context context){
        preferences = context.getSharedPreferences("Profile", Context.MODE_PRIVATE);
    }

    public void saveProfileFromJson(String json){
        Gson gson = new Gson();
        BagOfProfile bagOfProfile = gson.fromJson(json,BagOfProfile.class);

        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("id",bagOfProfile.getId());
        edit.putString("nickname",bagOfProfile.getNickname());
        edit.putString("avatar",bagOfProfile.getAvatar());
        edit.putString("skype",bagOfProfile.getSkype());
        edit.putString("qq",bagOfProfile.getQq());
        edit.putString("mobile",bagOfProfile.getMobile());
        edit.putString("email",bagOfProfile.getEmail());
        edit.putString("gtid",bagOfProfile.getGtid());
        edit.putInt("level",bagOfProfile.getLevel());
        edit.putInt("acoin",bagOfProfile.getAcoin());
        edit.apply();

    }
    public void clear(){
        preferences.edit().clear().apply();
    }

    public String getId(){return preferences.getString("id","");}
    public String getNickname(){return preferences.getString("nickname","");}
    public String getAvatar(){return preferences.getString("avatar","");}
    public String getSkype(){return preferences.getString("skype","");}
    public String getQq(){return preferences.getString("qq","");}
    public String getMobile(){return preferences.getString("mobile","");}
    public String getEmail(){return preferences.getString("email","");}
    public String getGtId(){return preferences.getString("gtid","");}
    public int    getLevel(){return preferences.getInt("level",0);}
    public int    getAcoin(){return preferences.getInt("acoin",0);}
}
