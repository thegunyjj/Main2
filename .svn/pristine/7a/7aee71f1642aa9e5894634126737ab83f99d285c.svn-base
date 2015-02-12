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

/**
 * Created by roya on 14/11/12.
 */
public class SaveBookClassManager {

    Context context;
    Gson gson;

    public class Error{
        String errorCode;
        String errorMsg;
    }


    public interface onSavedListner {
        public void onSuccess();
        public void onFailure(int errorCode, String Msg);
    }

    public SaveBookClassManager(Context context){
        this.context = context;
        gson = new Gson();
    }

    public void saveBookClass(RequestParams params, final onSavedListner listner){

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"saveBookClass",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("bookClass", json);
                try {
                    Error error = gson.fromJson(json,Error.class);
                    if(error.errorCode.equals("0")){
                        listner.onSuccess();
                    }else {
                        listner.onFailure(1,error.errorMsg);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    listner.onFailure(1,"未知错误");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listner.onFailure(0,"网络连接失败");
            }
        });

    }


}
