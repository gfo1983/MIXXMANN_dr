package com.gfo.mixxmann;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gfo.mixxmann.ui.connections.ConnectionsViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.gfo.mixxmann.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String LogTAG = "mixxmann";

    //region variables
    public static String[] PERMISSIONS_BLUETOOTH = {
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_PRIVILEGED,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_CONNECT,
    };
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private ConnectionsViewModel dataModel;
    public BTAdapter bt = new BTAdapter(this);
    //endregion variables
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            ConnectionsViewModel connectionsViewModel=new ViewModelProvider(MainActivity.this).get(ConnectionsViewModel.class);
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i(LogTAG,bt.toString());
                try {
                bt.addDevice(device);} catch (Exception i) {i.printStackTrace();}
                connectionsViewModel.setDeviceList(bt.getDeviceList());
            }
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                connectionsViewModel.setButtontext(getResources().getString(R.string.button_discovering)); ;
            }
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                connectionsViewModel.setButtontext(getResources().getString(R.string.button_discovery)); ;
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    public void registerReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver,filter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        registerReceiver();
        ConnectionsViewModel connectionsViewModel=new ViewModelProvider(this).get(ConnectionsViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> {

        }
        );
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_control, R.id.nav_connections).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        drawer.openDrawer(GravityCompat.START);
        //region menulistener
        Menu navMenu = navigationView.getMenu();
        MenuItem exitItem = navMenu.findItem(R.id.exitmenuitem);
        exitItem.setOnMenuItemClickListener(menuItem -> {
            finish();
            System.exit(0);
            return true;
        });
        MenuItem share = navMenu.findItem(R.id.share);
        share.setOnMenuItemClickListener(menuItem -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_URL));
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        });
        MenuItem website = navMenu.findItem(R.id.website);
        website.setOnMenuItemClickListener(menuItem -> {
            Intent browser = new Intent(Intent.ACTION_VIEW);
            browser.setData(Uri.parse(getString(R.string.website_URL)));
            startActivity(browser);
            return true;
        });
        MenuItem websitemanual = navMenu.findItem(R.id.manual);
        websitemanual.setOnMenuItemClickListener(menuItem -> {
            Intent browser = new Intent(Intent.ACTION_VIEW);
            browser.setData(Uri.parse(getString(R.string.appmanual_URL)));
            startActivity(browser);
            return true;
        });

        TextView stw = navigationView.getHeaderView(0).findViewById(R.id.supportMailTextView);
        stw.setOnClickListener(view -> {
            Intent intentEmail = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto", getString(R.string.supportEmail), null));
            intentEmail.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.supportSubject));
            intentEmail.putExtra(
                    Intent.EXTRA_TEXT,
                    Html.fromHtml("<p><b>Your name:</b></p>" +
                            "<p><b>Your location:</b></p>" +
                            "<small><p>Message content:\n</p></small>" +
                            "Our website: <a>" + getString(R.string.website_URL) + "</a>"));
            startActivity(Intent.createChooser(intentEmail, "Send email..."));
        });
        //endregion menulistener
        bt.fillDeviceList();
        connectionsViewModel.setDeviceList(bt.getDeviceList()) ;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void discoverBT() {
            //bt.s
            bt.discoverBT();

    }
    public boolean pairDevice(int lispPosition) {
        return bt.pairDevice(lispPosition);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }
    public void checkPermission(){
            int permission = ActivityCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,PERMISSIONS_BLUETOOTH,1);
            }
            String[] perm=new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
            if((ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)||(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) ) {
                ActivityCompat.requestPermissions(this,perm,1);
            }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}