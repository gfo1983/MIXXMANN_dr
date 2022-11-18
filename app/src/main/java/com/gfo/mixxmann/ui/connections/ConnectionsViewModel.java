package com.gfo.mixxmann.ui.connections;

 import android.util.Log;

 import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

 import com.gfo.mixxmann.BTAdapter;
 import com.gfo.mixxmann.constants;

public class ConnectionsViewModel extends ViewModel {
    public MutableLiveData<String[]> deviceList;

    public ConnectionsViewModel() {
        this.deviceList = deviceList;
    }

    public void setDeviceList(String[] devList) {
        deviceList.setValue(devList);
    }
}