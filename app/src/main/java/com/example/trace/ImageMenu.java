package com.example.trace;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ImageMenu extends AppCompatActivity {

    private static int IMG_RESULT = 1;
    Button LoadGalleryImage;
    Button LoadBingImage;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_menu);

        LoadGalleryImage = findViewById(R.id.galleryImage);
        LoadBingImage = findViewById(R.id.bingImage);

        //when "upload from gallery" button clicked
        LoadGalleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to gallery
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMG_RESULT);
            }
        });

        //when bing button clicked, goes to link
        LoadBingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bing.com/images/search?sp=1&pq=line+dr&sc=8-7&cvid=18D76BEEC95A40078DCE84ACB0381D6B&q=line+drawings&qft=+filterui:photo-transparent&FORM=IRFLTR"));
                startActivity(i);
                */
                Intent i = new Intent(v.getContext(), webBrowser.class);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_RESULT && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData(); //get img from gallery

            try {
                //Start camera activity and send image as an extra
                Intent intent = new Intent(this, CameraActivity.class);
                intent.putExtra("ImageURI", selectedImage);
                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
