package com.abc360.tool.userdeta;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import org.apache.http.client.CookieStore;

/**
 * Created by roya on 14/12/11.
 */
public class AsyncHttpClientUtils {

    private static AsyncHttpClient asyncHttpClient;
    private static PersistentCookieStore cookieStore;

    public static synchronized AsyncHttpClient getCasyncHttpClient(Context context){
        if (asyncHttpClient == null){
            asyncHttpClient = new AsyncHttpClient();
        }
        asyncHttpClient.setCookieStore(getCookieStore(context));
        return asyncHttpClient;
    }

    public static synchronized PersistentCookieStore getCookieStore(Context context){
        if (cookieStore == null){
            cookieStore = new PersistentCookieStore(context);
        }
        return cookieStore;
    }

}
