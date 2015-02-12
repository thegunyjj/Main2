package com.abc360.tool.userdeta.APIs;

import android.content.Context;
import android.util.Log;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.abc360.tool.userdeta.BookableClassManager;
import com.abc360.tool.userdeta.SearchResultManager;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by roya on 14/11/17.
 */
public class FavoriteSaver {

    Context context;
    Gson gson;

    public interface OnFinishedListner{
        void onSuccess();
        void onFailure();
    }

    class Result{
        String errorCode;
        String errorMsg;
    }


    public FavoriteSaver(Context context){
        this.context = context;
        gson = new Gson();
    }

    public void saveFavorite(String tid, String sid, final OnFinishedListner listner){
        RequestParams params = new RequestParams();
        params.put("sid",sid);
        params.put("tid",tid);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"saveMyFavTeacher",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                Result result = gson.fromJson(json, Result.class);

                if (result.errorCode.equals("0")){
                    listner.onSuccess();
                }else {
                    listner.onFailure();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listner.onFailure();
            }
        });
    }

    public void delFavorite(String sid, List<String> tids, final OnFinishedListner listner){
        RequestParams params = new RequestParams();
        params.put("sid",sid);
        params.put("tid",tids);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"delMyFavTeacher",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("json",json);
                Result result = gson.fromJson(json, Result.class);

                if (result.errorCode.equals("0")){
                    listner.onSuccess();
                }else {
                    listner.onFailure();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listner.onFailure();
            }
        });
    }

}
