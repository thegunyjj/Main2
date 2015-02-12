package com.abc360.tool.widgets;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by roya on 14/12/23.
 */
public class PdfDownloader {
    Context context;
    Uri uri;
    DownloadManager dManager;

    OnLoaded onLoaded;

    File file;
    File fileDir;

    public interface OnLoaded {
        void onLoaded(File file);
    }

    public PdfDownloader(Context context, String downloadPath){
        this.context = context;
        uri = Uri.parse(downloadPath);
        dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        fileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        file = new File(fileDir,getFileNameFormUri(uri));
    }

    public void loadPdf(){

        if (file.exists()){
            if (onLoaded != null)
                onLoaded.onLoaded(file);
        }else {
            getFromNet();
        }

    }

    public void setListener(OnLoaded onLoaded){
        this.onLoaded = onLoaded;
    }

    private void getFromNet(){
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setDestinationInExternalPublicDir("download", getFileNameFormUri(uri));
        request.setDescription("abc360");
        request.setMimeType("application/pdf");

        request.setVisibleInDownloadsUi(true);

        final long refernece = dManager.enqueue(request);

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        final BroadcastReceiver receiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (refernece == myDwonloadID) {

                    if (onLoaded != null)
                        onLoaded.onLoaded(file);

                    context.unregisterReceiver(this);
                }
            }
        };
        context.registerReceiver(receiver, filter);
    }

    private String getFileNameFormUri(Uri uri){
        String addr = uri.toString();
        return addr.substring(addr.lastIndexOf("/") + 1);
    }
}
