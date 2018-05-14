package com.example.trace;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class webBrowser extends AppCompatActivity {
    WebView webview;
    Button useImage;
    Button external;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);
        webview = findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //This line of code allows transparent http images to show up in an https window, rather than the low res cached image without transparency
        //Allows low risk mixed content
        webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

        //Context menu is the pop up - It can't open if you don't register for it onCreate
        registerForContextMenu(webview);
        webview.loadUrl("https://www.bing.com/images/search?sp=1&pq=line+dr&sc=8-7&cvid=18D76BEEC95A40078DCE84ACB0381D6B&q=line+drawings&qft=+filterui:photo-transparent&FORM=IRFLTR");


        useImage = findViewById(R.id.useImage);
        useImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = (webview.getUrl());

                Intent intent = new Intent(view.getContext(), CameraActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);



            }
        });


        //Open the current page in android's default browser - to be used in case of a problem with the built in browser
        external = findViewById(R.id.external);
        external.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = (webview.getUrl());

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });
    }


    //Allows the back button to pop the last page on the page stack rather than just exiting the app
    @Override
    public void onBackPressed() {
        if(webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }



//Runs on long press of an element
    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo){
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);

        //Checks the contents of the hit
        final WebView.HitTestResult webViewHitTestResult = webview.getHitTestResult();

        if (webViewHitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                webViewHitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {

            //contextMenu.setHeaderTitle("Select Image");
            //Context menu icon resource
            //contextMenu.setHeaderIcon(R.drawable.download);

            contextMenu.add(0, 1, 0, "Select image")
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            //Capture the image's URL from its HTML tag
                            String DownloadImageURL = webViewHitTestResult.getExtra();

                            if(URLUtil.isValidUrl(DownloadImageURL)){
                                //Download the image
                                //DownloadManager.Request mRequest = new DownloadManager.Request(Uri.parse(DownloadImageURL));
                                //mRequest.allowScanningByMediaScanner();
                                //mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                //DownloadManager mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                //mDownloadManager.enqueue(mRequest);

                                Intent intent = new Intent(webBrowser.this, CameraActivity.class);
                                intent.putExtra("url", DownloadImageURL);
                                startActivity(intent);

                                Toast.makeText(webBrowser.this,"Image selected",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(webBrowser.this,"Sorry, something went wrong...",Toast.LENGTH_LONG).show();
                            }
                            return false;
                        }
                    });



            contextMenu.add(0, 1, 0, "Select image and save to app")
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            //Capture the image's URL from its HTML tag
                            String DownloadImageURL = webViewHitTestResult.getExtra();

                            if(URLUtil.isValidUrl(DownloadImageURL)){
                                //Download the image
                                DownloadManager.Request mRequest = new DownloadManager.Request(Uri.parse(DownloadImageURL));
                                mRequest.allowScanningByMediaScanner();
                                mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                DownloadManager mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                mDownloadManager.enqueue(mRequest);

                                Intent intent = new Intent(webBrowser.this, CameraActivity.class);
                                intent.putExtra("url", DownloadImageURL);
                                startActivity(intent);

                                Toast.makeText(webBrowser.this,"Image selected and downloaded successfully",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(webBrowser.this,"Sorry, something went wrong...",Toast.LENGTH_LONG).show();
                            }
                            return false;
                        }
                    });


        }
    }

}
