package com.meseems.mereach;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.meseems.mereach.networking.ReachabilityServiceSocketImpl;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Asus on 25/08/2016.
 */
public class RemoveListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> servers;
    private ArrayList<Integer> serversToRemove;
    private LayoutInflater mInflater;

    public RemoveListAdapter(Context c, ArrayList<String> _servers, ArrayList<Integer> toRemove) {
        mContext = c;
        servers = _servers;
        serversToRemove = toRemove;
        mInflater = (LayoutInflater) c
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return servers.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        View view;
        if (convertView == null) {
            view = mInflater.inflate(R.layout.remove_row, viewGroup, false);
        }else{
            view = convertView;
        }

        TextView textView = (TextView)view.findViewById(R.id.textViewURLRemove);
        textView.setText(servers.get(position));

        final CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBox);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()) {
                    serversToRemove.add(position);
                    Log.d("ADAPTER", "To Remove: "+serversToRemove.size());
                } else {
                    if(serversToRemove.contains(Integer.valueOf(position))){
                        serversToRemove.remove(Integer.valueOf(position));
                    }
                }
            }
        });
        return view;
    }

}
