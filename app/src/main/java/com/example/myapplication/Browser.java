package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Browser extends AppCompatActivity {
    EditText urlInput;
    ImageView clearUrl;
    WebView webView;
    ProgressBar progressBar;
    ImageView webBack,webForward,webRefresh,webShare,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        getWindow().setStatusBarColor(ContextCompat.getColor(Browser.this, R.color.genshin));

        urlInput = findViewById(R.id.url_input);
        clearUrl = findViewById(R.id.clear_icon);
        progressBar = findViewById(R.id.progress_bar);
        webView = findViewById(R.id.web_view);
        back = findViewById(R.id.power_off);

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

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });

        // Retrieve the URL from the intent
        String url = getIntent().getStringExtra("url");
        // Load the URL into the WebView
        webView.loadUrl(url);

        urlInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_GO || i == EditorInfo.IME_ACTION_DONE){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(urlInput.getWindowToken(),0);
                    loadMyUrl(urlInput.getText().toString());
                    return true;
                }
                return false;
            }
        });

        clearUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlInput.setText("");
            }
        });

        webBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(webView.canGoBack()){
                    webView.goBack();
                } else {
                    Toast.makeText(getApplicationContext(), "Can no longer go back", Toast.LENGTH_SHORT).show();
                }
            }
        });

        webForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(webView.canGoForward()){
                    webView.goForward();
                }
            }
        });

        webRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.reload();
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            urlInput.setText(webView.getUrl());
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