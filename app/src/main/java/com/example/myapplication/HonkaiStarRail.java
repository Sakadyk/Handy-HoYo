package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.Methods.MethodUtils;

import java.util.Objects;

public class HonkaiStarRail extends AppCompatActivity {
    LinearLayout checkIn, redeemCode, userId, battle, map, wiki, kqm, enka;
    CardView appGi, appHsr, appHi3, appTot, appZzz, appHoyo;
    WebView webView;
    ProgressBar progressBar;
    ImageView webBack, webRefresh, webForward, webHome, webShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honkai_star_rail);
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

        checkIn = findViewById(R.id.check_in_hsr);
        redeemCode = findViewById(R.id.redeem_code_hsr);
        userId = findViewById(R.id.uid_hsr);
        battle = findViewById(R.id.battle_hsr);
        map = findViewById(R.id.map_hsr);
        wiki = findViewById(R.id.wiki_hsr);
        kqm = findViewById(R.id.kqm_hsr);
        enka = findViewById(R.id.enka_hsr);

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
        String defaultUrl = "https://pom.moe/timeline";
        MethodUtils.loadMyUrl(webView, defaultUrl);

        MethodUtils.setDownload(webView);
        MethodUtils.setLongClickListener(this, webView);

        //Buttons
        MethodUtils.BrowserBackForward browserBackForward = new MethodUtils.BrowserBackForward(webView, defaultUrl);
        webBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browserBackForward.handleBackButton();
            }
        });
        webForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browserBackForward.handleForwardButton();
            }
        });
        webRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.reload();
            }
        });
        MethodUtils.handleHomeButton(webView, webHome, defaultUrl);
        webShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
                startActivity(Intent.createChooser(intent, "Share URL"));
            }
        });

        //Features
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MethodUtils.loadMyUrl(webView, "https://act.hoyolab.com/bbs/event/signin/hkrpg/index.html?act_id=e202303301540311");
            }
        });
        redeemCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MethodUtils.loadMyUrl(webView, "https://hsr.hoyoverse.com/gift");
            }
        });
        userId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MethodUtils.startActivityWithAnimation(HonkaiStarRail.this, UID.class);
            }
        });
        battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MethodUtils.loadMyUrl(webView, "https://act.hoyolab.com/app/community-game-records-sea/m.html#/hsr");
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MethodUtils.loadMyUrl(webView, "https://act.hoyolab.com/sr/app/interactive-map/index.html");
            }
        });
        wiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MethodUtils.loadMyUrl(webView, "https://honkai-star-rail.fandom.com/");
            }
        });
        kqm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MethodUtils.loadMyUrl(webView, "https://hsr.keqingmains.com/");
            }
        });
        enka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String uidHsrText = sharedPreferences.getString("uid_hsr", "");

                String url;
                if (!uidHsrText.isEmpty()) { url = "https://enka.network/u/" + uidHsrText;
                } else {
                    url = "https://enka.network";
                }
                loadMyUrl(url);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("uid_hsr", uidHsrText);
                editor.apply();*/
                Toast.makeText(getApplicationContext(), "Coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        //App Buttons
        appGi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MethodUtils.startActivityWithoutAnimation(HonkaiStarRail.this, Genshin.class);
            }
        });
        appHsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Already in it", Toast.LENGTH_SHORT).show();
            }
        });
        appHi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MethodUtils.startActivityWithoutAnimation(HonkaiStarRail.this, Honkai3rd.class);
            }
        });
        appTot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MethodUtils.startActivityWithoutAnimation(HonkaiStarRail.this, TearsOfThemis.class);
            }
        });
        appZzz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MethodUtils.startActivityWithoutAnimation(HonkaiStarRail.this, ZenlessZoneZero.class);
            }
        });
        appHoyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MethodUtils.startActivityWithoutAnimation(HonkaiStarRail.this, HoYoLAB.class);
            }
        });
    }
}