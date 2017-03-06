package com.yajun.logapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.yajun.logapp.download.DownloadHelper;

public class MainActivity extends AppCompatActivity {

    private Button downloadBtn;
    private ProgressBar progressBar;

    DownloadHelper downloadHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadHelper = new DownloadHelper(this,progressBar);

        downloadBtn = (Button) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadHelper.downLoad();
            }
        });
    }

    @Override
    protected void onDestroy() {
        downloadHelper.unregisterReceiver();
        super.onDestroy();
    }
}
