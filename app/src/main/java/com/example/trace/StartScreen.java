package com.example.trace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

//TODO: Handle rotation of camera - possibly just disable landscape
//TODO: Save transparency state after rotation
//TODO: Scale all images to a similar starting size regardless of source resolution
//TODO: Fix transparency of images from web
//TODO: Saved app images
//TODO: Finalize webview image selection UI
//TODO: Add external camera functionality
//TODO: Use python method to allow removal of single color backgrounds
//TODO: Allow adding of multiple images

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    /** Called when the user taps the Help button */
    public void goToHelp(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    public void goToImageMenu(View view) {
        Intent intent = new Intent(this, ImageMenu.class);
        startActivity(intent);
    }
}
