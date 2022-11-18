package com.gfo.mixxmann;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.gfo.mixxmann.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class BTAdapter {
    protected String error;
    protected BluetoothAdapter adapter;
    private Activity activity;
    private static final String LogTAG = "mixxmannBT";

    public BTAdapter(Activity activity) {
        this.activity = activity;
        this.adapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean init() {

        if (adapter == null) {
            Snackbar sb = Snackbar.make(activity.findViewById(R.id.drawer_layout), R.string.bt_not_support, (int) 10000);
            sb.setActionTextColor(activity.getResources().getColor(R.color.orange_700));
            sb.setAction(R.string.action_exit, v -> {
                activity.finish();
                System.exit(0);
            });
            sb.show();
            error = activity.getString(R.string.bt_not_support);
            return false;
        }
        if (!adapter.isEnabled()) {
            Snackbar sb = Snackbar.make(activity.findViewById(R.id.drawer_layout), R.string.bt_not_enabled, (int) 1000000);
            sb.setActionTextColor(activity.getResources().getColor(R.color.orange_700));
            sb.setAction(R.string.action_enable_now, v -> {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
                    Snackbar sb1 = Snackbar.make(activity.findViewById(R.id.drawer_layout), R.string.bt_not_permit, (int) 100000);
                    sb1.setActionTextColor(activity.getResources().getColor(R.color.orange_700));
                    sb1.setAction(R.string.action_exit, v1 -> {
                        activity.finish();
                        System.exit(0);
                    });
                    sb1.show();
                    Log.i(LogTAG,"BT is not permitted");
                    error="BT is not permitted";
                } else {
                    adapter.enable();
                    while (!adapter.isEnabled()){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    init();
                }
            });
            sb.show();
            Log.i(LogTAG,"BT is not enabled");

            return false;
        } else {
            Log.i(LogTAG,"BT init ok");
            return true;
        }
    }
    public boolean isBtEnable(){
        if (adapter == null) {
            return false;
        }
        return adapter.isEnabled();
    }
    public ArrayList getDeviceList(){
        init();
        ArrayList list=new ArrayList<>();
        list.add("device1\naddr1");
        list.add("device2\naddr2");
        list.add("device3\naddr3");
        list.add("device4\naddr4");

        return list;
    }
}
