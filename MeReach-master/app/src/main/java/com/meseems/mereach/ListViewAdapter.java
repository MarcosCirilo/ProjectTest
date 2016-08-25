package com.meseems.mereach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.meseems.mereach.networking.ReachabilityServiceSocketImpl;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Asus on 24/08/2016.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> servers;
    private LayoutInflater mInflater;

    public ListViewAdapter(Context c, ArrayList<String> _servers) {
        mContext = c;
        servers = _servers;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view;
        if (convertView == null) {
            view = mInflater.inflate(R.layout.row, viewGroup, false);
        }else{
            view = convertView;
        }

        TextView url = (TextView)view.findViewById(R.id.textViewURL);
        TextView status = (TextView)view.findViewById(R.id.textViewStatus);
        ImageView image = (ImageView)view.findViewById(R.id.imageViewStatus);

        url.setText(servers.get(position));
        isReacheble(servers.get(position), status, image);

        return view;
    }

    private void isReacheble(String url, final TextView status, final ImageView image){

        new ReachabilityServiceSocketImpl()
                /*
                Reachable domains: www.google.com.br, www.meseems.com.br
                Unreachable domains: www.meseems-not-reachable.com.br
                 */
                .isReachable(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {

                    @Override
                    public void call(Boolean isReachable) {
                        if(isReachable) {
                            status.setText("online");
                            image.setImageResource(R.mipmap.greencircle);
                        } else {
                            status.setText("offline");
                            image.setImageResource(R.mipmap.redcircle);
                        }
                    }
                });
    }
}
