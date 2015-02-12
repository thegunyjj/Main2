package com.abc360.tool.userdeta;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.abc360.tool.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by roya on 14/11/12.
 */
public class SearchResultManager {

    Context context;
    Gson gson;

    public class Result{
        public String errorCode;
        public String errorMsg;
        public List<ResultData> data;
    }
    public class ResultData{
        public String id;
        public String begin_time;
        public String acoin;
        public String acoin_free;
        public Teacher teacher;
    }
    public class Teacher {
        public String id;                     //老师ID
        public String nickname;                //老师名字
        public String pic;                     //老师头像
        public int fav;                      //被收藏次数
        public boolean myfav;                   //是否收藏
    }

    public interface loadedSearchResultListner{
        public void onLoaded(Result result);
        public void onFailure(int errorCode);
    }

    public SearchResultManager(Context context){
        this.context = context;
        gson = new Gson();
    }

    public void loadSearchResult(ParamsForSearch paramsForSearch, final loadedSearchResultListner listner){
        RequestParams params = new RequestParams();
        params.put("sid",new UserProfileManger(context).getId());
        params.put("date",paramsForSearch.date);
        params.put("timeHH",paramsForSearch.timeHH);
        params.put("timeMM",paramsForSearch.timeMM);
        params.put("option",paramsForSearch.option);
        params.put("catalog",paramsForSearch.catalog);
        //Log.e("getSearchResult,option",paramsForSearch.option.toString());
        //Log.e("getSearchResult,params",params.toString());
        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"getSearchResult",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("getSearchResult,onSuccess",json);
                Result result;
                try {
                    result = gson.fromJson(json,Result.class);
                    if(result.errorCode.equals("0")) {
                        listner.onLoaded(result);
                    }else {
                        listner.onFailure(1);
                    }
                }catch (Exception e){
                    listner.onFailure(0);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listner.onFailure(0);
            }
        });
    }





}
