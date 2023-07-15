package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Objects;

public class ZenlessZoneZero extends AppCompatActivity {
    LinearLayout signUp;
    CardView appGi, appHsr, appHi3, appTot, appZzz, appHoyo;
    WebView webView;
    ProgressBar progressBar;
    ImageView webBack, webRefresh, webForward, webHome, webShare;
    private int backButtonClickCount = 0;
    private long backButtonLastClickTime = 0;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zenless_zone_zero);
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

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });
        loadMyUrl("https://zenless.hoyoverse.com/m/en-us/news");

        HorizontalScrollView horizontalScrollView = findViewById(R.id.app_scroll_zzz);
        // Adjust the scroll position to reverse the scrolling direction
        horizontalScrollView.post(() -> horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT));

        webBack.setOnClickListener(new View.OnClickListener() {
            String urlHome = webView.getUrl();
            @Override
            public void onClick(View view) {
                if (!Objects.equals(urlHome, "https://zenless.hoyoverse.com/m/en-us/news")) {
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

        webHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMyUrl("https://zenless.hoyoverse.com/m/en-us/news");
            }
        });

        webShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
                startActivity(Intent.createChooser(intent, "Share URL"));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMyUrl("https://zenless.hoyoverse.com/m/en-us");
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
                            @Override
                            public void run() {
                                // Action to perform after long click duration (2 seconds)
                                isLongClick = true;
                                // Perform your desired action here
                                Intent intent = new Intent(ZenlessZoneZero.this, SauceMaster.class);
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
                                Intent intent = new Intent(ZenlessZoneZero.this, SauceMaster.class);
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
                            Intent intent = new Intent(ZenlessZoneZero.this, Genshin.class);
                            intent.putExtra("previousActivity", Genshin.class.getName());
                            startActivity(intent);
                            overridePendingTransition(0, 0);
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
                            @Override
                            public void run() {
                                // Action to perform after long click duration (2 seconds)
                                isLongClick = true;
                                // Perform your desired action here
                                Intent intent = new Intent(ZenlessZoneZero.this, SauceMaster.class);
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
                            Intent intent = new Intent(ZenlessZoneZero.this, HonkaiStarRail.class);
                            intent.putExtra("previousActivity", Genshin.class.getName());
                            startActivity(intent);
                            overridePendingTransition(0, 0);
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
                                Intent intent = new Intent(ZenlessZoneZero.this, SauceMaster.class);
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
                            Intent intent = new Intent(ZenlessZoneZero.this, Honkai3rd.class);
                            intent.putExtra("previousActivity", Genshin.class.getName());
                            startActivity(intent);
                            overridePendingTransition(0, 0);
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

        appTot.setOnTouchListener(new View.OnTouchListener() {
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
                                Intent intent = new Intent(ZenlessZoneZero.this, SauceMaster.class);
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
                            Intent intent = new Intent(ZenlessZoneZero.this, TearsOfThemis.class);
                            intent.putExtra("previousActivity", Genshin.class.getName());
                            startActivity(intent);
                            overridePendingTransition(0, 0);
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

        appHoyo.setOnTouchListener(new View.OnTouchListener() {
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
                                Intent intent = new Intent(ZenlessZoneZero.this, SauceMaster.class);
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
                            Intent intent = new Intent(ZenlessZoneZero.this, HoYoLAB.class);
                            intent.putExtra("previousActivity", Genshin.class.getName());
                            startActivity(intent);
                            overridePendingTransition(0, 0);
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
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            // Handle the error, e.g., display an error message or try loading an alternative URL.
        }
    }
}