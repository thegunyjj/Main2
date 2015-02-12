package com.abc360.tool.widgets.LazyImageLoader;

import android.content.Context;

/**
 * Created by roya on 14/12/11.
 */
public class ImageLoaderUtils {

    private static ImageLoader imageLoader;

    public synchronized static ImageLoader getImageLoader(Context context){
        if (imageLoader == null){
            imageLoader = new ImageLoader(context);
        }
        return imageLoader;

    }

}
