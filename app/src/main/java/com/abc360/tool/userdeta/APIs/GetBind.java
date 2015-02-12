package com.abc360.tool.userdeta.APIs;

import android.content.Context;
import android.util.Log;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by roya on 15/1/3.
 */
public class GetBind {

    Context context;
    Gson gson;

    class Result{
        String errorCode;
        String errorMsg;
        List<Data> data;
    }

    public class Data{
        public String tid;
        public String catalog;
        public String tname;
        public String tpic;
        public String weekday;
        public List<LSN> lsn;
        public String fav;
    }

    public class LSN{
        public String id;
        public String just_time;
        public String begin_time;
        public String end_time;
    }

    public interface FinishedListener{
        public void onSuccess(List<Data> data);
        public void onFaiure(int errorCode, String errorMsg);
    }

    public GetBind(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public void loadBindInfo(final FinishedListener listener){
        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);
        client.post(context, context.getString(R.string.tool_api_link)+"myBindTeachers", null ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                Log.e(getClass().getName(),json);

                try {
                    Result result = gson.fromJson(json,Result.class);
                    listener.onSuccess(result.data);
                }catch (Exception e){
                    e.printStackTrace();
                    listener.onFaiure(0,"网络连接失败");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFaiure(0,"网络连接失败");
            }
        });

    }


}
