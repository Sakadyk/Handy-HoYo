package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SauceMaster extends AppCompatActivity {
    EditText code;
    RelativeLayout button, inputFields;
    ImageView clearCode;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sauce_master);
        getWindow().setStatusBarColor(ContextCompat.getColor(SauceMaster.this, R.color.nhcolor));

        button = findViewById(R.id.button);
        code = findViewById(R.id.code_input);
        clearCode = findViewById(R.id.clear_icon);
        inputFields = findViewById(R.id.input_fields);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Initialize MediaPlayer and set looping
        mediaPlayer = MediaPlayer.create(this, R.raw.saxsolo);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_LOSS:
                        // Stop playback and release MediaPlayer resources
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        break;
                }
            }
        };

        code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_GO || i == EditorInfo.IME_ACTION_DONE) {
                    performButtonClick();
                    return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performButtonClick();
            }
        });

        clearCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear the text of uid_gi EditText
                code.setText("");
            }
        });
    }

    private void performButtonClick() {
        String codeInput = code.getText().toString();
        String url;
        if (TextUtils.isEmpty(codeInput)) {
            url = "https://nhentai.net/";
        } else {
            url = "https://nhentai.net/g/" + codeInput;
        }
        Intent intent = new Intent(code.getContext(), Browser.class);
        intent.putExtra("url", url);
        intent.putExtra("previousActivity", Genshin.class.getName());
        code.getContext().startActivity(intent);
        releaseMediaPlayer();
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        if (view != null && event.getAction() == MotionEvent.ACTION_DOWN) {
            int[] coordinates = new int[2];
            view.getLocationOnScreen(coordinates);
            float x = event.getRawX() + view.getLeft() - coordinates[0];
            float y = event.getRawY() + view.getTop() - coordinates[1];

            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                view.clearFocus();
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // Release the MediaPlayer resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void onBackPressed() {
        // Get the class name of the previous activity
        String previousActivityClassName = getIntent().getStringExtra("previousActivity");

        if (previousActivityClassName != null) {
            try {
                // Create an Intent for the previous activity using its class name
                Class<?> previousActivityClass = Class.forName(previousActivityClassName);
                Intent intent = new Intent(SauceMaster.this, previousActivityClass);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            super.onBackPressed(); // If no previous activity specified, perform default back button behavior
        }
    }
}
