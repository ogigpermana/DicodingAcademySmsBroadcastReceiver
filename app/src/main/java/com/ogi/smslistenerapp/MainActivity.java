package com.ogi.smslistenerapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("My Broadcast Receiver");
        }
        Button btnPermission = findViewById(R.id.btn_permission);
        btnPermission.setOnClickListener(this);
    }

    final int SMS_PERMISSION_CODE = 101;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_permission){
           PermissionManager.check(this, Manifest.permission.RECEIVE_SMS, SMS_PERMISSION_CODE);
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
