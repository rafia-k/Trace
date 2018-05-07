package com.example.trace;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class webBrowser extends AppCompatActivity {
    WebView webview;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);
        webview = findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.loadUrl("https://www.bing.com/images/search?sp=1&pq=line+dr&sc=8-7&cvid=18D76BEEC95A40078DCE84ACB0381D6B&q=line+drawings&qft=+filterui:photo-transparent&FORM=IRFLTR");
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = (webview.getUrl());



                Intent intent = new Intent(view.getContext(), CameraActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);



            }
        });



    }


}
