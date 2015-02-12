package com.abc360.tool.userdeta.APIs;

import android.content.Context;
import android.util.Log;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.AsyncHttpClientUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roya on 14/11/13.
 */
public class TextbookIndex  {

    Context context;
    Gson gson;

    public class ResultForCName{
        public String errorCode;
        public String errorMsg;
        public List<CName> data;
    }

    public class ResultForTName{
        public String errorCode;
        public String errorMsg;
        public List<TName> data;
    }

    public class ResultForMName{
        public String errorCode;
        public String errorMsg;
        public List<MName> data;
    }

    public class CName{
        public String cname;
        public String cid;
    }

    public class TName{
        public String tname;
        public String tid;
    }

    public class MName{
        public String mname;
        public String mid;
    }

    public TextbookIndex(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public interface OnLoadedCNamesListner{
        void onLoaded(List<CName> cNameses);
        void onFailure();
    }
    public interface OnLoadedTNamesListner{
        void onLoaded(List<TName> tNameses);
        void onFailure();
    }
    public interface OnLoadedMNamesListner{
        void onLoaded(List<MName> mNameses);
        void onFailure();
    }

    public void loadCNames(final OnLoadedCNamesListner listner){

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context.getString(R.string.tool_api_link) + "getCourses", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("json",json);
                ResultForCName cNames = gson.fromJson(json, ResultForCName.class);
                if(cNames.errorCode.equals("0")) {
                    listner.onLoaded(cNames.data);
                }else{
                    ArrayList<CName> nullDatas = new ArrayList<CName>();
                    CName nullData = new CName();
                    nullData.cid = "0";
                    nullData.cname = "全本";
                    nullDatas.add(nullData);
                    listner.onLoaded(nullDatas);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    public void loadTNames(String cid ,final OnLoadedTNamesListner listner){

        RequestParams params = new RequestParams();
        params.put("cid",cid);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context , context.getString(R.string.tool_api_link) + "getSmallType",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("time",json);
                ResultForTName tNames = gson.fromJson(json, ResultForTName.class);
                if (tNames.errorCode.equals("0")) {
                    listner.onLoaded(tNames.data);
                }else {
                    ArrayList<TName> nullDatas = new ArrayList<TName>();
                    TName nullData = new TName();
                    nullData.tid = "0";
                    nullData.tname = "全本";
                    nullDatas.add(nullData);
                    listner.onLoaded(nullDatas);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void loadMNames(String tid ,final OnLoadedMNamesListner listner){

        RequestParams params = new RequestParams();
        params.put("tid",tid);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context , context.getString(R.string.tool_api_link) + "getMaterials",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                //Log.e("time",json);
                ResultForMName mNames = gson.fromJson(json, ResultForMName.class);
                if (mNames.errorCode.equals("0")) {
                    listner.onLoaded(mNames.data);
                }else {
                    ArrayList<MName> nullDatas = new ArrayList<MName>();
                    MName nullData = new MName();
                    nullData.mid = "0";
                    nullData.mname = "全本";
                    nullDatas.add(nullData);
                    listner.onLoaded(nullDatas);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

}
