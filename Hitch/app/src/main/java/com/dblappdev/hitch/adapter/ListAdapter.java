package com.dblappdev.hitch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.dblappdev.hitch.model.ListViewItem;
import com.dblappdev.hitch.app.R;
import java.util.List;

/**
 * Created by s138308 on 29-3-2015.
 */
public class ListAdapter extends ArrayAdapter<ListViewItem> {

    public ListAdapter(Context context, List<ListViewItem> items) {
        super(context, R.layout.listview_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            viewHolder.tvRouteName = (TextView) convertView.findViewById(R.id.tvRouteName);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            viewHolder.ivStars = (ImageView) convertView.findViewById(R.id.ivStars);
            viewHolder.ivArrow = (ImageView) convertView.findViewById(R.id.ivArrow);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        ListViewItem item = getItem(position);
        viewHolder.ivIcon.setImageDrawable(item.icon);
        viewHolder.tvName.setText(item.name);
        viewHolder.tvRouteName.setText(item.routeName);
        viewHolder.tvTime.setText(item.time);
        viewHolder.ivStars.setImageDrawable(item.stars);
        viewHolder.ivArrow.setImageDrawable(item.arrow);

        return convertView;
    }

    /**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     *
     * @see http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
     */
    private static class ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        TextView tvTime;
        TextView tvRouteName;
        ImageView ivStars;
        ImageView ivArrow;
    }
}