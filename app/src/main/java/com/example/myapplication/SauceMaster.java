package com.example.myapplication;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myapplication.Methods.MethodUtils;

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

        audioFocusChangeListener = focusChange -> {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {// Stop playback and release MediaPlayer resources
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        };

        code.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_GO || i == EditorInfo.IME_ACTION_DONE) {
                performButtonClick();
                return true;
            }
            return false;
        });

        button.setOnClickListener(view -> performButtonClick());

        clearCode.setOnClickListener(view -> {
            // Clear the text of uid_gi EditText
            code.setText("");
        });
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
        finish();
    }

    @Override
    public void onBackPressed() {
        String previousActivityClassName = getIntent().getStringExtra("previousActivity");
        if (previousActivityClassName != null) {
            try {
                // Create an Intent for the previous activity using its class name
                Class<?> previousActivityClass = Class.forName(previousActivityClassName);
                MethodUtils.startActivityWithAnimation(SauceMaster.this, previousActivityClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            super.onBackPressed(); // If no previous activity specified, perform default back button behavior
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean result = MethodUtils.dispatchTouchEvent(this, event);
        return result || super.dispatchTouchEvent(event);
    }
}
