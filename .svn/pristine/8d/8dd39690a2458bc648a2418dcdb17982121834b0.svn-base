package com.abc360.tool.widgets;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Log;

public class CircleBitmap {

    public Bitmap getCircleBitmap(Bitmap bitmap,float circleRadius) {

        Bitmap bitmapSquare = getSquareBitmap(bitmap);
        Bitmap output = Bitmap.createBitmap(bitmapSquare.getWidth(),
                bitmapSquare.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint  = new Paint();
        final int color = 0xff424242;

        final Rect rect = new Rect(0, 0, bitmapSquare.getWidth(), bitmapSquare.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int x = bitmapSquare.getHeight();

        canvas.drawCircle(x / 2, x / 2, x / circleRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapSquare, rect, rect, paint);
        return output;
    }
    public Bitmap getSquareBitmap(Bitmap bitmap){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        if (w >= h){
            return Bitmap.createBitmap(bitmap,w-h,0,w-(w-h),h);
        }else {
            return Bitmap.createBitmap(bitmap,0,0,w,w);
        }

    }
    public Bitmap getSquareBitmap(Bitmap bitmap, int CropType){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int k;
        if (w >= h){
            k = w-h;
            return Bitmap.createBitmap(bitmap,k/2,0,w-k,h);
        }else {
            k = h-w;
            return Bitmap.createBitmap(bitmap,0,k/2,w,h-k);
        }

    }


}
