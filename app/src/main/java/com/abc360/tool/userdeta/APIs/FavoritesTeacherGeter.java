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

import java.util.List;

/**
 * Created by roya on 14/11/17.
 */

public class FavoritesTeacherGeter {

    public class Result{
        public String errorCode;
        public String errorMsg;
        public List<Teacher> teacher;
    }

    public class Teacher{
        public String tid;
        public String pic;
        public String nickname;
        public String catalog;
    }

    Context context;
    Gson gson;


    public FavoritesTeacherGeter(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public interface OnloadedListner{
        public void onFinished(List<Teacher> teachers);
        public void onFailure(int errorCode);
    }

    public void getFavoriteTeachers(String sid, final OnloadedListner listner){

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        RequestParams params = new RequestParams();
        params.put("sid",sid);

        client.post(context,context.getString(R.string.tool_api_link)+"getMyFavTeacher",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                try {
                    Result result = gson.fromJson(json,Result.class);
                    if(result.errorCode.equals("0")) {
                        listner.onFinished(result.teacher);
                    }else{
                        listner.onFailure(1);
                    }
                }catch (Exception e){
                    listner.onFailure(0);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listner.onFailure(0);
            }
        });
    }


}
