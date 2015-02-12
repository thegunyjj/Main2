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
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.protocol.HttpContext;

public class UserLoginManager {
    private Context context;
    protected LoginListener listener;

    public static int ERROR_ID = 0;
    public static int ERROR_NETWORK = 1;

    private class BagOfError {
        String errorCode ;
    }

    public interface LoginListener{
        void onSuccess(byte[] response);
        void onFailure(int ERROR_TYPE);
    }

    public void setLoginListener(LoginListener listener){
        this.listener = listener;
    }



    public UserLoginManager(Context context){
        this.context = context;

    }

    public void login(RequestParams idParams){
        final AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        idParams.put("source",2);
        client.post(context,context.getString(R.string.tool_api_link)+"login",idParams,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(responseBody != null){
                    String body = new String(responseBody);
                    //Log.e("json",body);
                    //PersistentCookieStore a = AsyncHttpClientUtils.getCookieStore(context);
                    //Log.e("cookie",a.getCookies().toString());
                    try {
                        BagOfError bagOfError;
                        Gson gson = new Gson();
                        bagOfError = gson.fromJson(body, BagOfError.class);
                        //System.out.print("success");
                        if (bagOfError.errorCode == null){
                            listener.onFailure(ERROR_NETWORK);
                        }
                        else if (bagOfError.errorCode.equals("0")) {
                            listener.onSuccess(responseBody);
                        } else {
                            listener.onFailure(ERROR_ID);
                        }
                    }catch (Exception ignored){
                        listener.onFailure(ERROR_NETWORK);
                        //System.out.print("error");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(ERROR_NETWORK);
            }

        });

    }
}
