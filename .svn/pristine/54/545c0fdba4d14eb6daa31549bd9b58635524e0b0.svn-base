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
 * Created by roya on 14/12/19.
 */
public class LeaveRecords {

    Context context;
    Gson gson;

    public class Result{
        public String errorCode;
        public String errorMsg;
        public LeaveTime leave_time;
        public List<Data> data;
    }

    public class Data{
        public String id;
        public String catalog;
        public String begin_time;
        public String end_time;
        public int cancel_able;
    }

    public class LeaveTime{
        public int ph;
        public int eu;
    }

    public interface LoadedListener{
        public void onLoaded(Result result);
        public void onFailure(int errorCode, String errorMsg);
    }

    public LeaveRecords(Context context){
        this.context = context;
        gson = new Gson();
    }

    public void getLeaveRecords(final LoadedListener listener){
        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);
        client.post(context,context.getString(R.string.tool_api_link)+"getLeaveRecords",null,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("json",json);
                try {
                    Result result = gson.fromJson(json,Result.class);
                    if (result.errorCode.equals("0")) {
                        listener.onLoaded(result);
                    }else {
                        listener.onFailure(0,result.errorMsg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    listener.onFailure(0,"网络连接失败，请稍后再试");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(0,"网络连接失败，请稍后再试");
            }
        });
    }
}
