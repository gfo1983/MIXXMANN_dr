package com.gfo.mixxmann;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
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

public class MainActivity extends AppCompatActivity {
    //private static final String TAG = "mixxmann";

    //region privatevariable
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
    public BTAdapter getBt() {
        return bt;
    }
    public BTAdapter bt;
    //endregion privatevariable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
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
        BTAdapter bt=new BTAdapter(this);
        bt.init();
        connectionsViewModel.setDeviceList(bt.getDeviceList());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
    }
}