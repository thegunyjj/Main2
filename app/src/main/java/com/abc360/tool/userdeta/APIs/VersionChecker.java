package com.abc360.tool.userdeta.APIs;

import android.content.Context;
import android.util.Log;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.abc360.tool.userdeta.BookableClassManager;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * Created by roya on 14/11/24.
 */
public class VersionChecker {

    Context context;
    Gson gson;

    class Resule{
        String errorCode;
        String errorMsg;
        Data data;
    }

    class Data{
        int version;
        String versionName;
    }

    public interface OnFinishedListener {
        void onSuccess(int versionCode, String versionName, String link);
        void onFailure(int error);
    }

    public VersionChecker(Context context){
        this.context = context;
        gson = new Gson();
    }

    public void check(final OnFinishedListener listener){
        RequestParams params = new RequestParams();

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"appUpdate",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("json",json);
                try {
                    Resule resule = gson.fromJson(json,Resule.class);
                    if (resule.errorCode.equals("0")){
                        listener.onSuccess(resule.data.version, resule.data.versionName, "http://www.abc360.com/"+"Uploads/App/abc360.latest.apk");
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
