package com.abc360.tool.userdeta;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by roya on 14/12/9.
 */
public class AppFirstStartManager {

    SharedPreferences preference;
    SharedPreferences.Editor preferenceEdit;

    public AppFirstStartManager(Context context){
        preference = context.getSharedPreferences("AppFristStartManager",Context.MODE_PRIVATE);
        preferenceEdit = preference.edit();
    }

    public boolean isFristStartState(boolean def){
        return preference.getBoolean("isFristStartState", def);
    }

    public void setIsFristStartState(boolean def){
        preferenceEdit.putBoolean("isFristStartState",def);
        preferenceEdit.apply();
    }

}
