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
 * Created by roya on 14/11/19.
 */
public class ProfileChanger {

    Context context;
    Gson gson;

    public class Result{
        String errorCode;
        String errorMsg;

    }

    public interface OnFinishedListener{
        void onSuccess();
        void onFailure(int errorCode);
    }

    public ProfileChanger(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public void changeProfile(String sId, String sMd5,
                              String newName,
                              String newMail,
                              String newSkpyeID ,
                              String newQQId,
                              final OnFinishedListener listener)
    {
        RequestParams params = new RequestParams();
        params.put("sid",sId);
        params.put("password",sMd5);

        if (newName     != null){ params.put("nickname",newName);   }
        if (newMail     != null){ params.put("email",newMail);      }
        if (newSkpyeID  != null){ params.put("skype",newSkpyeID);   }
        if (newQQId     != null){ params.put("qq",newQQId);         }

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"setProfile",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                Result result = gson.fromJson(json,Result.class);
                if (result.errorCode.equals("0")){
                    listener.onSuccess();
                }else {
                    listener.onFailure(0);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(-1);
            }
        });
    }

}
