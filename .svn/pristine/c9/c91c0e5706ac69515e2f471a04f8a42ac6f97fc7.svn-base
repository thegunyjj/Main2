package com.abc360.tool.userdeta;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserIDManager {

    //存储到 SharedPreferences “login” 中
    private SharedPreferences preferences ;

    //账号密码
    class BagOfLogin {
        private String username ;
        private String password ;
        public void setUsername(String username) {
            this.username = username;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
    }

    public UserIDManager(Context context){
        preferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
    }

    public RequestParams getLoginParams()  {
        if (!hasID()){
            return null;
        }
        BagOfLogin bagOfLogin = new BagOfLogin();
        bagOfLogin.setUsername(getUserName());
        bagOfLogin.setPassword(getPassword());
        RequestParams params = new RequestParams();
        params.put("username",bagOfLogin.getUsername());
        params.put("password",bagOfLogin.getPassword());
        return params;
    }

    public String getUserName(){
        return preferences.getString("username",null);
    }

    public String getPassword(){
        return preferences.getString("password",null);
    }

    public void clear(){
        preferences.edit().clear().apply();
    }

    public void setUserNameANDPassword(String username, String password){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.putString("password", md5(password));
        editor.apply();
    }

    public boolean hasUername(){
        return (preferences.contains("username"));
    }

    public boolean hasPassword(){
        return (preferences.contains("password"));
    }

    public boolean hasID(){
        return (hasUername() && hasPassword());

    }

    //生成MD5
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
