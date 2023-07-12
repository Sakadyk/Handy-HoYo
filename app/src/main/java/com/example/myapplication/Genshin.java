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

public class Genshin extends AppCompatActivity {
    LinearLayout checkIn, redeemCode, userId, battle, map, wiki, kqm;
    CardView app_gi, app_hsr, app_hi3;
    WebView webView;
    ImageView webBack, webRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genshin);
        getWindow().setStatusBarColor(ContextCompat.getColor(Genshin.this, R.color.genshin));

        webView = findViewById(R.id.web_view);
        webBack = findViewById(R.id.web_back);
        webRefresh = findViewById(R.id.web_refresh);

        app_gi = findViewById(R.id.gi);
        app_hsr = findViewById(R.id.hsr);
        app_hi3 = findViewById(R.id.hi3);

        checkIn = findViewById(R.id.check_in_gi);
        redeemCode = findViewById(R.id.redeem_code_gi);
        userId = findViewById(R.id.uid_gi);
        battle = findViewById(R.id.battle_gi);
        map = findViewById(R.id.map_gi);
        wiki = findViewById(R.id.wiki_gi);
        kqm = findViewById(R.id.keqing_mains);

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
        loadMyUrl("https://genshin.hoyoverse.com/en/news");

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
                String url = "https://act.hoyolab.com/ys/event/signin-sea-v3/index.html?act_id=e202102251931481";
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                intent.putExtra("previousActivity", Genshin.class.getName());
                view.getContext().startActivity(intent);
            }
        });

        redeemCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://genshin.hoyoverse.com/en/gift";
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                intent.putExtra("previousActivity", Genshin.class.getName());
                view.getContext().startActivity(intent);
            }
        });

        userId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Genshin.this, UID.class);
                intent.putExtra("previousActivity", Genshin.class.getName());
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://act.hoyolab.com/app/community-game-records-sea/m.html";
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                intent.putExtra("previousActivity", Genshin.class.getName());
                view.getContext().startActivity(intent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://act.hoyolab.com/ys/app/interactive-map/index.html";
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                intent.putExtra("previousActivity", Genshin.class.getName());
                view.getContext().startActivity(intent);
            }
        });

        wiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://genshin-impact.fandom.com/";
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                intent.putExtra("previousActivity", Genshin.class.getName());
                view.getContext().startActivity(intent);
            }
        });

        kqm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://keqingmains.com/";
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                intent.putExtra("previousActivity", Genshin.class.getName());
                view.getContext().startActivity(intent);
            }
        });

        app_hsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Genshin.this, HonkaiStarRail.class);
                intent.putExtra("previousActivity", Genshin.class.getName());
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        app_hi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Genshin.this, Honkai3rd.class);
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