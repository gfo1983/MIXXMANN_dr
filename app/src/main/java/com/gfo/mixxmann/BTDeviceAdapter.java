package com.gfo.mixxmann;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BTDeviceAdapter extends ArrayAdapter<BTdevice> {
    private Context mcontext;
    private List<BTdevice> BTdeviceList = new ArrayList<>();


    public BTDeviceAdapter(@NonNull Context context, ArrayList<BTdevice> BTdeviceList) {
        super(context, 0, BTdeviceList);
        this.mcontext = context;
        this.BTdeviceList = BTdeviceList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(mcontext).inflate(R.layout.device_list_item,parent,false);
        }
        BTdevice device=BTdeviceList.get(position);
        TextView name= listItem.findViewById(R.id.dname);
        name.setText(device.getName());
        TextView addr= listItem.findViewById(R.id.daddr);
        addr.setText(device.getAddr());
        return listItem;
        //return super.getView(position, convertView, parent);

    }
}
