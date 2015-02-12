package com.abc360.tool.Receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.abc360.tool.Activity.MainActivity;
import com.abc360.tool.R;
import com.igexin.sdk.PushConsts;

/**
 * Created by roya on 14/12/5.
 */
public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        //Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:

                byte[] payload = bundle.getByteArray("payload");

                if (payload != null)
                {
                    String data = new String(payload);
                    //Log.d("GetuiSdkDemo", "Got Payload:" + data);

                    NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                    mBuilder.setContentTitle("ABC360上课提醒")
                            .setTicker(data)
                            .setContentText(data)
                            .setWhen(System.currentTimeMillis())
                            .setAutoCancel(true)
                            .setPriority(1)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentIntent(
                                    PendingIntent.getActivity(context,
                                            0,
                                            new Intent(context, MainActivity.class),
                                            0));


                    mNotificationManager.notify(0,mBuilder.build());
                }
                break;
            //添加其他case
            //.........
            default:
                break;
        }
    }
}