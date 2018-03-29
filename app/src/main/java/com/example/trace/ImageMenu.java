package com.example.trace;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImageMenu extends AppCompatActivity {

    private static int IMG_RESULT = 1;
    String ImageDecode;
    Button LoadGalleryImage;
    Button LoadBingImage;
    Intent intent;
    String[] FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_menu);

        LoadGalleryImage = (Button)findViewById(R.id.galleryImage);
        LoadBingImage = (Button)findViewById(R.id.bingImage);

        LoadGalleryImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMG_RESULT);
            }
        });

        LoadBingImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bing.com/images/search?sp=1&pq=line+dr&sc=8-7&cvid=18D76BEEC95A40078DCE84ACB0381D6B&q=line+drawings&qft=+filterui:photo-transparent&FORM=IRFLTR"));
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_RESULT && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            try {
                String[] FILE = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage, FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String images= cursor.getString(columnIndex);

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
