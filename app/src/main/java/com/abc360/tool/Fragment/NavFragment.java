package com.abc360.tool.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abc360.tool.Activity.AcoinLogActivity;
import com.abc360.tool.Activity.BindingActivity;
import com.abc360.tool.Activity.FavoritesActivity;
import com.abc360.tool.Activity.LeaveActivity;
import com.abc360.tool.Activity.LoginActivity;
import com.abc360.tool.Activity.ProfileActivity;
import com.abc360.tool.R;
import com.abc360.tool.userdeta.UserProfileManger;
import com.abc360.tool.widgets.CircleBitmap;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoader;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoaderUtils;


public class NavFragment extends Fragment {

    ImageView imageUserAvater;
    TextView textUserName;
    TextView textUserLevel;
    TextView textViewAcoin;

    OnItemClickListener listener;
    View view ;

    //点击item的事件
    public interface OnItemClickListener{
        void onItemClick(boolean isFinish);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.listener = onItemClickListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_nav, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle bl){
        super.onActivityCreated(bl);
        view = getView();

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        imageUserAvater = (ImageView) view.findViewById(R.id.user_avatar);
        textUserName = (TextView) view.findViewById(R.id.user_name);
        textUserLevel = (TextView)view.findViewById(R.id.user_level);
        textViewAcoin = (TextView)view.findViewById(R.id.nav_tv_acoin);

        ImageButton buttonBack = (ImageButton) view.findViewById(R.id.navigation_button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(false);
            }
        });

        LinearLayout buttonLogout = (LinearLayout) view.findViewById(R.id.button_nav_logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(true); //need close mainactivity
                Intent it = new Intent(getActivity(),LoginActivity.class);
                startActivity(it);
            }
        });

        LinearLayout buttonchange = (LinearLayout) view.findViewById(R.id.button_nav_favorits);
        buttonchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(false);
                Intent it = new Intent(getActivity(),FavoritesActivity.class);
                startActivity(it);
            }
        });

        LinearLayout buttonProfile = (LinearLayout)view.findViewById(R.id.button_nav_profile);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(false);
                Intent it = new Intent(getActivity(),ProfileActivity.class);
                startActivity(it);
            }
        });

        LinearLayout buttonAcoinLog = (LinearLayout)view.findViewById(R.id.button_nav_afl);
        buttonAcoinLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(false);
                Intent intent = new Intent(getActivity(), LeaveActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout linearLayoutBinding = (LinearLayout)view.findViewById(R.id.button_nav_binding);
        linearLayoutBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(false);
                Intent intent = new Intent(getActivity(), BindingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        UserProfileManger userProfileManger = new UserProfileManger(getActivity().getApplicationContext());

        textUserName.setText(userProfileManger.getNickname());

        if (userProfileManger.getLevel() > 0) {
            textUserLevel.setText(" Level" + userProfileManger.getLevel()+" ");
        }else {
            textUserLevel.setText(" 未测评 ");
        }

        textViewAcoin.setText("您剩余的A币数是 "+ userProfileManger.getAcoin() +" 个" );

        if (userProfileManger.getAvatar() == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_default);
            imageUserAvater.setImageBitmap(new CircleBitmap().getCircleBitmap(bitmap, 2.15f));
        }else {
            ImageLoader imageLoader = ImageLoaderUtils.getImageLoader(getActivity().getApplicationContext());
            imageLoader.DisplayImage(userProfileManger.getAvatar(),imageUserAvater);
        }
    }

}
