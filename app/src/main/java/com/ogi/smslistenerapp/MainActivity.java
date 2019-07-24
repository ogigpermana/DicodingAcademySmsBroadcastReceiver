package com.ogi.smslistenerapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ACTION_DOWNLOAD_STATUS = "download_status";
    private BroadcastReceiver downloadReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("My Broadcast Receiver");
        }
        Button btnPermission = findViewById(R.id.btn_permission);
        btnPermission.setOnClickListener(this);
        Button btnDownload = findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(this);

        downloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(DownloadService.TAG, "Download selesai");
                Toast.makeText(context, "Download selesai", Toast.LENGTH_SHORT).show();
            }
        };
        IntentFilter downloadIntentFilter = new IntentFilter(ACTION_DOWNLOAD_STATUS);
        registerReceiver(downloadReceiver, downloadIntentFilter);
    }

    final int SMS_PERMISSION_CODE = 101;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_permission){
           PermissionManager.check(this, Manifest.permission.RECEIVE_SMS, SMS_PERMISSION_CODE);
        }else if (v.getId() == R.id.btn_download){
            Intent downloadServiceIntent = new Intent(MainActivity.this, DownloadService.class);
            startService(downloadServiceIntent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (downloadReceiver != null){
            unregisterReceiver(downloadReceiver);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SMS_PERMISSION_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Sms receiver permission diterima", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Sms permission receiver ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
