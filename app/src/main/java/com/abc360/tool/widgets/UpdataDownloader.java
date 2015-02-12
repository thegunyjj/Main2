package com.abc360.tool.widgets;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by roya on 14/12/9.
 */
public class UpdataDownloader {

    public UpdataDownloader(Context context, String dowloadPath){

        final DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        final Uri uri = Uri.parse(dowloadPath);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        // 设置下载路径和文件名
        request.setDestinationInExternalPublicDir("download", "abc360-tool-updata.apk");
        request.setDescription("abc360");
        request.setMimeType("application/vnd.android.package-archive");
        // 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        // 获取此次下载的ID
        final long refernece = dManager.enqueue(request);
        // 注册广播接收器，当下载完成时自动安装
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        final BroadcastReceiver receiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (refernece == myDwonloadID) {
                    context.unregisterReceiver(this);
                    try {
                        File fileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        File file = new File(fileDir,"abc360-tool-updata.apk");

                        Intent intent1= new Intent(Intent.ACTION_VIEW);
                        intent1.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
        context.registerReceiver(receiver, filter);
    }

}
