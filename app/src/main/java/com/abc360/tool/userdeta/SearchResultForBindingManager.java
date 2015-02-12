package com.abc360.tool.userdeta;

import android.content.Context;
import android.util.Log;

import com.abc360.tool.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by roya on 14/11/12.
 */
public class SearchResultForBindingManager {

    Context context;
    Gson gson;

    public class Result{
        public String errorCode;
        public String errorMsg;
        public List<ResultData> data;
    }
    public class ResultData{
        public String id;
        public String tid;
        public String tname;
        public String weekday;
        public String just_time;
        public String pic;
        public String catalog;
        public String fav;
        public float good;
    }



    public interface LoadedListener{
        public void onLoaded(List<ResultData> data);
        public void onFailure(int errorCode, String errorMsg);
    }

    public SearchResultForBindingManager(Context context){
        this.context = context;
        gson = new Gson();
    }

    public void loadSearchResult(int page, String first_letter, String begin_time, int catalog, final LoadedListener listener){
        RequestParams params = new RequestParams();
        params.put("p",page);
        params.put("first_letter",first_letter);
        params.put("catalog",catalog);
        params.put("begin_time",begin_time);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"getBindTeachers",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("getSearchResult,onSuccess", json);
                Result result;
                try {
                    result = gson.fromJson(json,Result.class);
                    if(result.errorCode.equals("0")) {
                        listener.onLoaded(result.data);
                    }else {
                        listener.onFailure(0 , result.errorMsg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    listener.onFailure(0 , "网络连接失败，请稍后再试");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(0 , "网络连接失败，请稍后再试");
            }
        });
    }





}
