package com.abc360.tool.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.APIs.CancelBind;
import com.abc360.tool.userdeta.APIs.GetBind;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoader;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoaderUtils;
import com.abc360.tool.widgets.ResultsAdapterItem;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by roya on 14/11/21.
 */
public class BindingActivity extends Activity{


    RelativeLayout layoutNullList;
    LinearLayout layoutList;
    ScrollView scrollView;

    GetBind getBind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bingding);
        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        View.OnClickListener doListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SearchForBindingActivity.class);
                startActivity(intent);
            }
        };

        Button buttonDo = (Button)findViewById(R.id.binding_btn_do);
        buttonDo.setOnClickListener(doListener);

        TextView textViewAdd = (TextView)findViewById(R.id.binding_tv_add);
        textViewAdd.setOnClickListener(doListener);

        layoutNullList = (RelativeLayout)findViewById(R.id.bingding_rl_null_list);
        layoutList = (LinearLayout)findViewById(R.id.bingding_ll_list);
        scrollView = (ScrollView)findViewById(R.id.binding_sv_list);

        setShowData(true);

        getBind = new GetBind(getApplicationContext());

    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    private void load(){

        getBind.loadBindInfo(new GetBind.FinishedListener() {

            @Override
            public void onSuccess(List<GetBind.Data> data) {
                layoutList.removeAllViews();
                if (data == null){
                    setShowData(false);
                    return;
                }
                setShowData(true);
                for (final GetBind.Data i : data){
                    BindingItem bindingItem = new BindingItem(getApplicationContext(),layoutList);
                    bindingItem.setTeacherName(i.tname);
                    bindingItem.setAvater(i.tpic);
                    bindingItem.setDays(i.weekday.replace("星期","周").replace("天","日"));
                    bindingItem.setFavs(i.fav);
                    bindingItem.setOnAddClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), ReservationForBindingActivity.class);
                            intent.putExtra("id",i.tid);
                            intent.putExtra("teacherID",i.tid);
                            intent.putExtra("teacherName",i.tname);
                            intent.putExtra("teacherSrcLink",i.tpic);
                            intent.putExtra("begin_time","06:00");
                            startActivity(intent);
                        }
                    });
                    for ( final GetBind.LSN j : i.lsn){
                        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd");
                        BindingItemItem itemitem = new BindingItemItem(getApplicationContext(),bindingItem.getListZone());
                        itemitem.setBeginTimeText(j.just_time);
                        itemitem.setJustTimeText("生效日期:" + format.format(Long.decode(j.begin_time) * 1000));
                        itemitem.setOnCancelClickListenter(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CancelBind cancelBind = new CancelBind(getApplicationContext());
                                cancelBind.cancel(j.id, new CancelBind.OnCancelListener() {
                                    @Override
                                    public void onSuccess() {
                                        Toast.makeText(getApplicationContext(),"取消成功!" ,Toast.LENGTH_LONG).show();
                                        load();
                                    }

                                    @Override
                                    public void onFailure(int errorCode, String errorMsg) {
                                        Toast.makeText(getApplicationContext(),errorMsg ,Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                        bindingItem.getListZone().addView(itemitem);
                    }

                    layoutList.addView(bindingItem);
                }
            }

            @Override
            public void onFaiure(int errorCode, String errorMsg) {
                setShowData(false);
                Toast.makeText(getApplicationContext(),errorMsg,Toast.LENGTH_LONG).show();
            }
        });


    }

    private void setShowData(boolean isShow){
        if (isShow){
            layoutNullList.setVisibility(View.INVISIBLE);
            layoutList.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.VISIBLE);
        }else {
            layoutNullList.setVisibility(View.VISIBLE);
            layoutList.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
        }
    }

    private class BindingItem extends LinearLayout{

        private Context context;
        private LayoutInflater layoutInflater;
        private LinearLayout view;

        private ViewGroup viewGroup;

        private LinearLayout linearLayoutItemList;

        private String id;
        private ImageView imageViewAvater;
        private TextView teacherName;
        private TextView favs;
        private TextView days;

        private Button buttonAdd;

        public BindingItem(Context context,ViewGroup viewGroup) {
            super(context);
            this.context = context;
            this.viewGroup = viewGroup;
            init();
        }

        private void init(){
            if (isInEditMode())return;
            layoutInflater = LayoutInflater.from(context);
            view = (LinearLayout) layoutInflater.inflate(R.layout.binding_item, viewGroup, false);

            this.addView(view);

            linearLayoutItemList = (LinearLayout)findViewById(R.id.binding_ll_list);

            teacherName = (TextView)findViewById(R.id.binding_item_tv_teacher_name);
            imageViewAvater = (ImageView)findViewById(R.id.binding_item_iv_teacher);
            days = (TextView)findViewById(R.id.binding_item_tv_teacher_days);
            favs = (TextView)findViewById(R.id.binding_item_tv_teacher_favs);

            buttonAdd = (Button)findViewById(R.id.binding_item_btn_add);

        }
        public LinearLayout getListZone(){return linearLayoutItemList;}

        public void setId(String id){
            this.id = id;
        }

        public void setTeacherName(String teacherName1){
            this.teacherName.setText(teacherName1);
        }

        public void setAvater(String link){
            ImageLoader imageLoader = ImageLoaderUtils.getImageLoader(context);
            imageViewAvater.setTag(link);
            imageLoader.DisplayImage(link,imageViewAvater);
        }

        public void setFavs(String favs1){
            this.favs.setText(favs1);
        }

        public void setDays(String days1){
            this.days.setText(days1);
        }

        public void setOnAddClickListener(OnClickListener onClickListener){
            this.buttonAdd.setOnClickListener(onClickListener);
        }
    }

    private class BindingItemItem extends RelativeLayout {

        private Context context;
        private ViewGroup viewGroup;

        private RelativeLayout view;

        TextView textViewBeginTime;
        TextView textViewJustTime;
        Button buttonCancel;

        public BindingItemItem(Context context,ViewGroup viewGroup) {
            super(context);
            this.context = context;
            this.viewGroup = viewGroup;
            init();
        }

        private void init(){
            if (isInEditMode())return;
            view = (RelativeLayout) getLayoutInflater().inflate(R.layout.binding_item_item, viewGroup, false);

            this.addView(view);

            textViewBeginTime = (TextView)findViewById(R.id.binding_item_item_begintime);
            textViewJustTime = (TextView)findViewById(R.id.binding_item_item_justtime);
            buttonCancel = (Button)findViewById(R.id.binding_item_item_btn_cancel);
        }

        public void setBeginTimeText(String text){
            textViewBeginTime.setText(text);
        }

        public void setJustTimeText(String text){
            textViewJustTime.setText(text);
        }

        public void setOnCancelClickListenter(OnClickListener onClickListener){
            buttonCancel.setOnClickListener(onClickListener);
        }
    }
}
