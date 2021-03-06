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

public class UserCoursesDetailManager {

    public class CourseDetailsData {
        public String id;                      //课程ID`
        public String begin_time;                    //时间`
        public String use_tool;                //上课工具`
        public int choice;                  //学生评价`
        public String comment;         //评价文本`

        public boolean hasTextbook;            //是否有教材
        public String videoName;
        public String cname;         //教材类型
        public String tname;            //教材名
        public String mname;         //教材章节
        public String cid;
        public String tid;
        public String mid;

        public String free_try;    // "0" = 否

        public String pdf;
        public String use_free;      //是否使用自由课时，”1“为true
        public String pay;            //花费A币


        public String nickname;             //老师名`
        public String pic;           //老师头像链接`
        public String skype;          //老师SKYPE`
        public String qq;             //老师QQ`
    }

    public interface OnDetailsDataLoadedListener{
        void onDetailsDataLoaded();
        void onDetailsDataFailure();
    }

    Context context;
    SharedPreferences preferences;
    Gson gson;

    public UserCoursesDetailManager(Context context){
        this.context = context;
        gson = new Gson();
        this.preferences = context.getSharedPreferences("CoursesDetail", Context.MODE_PRIVATE);
    }

    public CourseDetailsData getCourseDetail(String Id){

        String dataJson = preferences.getString(Id, null);
        //Log.e("dataJson",dataJson);
       if(dataJson != null){
            return gson.fromJson(dataJson,CourseDetailsData.class);

        }else {
            return null;

        }
    }

    public void saveCourseDetail(String Id, String Json){
        SharedPreferences.Editor editer = preferences.edit();
        editer.putString(Id,Json);
        editer.apply();
    }

    public void loadCourseDetail(final String Id, final OnDetailsDataLoadedListener listener){
        RequestParams params = new RequestParams();
        params.put("sid",new UserProfileManger(context).getId());
        params.put("cid",Id);

        AsyncHttpClient client = AsyncHttpClientUtils.getCasyncHttpClient(context);

        client.post(context,context.getString(R.string.tool_api_link)+"getClassDetail",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                saveCourseDetail(Id, new String(responseBody));
                //Log.e("载入成功", new String(responseBody));
                listener.onDetailsDataLoaded();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
              //  Log.e("载入失败","");
                listener.onDetailsDataFailure();
            }
        });
    }
}
