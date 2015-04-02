package com.dblappdev.hitch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.dblappdev.hitch.model.RatingViewItem;
import com.dblappdev.hitch.app.R;

import java.util.List;

/**
 * Created by guusleijsten on 02/04/15.
 */
public class RatingAdapter extends ArrayAdapter<RatingViewItem> {

    public RatingAdapter(Context context, List<RatingViewItem> items) {
        super(context, R.layout.listview_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.ratingview_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.nameLabel);
            viewHolder.comment = (TextView) convertView.findViewById(R.id.commentLabel);
            viewHolder.rating = (RatingBar) convertView.findViewById(R.id.ratingBar);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        RatingViewItem item = getItem(position);
        viewHolder.name.setText(item.name);
        viewHolder.comment.setText(item.comment);
        viewHolder.rating.setRating((float)item.rating);

        return convertView;
    }

    /**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     *
     * @see http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
     */
    private static class ViewHolder {
        TextView name;
        TextView comment;
        RatingBar rating;
    }
}
