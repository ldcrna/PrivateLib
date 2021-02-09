package com.example.penenginetest;

import android.os.Bundle;
import android.util.Log;

import com.pengfei.huanlib.SystemUtil;
import com.pengfei.huanlib.TDevice;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int i=1;
        if(i==1){
            Log.e("TAG", "onCreate: ");
        }
//        SystemUtil.openWifiSetting(this);

        TDevice.initApplicationContext(getApplicationContext());

        if(!TDevice.isConnectWIFI()){
            SystemUtil.openWifiSetting(this);
        }

        Log.e("MainActivity", "onCreate: getBuildName"+TDevice.getBuildName());
        Log.e("MainActivity", "onCreate: getMobileSystem"+TDevice.getMobileSystem());
        Log.e("MainActivity", "onCreate: getPackageName"+TDevice.getPackageName());
        Log.e("MainActivity", "onCreate: getConnectionType"+TDevice.getConnectionType());
//        Log.e("MainActivity", "onCreate: getLocalIpAddress"+ TDevice.getLocalIpAddress());
        Log.e("MainActivity", "onCreate: getCurrentNetworkType"+ TDevice.getCurrentNetworkType());

//        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//        startActivity(new Intent(Settings.ACTION_WIFI_IP_SETTINGS));
//        startActivity(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
//        startActivity(new Intent(Settings.ACTION_PROCESS_WIFI_EASY_CONNECT_URI));
    }
}