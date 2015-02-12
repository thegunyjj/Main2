package com.abc360.tool.userdeta.APIs;

import android.content.Context;
import android.util.Log;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.abc360.tool.userdeta.SearchResultManager;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by roya on 14/11/18.
 */
public class TeacherClassGeter {
    Context context;
    Gson gson;

    public interface OnloadenListner{
        void onSuccess(List<Courses> course);
        void onFailure(int errorCode);
    }

    public class Result{
        public String errorCode;
        public String errorMsg;
        public Teacher teacher;
        public List<Courses> course;
    }
    public class Teacher{
        public String nickname;
        public String pic;
        public String catalog;
    }
    public class Courses{
        public String id;
        public String begin_time;
    }

    public TeacherClassGeter(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public void loadTeacherClass(String teacherId, int days, final OnloadenListner listner){
        RequestParams params = new RequestParams();
        params.put("tid",teacherId);

        SimpleDateFormat format =new SimpleDateFormat("yyyyMMdd");
        long todayUnixTime = System.currentTimeMillis() ;
        String dateYYYYMMDD = format.format(todayUnixTime + days * 3600 *24 *1000);

        params.put("date",Long.valueOf(dateYYYYMMDD));
        //Log.e("params",params.toString());
        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"getTeacherClass",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
        //        Log.e("params",json);
                try {
                    Result result = gson.fromJson(json, Result.class);
                    if (result.errorCode.equals("0")){
                        listner.onSuccess(result.course);
                    }else if (result.errorCode.equals("2")) {
                        listner.onFailure(2);
                    }else if (result.errorCode.equals("3")){
                        listner.onFailure(3);
                    }
                }catch (Exception e){
                    listner.onFailure(-1);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listner.onFailure(-1);
            }
        });

    }


}
