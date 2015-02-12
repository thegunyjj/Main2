package com.abc360.tool.Activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.APIs.FavoriteSaver;
import com.abc360.tool.userdeta.APIs.FavoritesTeacherGeter;
import com.abc360.tool.userdeta.UserIDManager;
import com.abc360.tool.userdeta.UserProfileManger;
import com.abc360.tool.widgets.FavoritesAdapter;
import com.abc360.tool.widgets.FavoritesAdapterItem;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends ListActivity {

    ListView listView;
    Context context;
    List<FavoritesAdapterItem> data;
    FavoritesAdapter adapter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        LinearLayout buttonBack = (LinearLayout) findViewById(R.id.favorites_button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.context = getApplicationContext();
        listView = getListView();

        data = new ArrayList<FavoritesAdapterItem>();
        adapter = new FavoritesAdapter(getApplicationContext(),data);
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapter);
        alphaInAnimationAdapter.setAbsListView(listView);
        listView.setAdapter(alphaInAnimationAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context,ReservationFormDetaActivity.class);
                intent.putExtra("teacherID",data.get(i).id);
                intent.putExtra("teacherName",data.get(i).name);
                intent.putExtra("teacherSrcLink",data.get(i).pic);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                String[] items = new String[]{"查看老师详情","取消收藏"};
                AlertDialog.Builder builder = new AlertDialog.Builder(FavoritesActivity.this);
                builder.setTitle(data.get(i).name);
                builder.setItems(items,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int t) {
                        switch (t){
                            case 0:{
                                Intent intent = new Intent(getApplicationContext(),CheckTeacherActivity.class);
                                intent.putExtra("teacherID",data.get(i).id);
                                intent.putExtra("teacherName",data.get(i).name);
                                intent.putExtra("teacherSrcLink",data.get(i).pic);
                                startActivity(intent);
                                break;
                                }
                            case 1:{
                                final List<String> tids = new ArrayList<String>();
                                tids.add(data.get(i).id);
                                new AlertDialog.Builder(FavoritesActivity.this)
                                        .setTitle("取消收藏")
                                        .setMessage("确认取消收藏老师 "+data.get(i).name+" ?")
                                        .setNeutralButton("取消",null)
                                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int j) {
                                                List<String> tids = new ArrayList<String>();
                                                tids.add(data.get(i).id);
                                                new FavoriteSaver(getApplicationContext()).delFavorite(new UserProfileManger(context).getId(),tids,new FavoriteSaver.OnFinishedListner() {
                                                    @Override
                                                    public void onSuccess() {
                                                        Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                                                        getFavorites();
                                                    }

                                                    @Override
                                                    public void onFailure() {
                                                        Toast.makeText(context,"删除失败,请稍后再试",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        })
                                        .show();
                                break;
                                }
                        }
                    }
                });
                builder.show();
                return true;
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("请稍后...");
        progressDialog.show();
        getFavorites();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getFavorites();
    }

    private void getFavorites(){

        FavoritesTeacherGeter teacherGeter = new FavoritesTeacherGeter(context);
        teacherGeter.getFavoriteTeachers(new UserProfileManger(context).getId(),new FavoritesTeacherGeter.OnloadedListner() {
            @Override
            public void onFinished(List<FavoritesTeacherGeter.Teacher> teachers) {
                progressDialog.dismiss();
                data.clear();
                for (FavoritesTeacherGeter.Teacher i : teachers){
                    FavoritesAdapterItem item = new FavoritesAdapterItem();
                    item.id = i.tid;
                    item.name = i.nickname;
                    item.catalog = i.catalog;
                    item.pic = i.pic;
                    data.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int errorCode) {
                progressDialog.dismiss();
                if (errorCode == 0){
                    Toast.makeText(context,"网络连接失败，请稍后再试",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
