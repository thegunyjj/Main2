package com.abc360.tool.Activity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.ParamsForSearch;
import com.abc360.tool.userdeta.SearchResultForBindingManager;
import com.abc360.tool.userdeta.SearchResultManager;
import com.abc360.tool.widgets.ResultsAdapter;
import com.abc360.tool.widgets.ResultsAdapterItem;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultsForBindingActivity extends ListActivity {

    BagForSeacher bagForSeacher;

    ResultsAdapter adapter;

    List<ResultsAdapterItem> listData;
    ListView listView;

    SearchResultForBindingManager resultManager;
    ProgressDialog progressDialog;

    boolean isAllLoaded = false;
    boolean isLoading = false;
    int page;

    private class BagForSeacher{
        int catalog = 0;
        String times;
        String name = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.reservation_button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        final Context context = getApplicationContext();
        Intent intent = getIntent();
        bagForSeacher = new BagForSeacher();
        bagForSeacher.catalog = intent.getIntExtra("catalog",0);
        bagForSeacher.name = intent.getStringExtra("first_letter");
        bagForSeacher.times= intent.getStringExtra("begin_time");

        listData = new ArrayList<ResultsAdapterItem>();
        adapter = new ResultsAdapter(getApplicationContext(), listData);
        listView = getListView();

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(adapter);
        swingBottomInAnimationAdapter.setAbsListView(listView);
        listView.setAdapter(swingBottomInAnimationAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ReservationForBindingActivity.class);
                intent.putExtra("id",((ResultsAdapterItem)adapter.getItem(i)).ID);
                intent.putExtra("teacherID",((ResultsAdapterItem)adapter.getItem(i)).teacherID);
                intent.putExtra("teacherName",((ResultsAdapterItem)adapter.getItem(i)).teacherName);
                intent.putExtra("teacherSrcLink",((ResultsAdapterItem)adapter.getItem(i)).teacherSrcLink);
                intent.putExtra("begin_time",((ResultsAdapterItem)adapter.getItem(i)).time);
                startActivity(intent);
            }
        });



        progressDialog = new ProgressDialog(ResultsForBindingActivity.this);
        progressDialog.setMessage("载入中...");
        progressDialog.show();
        resultManager =new SearchResultForBindingManager(getApplicationContext());
        page = 0;

        this.getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                if ((i3 - i) < 10) {
                    loadData();
                }
            }
        });

        listData.clear();
        loadData();

    }

    private void loadData(){

        if (resultManager == null){ return;}
        if (isAllLoaded || isLoading){ return;}

        isLoading = true;
        resultManager.loadSearchResult(
                page,
                bagForSeacher.name,
                bagForSeacher.times,
                bagForSeacher.catalog,
                new SearchResultForBindingManager.LoadedListener() {
            @Override
            public void onLoaded(List<SearchResultForBindingManager.ResultData> data) {
                progressDialog.dismiss();
                if(data == null){
                    Toast.makeText(getApplicationContext(),"找不到符合条件的老师",Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }else {
                    if (data.size() < 20) { isAllLoaded = true; }
                    for ( SearchResultForBindingManager.ResultData i : data){
                        ResultsAdapterItem item = new ResultsAdapterItem();
                        item.ID = i.id;
                        item.teacherID = i.tid;
                        item.teacherSrcLink = i.pic;
                        item.teacherName = i.tname;
                        item.Favorites = i.fav;
                        item.string = "学员好评率: "+i.good+"%";
                        item.time = i.just_time;
                        listData.add(item);
                    }
                    page++;
                    adapter.notifyDataSetChanged();
                }
                isLoading = false;
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),errorMsg,Toast.LENGTH_LONG).show();
                isLoading = false;
            }
        });
    }
}
