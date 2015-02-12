package com.abc360.tool.userdeta;

import android.content.Context;
import android.util.Log;

import com.abc360.tool.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roya on 14/11/12.
 */
public class BookableClassManager {

    Context context;
    Gson gson;


    public class Result{
        public String errorCode;
        public String errorMsg;
        public Teacher teacher;
        public List<Data> data;
    }

    public class Teacher{
        public String nickname;
        public String tid;
        public String pic;
    }
    public class Data{
        public String id;
        public String begin_time;
    }

    public interface onLoadedListner{
        public void onLoaded(Result result);
        public void onFailure();
    }

    public BookableClassManager(Context context){
        this.context = context;
        gson = new Gson();
    }

    public void loadBookableClasses(String id, final onLoadedListner listner){
        RequestParams params = new RequestParams();
        params.put("cid",id);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"bookClass",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("bookClass",json);
                Result result = gson.fromJson(json , Result.class);
                if (result.errorCode.equals("0")){
                    listner.onLoaded(result);
                }else {
                    listner.onLoaded(null);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listner.onFailure();
            }
        });

    }


}
