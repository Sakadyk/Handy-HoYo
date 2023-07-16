package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.myapplication.Methods.MethodUtils;

public class Genshin extends AppCompatActivity {
    LinearLayout checkIn, redeemCode, userId, battle, map, wiki, calc, kqm, enka;
    CardView appGi, appHsr, appHi3, appTot, appZzz, appHoyo;
    WebView webView;
    ProgressBar progressBar;
    ImageView webBack, webRefresh, webForward, webHome, webShare;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genshin);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        progressBar = findViewById(R.id.progress_bar);
        webView = findViewById(R.id.web_view);
        webBack = findViewById(R.id.web_back);
        webForward = findViewById(R.id.web_forward);
        webRefresh = findViewById(R.id.web_refresh);
        webHome = findViewById(R.id.web_home);
        webShare = findViewById(R.id.web_share);

        appGi = findViewById(R.id.gi);
        appHsr = findViewById(R.id.hsr);
        appHi3 = findViewById(R.id.hi3);
        appTot = findViewById(R.id.tot);
        appZzz = findViewById(R.id.zzz);
        appHoyo = findViewById(R.id.hoyo);

        checkIn = findViewById(R.id.check_in_gi);
        redeemCode = findViewById(R.id.redeem_code_gi);
        userId = findViewById(R.id.uid_gi);
        battle = findViewById(R.id.battle_gi);
        map = findViewById(R.id.map_gi);
        wiki = findViewById(R.id.wiki_gi);
        calc = findViewById(R.id.calc_gi);
        kqm = findViewById(R.id.keqing_mains);
        enka = findViewById(R.id.enka_gi);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        WebView.setWebContentsDebuggingEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        webView.setWebViewClient(new MethodUtils.MyWebViewClient(progressBar));
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });
        String defaultUrl = "https://paimon.moe/timeline";
        MethodUtils.loadMyUrl(webView, defaultUrl);

        MethodUtils.setDownload(webView);
        MethodUtils.setLongClickListener(this, webView);

        //Web Buttons
        MethodUtils.BrowserBackForward browserBackForward = new MethodUtils.BrowserBackForward(webView, defaultUrl);
        webBack.setOnClickListener(view -> browserBackForward.handleBackButton());
        webForward.setOnClickListener(view -> browserBackForward.handleForwardButton());
        webRefresh.setOnClickListener(view -> webView.reload());
        MethodUtils.handleHomeButton(webView, webHome, defaultUrl);
        webShare.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
            startActivity(Intent.createChooser(intent, "Share URL"));
        });

        //Features
        checkIn.setOnClickListener(view -> MethodUtils.loadMyUrl(webView, "https://act.hoyolab.com/ys/event/signin-sea-v3/index.html?act_id=e202102251931481"));
        redeemCode.setOnClickListener(view -> MethodUtils.loadMyUrl(webView, "https://genshin.hoyoverse.com/m/en/gift"));
        userId.setOnClickListener(view -> MethodUtils.startActivityWithAnimation(Genshin.this, UID.class));
        battle.setOnClickListener(view -> MethodUtils.loadMyUrl(webView, "https://act.hoyolab.com/app/community-game-records-sea/m.html"));
        map.setOnClickListener(view -> MethodUtils.loadMyUrl(webView, "https://act.hoyolab.com/ys/app/interactive-map/index.html"));
        wiki.setOnClickListener(view -> MethodUtils.loadMyUrl(webView, "https://genshin-impact.fandom.com/"));
        calc.setOnClickListener(view -> MethodUtils.loadMyUrl(webView, "https://act.hoyolab.com/ys/event/calculator-sea/index.html"));
        kqm.setOnClickListener(view -> MethodUtils.loadMyUrl(webView, "https://keqingmains.com/"));
        enka.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String uidGiText = sharedPreferences.getString("uid_gi", "");

            String url;
            if (!uidGiText.isEmpty()) { url = "https://enka.network/u/" + uidGiText;
            } else {
                url = "https://enka.network";
            }
            MethodUtils.loadMyUrl(webView, url);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("uid_gi", uidGiText);
            editor.apply();
        });

        //App Buttons
        appGi.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Already in it", Toast.LENGTH_SHORT).show());
        appHsr.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(Genshin.this, HonkaiStarRail.class));
        appHi3.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(Genshin.this, Honkai3rd.class));
        appTot.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(Genshin.this, TearsOfThemis.class));
        appZzz.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(Genshin.this, ZenlessZoneZero.class));
        appHoyo.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(Genshin.this, HoYoLAB.class));
    }
}