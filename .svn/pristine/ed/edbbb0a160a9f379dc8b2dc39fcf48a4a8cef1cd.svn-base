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
 * Created by roya on 14/11/21.
 */
public class ClassCommentSaver {

    Context context;
    Gson gson;

    class Resule{
        String errorCode;
        String errorMsg;
    }

    public interface OnfinishedListener{
        void onSuccess();
        void onFailure(int errorCode);
    }

    public ClassCommentSaver(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public void saveClassComment(String cid,int comment, final OnfinishedListener listener){
        RequestParams params = new RequestParams();
        params.put("cid",cid);
        params.put("comment",comment);
        //Log.e("saveClassComment",params.toString());

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"saveClassComment",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("saveClassComment",json);
                Resule resule = gson.fromJson(json,Resule.class);
                if (resule.errorCode.equals("0")){
                    listener.onSuccess();
                }else {
                    listener.onFailure(1);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(0);
            }
        });

    }


}
