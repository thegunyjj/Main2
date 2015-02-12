package com.abc360.tool.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.abc360.tool.R;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roya on 14/12/9.
 */
public class FirstStartActivity extends Activity {

    ViewPager mViewPager;
    List<Bitmap> images;
    List<ImageView> imageViews;
    LinearLayout linearLayoutPageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first_page);


        //final Animation tm = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fda);

        linearLayoutPageIndicator = (LinearLayout)findViewById(R.id.pageIndicator);

        images = new ArrayList<Bitmap>();
        imageViews = new ArrayList<ImageView>();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;


        Bitmap page1 = BitmapFactory.decodeResource(getResources(),R.drawable.page1,options);
        Bitmap page2 = BitmapFactory.decodeResource(getResources(),R.drawable.page2,options);
        Bitmap page3 = BitmapFactory.decodeResource(getResources(),R.drawable.page3,options);
        Bitmap page4 = BitmapFactory.decodeResource(getResources(),R.drawable.page4,options);

        final ImageView imageView1 = newPoint();
        final ImageView imageView2 = newPoint();
        final ImageView imageView3 = newPoint();
        final ImageView imageView4 = newPoint();

        linearLayoutPageIndicator.addView(imageView1);
        linearLayoutPageIndicator.addView(imageView2);
        linearLayoutPageIndicator.addView(imageView3);
        linearLayoutPageIndicator.addView(imageView4);
        imageView1.setImageResource(R.drawable.point_white_big);

        images.add(page1);
        images.add(page2);
        images.add(page3);
        images.add(page4);

        mViewPager = (ViewPager)findViewById(R.id.viewPager);
        mViewPager.setAdapter(new mViewPager(images));

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                imageView1.setImageResource(R.drawable.point_white);
                imageView2.setImageResource(R.drawable.point_white);
                imageView3.setImageResource(R.drawable.point_white);
                imageView4.setImageResource(R.drawable.point_white);
                switch (i){
                    case 0: imageView1.setImageResource(R.drawable.point_white_big); break;
                    case 1: imageView2.setImageResource(R.drawable.point_white_big); break;
                    case 2: imageView3.setImageResource(R.drawable.point_white_big); break;
                    case 3: imageView4.setImageResource(R.drawable.point_white_big); break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

    }


    private ImageView newPoint(){
        ImageView imageView1 = new ImageView(getApplicationContext());
        imageView1.setImageResource(R.drawable.point_white);
        return imageView1;
    }

    private class mViewPager extends PagerAdapter{

        List<Bitmap> images;
        LayoutInflater layoutInflater;

        public mViewPager(List<Bitmap> images){
            layoutInflater = getLayoutInflater();
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewGroup childView = (ViewGroup)layoutInflater.inflate(R.layout.pager_page, container, false);
            ImageView imageView = (ImageView)childView.findViewById(R.id.imageView);
            imageView.setImageBitmap(images.get(position));

            if (position == images.size()-1) {
                ImageButton imageButton = (ImageButton) childView.findViewById(R.id.imageButton);
                imageButton.setVisibility(View.VISIBLE);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                        finish();
                    }
                });
            }

            childView.setId(position);
            container.addView(childView);
            return childView;
        }
    }


}
