package com.eroadcar.networkmanagement.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.activity.PermissionssActivity;
import com.eroadcar.networkmanagement.bean.PermissionssBean;

import java.util.ArrayList;

public class PermissionssAdapter extends BaseAdapter {
    private ArrayList<PermissionssBean> mData;
    private Context mContext;


    public PermissionssAdapter(Context context, ArrayList<PermissionssBean> data) {
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
        if (convertView == null || convertView.getTag() == null) {
            convertView = ((LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.adapter_permissionss_item, null);
        }

        final PermissionssBean bean = mData.get(position);

        CheckBox choose_cb = (CheckBox) convertView.findViewById(R.id.choose_cb);
        TextView role = (TextView) convertView.findViewById(R.id.role_tv);

        role.setText(bean.getRole_app_name());

        if (((PermissionssActivity) mContext).ids.contains(bean.getRole_app_id())) {
            choose_cb.setChecked(true);
            ((PermissionssActivity) mContext).permissionssBean.add(bean);
        }


        choose_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ((PermissionssActivity) mContext).permissionssBean.add(bean);
                } else {
                    for (int i = 0; i < ((PermissionssActivity) mContext).permissionssBean.size(); i++) {
                        if (((PermissionssActivity) mContext).permissionssBean.get(i).getRole_app_id().equals(bean.getRole_app_id())) {
                            ((PermissionssActivity) mContext).permissionssBean.remove(i);
                            break;
                        }
                    }
                }
            }
        });


        return convertView;
    }
}
