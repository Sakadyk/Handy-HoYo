package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Honkai3rd extends AppCompatActivity {
    LinearLayout checkIn, userId, battle, wiki;
    CardView appGi, appHsr, appHi3, appZzz;
    WebView webView;
    ImageView webBack, webRefresh, webForward;
    private int backButtonClickCount = 0;
    private long backButtonLastClickTime = 0;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honkai3rd);
        getWindow().setStatusBarColor(ContextCompat.getColor(Honkai3rd.this, R.color.genshin));

        webView = findViewById(R.id.web_view);
        webBack = findViewById(R.id.web_back);
        webForward = findViewById(R.id.web_forward);
        webRefresh = findViewById(R.id.web_refresh);

        appGi = findViewById(R.id.gi);
        appHsr = findViewById(R.id.hsr);
        appHi3 = findViewById(R.id.hi3);
        appZzz = findViewById(R.id.zzz);

        checkIn = findViewById(R.id.check_in_hi3);
        userId = findViewById(R.id.uid_hi3);
        battle = findViewById(R.id.battle_hi3);
        wiki = findViewById(R.id.wiki_hi3);

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
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    long currentTime = System.currentTimeMillis();
                    long elapsedTime = currentTime - backButtonLastClickTime;
                    if (elapsedTime < 1000) { // Check if the button is pressed within 1 second
                        backButtonClickCount++;
                    } else {
                        backButtonClickCount = 1;
                    }
                    backButtonLastClickTime = currentTime;
                    if (backButtonClickCount >= 3) { // Check if the button is pressed 3 or more times consecutively
                        Toast.makeText(getApplicationContext(), "CAN NO LONGER GO BACK", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Can no longer go back", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        webForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoForward()) {
                    webView.goForward();
                } else {
                    long currentTime = System.currentTimeMillis();
                    long elapsedTime = currentTime - backButtonLastClickTime;
                    if (elapsedTime < 1000) { // Check if the button is pressed within 1 second
                        backButtonClickCount++;
                    } else {
                        backButtonClickCount = 1;
                    }
                    backButtonLastClickTime = currentTime;
                    if (backButtonClickCount >= 3) { // Check if the button is pressed 3 or more times consecutively
                        Toast.makeText(getApplicationContext(), "CAN NO LONGER GO FORWARD", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Can no longer go forward", Toast.LENGTH_SHORT).show();
                    }
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
                //overridePendingTransition(0, 0);
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

        wiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://honkaiimpact3.fandom.com/";
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                intent.putExtra("previousActivity", Honkai3rd.class.getName());
                view.getContext().startActivity(intent);
            }
        });

        appHi3.setOnTouchListener(new View.OnTouchListener() {
            private Handler handler;
            private Runnable runnable;
            private boolean isLongClick = false;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler = new Handler();
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                // Action to perform after long click duration (2 seconds)
                                isLongClick = true;
                                // Perform your desired action here
                                Intent intent = new Intent(Honkai3rd.this, SauceMaster.class);
                                intent.putExtra("previousActivity", Genshin.class.getName());
                                startActivity(intent);
                                finish();
                            }
                        };
                        handler.postDelayed(runnable, 2000); // Set long click duration (2 seconds)
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (!isLongClick) {
                            Toast.makeText(getApplicationContext(), "Already in it", Toast.LENGTH_SHORT).show();
                        }
                        if (handler != null && runnable != null) {
                            handler.removeCallbacks(runnable);
                        }
                        isLongClick = false;
                        return true;
                }
                return false;
            }
        });

        appGi.setOnTouchListener(new View.OnTouchListener() {
            private Handler handler;
            private Runnable runnable;
            private boolean isLongClick = false;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler = new Handler();
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                // Action to perform after long click duration (2 seconds)
                                isLongClick = true;
                                // Perform your desired action here
                                Intent intent = new Intent(Honkai3rd.this, SauceMaster.class);
                                intent.putExtra("previousActivity", Genshin.class.getName());
                                startActivity(intent);
                                finish();
                            }
                        };
                        handler.postDelayed(runnable, 2000); // Set long click duration (2 seconds)
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (!isLongClick) {
                            Intent intent = new Intent(Honkai3rd.this, Genshin.class);
                            intent.putExtra("previousActivity", Genshin.class.getName());
                            startActivity(intent);
                            finish();
                        }
                        if (handler != null && runnable != null) {
                            handler.removeCallbacks(runnable);
                        }
                        isLongClick = false;
                        return true;
                }
                return false;
            }
        });

        appHsr.setOnTouchListener(new View.OnTouchListener() {
            private Handler handler;
            private Runnable runnable;
            private boolean isLongClick = false;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler = new Handler();
                        runnable = new Runnable() {
                            @SuppressLint("ClickableViewAccessibility")
                            @Override
                            public void run() {
                                // Action to perform after long click duration (2 seconds)
                                isLongClick = true;
                                // Perform your desired action here
                                Intent intent = new Intent(Honkai3rd.this, SauceMaster.class);
                                intent.putExtra("previousActivity", Genshin.class.getName());
                                startActivity(intent);
                                finish();
                            }
                        };
                        handler.postDelayed(runnable, 2000); // Set long click duration (2 seconds)
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (!isLongClick) {
                            Intent intent = new Intent(Honkai3rd.this, HonkaiStarRail.class);
                            intent.putExtra("previousActivity", Genshin.class.getName());
                            startActivity(intent);
                            finish();
                        }
                        if (handler != null && runnable != null) {
                            handler.removeCallbacks(runnable);
                        }
                        isLongClick = false;
                        return true;
                }
                return false;
            }
        });

        appZzz.setOnTouchListener(new View.OnTouchListener() {
            private Handler handler;
            private Runnable runnable;
            private boolean isLongClick = false;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler = new Handler();
                        runnable = new Runnable() {
                            @SuppressLint("ClickableViewAccessibility")
                            @Override
                            public void run() {
                                // Action to perform after long click duration (2 seconds)
                                isLongClick = true;
                                // Perform your desired action here
                                Intent intent = new Intent(Honkai3rd.this, SauceMaster.class);
                                intent.putExtra("previousActivity", Genshin.class.getName());
                                startActivity(intent);
                                finish();
                            }
                        };
                        handler.postDelayed(runnable, 2000); // Set long click duration (2 seconds)
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (!isLongClick) {
                            Intent intent = new Intent(Honkai3rd.this, ZenlessZoneZero.class);
                            intent.putExtra("previousActivity", Genshin.class.getName());
                            startActivity(intent);
                            finish();
                        }
                        if (handler != null && runnable != null) {
                            handler.removeCallbacks(runnable);
                        }
                        isLongClick = false;
                        return true;
                }
                return false;
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