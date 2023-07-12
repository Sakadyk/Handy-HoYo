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

public class HonkaiStarRail extends AppCompatActivity {
    LinearLayout checkIn, redeemCode, userId, battle, map, wiki;
    CardView app_gi, app_hsr, app_hi3;
    WebView webView;
    ImageView webBack, webRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honkai_star_rail);
        getWindow().setStatusBarColor(ContextCompat.getColor(HonkaiStarRail.this, R.color.genshin));

        webView = findViewById(R.id.web_view);
        webBack = findViewById(R.id.web_back);
        webRefresh = findViewById(R.id.web_refresh);

        app_gi = findViewById(R.id.gi);
        app_hsr = findViewById(R.id.hsr);
        app_hi3 = findViewById(R.id.hi3);

        checkIn = findViewById(R.id.check_in_hsr);
        redeemCode = findViewById(R.id.redeem_code_hsr);
        userId = findViewById(R.id.uid_hsr);
        battle = findViewById(R.id.battle_hsr);
        map = findViewById(R.id.map_hsr);
        wiki = findViewById(R.id.wiki_hsr);

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
        loadMyUrl("https://hsr.hoyoverse.com/en-us/news");

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
                String url = "https://act.hoyolab.com/bbs/event/signin/hkrpg/index.html?act_id=e202303301540311";
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                intent.putExtra("previousActivity", HonkaiStarRail.class.getName());
                view.getContext().startActivity(intent);
            }
        });

        redeemCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://hsr.hoyoverse.com/gift";
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                intent.putExtra("previousActivity", HonkaiStarRail.class.getName());
                view.getContext().startActivity(intent);
            }
        });

        userId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (HonkaiStarRail.this, UID.class);
                intent.putExtra("previousActivity", HonkaiStarRail.class.getName());
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://act.hoyolab.com/app/community-game-records-sea/index.html?bbs_auth_required=true&bbs_presentation_style=fullscreen&gid=6&utm_campaign=battlechronicle&utm_id=6&utm_medium=tools&utm_source=hoyolab&v=101&bbs_theme=dark&bbs_theme_device=1#/hsr";
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                intent.putExtra("previousActivity", HonkaiStarRail.class.getName());
                view.getContext().startActivity(intent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://act.hoyolab.com/sr/app/interactive-map/index.html";
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                intent.putExtra("previousActivity", HonkaiStarRail.class.getName());
                view.getContext().startActivity(intent);
            }
        });

        wiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://honkai-star-rail.fandom.com/";
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                intent.putExtra("previousActivity", HonkaiStarRail.class.getName());
                view.getContext().startActivity(intent);
            }
        });

        app_gi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (HonkaiStarRail.this, Genshin.class);
                intent.putExtra("previousActivity", Genshin.class.getName());
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        app_hi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (HonkaiStarRail.this, Honkai3rd.class);
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