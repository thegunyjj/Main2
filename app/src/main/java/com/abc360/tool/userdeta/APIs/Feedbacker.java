package com.abc360.tool.userdeta.APIs;

import android.content.Context;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * Created by roya on 14/11/24.
 */
public class Feedbacker {

    Context context;
    Gson gson;

    class Resule{
        String errorCode;
        String errorMsg;
    }

    public interface OnFinishedListener {
        void onSuccess();
        void onFailure(int error);
    }

    public Feedbacker(Context context){
        this.context = context;
        gson = new Gson();
    }

    public void feedback(String sid, String comm, final OnFinishedListener listener){
        RequestParams params = new RequestParams();
        params.put("sid",sid);
        params.put("content",comm);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"saveFeedback",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                try {
                    Resule resule = gson.fromJson(json,Resule.class);
                    if (resule.errorCode.equals("0")){
                        listener.onSuccess();
                    }else {
                        listener.onFailure(1);
                    }
                }catch (Exception e){
                    listener.onFailure(0);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(0);
            }
        });

    }

}
