package com.dblappdev.hitch.adapter;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Map;
        import java.util.concurrent.Callable;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.app.Fragment;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.graphics.Typeface;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
        import android.widget.BaseExpandableListAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;
        import com.dblappdev.hitch.app.DriverFragment;
        import com.dblappdev.hitch.app.R;
        import com.dblappdev.hitch.network.API;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    // Adapter variables
    private DriverFragment context;
    private Map<String, List<String>> routeCollections;
    private List<String> routes;
    private List<Integer> ids;

    public ExpandableListAdapter(DriverFragment context, List<String> routes,
                                 List<Integer> ids, Map<String, List<String>> routeCollections) {
        this.context = context;
        this.routeCollections = routeCollections;
        this.routes = routes;
        this.ids = ids;
    }

    // Gets the child of an object
    public Object getChild(int groupPosition, int childPosition) {
        return routeCollections.get(routes.get(groupPosition)).get(childPosition);
    }

    // Gets object's child ID
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // Returns a view for children
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String route = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getActivity().getLayoutInflater();;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.route);

        item.setText(route);
        return convertView;
    }

    // Counts children given parent position
    public int getChildrenCount(int groupPosition) {
        return routeCollections.get(routes.get(groupPosition)).size();
    }

    // Returns a parent
    public Object getGroup(int groupPosition) {
        return routes.get(groupPosition);
    }

    // Returns number or parent elements
    public int getGroupCount() {
        return routes.size();
    }

    // Returns parent id
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // Returns a parents view
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String routeName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.route);

        // If delete button is clicked - remove the parent and nofify Listeners.
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new OnClickListener() {
            List<String> removed;
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity());
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                API api = new API();
                                api.deleteUserRoute(ids.get(groupPosition), new Callable<Void>() {
                                    @Override
                                    public Void call() throws Exception {
                                        removed = routeCollections.remove(routes.get(groupPosition));
                                        routes.remove(groupPosition);
                                        ids.remove(groupPosition);
                                        notifyDataSetChanged();
                                        return null;
                                    }
                                });
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        item.setTypeface(null, Typeface.BOLD);
        item.setText(routeName);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}