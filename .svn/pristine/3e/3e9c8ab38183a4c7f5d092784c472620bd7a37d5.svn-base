package com.abc360.tool.userdeta;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.abc360.tool.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class UserCourseIDManager {

    SharedPreferences preferences;
    Gson gson;
    Context context;

    public class BagforId{
        public String id;
    }

    public interface onIdLoadListener {
        void onIdLoaded();
        void onIdLoadFailure();
    }


    public UserCourseIDManager(Context context){
        preferences = context.getSharedPreferences("CourseID", Context.MODE_PRIVATE);
        gson = new Gson();
        this.context = context;
    }


    public List<BagforId> getPredestinateId(){
        String predestinateIdmJson = preferences.getString("predestinateId", null);
        //Log.e("predestinateIdmJson",predestinateIdmJson);
        if (predestinateIdmJson == null){
            return null;
        }else {
            try {
                return gson.fromJson(predestinateIdmJson, new TypeToken<List<BagforId>>() {
                }.getType());
            } catch (Exception e) {
                return null;
            }
        }

    }

    public void loadPredestinateId(final onIdLoadListener listener){

        RequestParams params = new RequestParams();
        params.put("username",new UserIDManager(context).getUserName());

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"getPredestinateId",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                savePredestinateId(new String(responseBody));
                Log.e("载入成功",new String(responseBody));
                listener.onIdLoaded();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("载入失败","");
                listener.onIdLoadFailure();
            }
        });
    }

    private void savePredestinateId(List<String> predestinateId){

        SharedPreferences.Editor editor = preferences.edit();

        String predestinateIdJson = gson.toJson(predestinateId);
        editor.putString("predestinateId",predestinateIdJson);
        editor.apply();

    }

    private void savePredestinateId(String predestinateIdJson){

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("predestinateId",predestinateIdJson);
        editor.apply();

    }



    public List<String> getHistoryId(){

        String historyIdJson = preferences.getString("historyId", null);
        //Log.e("historyIdJson",historyIdJson);
        if (historyIdJson == null){
            return null;
        }else {
            try {
                return gson.fromJson(historyIdJson, new TypeToken<List<String>>(){}.getType());
            }catch (Exception e){
                return null;
            }
        }

    }

    public void loadHistoryId(final onIdLoadListener listener){

        RequestParams params = new RequestParams();
        params.put("username",new UserIDManager(context).getUserName());

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(context,context.getString(R.string.tool_api_link)+"getHistoryId",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                saveHistoryId(new String(responseBody));
                //Log.e("载入成功",new String(responseBody));
                listener.onIdLoaded();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Log.e("载入失败","");
                listener.onIdLoadFailure();
            }
        });
    }

    private void saveHistoryId(List<String> historyId){

        SharedPreferences.Editor editor = preferences.edit();

        String historyIdJson = gson.toJson(historyId);
        editor.putString("historyId",historyIdJson);
        editor.apply();

    }

    private void saveHistoryId(String historyIdJson){

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("historyId",historyIdJson);
        editor.apply();

    }

}
