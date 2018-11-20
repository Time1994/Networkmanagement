package com.eroadcar.networkmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.HomeBean;

import java.util.ArrayList;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class FunctionAdapter extends BaseAdapter {
    private ArrayList<HomeBean> mData;
    private Context mContext;
    private int maxImgCount;


    public FunctionAdapter(Context context, ArrayList<HomeBean> data) {
        // TODO Auto-generated constructor stub
        mData = data;
        mContext = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return mData.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        if (convertView == null || convertView.getTag() == null) {
            convertView = ((LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.adapter_function_item, null);
        }
        HomeBean bean = mData.get(position);
        ImageView img_iv =(ImageView) convertView .findViewById(R.id.img_iv);
        TextView name_tv = (TextView) convertView.findViewById(R.id.name_tv);
        img_iv.setImageResource(bean.getImageId());
        name_tv.setText(bean.getContent());

        if (bean.getType().equals("sale")){
            name_tv.setTextColor(mContext.getResources().getColor(R.color.red));
        }else if(bean.getType().equals("wei")){
            name_tv.setTextColor(mContext.getResources().getColor(R.color.greens));
        }else if(bean.getType().equals("ku")){
            name_tv.setTextColor(mContext.getResources().getColor(R.color.bluez));
        }else if(bean.getType().equals("employee")){
            name_tv.setTextColor(mContext.getResources().getColor(R.color.orange));
        }else if(bean.getType().equals("car")){
            name_tv.setTextColor(mContext.getResources().getColor(R.color.oranger));
        }else if(bean.getType().equals("tong")){
            name_tv.setTextColor(mContext.getResources().getColor(R.color.blues));
        }
//        QBadgeView badgeView = new QBadgeView(context);
//        new QBadgeView(context).bindTarget(img_iv).setBadgeText("99+").setShowShadow(false).setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
//            @Override
//            public void onDragStateChanged(int dragState, Badge badge, View targetView) {
//
//            }
//        });
        return convertView;
    }

}
