package com.abc360.tool.userdeta.APIs;

import android.content.Context;
import android.util.Log;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by roya on 14/12/19.
 */
public class TeacherBindDetail {

    Context context;
    Gson gson;

    class Result{
        String errorCode;
        String errorMsg;
        Data data;
    }

    public class Data{
        public String tid;
        public String catalog;
        public String tname;
        public String tpic;
        public String weekday;
        public String fav;
        public float good;
        public String acoin;
        public List<LSN> lsn;
    }

    public class LSN{
        public String id;
        public String just_time;
    }



    public interface FinishedListener{
        public void onSuccess(Data data);
        public void onFaiure(int errorCode, String errorMsg);
    }

    public TeacherBindDetail(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public void load(String teacherId, String time, final FinishedListener listener){
        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);
        RequestParams params = new RequestParams();
        params.put("tid",teacherId);
        params.put("begin_time",time);

        client.post(context, context.getString(R.string.tool_api_link)+"teacherBindDetail", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("json", json);
                try {
                    Result result = gson.fromJson(json,Result.class);
                    if (result.errorCode.equals("0")){
                        listener.onSuccess(result.data);
                    }else {
                        listener.onFaiure(1,result.errorMsg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    listener.onFaiure(0,"网络连接失败，请稍后再试");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFaiure(0,"网络连接失败，请稍后再试");
            }
        });
    }


}
