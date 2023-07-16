package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myapplication.Methods.MethodUtils;

public class Browser extends AppCompatActivity {
    EditText urlInput;
    ImageView clearUrl;
    WebView webView;
    ProgressBar progressBar;
    ImageView webBack,webForward,webRefresh,webShare, returnToHome;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        getWindow().setStatusBarColor(ContextCompat.getColor(Browser.this, R.color.nhcolor));

        urlInput = findViewById(R.id.url_input);
        clearUrl = findViewById(R.id.clear_icon);
        progressBar = findViewById(R.id.progress_bar);
        webView = findViewById(R.id.web_view);
        returnToHome = findViewById(R.id.power_off);

        webBack = findViewById(R.id.web_back);
        webForward = findViewById(R.id.web_forward);
        webRefresh = findViewById(R.id.web_refresh);
        webShare = findViewById(R.id.web_share);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        WebView.setWebContentsDebuggingEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        webView.setWebViewClient(new MethodUtils.MyWebViewClientSecret(webView, progressBar, urlInput));
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });

        MethodUtils.setDownload(webView);
        MethodUtils.setLongClickListenerSecret(this, webView);

        // Retrieve the URL from the intent
        String url = getIntent().getStringExtra("url");
        // Load the URL into the WebView
        MethodUtils.loadMyUrl(webView, url);

        //Web Buttons
        urlInput.setOnEditorActionListener((textView, i, keyEvent) -> {
            if(i == EditorInfo.IME_ACTION_GO || i == EditorInfo.IME_ACTION_DONE){
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(urlInput.getWindowToken(),0);
                MethodUtils.loadMyUrl(webView, urlInput.getText().toString());
                return true;
            }
            return false;
        });
        clearUrl.setOnClickListener(view -> urlInput.setText(""));
        MethodUtils.BrowserBackForward browserBackForward = new MethodUtils.BrowserBackForward(webView, url);
        webBack.setOnClickListener(view -> browserBackForward.handleBackButtonSecret());
        webForward.setOnClickListener(view -> browserBackForward.handleForwardButton());
        webRefresh.setOnClickListener(view -> webView.reload());
        webShare.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
            startActivity(Intent.createChooser(intent, "Share URL"));
        });
        returnToHome.setOnClickListener(view -> {
            // Get the class name of the previous activity
            String previousActivityClassName = getIntent().getStringExtra("previousActivity");

            if (previousActivityClassName != null) {
                try {
                    // Create an Intent for the previous activity using its class name
                    Class<?> previousActivityClass = Class.forName(previousActivityClassName);
                    Intent intent = new Intent(Browser.this, previousActivityClass);
                    startActivity(intent);
                    webView.clearHistory();
                    finish();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            // Get the class name of the previous activity
            String previousActivityClassName = getIntent().getStringExtra("previousActivity");
            if (previousActivityClassName != null) {
                try {
                    // Create an Intent for the previous activity using its class name
                    webView.clearHistory();
                    Class<?> previousActivityClass = Class.forName(previousActivityClassName);
                    MethodUtils.startActivityWithAnimation(Browser.this, previousActivityClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                super.onBackPressed(); // If no previous activity specified, perform default back button behavior
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        if (view != null && event.getAction() == MotionEvent.ACTION_DOWN) {
            int[] coordinates = new int[2];
            view.getLocationOnScreen(coordinates);
            float x = event.getRawX() + view.getLeft() - coordinates[0];
            float y = event.getRawY() + view.getTop() - coordinates[1];

            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                view.clearFocus();
            }
        }
        return super.dispatchTouchEvent(event);
    }
}