package com.redden33.contentproviderdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_dial = (Button)findViewById(R.id.btn_dial);
        btn_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else {
                    call();}}
        });
    }
    void call(){
        try{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:15557904618"));
            startActivity(intent);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }
    public void onRequestPermissionResult(int requestCode,String[] permission,int[] grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length> 0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                call();
            }else {
                Toast.makeText(MainActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
            }
                break;
                default:
        }
    }
}
