package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UID extends AppCompatActivity {
    EditText uid_gi, uid_hsr, uid_hi3;
    ImageView copy_gi, copy_hsr, copy_hi3, clear_gi, clear_hsr, clear_hi3, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uid);
        getWindow().setStatusBarColor(ContextCompat.getColor(UID.this, R.color.genshin));

        copy_gi = findViewById(R.id.copy_uid_gi);
        copy_hsr = findViewById(R.id.copy_uid_hsr);
        copy_hi3 = findViewById(R.id.copy_uid_hi3);
        clear_gi = findViewById(R.id.clear_uid_gi);
        clear_hsr = findViewById(R.id.clear_uid_hsr);
        clear_hi3 = findViewById(R.id.clear_uid_hi3);
        uid_gi = findViewById(R.id.uid_gi);
        uid_hsr = findViewById(R.id.uid_hsr);
        uid_hi3 = findViewById(R.id.uid_hi3);
        back = findViewById(R.id.web_back);

        // Retrieve the saved values from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String uidGiText = sharedPreferences.getString("uid_gi", "");
        String uidHsrText = sharedPreferences.getString("uid_hsr", "");
        String uidHi3Text = sharedPreferences.getString("uid_hi3", "");

        // Set the retrieved values back to the EditText fields
        uid_gi.setText(uidGiText);
        uid_hsr.setText(uidHsrText);
        uid_hi3.setText(uidHi3Text);

        // Add TextWatcher to uid_gi EditText
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

        // Add TextWatcher to uid_hsr EditText
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

        // Add TextWatcher to uid_hi3 EditText
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

        copy_gi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from uid_gi EditText
                String uidGiText = uid_gi.getText().toString();

                // Check if the EditText is empty
                if (uidGiText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No UID to copy", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Copy the text to the clipboard
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied UID", uidGiText);
                clipboard.setPrimaryClip(clip);

                // Display a Toast message
                Toast.makeText(getApplicationContext(), "UID copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        clear_gi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear the text of uid_gi EditText
                uid_gi.setText("");
            }
        });

        copy_hsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from uid_hsr EditText
                String uidHsrText = uid_hsr.getText().toString();

                // Check if the EditText is empty
                if (uidHsrText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No UID to copy", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Copy the text to the clipboard
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied UID", uidHsrText);
                clipboard.setPrimaryClip(clip);

                // Display a Toast message
                Toast.makeText(getApplicationContext(), "UID copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        clear_hsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear the text of uid_gi EditText
                uid_hsr.setText("");
            }
        });

        copy_hi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from uid_hi3 EditText
                String uidHi3Text = uid_hi3.getText().toString();

                // Check if the EditText is empty
                if (uidHi3Text.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No UID to copy", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Copy the text to the clipboard
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied UID", uidHi3Text);
                clipboard.setPrimaryClip(clip);

                // Display a Toast message
                Toast.makeText(getApplicationContext(), "UID copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        clear_hi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear the text of uid_gi EditText
                uid_hi3.setText("");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the class name of the previous activity
                String previousActivityClassName = getIntent().getStringExtra("previousActivity");

                if (previousActivityClassName != null) {
                    try {
                        // Create an Intent for the previous activity using its class name
                        Class<?> previousActivityClass = Class.forName(previousActivityClassName);
                        Intent intent = new Intent(UID.this, previousActivityClass);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
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
                Intent intent = new Intent(UID.this, previousActivityClass);
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
