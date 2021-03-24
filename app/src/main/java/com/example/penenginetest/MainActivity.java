package com.example.penenginetest;

import android.os.Bundle;
import android.util.Log;

import com.pengfei.huanlib.system.SystemUtil;
import com.pengfei.huanlib.system.DeviceUtil;

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

        DeviceUtil.initApplicationContext(getApplicationContext());

        if(!DeviceUtil.isConnectWifi()){
            SystemUtil.openWifiSetting(this);
        }

        Log.e("MainActivity", "onCreate: getBuildName"+ DeviceUtil.getBuildName());
        Log.e("MainActivity", "onCreate: getMobileSystem"+ DeviceUtil.getMobileSystem());
        Log.e("MainActivity", "onCreate: getPackageName"+ DeviceUtil.getPackageName());
        Log.e("MainActivity", "onCreate: getConnectionType"+ DeviceUtil.getConnectionType());
//        Log.e("MainActivity", "onCreate: getLocalIpAddress"+ TDevice.getLocalIpAddress());
        Log.e("MainActivity", "onCreate: getCurrentNetworkType"+ DeviceUtil.getCurrentNetworkType());

//        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//        startActivity(new Intent(Settings.ACTION_WIFI_IP_SETTINGS));
//        startActivity(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
//        startActivity(new Intent(Settings.ACTION_PROCESS_WIFI_EASY_CONNECT_URI));
        SystemUtil.jumpQQ(this,"1105436599");
    }
}