package com.abc360.tool.widgets.LazyImageLoader;

import java.io.File;
import android.content.Context;

public class FileCache {
    
    private File cacheDir;
    
    public FileCache(Context context){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=context.getExternalCacheDir();
            //new File(android.os.Environment.getExternalStorageDirectory(),"Cache");
            if (cacheDir == null){
                cacheDir = context.getCacheDir();
            }
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }
    
    public File getFile(String url){
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename=String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        //String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;
        
    }

    public void clear(String url){
        File f = getFile(url);
        if (f == null){
            return;
        }else {
            f.delete();
        }
    }

    public void clearAll(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }

}