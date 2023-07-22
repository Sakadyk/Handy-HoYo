package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings({"BusyWait", "IntegerDivisionInFloatingPointContext"})
@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    ProgressBar progressBar;
    TextView textView;
    ImageView gifImageView;
    int value;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check Android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12 or above, start Genshin activity
            Intent intent = new Intent(this, Genshin.class);
            startActivity(intent);
            finish();
        } else {
            // Android 11 or lower, continue with the normal flow
            setContentView(R.layout.activity_splash_screen);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            progressBar = findViewById(R.id.progressbar);
            textView = findViewById(R.id.progresstext);
            gifImageView = findViewById(R.id.gif);

            Thread thread = new Thread(this::startProgress);
            thread.start();
        }
    }

    @SuppressLint("SetTextI18n")
    public void startProgress() {
        for (value = 0; value < 100; value++) {
            try {
                Thread.sleep(10); //Progressbar speed
                progressBar.setProgress(value);
                moveGif();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(() -> textView.setText(value + "%"));
        }
        run();
    }

    public void moveGif() {
        float progressBarWidth = progressBar.getWidth();
        float progress = progressBar.getProgress();
        float maxProgress = progressBar.getMax();

        float distance = progressBarWidth * (progress / maxProgress);
        float targetX = distance - (gifImageView.getWidth() / 2);

        Animation animation = new TranslateAnimation(targetX, targetX, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(25);
        gifImageView.startAnimation(animation);
    }

    public void run() {
        Intent intent = new Intent(SplashScreen.this, Genshin.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}