package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.myapplication.Methods.MethodUtils;

public class ZenlessZoneZero extends AppCompatActivity {
    LinearLayout signUp;
    CardView appGi, appHsr, appHi3, appTot, appZzz, appHoyo;
    WebView webView;
    ProgressBar progressBar;
    ImageView webBack, webRefresh, webForward, webHome, webShare;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zenless_zone_zero);
        MethodUtils.transparentNavBar(this);

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

        signUp = findViewById(R.id.signup_zzz);

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
        String defaultUrl = "https://zenless.hoyoverse.com/m/en-us/news";
        MethodUtils.loadMyUrl(webView, defaultUrl);

        MethodUtils.setDownload(webView);
        MethodUtils.setLongClickListener(this, webView);

        HorizontalScrollView horizontalScrollView = findViewById(R.id.app_scroll_zzz);
        // Adjust the scroll position to reverse the scrolling direction
        horizontalScrollView.post(() -> horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT));

        //Buttons
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

        //Features (lol)
        signUp.setOnClickListener(view -> MethodUtils.loadMyUrl(webView, "https://zenless.hoyoverse.com/m/en-us"));

        //App Buttons
        appGi.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(ZenlessZoneZero.this, Genshin.class));
        appHsr.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(ZenlessZoneZero.this, HonkaiStarRail.class));
        appHi3.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(ZenlessZoneZero.this, Honkai3rd.class));
        appTot.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(ZenlessZoneZero.this, TearsOfThemis.class));
        appZzz.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Already in it", Toast.LENGTH_SHORT).show());
        appHoyo.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(ZenlessZoneZero.this, HoYoLAB.class));
    }
}