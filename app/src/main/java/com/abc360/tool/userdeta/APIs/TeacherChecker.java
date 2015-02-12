package com.abc360.tool.userdeta.APIs;

import android.content.Context;
import android.util.Log;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.abc360.tool.userdeta.UserProfileManger;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by roya on 14/11/14.
 */
public class TeacherChecker {

    Context context;
    Gson gson;

    public class ResultFotTeacherDetail{
        public String errorCode;
        public String errorMsg;
        public Teacher teacher;
    }

    public class Teacher{
        public String id;
        public String nickname;
        public String fav;
        public String voice;
        public boolean myfav;
        public String pic;
        public String chinese;
        public String catalog;
        public List<String> courses;
        public List<Marks> marks;
    }

    public class Marks{
        public String id;
        public String label;
    }

    public interface OnLoadedListner{
        void onSuccess(Teacher teacher);
        void onFailure(int errorCode);
    }

    public TeacherChecker(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public void getTeacherDetail(String teacherID, final OnLoadedListner listner){
        RequestParams params = new RequestParams();
        params.put("tid",teacherID);
        params.put("sid",new UserProfileManger(context).getId());
//        Log.e("json",teacherID);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"getTeacherDetail",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);

                //Log.e("json",json);
                ResultFotTeacherDetail resultFotTeacherDetail = gson.fromJson(json,ResultFotTeacherDetail.class);
                //Log.e("json",resultFotTeacherDetail.teacher.nickname);
                //Log.e("json",resultFotTeacherDetail.teacher.marks.get(0).label);

                if (resultFotTeacherDetail.errorCode.equals("0")){
                    listner.onSuccess(resultFotTeacherDetail.teacher);
                }else {
                    listner.onFailure(1);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listner.onFailure(0);
            }
        });


    }



}
