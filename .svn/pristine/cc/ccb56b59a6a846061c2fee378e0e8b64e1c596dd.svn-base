package com.abc360.tool.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.abc360.tool.Activity.NoteActivity;
import com.abc360.tool.R;
import com.abc360.tool.userdeta.APIs.ClassCommentSaver;
import com.abc360.tool.userdeta.APIs.FinishedClassGeter;
import com.abc360.tool.userdeta.UserProfileManger;
import com.abc360.tool.widgets.LogAdapter;
import com.abc360.tool.widgets.LogAdapterItem;
import com.abc360.tool.widgets.SelectedAdapter;
import com.abc360.tool.widgets.SelectedAdapterItem;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HistoryFragment extends ListFragment {

    private SwipeRefreshLayout mSwipeLayout;

    FinishedClassGeter finishedClassGeter;
    private Context context;

    List<LogAdapterItem> listData;
    LogAdapter logAdapter;

    boolean pageMode = true;

    //SmoothProgressBar smoothProgressBar;

    PopupWindow popupWindow;
    int iee;

    boolean isAllLoaded = false;
    boolean isLoading = false;
    int page = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_main, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle bl) {
        super.onActivityCreated(bl);

        //smoothProgressBar = (SmoothProgressBar)getActivity().findViewById(R.id.smoothProgressBar);

        LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.getListView().addHeaderView(lif.inflate(R.layout.headerview, null ,false));

        this.getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                if (pageMode) {
                    if ((i3 - i) < 10) {
                        flishByPage();
                    }
                }
            }
        });

        mSwipeLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.id_swipe_ly);
        mSwipeLayout.setColorSchemeResources(R.color.main_color);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                backToday();
            }
        });

        listData = new ArrayList<LogAdapterItem>();
        logAdapter = new LogAdapter(getActivity(), listData);

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(logAdapter);
        swingBottomInAnimationAdapter.setAbsListView(getListView());

        setListAdapter(swingBottomInAnimationAdapter);
        //this.setListAdapter(logAdapter);

//        final View viewSet = getActivity().getLayoutInflater().inflate(R.layout.popup_window_comment,null,false);
//        ImageView imageViewGood = (ImageView)viewSet.findViewById(R.id.popup_comment_good);
//        ImageView imageViewBad = (ImageView)viewSet.findViewById(R.id.popup_comment_bad);
//
//        View.OnClickListener onStateClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switch (view.getId()){
//                    case R.id.popup_comment_good:{
//                        listData.get(iee).praiseState = LogAdapterItem.STATE_GOOD;
//                        new ClassCommentSaver(context).saveClassComment(listData.get(iee).ID,LogAdapterItem.STATE_GOOD,new ClassCommentSaver.OnfinishedListener() {
//                            @Override
//                            public void onSuccess() { }
//                            @Override
//                            public void onFailure(int errorCode) {
//                                Toast.makeText(context,"评价失败，请稍后再试",Toast.LENGTH_SHORT).show();
//                                listData.get(iee).praiseState = LogAdapterItem.STATE_NULL;
//                                logAdapter.notifyDataSetChanged();
//                            }
//                        });
//                        break;}
//                    case R.id.popup_comment_bad:{
//                        listData.get(iee).praiseState = LogAdapterItem.STATE_BAD;
//                        new ClassCommentSaver(context).saveClassComment(listData.get(iee).ID,LogAdapterItem.STATE_BAD,new ClassCommentSaver.OnfinishedListener() {
//                            @Override
//                            public void onSuccess() { }
//                            @Override
//                            public void onFailure(int errorCode) {
//                                Toast.makeText(context,"评价失败，请稍后再试",Toast.LENGTH_SHORT).show();
//                                listData.get(iee).praiseState = LogAdapterItem.STATE_NULL;
//                                logAdapter.notifyDataSetChanged();
//                            }
//                        });
//                        break;}
//                }
//                popupWindow.dismiss();
//                logAdapter.notifyDataSetChanged();
//            }
//        };
//        imageViewGood.setOnClickListener(onStateClickListener);
//        imageViewBad.setOnClickListener(onStateClickListener);
//        logAdapter.setOnCommentClickListener(new LogAdapter.OnCommentClickListener() {
//            @Override
//            public void onClick(View view, int i) {
//                popupWindow = new PopupWindow(viewSet,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//                popupWindow.setTouchable(true);
//                popupWindow.setOutsideTouchable(true);
//                popupWindow.setBackgroundDrawable(new ColorDrawable());
//                final float dp = getResources().getDisplayMetrics().density;
//                popupWindow.showAsDropDown(view,(int)(-(32+12)*dp),(int)(-32*dp));
//                iee=i;
//
//            }
//        });
        logAdapter.setOnCommentClickListener(new LogAdapter.OnCommentClickListener() {
            @Override
            public void onClick(View view, final int i) {
                final String[] showComm = {"满意","基本满意","不满意"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(showComm,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        int comm;

                        if (j == 0){
                            comm = LogAdapterItem.STATE_GOOD;
                        }else if (j == 1){
                            comm = LogAdapterItem.STATE_OK;
                        }else{
                            comm = LogAdapterItem.STATE_BAD;
                        }

                        final int rawComm = listData.get(i).praiseState;
                        new ClassCommentSaver(context).saveClassComment(listData.get(i).ID,comm,new ClassCommentSaver.OnfinishedListener() {
                            @Override
                            public void onSuccess() { }
                            @Override
                            public void onFailure(int errorCode) {
                                Toast.makeText(context,"评价失败，请稍后再试",Toast.LENGTH_SHORT).show();
                                listData.get(i).praiseState = rawComm;
                                logAdapter.notifyDataSetChanged();
                            }
                        });
                        listData.get(i).praiseState = comm;
                        logAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });

        logAdapter.setOnItemClickListener(new LogAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i, LogAdapterItem item) {
                Intent intent = new Intent(getActivity().getApplicationContext(),NoteActivity.class);
                intent.putExtra("id",item.ID);
                startActivity(intent);
            }
        });





        finishedClassGeter = new FinishedClassGeter(context);
        isAllLoaded = false;
        isLoading = false;
        page = 0;
        listData.clear();
        flishByPage();

        //smoothProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setListAdapter(null);
        page = 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void backToday(){
        if (!isLoading){
            pageMode = true;
            page = 0;
            listData.clear();
            logAdapter.notifyDataSetChanged();
            isAllLoaded = false;
            //smoothProgressBar.setVisibility(View.VISIBLE);
            flishByPage();
        }
    }

    private void flishByPage(){
        if (finishedClassGeter == null){ return;}
        if (isAllLoaded || isLoading){ return;}
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("载入中...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        TypedValue typed_value = new TypedValue();
        getActivity().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typed_value, true);
        mSwipeLayout.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value.resourceId));
        mSwipeLayout.setRefreshing(true);
        isLoading = true;
        finishedClassGeter.getFinishedClass(new UserProfileManger(context).getId(),page,new FinishedClassGeter.OnloadedListener() {
            @Override
            public void onSuccess(List<FinishedClassGeter.Data> data) {
                mSwipeLayout.setRefreshing(false);
                //smoothProgressBar.progressiveStop();
                //smoothProgressBar.setVisibility(View.INVISIBLE);
                if (page == 0){
                    listData.clear();
                }
                page = page +1 ;
                if (data == null){return;}
                if (data.size() < 20) { isAllLoaded = true; }
                if (data.size() >= 1) {

                    SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm");
                    for (FinishedClassGeter.Data dataItem : data) {
                        LogAdapterItem item = new LogAdapterItem();
                        item.ID = dataItem.id;
                        item.teacherName = dataItem.teacher.nickname;
                        item.teacherSrcLink = dataItem.teacher.pic;
                        if (dataItem.textbook != null) {
                            item.textbookName = dataItem.textbook;
                        }else {
                            item.textbookName = "未选择";
                        }
                        item.canCom = dataItem.canCom;
                        item.praiseState = dataItem.comment;
                        item.time = formatter.format(new Date(Long.parseLong(dataItem.begin_time) * 1000));
                        listData.add(item);
                    }
                    logAdapter.notifyDataSetChanged();
                }
                isLoading = false;
//                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int errorCode) {
                mSwipeLayout.setRefreshing(false);
                //smoothProgressBar.progressiveStop();
                //smoothProgressBar.setVisibility(View.INVISIBLE);
                if (errorCode == 0) {
                    Toast.makeText(context, "载入失败,请稍后再试", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "无记录", Toast.LENGTH_SHORT).show();
                }
                isLoading = false;
//                progressDialog.dismiss();
            }
        });

    }


    public void flishByDate(String date){
        pageMode = false;
        TypedValue typed_value = new TypedValue();
        getActivity().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typed_value, true);
        mSwipeLayout.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value.resourceId));
        mSwipeLayout.setRefreshing(true);
        //Log.e("date",date);
        //smoothProgressBar.setVisibility(View.VISIBLE);
        finishedClassGeter.getFinishedClass(new UserProfileManger(context).getId(),date,new FinishedClassGeter.OnloadedListener() {
            @Override
            public void onSuccess(List<FinishedClassGeter.Data> data) {
                mSwipeLayout.setRefreshing(false);
                //smoothProgressBar.progressiveStop();
                //smoothProgressBar.setVisibility(View.INVISIBLE);

                //Log.e("date","onSuccess");
                listData.clear();
                if (data == null){return;}
                if (data.size() >= 1) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm");
                    for (FinishedClassGeter.Data dataItem : data) {
                        LogAdapterItem item = new LogAdapterItem();
                        item.ID = dataItem.id;
                        item.teacherName = dataItem.teacher.nickname;
                        item.teacherSrcLink = dataItem.teacher.pic;
                        item.canCom = dataItem.canCom;
                        if (dataItem.textbook != null) {
                            item.textbookName = dataItem.textbook;
                        }else {
                            item.textbookName = "未选择";
                        }
                        item.praiseState = dataItem.comment;
                        item.time = formatter.format(new Date(Long.parseLong(dataItem.begin_time) * 1000));
                        listData.add(item);
                    }
                    logAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int errorCode) {
                mSwipeLayout.setRefreshing(false);
                //smoothProgressBar.progressiveStop();
                //smoothProgressBar.setVisibility(View.INVISIBLE);

                //Log.e("date","onFailure");
                if (errorCode == 0) {
                    Toast.makeText(context, "载入失败,请稍后再试", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "当天没有课程", Toast.LENGTH_SHORT).show();
                }

                listData.clear();
                logAdapter.notifyDataSetChanged();
            }
        });
    }
}