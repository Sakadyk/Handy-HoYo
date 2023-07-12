package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Honkai3rd extends AppCompatActivity {
    LinearLayout checkIn, userId, battle;
    CardView app_gi, app_hsr, app_hi3;
    WebView webView;
    ImageView webBack, webRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honkai3rd);
        getWindow().setStatusBarColor(ContextCompat.getColor(Honkai3rd.this, R.color.genshin));

        webView = findViewById(R.id.web_view);
        webBack = findViewById(R.id.web_back);
        webRefresh = findViewById(R.id.web_refresh);

        app_gi = findViewById(R.id.gi);
        app_hsr = findViewById(R.id.hsr);
        app_hi3 = findViewById(R.id.hi3);

        checkIn = findViewById(R.id.check_in_hi3);
        userId = findViewById(R.id.uid_hi3);
        battle = findViewById(R.id.battle_hi3);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        WebView.setWebContentsDebuggingEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        loadMyUrl("https://honkaiimpact3.hoyoverse.com/global/en-us/news");

        webBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(webView.canGoBack()){
                    webView.goBack();
                }
            }
        });

        webRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.reload();
            }
        });

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://act.hoyolab.com/bbs/event/signin-bh3/index.html?act_id=e202110291205111";
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                intent.putExtra("previousActivity", Honkai3rd.class.getName());
                view.getContext().startActivity(intent);
            }
        });

        userId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Honkai3rd.this, UID.class);
                intent.putExtra("previousActivity", Honkai3rd.class.getName());
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://act.hoyolab.com/app/community-game-records-sea/index.html?bbs_presentation_style=fullscreen&bbs_auth_required=true&gid=1&utm_source=hoyolab&utm_medium=tools&bbs_theme=dark&bbs_theme_device=1#/bh3";
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                intent.putExtra("previousActivity", Honkai3rd.class.getName());
                view.getContext().startActivity(intent);
            }
        });

        app_gi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Honkai3rd.this, Genshin.class);
                intent.putExtra("previousActivity", Genshin.class.getName());
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        app_hsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Honkai3rd.this, HonkaiStarRail.class);
                intent.putExtra("previousActivity", Genshin.class.getName());
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    void loadMyUrl(String url){
        boolean matchUrl = Patterns.WEB_URL.matcher(url).matches();
        if(matchUrl){
            webView.loadUrl(url);
        }else{
            webView.loadUrl("google.com/search?q="+url);
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            // Handle the error, e.g., display an error message or try loading an alternative URL.
        }
    }
}