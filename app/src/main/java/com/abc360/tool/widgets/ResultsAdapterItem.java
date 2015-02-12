package com.abc360.tool.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.abc360.tool.R;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoader;

import java.util.List;

public class ResultsAdapterItem  {


    public String ID;

    public String teacherID;
    public String teacherName;
    public String teacherSrcLink;
    public String time;
    public String Favorites;
    public boolean Starred;
    public String acoin;
    public String acoinFree;
    public String string;
}
