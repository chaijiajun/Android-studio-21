package com.example.android_studio_21;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textview);
        ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);//先取得此service
        NetworkInfo networInfo = conManager.getActiveNetworkInfo();       //在取得相關資訊

        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);


        //判斷是否有網路
        ad.setMessage("目前沒有網路，是否前往設定？");
        ad.setPositiveButton("設定網路", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS));
            }
        });
        ad.setNegativeButton("不用設定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //不設定所以不觸發事件
            }
        });
        ad.setCancelable(false);  //使Android主要功能鍵不起作用（防止亂按導致視窗消失）



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(networInfo.isConnected()){
                        textView.setText("有網路可用");
                    }
                }catch (Exception e){
                    runOnUiThread(()->{
                        ad.show();
                    });
                }
            }
        }).start();
    }
}