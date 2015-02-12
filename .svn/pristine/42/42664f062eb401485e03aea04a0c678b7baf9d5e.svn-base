package com.abc360.tool.widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.abc360.tool.R;

import java.util.List;

/**
 * Created by roya on 14/11/12.
 */
public class BookListviewAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater flater ;
    private List<BookListviewAdapterItem> mData;

    private String buttonText;

    public BookListviewAdapter(Context context,List<BookListviewAdapterItem> data){
        this.context = context;
        this.flater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        convertView = flater.inflate(R.layout.list_book_list_item,viewGroup,false);
        TextView textViewTime =(TextView)convertView.findViewById(R.id.book_list_item_tv);
        Button buttonSwitch = (Button)convertView.findViewById(R.id.book_list_item_bt);

        if (buttonText !=null){
            buttonSwitch.setText(buttonText);
        }

        textViewTime.setText(mData.get(i).begin_time);
        if (mData.get(i).selected  == 1){
            buttonSwitch.setBackgroundResource(R.drawable.btn_open_f);
            buttonSwitch.setTextColor(Color.parseColor("#ffffff"));
        }else if (mData.get(i).selected == 0) {
            buttonSwitch.setBackgroundResource(R.drawable.btn_open_t);
            buttonSwitch.setTextColor(Color.parseColor("#32a7ee"));
        }else {
            buttonSwitch.setVisibility(View.GONE);
        }

        buttonSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButton(i);
            }
        });
        return convertView;
    }

    public void setButtonText(String text){
        buttonText = text;
    }

    private void setButton(int i){
        if(mData.get(i).selected == 1){
            mData.get(i).selected = 0;
        }else {
            mData.get(i).selected = 1;
        }
        notifyDataSetChanged();
    }

}
