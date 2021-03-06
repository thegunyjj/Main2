package com.abc360.tool.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.abc360.tool.R;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoader;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoaderUtils;

import java.util.List;

public class ResultsAdapter extends BaseAdapter {

    private LayoutInflater flater ;
    private List<ResultsAdapterItem> mData;

    private Context context;

    public ResultsAdapter(Context context, List<ResultsAdapterItem> data) {
        this.flater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
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
    public int getViewTypeCount(){
        return 1;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null){

            convertView = flater.inflate(R.layout.list_results_item,viewGroup,false);
            holder = new ViewHolder();

            holder.textViewTeacher = (TextView)convertView.findViewById(R.id.results_item_textview_teacher);
            holder.textViewTime = (TextView)convertView.findViewById(R.id.results_item_textview_time);
            holder.textViewStar = (TextView)convertView.findViewById(R.id.results_item_tv_star);
            holder.textViewAcoin = (TextView)convertView.findViewById(R.id.results_item_textview_acoin);
            holder.imageViewStar = (ImageView)convertView.findViewById(R.id.results_item_iv_star);
            holder.imageViewTeacherPicture = (ImageView)convertView.findViewById(R.id.results_item_imageview_teacher_pic);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();

        }
        ResultsAdapterItem itemData = mData.get(i);

        ImageLoader imageLoader = ImageLoaderUtils.getImageLoader(context);
        holder.imageViewTeacherPicture.setTag(itemData.teacherSrcLink);
        imageLoader.DisplayImage(itemData.teacherSrcLink,holder.imageViewTeacherPicture);

        if (itemData.string == null) {
            holder.textViewAcoin.setText("包月:" + itemData.acoin + "A币 " + "自由:" + itemData.acoinFree + "A币");
        }else {
            holder.textViewAcoin.setText(itemData.string);
        }

        holder.textViewStar.setText(itemData.Favorites+"");
        holder.textViewTeacher.setText(itemData.teacherName);
        holder.textViewTime.setText(itemData.time);
        if (itemData.Starred) {
            holder.imageViewStar.setVisibility(View.VISIBLE);
        }else {
            holder.imageViewStar.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }


    public final class ViewHolder{
        ImageView imageViewStar;
        TextView textViewTeacher;
        TextView textViewTime;
        ImageView imageViewTeacherPicture;
        TextView textViewStar;
        TextView textViewAcoin;
    }
}
