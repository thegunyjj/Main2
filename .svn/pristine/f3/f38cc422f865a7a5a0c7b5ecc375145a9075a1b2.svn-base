package com.abc360.tool.Activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.abc360.tool.R;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by roya on 14/11/21.
 */
public class AcoinLogActivity extends ListActivity{

    ListView listView;
    SimpleAdapter adapter;

    List<Map<String,String>> data;
    String[] dataKeys = new String[]{"title","text","coin","time"};
    int[] viewItemIds = new int[]{R.id.title,R.id.text,R.id.coin,R.id.time};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acoin_log);
        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView = getListView();

        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.headervew_acoin_log,listView,false);
        listView.addHeaderView(headerView);

        data = new ArrayList<>();

        adapter = new SimpleAdapter(getApplicationContext(),data,R.layout.item_acoinlog,dataKeys,viewItemIds);

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(adapter);
        swingBottomInAnimationAdapter.setAbsListView(listView);

        listView.setAdapter(swingBottomInAnimationAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        for (int i=0; i<20; i++){
            Map<String,String> item = new ArrayMap<String, String>();
            item.put(dataKeys[0],"aa");
            item.put(dataKeys[1],"bb");
            item.put(dataKeys[2],"co");
            item.put(dataKeys[3],"tt");
            data.add(item);
        }
        adapter.notifyDataSetChanged();

    }

}
