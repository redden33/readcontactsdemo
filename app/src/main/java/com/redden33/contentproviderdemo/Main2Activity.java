package com.redden33.contentproviderdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private List<String> contactsList = new ArrayList<>();
    private ArrayAdapter adapter;
    private Cursor cursor =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btn_show_contacts = (Button)findViewById(R.id.btn_show_contacts);
        ListView lv_contacts = (ListView)findViewById(R.id.lv_contacts);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,contactsList);
        lv_contacts.setAdapter(adapter);

        btn_show_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.READ_CONTACTS)!=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Main2Activity.this,new String[]{Manifest.permission.READ_CONTACTS},2);
                }else{
                    readContacts();}
            }
            });
        }




    private void readContacts(){
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if (cursor!=null){
                while (cursor.moveToNext()){
                    String contacts_name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String contacts_phone_number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactsList.add(contacts_name+contacts_phone_number);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null){
                cursor.close();
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 2:
                if (grantResults.length> 0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    readContacts();
                }else {
                    Toast.makeText(Main2Activity.this, "授权失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
