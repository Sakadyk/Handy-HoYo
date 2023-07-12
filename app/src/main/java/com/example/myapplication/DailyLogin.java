package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DailyLogin extends AppCompatActivity {
    Button redeem, uid;
    CardView gi, hsr, hi3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(DailyLogin.this, R.color.genshin));

        gi = findViewById(R.id.gi);
        hsr = findViewById(R.id.hsr);
        hi3 = findViewById(R.id.hi3);
        redeem = findViewById(R.id.redemption_code);
        uid = findViewById(R.id.uid);

        gi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://act.hoyolab.com/ys/event/signin-sea-v3/index.html?act_id=e202102251931481&hyl_auth_required=true&hyl_presentation_style=fullscreen&utm_source=hoyolab&utm_medium=tools&lang=en-us&bbs_theme=dark&bbs_theme_device=1";

                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                view.getContext().startActivity(intent);
            }
        });

        hsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://act.hoyolab.com/bbs/event/signin/hkrpg/index.html?act_id=e202303301540311&hyl_auth_required=true&hyl_presentation_style=fullscreen&utm_source=hoyolab&utm_medium=tools&utm_campaign=checkin&utm_id=6&lang=en-us&bbs_theme=dark&bbs_theme_device=1";

                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                view.getContext().startActivity(intent);
            }
        });

        hi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://act.hoyolab.com/bbs/event/signin-bh3/index.html?act_id=e202110291205111&utm_source=hoyolab&utm_medium=tools&bbs_theme=dark&bbs_theme_device=1";

                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                view.getContext().startActivity(intent);
            }
        });

        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redeem_code = new Intent (DailyLogin.this, RedemptionCode.class);
                startActivity(redeem_code);
                finish();
            }
        });

        uid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redeem_code = new Intent (DailyLogin.this, UID.class);
                startActivity(redeem_code);
                finish();
            }
        });
    }
}