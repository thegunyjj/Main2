package com.abc360.tool.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abc360.tool.R;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoader;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoaderUtils;

import java.lang.Override;
import java.util.List;

public class SelectedAdapter extends BaseAdapter {

    private LayoutInflater flater ;
    private List<SelectedAdapterItem> mData;

    private Context context;

    public SelectedAdapter(Context context, List<SelectedAdapterItem> data) {
        this.flater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).type;
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
        return 2;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

            if (getItemViewType(i) == SelectedAdapterItem.TYPE_ITEM) {

                SelectedAdapterItem itemData = mData.get(i);
                convertView = flater.inflate(R.layout.list_main_item_item, viewGroup, false);
                TextView textViewTeacher = (TextView) convertView.findViewById(R.id.item1_textview_teacher);
                TextView textViewToolId = (TextView) convertView.findViewById(R.id.item1_textview_tool_id);
                TextView textViewTime = (TextView) convertView.findViewById(R.id.item1_textview_time);
                ImageView imageViewToolIcon = (ImageView) convertView.findViewById(R.id.item1_imageview_tool_icon);
                ImageView imageViewTextbook_Has = (ImageView) convertView.findViewById(R.id.item1_imageview_textbook_HAS);
                ImageView imageViewTextbook_Not = (ImageView) convertView.findViewById(R.id.item1_imageview_textbook_NOT);
                ImageView imageViewTeacherPicture = (ImageView) convertView.findViewById(R.id.item1_imageview_teacher_pic);
                ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.item1_pogressbar_loading);
                RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.item1_relativelayout_data);

                if (itemData.teacherName != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    textViewTeacher.setText(itemData.teacherName);

                    textViewTime.setText(itemData.time);

                    if (SelectedAdapterItem.TYPE_TOOL_QQ.equals(itemData.toolType)) {
                        imageViewToolIcon.setImageResource(R.drawable.ic_qq);
                        textViewToolId.setText(itemData.QQ);
                    } else {
                        imageViewToolIcon.setImageResource(R.drawable.ic_skype);
                        textViewToolId.setText(itemData.Skype);
                    }
                    if (!itemData.hasTextbook) {
                        imageViewTextbook_Has.setVisibility(View.INVISIBLE);
                    } else {
                        imageViewTextbook_Not.setVisibility(View.INVISIBLE);
                    }
                } else {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    imageViewTeacherPicture.setVisibility(View.INVISIBLE);
                }
                imageViewTeacherPicture.setTag(itemData.teacherSrcLink);
                ImageLoader imageLoader = ImageLoaderUtils.getImageLoader(context);
                imageLoader.DisplayImage(itemData.teacherSrcLink, imageViewTeacherPicture);

            } else {
                convertView = flater.inflate(R.layout.list_main_item_deta, viewGroup, false);
                TextView textViewDeta = (TextView) convertView.findViewById(R.id.item2_textview_deta);
                textViewDeta.setText(mData.get(i).deta);
            }

        return convertView;
    }

    private static class ViewHolderItem{
        TextView textViewTeacher;
        TextView textViewToolId;
        TextView textViewTime;
        ImageView imageViewToolIcon;
        ImageView imageViewTextbook_Has;
        ImageView imageViewTextbook_Not;
        ImageView imageViewTeacherPicture;
        ProgressBar progressBar;
        RelativeLayout relativeLayout;

        View convertViewDeta;
        TextView textViewDeta;
    }
    private static class ViewHolderDeta{
        TextView textViewDeta;
    }
}
