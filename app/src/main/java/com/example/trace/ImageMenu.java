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
    ImageView imageViewLoad;
    Button LoadImage;
    Intent intent;
    String[] FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_menu);

        LoadImage = (Button)findViewById(R.id.galleryImage);
        imageViewLoad = (ImageView) findViewById(R.id.traceable);

        LoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMG_RESULT);
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
                imageViewLoad.setImageURI(selectedImage);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
