package com.abc360.tool.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abc360.tool.Activity.LeaveActivity;
import com.abc360.tool.R;

/**
 * Created by roya on 14/12/19.
 */
public class LeaveItem extends LinearLayout{

    private Context context;
    private LayoutInflater layoutInflater;
    private LinearLayout view;

    ViewGroup viewGroup;

    private TextView textViewTitle;
    private TextView textViewSubtitle;
    private Button button;

    public LeaveItem(Context context , ViewGroup viewGroup){
        super(context);
        this.context = context;
        this.viewGroup = viewGroup;
        init();
    }

    private void init(){
        if (isInEditMode())return;
        layoutInflater = LayoutInflater.from(context);
        view = (LinearLayout) layoutInflater.inflate(R.layout.leave_item, viewGroup, false);

        textViewTitle = (TextView) view.findViewById(R.id.leave_item_title);
        textViewSubtitle = (TextView) view.findViewById(R.id.leave_item_subtitle);
        button = (Button) view.findViewById(R.id.leave_item_btn);

        this.addView(view);
    }

    public void setTitle(String title){
        textViewTitle.setText(title);
    }

    public void setSubtitle(String subtitle){
        textViewSubtitle.setText(subtitle);
    }

    public void setButtonListenter(OnClickListener onClickListener){
        button.setOnClickListener(onClickListener);
    }
}
