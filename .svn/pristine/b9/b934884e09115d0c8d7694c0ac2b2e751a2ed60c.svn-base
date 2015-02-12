package com.abc360.tool.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.abc360.tool.Activity.FavoritesActivity;
import com.abc360.tool.R;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoader;
import com.abc360.tool.widgets.LazyImageLoader.ImageLoaderUtils;

import java.util.List;

/**
 * Created by roya on 14/11/17.
 */
public class FavoritesAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater flater;
    private List<FavoritesAdapterItem> mData;

    public FavoritesAdapter(Context context, List<FavoritesAdapterItem> Data){
        this.context = context;
        this.flater = LayoutInflater.from(context);
        this.mData = Data;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null){
            convertView = flater.inflate(R.layout.list_favorites_item,viewGroup,false);
            holder = new ViewHolder();
            holder.imageViewPic = (ImageView)convertView.findViewById(R.id.list_favorites_iv_pic);
            holder.textViewName = (TextView)convertView.findViewById(R.id.list_favorites_tv_name);
            holder.textViewCatalog=(TextView) convertView.findViewById(R.id.list_favorites_tv_catalog);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        FavoritesAdapterItem item = mData.get(i);
        holder.textViewName.setText(item.name);
        holder.textViewCatalog.setText(item.catalog);

        ImageLoader imageLoader = ImageLoaderUtils.getImageLoader(context);
        imageLoader.DisplayImage(item.pic,holder.imageViewPic);

        return convertView;

    }

    public final class ViewHolder{
        ImageView imageViewPic;
        TextView textViewName;
        TextView textViewCatalog;
    }
}
