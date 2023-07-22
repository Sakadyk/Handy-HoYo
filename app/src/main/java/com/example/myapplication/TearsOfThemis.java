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

public class TearsOfThemis extends AppCompatActivity {
    LinearLayout checkIn, redeemCode, userId, wiki;
    CardView appGi, appHsr, appHi3, appTot, appZzz, appHoyo;
    WebView webView;
    ProgressBar progressBar;
    ImageView webBack, webRefresh, webForward, webHome, webShare;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tears_of_themis);
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

        checkIn = findViewById(R.id.check_in_tot);
        redeemCode = findViewById(R.id.redeem_code_tot);
        userId = findViewById(R.id.uid_tot);
        wiki = findViewById(R.id.wiki_tot);

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
        String defaultUrl = "https://tot.hoyoverse.com/m/en-us/information/all";
        MethodUtils.loadMyUrl(webView, defaultUrl);

        MethodUtils.setDownload(webView);
        MethodUtils.setLongClickListener(this, webView);

        HorizontalScrollView horizontalScrollView = findViewById(R.id.app_scroll_tot);
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

        //Features
        checkIn.setOnClickListener(view -> MethodUtils.loadMyUrl(webView, "https://act.hoyolab.com/bbs/event/signin/nxx/index.html?act_id=e202202281857121"));
        redeemCode.setOnClickListener(view -> MethodUtils.loadMyUrl(webView, "https://tot.hoyoverse.com/gift/#/"));
        userId.setOnClickListener(view -> MethodUtils.startActivityWithAnimation(TearsOfThemis.this, UID.class));
        wiki.setOnClickListener(view -> MethodUtils.loadMyUrl(webView, "https://tearsofthemis.fandom.com/"));

        //App Buttons
        appGi.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(TearsOfThemis.this, Genshin.class));
        appHsr.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(TearsOfThemis.this, HonkaiStarRail.class));
        appHi3.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(TearsOfThemis.this, Honkai3rd.class));
        appTot.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Already in it", Toast.LENGTH_SHORT).show());
        appZzz.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(TearsOfThemis.this, ZenlessZoneZero.class));
        appHoyo.setOnClickListener(v -> MethodUtils.startActivityWithoutAnimation(TearsOfThemis.this, HoYoLAB.class));
    }
}