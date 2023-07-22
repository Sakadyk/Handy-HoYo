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

public class HoYoLAB extends AppCompatActivity {
    LinearLayout mimoDash;
    CardView appGi, appHsr, appHi3, appTot, appZzz, appHoyo;
    WebView webView;
    ProgressBar progressBar;
    ImageView webBack, webRefresh, webForward, webHome, webShare;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoyolab);
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

        mimoDash = findViewById(R.id.mimo_dash);

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
        String defaultURL = "https://www.hoyoverse.com/en-us/news";
        MethodUtils.loadMyUrl(webView, defaultURL);

        MethodUtils.setDownload(webView);
        MethodUtils.setLongClickListener(this, webView);

        HorizontalScrollView horizontalScrollView = findViewById(R.id.app_scroll_hoyo);
        // Adjust the scroll position to reverse the scrolling direction
        horizontalScrollView.post(() -> horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT));

        //Web Buttons
        MethodUtils.BrowserBackForward browserBackForward = new MethodUtils.BrowserBackForward(webView, defaultURL);
        webBack.setOnClickListener(view -> browserBackForward.handleBackButton());
        webForward.setOnClickListener(view -> browserBackForward.handleForwardButton());
        webRefresh.setOnClickListener(view -> webView.reload());
        MethodUtils.handleHomeButton(webView, webHome, defaultURL);
        webShare.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
            startActivity(Intent.createChooser(intent, "Share URL"));
        });

        //Features (lol)
        mimoDash.setOnClickListener(view -> MethodUtils.loadMyUrl(webView, "https://act.hoyolab.com/bbs/event/e20220401-april-fools/index.html"));

        //App Buttons
        appGi.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(HoYoLAB.this, Genshin.class));
        appHsr.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(HoYoLAB.this, HonkaiStarRail.class));
        appHi3.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(HoYoLAB.this, Honkai3rd.class));
        appTot.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(HoYoLAB.this, TearsOfThemis.class));
        appZzz.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(HoYoLAB.this, ZenlessZoneZero.class));
        appHoyo.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Already in it", Toast.LENGTH_SHORT).show());
    }
}