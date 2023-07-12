package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RedemptionCode extends AppCompatActivity {
    Button  daily, uid;
    CardView gi, hsr, hi3;
    EditText code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redemption_code);
        getWindow().setStatusBarColor(ContextCompat.getColor(RedemptionCode.this, R.color.genshin));

        gi = findViewById(R.id.gi);
        hsr = findViewById(R.id.hsr);
        hi3 = findViewById(R.id.hi3);
        daily = findViewById(R.id.daily_login);
        uid = findViewById(R.id.uid);
        code = findViewById(R.id.code);

        gi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://genshin.hoyoverse.com/en/gift?code=" + code.getText().toString();
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                view.getContext().startActivity(intent);
            }
        });

        hsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = code.getText().toString();
                if (!text.isEmpty()) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copied Code", text);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), "Code copied to clipboard", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Nothing to copy", Toast.LENGTH_SHORT).show();
                }

                String url = "https://hsr.hoyoverse.com/gift";
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                view.getContext().startActivity(intent);
            }
        });

        hi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Redemption via web unavailable.", Toast.LENGTH_SHORT).show();
            }
        });

        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redeem_code = new Intent (RedemptionCode.this, DailyLogin.class);
                startActivity(redeem_code);
                finish();
            }
        });

        uid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redeem_code = new Intent (RedemptionCode.this, UID.class);
                startActivity(redeem_code);
                finish();
            }
        });
    }
}