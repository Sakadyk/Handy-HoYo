package com.example.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Methods.MethodUtils;

public class UID extends AppCompatActivity {
    EditText uid_gi, uid_hsr, uid_hi3, uid_tot;
    ImageView copy_gi, copy_hsr, copy_hi3, copy_tot, clear_gi, clear_hsr, clear_hi3, clear_tot;
    RelativeLayout returnToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uid);
        //getWindow().setStatusBarColor(ContextCompat.getColor(UIDGenshin.this, R.color.genshin));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        copy_gi = findViewById(R.id.copy_uid_gi);
        copy_hsr = findViewById(R.id.copy_uid_hsr);
        copy_hi3 = findViewById(R.id.copy_uid_hi3);
        copy_tot = findViewById(R.id.copy_uid_tot);
        clear_gi = findViewById(R.id.clear_uid_gi);
        clear_hsr = findViewById(R.id.clear_uid_hsr);
        clear_hi3 = findViewById(R.id.clear_uid_hi3);
        clear_tot = findViewById(R.id.clear_uid_tot);
        uid_gi = findViewById(R.id.uid_gi_text);
        uid_hsr = findViewById(R.id.uid_hsr_text);
        uid_hi3 = findViewById(R.id.uid_hi3_text);
        uid_tot = findViewById(R.id.uid_tot_text);
        returnToMain = findViewById(R.id.button);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String uidGiText = sharedPreferences.getString("uid_gi", "");
        String uidHsrText = sharedPreferences.getString("uid_hsr", "");
        String uidHi3Text = sharedPreferences.getString("uid_hi3", "");
        String uidTotText = sharedPreferences.getString("uid_tot", "");
        uid_gi.setText(uidGiText);
        uid_hsr.setText(uidHsrText);
        uid_hi3.setText(uidHi3Text);
        uid_tot.setText(uidTotText);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid_gi", uid_gi.getText().toString());
        editor.putString("uid_hsr", uid_hsr.getText().toString());
        editor.apply();

        uid_gi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation needed
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Update the value in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("uid_gi", charSequence.toString());
                editor.apply();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                // No implementation needed
            }
        });

        uid_hsr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation needed
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Update the value in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("uid_hsr", charSequence.toString());
                editor.apply();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                // No implementation needed
            }
        });

        uid_hi3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation needed
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Update the value in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("uid_hi3", charSequence.toString());
                editor.apply();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                // No implementation needed
            }
        });

        uid_tot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation needed
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Update the value in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("uid_tot", charSequence.toString());
                editor.apply();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                // No implementation needed
            }
        });

        copy_gi.setOnClickListener(view -> {
            // Get the text from uid_gi EditText
            String uidGiText1 = uid_gi.getText().toString();

            // Check if the EditText is empty
            if (uidGiText1.isEmpty()) {
                Toast.makeText(getApplicationContext(), "No UID to copy", Toast.LENGTH_SHORT).show();
                return;
            }

            // Copy the text to the clipboard
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied UID", uidGiText1);
            clipboard.setPrimaryClip(clip);

            // Display a Toast message
            Toast.makeText(getApplicationContext(), "UID copied to clipboard", Toast.LENGTH_SHORT).show();
        });
        clear_gi.setOnClickListener(view -> {
            // Clear the text of uid_gi EditText
            uid_gi.setText("");
        });

        copy_hsr.setOnClickListener(view -> {
            // Get the text from uid_hsr EditText
            String uidHsrText1 = uid_hsr.getText().toString();

            // Check if the EditText is empty
            if (uidHsrText1.isEmpty()) {
                Toast.makeText(getApplicationContext(), "No UID to copy", Toast.LENGTH_SHORT).show();
                return;
            }

            // Copy the text to the clipboard
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied UID", uidHsrText1);
            clipboard.setPrimaryClip(clip);

            // Display a Toast message
            Toast.makeText(getApplicationContext(), "UID copied to clipboard", Toast.LENGTH_SHORT).show();
        });
        clear_hsr.setOnClickListener(view -> {
            // Clear the text of uid_gi EditText
            uid_hsr.setText("");
        });

        copy_hi3.setOnClickListener(view -> {
            // Get the text from uid_hi3 EditText
            String uidHi3Text1 = uid_hi3.getText().toString();

            // Check if the EditText is empty
            if (uidHi3Text1.isEmpty()) {
                Toast.makeText(getApplicationContext(), "No UID to copy", Toast.LENGTH_SHORT).show();
                return;
            }

            // Copy the text to the clipboard
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied UID", uidHi3Text1);
            clipboard.setPrimaryClip(clip);

            // Display a Toast message
            Toast.makeText(getApplicationContext(), "UID copied to clipboard", Toast.LENGTH_SHORT).show();
        });
        clear_hi3.setOnClickListener(view -> {
            // Clear the text of uid_gi EditText
            uid_hi3.setText("");
        });

        copy_tot.setOnClickListener(view -> {
            // Get the text from uid_tot EditText
            String uidTotText1 = uid_tot.getText().toString();

            // Check if the EditText is empty
            if (uidTotText1.isEmpty()) {
                Toast.makeText(getApplicationContext(), "No UID to copy", Toast.LENGTH_SHORT).show();
                return;
            }

            // Copy the text to the clipboard
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied UID", uidTotText1);
            clipboard.setPrimaryClip(clip);

            // Display a Toast message
            Toast.makeText(getApplicationContext(), "UID copied to clipboard", Toast.LENGTH_SHORT).show();
        });
        clear_tot.setOnClickListener(view -> {
            // Clear the text of uid_gi EditText
            uid_tot.setText("");
        });

        returnToMain.setOnClickListener(view -> {
            // Get the class name of the previous activity
            String previousActivityClassName = getIntent().getStringExtra("previousActivity");

            if (previousActivityClassName != null) {
                try {
                    // Create an Intent for the previous activity using its class name
                    Class<?> previousActivityClass = Class.forName(previousActivityClassName);
                    Intent intent = new Intent(UID.this, previousActivityClass);
                    startActivity(intent);
                    finish();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Get the class name of the previous activity
        String previousActivityClassName = getIntent().getStringExtra("previousActivity");
        if (previousActivityClassName != null) {
            try {
                // Create an Intent for the previous activity using its class name
                Class<?> previousActivityClass = Class.forName(previousActivityClassName);
                MethodUtils.startActivityWithAnimation(UID.this, previousActivityClass);
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
