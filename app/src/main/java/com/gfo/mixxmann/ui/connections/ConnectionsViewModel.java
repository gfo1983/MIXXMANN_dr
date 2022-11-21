package com.gfo.mixxmann.ui.connections;

 import android.util.Log;

 import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

 import com.gfo.mixxmann.BTAdapter;
 import com.gfo.mixxmann.constants;

 import java.util.ArrayList;

public class ConnectionsViewModel extends ViewModel {
    public MutableLiveData<ArrayList> deviceList = new MutableLiveData<>();
    public MutableLiveData<String> buttontext = new MutableLiveData<>();

    public MutableLiveData<String> getButtontext() {
        return buttontext;
    }

    public void setButtontext(String buttontext) {
        this.buttontext.setValue(buttontext);
    }



    public MutableLiveData<ArrayList> getDeviceList() {
        return deviceList;
    }
    public void setDeviceList(ArrayList deviceList) {
        this.deviceList.setValue(deviceList);
    }



}