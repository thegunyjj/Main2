package com.abc360.tool.userdeta;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.abc360.tool.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roya on 14/11/11.
 */
public class UserCourseBookedManager {

    String TAG_UserCourseBooked = "CourseBooked";

    Context context;
    SharedPreferences preferences;
    Gson gson;

    public class Result{
        public String errorCode;
        public String errorMsg;
        List<BagForCourseBooked> data;
    }

    public class BagForCourseBooked {
        public BagForCourseBooked(){}

        public String id;                      //课程ID
        public String begin_time;              //上课时间
        public String use_tool;                //使用工具
        public String mid;                     //”1“代表hasTextbook
        public boolean hasTextbook;
        public teacher teacher;
    }
    public class teacher {
        public String tid;                     //老师ID
        public String nickname;                //老师名字
        public String pic;                     //老师头像
        public String qq;                      //老师qq
        public String skype;                   //老师skype
        public String zoom;                    //老师zoom
    }



    public interface OnLoadUserCourseBookedListener{
        void onLoaded();
        void onFailure();
    }


    public UserCourseBookedManager(Context context){
        this.context = context;
        preferences = context.getSharedPreferences("CourseBooked", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void loadUserCourseBooked(String user_id, final OnLoadUserCourseBookedListener listener){

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        RequestParams params = new RequestParams();
        params.put("sid",user_id);
        client.post(context,context.getString(R.string.tool_api_link)+"getBookedClass",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json =  new String(responseBody);
                //Log.e("getBookedClass",json);
//                List<BagForCourseBooked> bagForCourseBookeds = gson.fromJson(json,new TypeToken<List<BagForCourseBooked>>(){}.getType());
//                Log.e("json",bagForCourseBookeds.get(0).teacher.tid+"");
                SharedPreferences.Editor edit = preferences.edit();
                edit.clear();
                edit.putString(TAG_UserCourseBooked,new String(responseBody));
                edit.apply();
//                List<BagForCourseBooked> bagForCourseBookeds = gson.fromJson(preferences.getString(TAG_UserCourseBooked,"null"),new TypeToken<List<BagForCourseBooked>>(){}.getType());
//                Log.e("json",bagForCourseBookeds.get(0).teacher.tid+"");
                //Log.e("getBookedClassFailure","onLoaded");
                listener.onLoaded();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Log.e("getBookedClassFailure","onFailure");
                listener.onFailure();
            }
        });

    }

    public List<BagForCourseBooked> getBagForCourseBookeds(){
//        if(preferences.getString(TAG_UserCourseBooked,"null").equals("null")){
//            return new ArrayList<BagForCourseBooked>();
//        }
        String json = preferences.getString(TAG_UserCourseBooked,null);
        if (json == null){return null;}

        List<BagForCourseBooked> mData ;
        try {
            mData = gson.fromJson(json,Result.class).data;
        }catch (Exception e){
            return null;
        }
        return mData;
    }

    public void clear(){
        preferences.edit().clear().apply();
    }
}
