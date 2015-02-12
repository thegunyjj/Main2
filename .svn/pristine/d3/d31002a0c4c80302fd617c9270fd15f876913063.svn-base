package com.abc360.tool.userdeta.APIs;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by roya on 14/11/25.
 */
public class AvatarSaver {

    Context context;
    Gson gson;

    class Result{
        String errorCode;
        String errorMsg;
        String avatar;
    }

    public interface onFinishedListener{
        void onSuccess(String avater);
        void onFailure(int errorCode, String msg);
    }

    public AvatarSaver(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public void saveAvater(String sid, InputStream inputStream, final onFinishedListener listener){
        RequestParams params = new RequestParams();

        params.put("sid",sid);
        params.put("file",inputStream);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"saveAvatar",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("json",json);
                try {
                    Result result = gson.fromJson(json,Result.class);
                    //Log.e("json", result.errorCode);
                    if (result.errorCode.equals("0")){
                        listener.onSuccess(result.avatar);
                    }else {
                        listener.onFailure(1, result.errorMsg);
                    }
                }catch (Exception e){
                    listener.onFailure(0,"网络连接失败");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(0,"网络连接失败");
            }
        });

    }



}
