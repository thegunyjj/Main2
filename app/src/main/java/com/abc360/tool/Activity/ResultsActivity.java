package com.abc360.tool.Activity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.ParamsForSearch;
import com.abc360.tool.userdeta.SearchResultManager;
import com.abc360.tool.widgets.ResultsAdapter;
import com.abc360.tool.widgets.ResultsAdapterItem;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultsActivity extends ListActivity {

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

        ParamsForSearch paramsForSearch = new ParamsForSearch();
        paramsForSearch.date = intent.getStringExtra("date");
        paramsForSearch.timeHH = intent.getIntExtra("timeHH",-2);
        paramsForSearch.timeMM = intent.getIntExtra("timeMM", -2);
        paramsForSearch.catalog= intent.getIntExtra("catalog",0);
        paramsForSearch.option = intent.getStringArrayListExtra("option");


        TextView textViewTitle = (TextView)findViewById(R.id.results_tv_title);
        textViewTitle.setText(intent.getStringExtra("dateSow"));

        final List<ResultsAdapterItem> data = new ArrayList<ResultsAdapterItem>();
        final ResultsAdapter adapter = new ResultsAdapter(getApplicationContext(), data);
        ListView listView = getListView();

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(adapter);
        swingBottomInAnimationAdapter.setAbsListView(listView);
        listView.setAdapter(swingBottomInAnimationAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ReservationFormCidActivity.class);
                intent.putExtra("id",data.get(i).ID);
                intent.putExtra("teacherID",data.get(i).teacherID);
                intent.putExtra("teacherName",data.get(i).teacherName);
                intent.putExtra("teacherSrcLink",data.get(i).teacherSrcLink);
                startActivity(intent);
            }
        });
        final ProgressDialog progressDialog = new ProgressDialog(ResultsActivity.this);
        progressDialog.setMessage("载入中...");
        progressDialog.show();
        SearchResultManager resultManager =new SearchResultManager(getApplicationContext());
        resultManager.loadSearchResult(paramsForSearch,new SearchResultManager.loadedSearchResultListner() {
            @Override
            public void onLoaded(SearchResultManager.Result result) {
                progressDialog.cancel();
                data.clear();
                if(result == null){
                    Toast.makeText(context,"找不到符合条件的课程，请换个条件再试？",Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }

                if(result.data.size() >= 1){

                    for(int j = 0; j < result.data.size(); j++){
                        //Log.e("onLoaded",i.id);
                        SearchResultManager.ResultData i = result.data.get(j);
                        ResultsAdapterItem item = new ResultsAdapterItem();
                        item.ID=i.id;
                        item.teacherName= i.teacher.nickname;
                        item.teacherSrcLink = i.teacher.pic;
                        item.Favorites = i.teacher.fav+"";
                        item.Starred = i.teacher.myfav;
                        item.time = i.begin_time;
                        item.teacherID = i.teacher.id;
                        item.acoin = i.acoin;
                        item.acoinFree = i.acoin_free;
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                        item.time = formatter.format(new Date(Long.parseLong(i.begin_time) * 1000));
                        data.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int errorCode) {
                progressDialog.cancel();
                if (errorCode == 0){
                    Toast.makeText(getApplicationContext(),"网络连接失败，请稍后再试",Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "找不到符合条件的课程，请换个条件再试？", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });


    }
}
