package com.abc360.tool.userdeta.APIs;

import android.content.Context;
import android.util.Log;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * Created by roya on 14/11/28.
 */
public class MemoReviewer {

    Context context;
    Gson gson;

    public interface OnfinishedListener{
        public void onSuccess(Data data);
        public void onFailure(int errorCode, String msg);
    }

    class Result {
        String errorCode;
        String errorMsg;
        Data data;
    }

    public class Data{
        public String mname;
        public String cname;
        public String tname;
        public String memo_words;
        public String memo_phrases;
        public String memo_advice;
        public String memo_voice;
    }

    public MemoReviewer(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public void getReviewMemo(String sid, String cid, final OnfinishedListener listener){
        RequestParams params = new RequestParams();
        params.put("sid", sid);
        params.put("cid", cid);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context, context.getString(R.string.tool_api_link)+"reviewMemo",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("json",json);
                try {
                    Result result = gson.fromJson(json, Result.class);
                    if (result.errorCode.equals("0")){
                        listener.onSuccess(result.data);
                    }else {
                        listener.onFailure(1,result.errorMsg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    listener.onFailure(0,"网络连接失败,请稍后再试");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(0,"网络连接失败,请稍后再试");
            }
        });
    }



}
