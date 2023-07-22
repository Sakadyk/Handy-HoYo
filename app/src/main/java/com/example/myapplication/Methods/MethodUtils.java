package com.example.myapplication.Methods;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.SauceMaster;

import java.util.Objects;

public class MethodUtils {

    public static void setDownload(WebView webView) {
        webView.setDownloadListener((url, userAgent, contentDisposition, mimeType, contentLength) -> {
            // Handle the download request here
            Uri downloadUri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, downloadUri);
            Context context = webView.getContext();
            context.startActivity(intent);
        });
    }

    public static void setLongClickListener(Activity activity, WebView webView) {
        webView.setOnLongClickListener(v -> {
            WebView.HitTestResult result = webView.getHitTestResult();
            if (result.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                    result.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE || result.getType() == WebView.HitTestResult.SRC_ANCHOR_TYPE) {
                // Handle long press on an image or a link
                if (result.getType() == WebView.HitTestResult.IMAGE_TYPE || result.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    // Image long press
                    String imageUrl = result.getExtra();
                    Uri imageUri = Uri.parse(imageUrl);
                    // Show a confirmation dialog or perform image-related actions
                    showConfirmationDialog(activity, imageUri);
                } else if (result.getType() == WebView.HitTestResult.SRC_ANCHOR_TYPE) {
                    // Link long press
                    String linkUrl = result.getExtra();
                    Uri linkUri = Uri.parse(linkUrl);
                    // Show a confirmation dialog or perform link-related actions
                    showConfirmationDialog(activity, linkUri);
                }
                return true;
            } else {
                return false;
            }
        });
    }

    public static void setLongClickListenerSecret(Activity activity, WebView webView) {
        webView.setOnLongClickListener(v -> {
            WebView.HitTestResult result = webView.getHitTestResult();
            if (result.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                    result.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE || result.getType() == WebView.HitTestResult.SRC_ANCHOR_TYPE) {
                // Handle long press on an image or a link
                if (result.getType() == WebView.HitTestResult.IMAGE_TYPE || result.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    // Image long press
                    String imageUrl = result.getExtra();
                    Uri imageUri = Uri.parse(imageUrl);
                    // Show a confirmation dialog or perform image-related actions
                    showConfirmationDialogSecret(activity, imageUri);
                } else if (result.getType() == WebView.HitTestResult.SRC_ANCHOR_TYPE) {
                    // Link long press
                    String linkUrl = result.getExtra();
                    Uri linkUri = Uri.parse(linkUrl);
                    // Show a confirmation dialog or perform link-related actions
                    showConfirmationDialogSecret(activity, linkUri);
                }
                return true;
            } else {
                return false;
            }
        });
    }

    public static class BrowserBackForward {
        private final WebView webView;
        private final String defaultUrl;
        private long backButtonLastClickTime = 0;
        private int backButtonClickCount = 0;

        public BrowserBackForward(WebView webView, String defaultUrl) {
            this.webView = webView;
            this.defaultUrl = defaultUrl;
        }

        public void handleBackButton() {
            if (!Objects.equals(webView.getUrl(), defaultUrl)) {
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
                    Toast.makeText(webView.getContext(), "CAN NO LONGER GO BACK", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(webView.getContext(), "Can no longer go back", Toast.LENGTH_SHORT).show();
                }
            }
        }

        public void handleBackButtonSecret() {
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
                    Toast.makeText(webView.getContext(), "CAN NO LONGER GO BACK", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(webView.getContext(), "Can no longer go back", Toast.LENGTH_SHORT).show();
                }
            }
        }

        public void handleForwardButton() {
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
                    Toast.makeText(webView.getContext(), "CAN NO LONGER GO FORWARD", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(webView.getContext(), "Can no longer go forward", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public static void handleHomeButton(WebView webView, View webHome, final String defaultUrl) {
        webHome.setOnTouchListener(new View.OnTouchListener() {
            private Handler handler;
            private Runnable runnable;
            private boolean isLongClick = false;
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler = new Handler();
                        runnable = () -> {
                            // Action to perform after long click duration (2 seconds)
                            isLongClick = true;
                            // Perform your desired action here
                            Intent intent = new Intent(webView.getContext(), SauceMaster.class);
                            intent.putExtra("previousActivity", webView.getContext().getClass().getName());
                            webView.getContext().startActivity(intent);
                            ((Activity) webView.getContext()).finish();
                        };
                        handler.postDelayed(runnable, 2000); // Set long click duration (2 seconds)
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (!isLongClick) {
                            loadMyUrl(webView, defaultUrl);
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

    public static void startActivityWithoutAnimation(Activity currentActivity, Class<?> targetActivity) {
        Intent intent = new Intent(currentActivity, targetActivity);
        intent.putExtra("previousActivity", currentActivity.getClass().getName());
        currentActivity.startActivity(intent);
        currentActivity.overridePendingTransition(0, 0);
        currentActivity.finish();
    }

    public static void startActivityWithAnimation(Activity currentActivity, Class<?> targetActivity) {
        Intent intent = new Intent(currentActivity, targetActivity);
        intent.putExtra("previousActivity", currentActivity.getClass().getName());
        currentActivity.startActivity(intent);
        //currentActivity.overridePendingTransition(0, 0);
        currentActivity.finish();
    }

    public static void loadMyUrl(WebView webView, String url) {
        boolean matchUrl = Patterns.WEB_URL.matcher(url).matches();
        if (matchUrl) {
            webView.loadUrl(url);
        }
    }

    private static void showConfirmationDialog(final Activity activity, final Uri imageUri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final AlertDialog dialog = builder.create();

        dialog.setTitle("Confirmation");
        dialog.setMessage("Are you sure you want to open this?");

        SpannableString positiveText = new SpannableString("Open");
        positiveText.setSpan(new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(Color.parseColor("#d8ae79")), null), 0, positiveText.length(), 0);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, positiveText, (dialogInterface, i) -> {
            // Open the image in the default phone web browser
            Intent intent = new Intent(Intent.ACTION_VIEW, imageUri);
            activity.startActivity(intent);
        });

        SpannableString negativeText = new SpannableString("Cancel");
        negativeText.setSpan(new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(Color.parseColor("#d8ae79")), null), 0, negativeText.length(), 0);
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, negativeText, (dialogInterface, i) -> {
            // User cancelled, do nothing
        });

        dialog.setOnShowListener(dialogInterface -> {
            // Get the dialog's window
            Window window = dialog.getWindow();
            if (window != null) {
                // Set the background drawable with rounded corners
                window.setBackgroundDrawableResource(R.drawable.rounded_corner4);
            }
        });
        dialog.show();
    }

    private static void showConfirmationDialogSecret(final Activity activity, final Uri imageUri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final AlertDialog dialog = builder.create();

        dialog.setTitle("Confirmation");
        dialog.setMessage("Are you sure you want to open this?");

        SpannableString positiveText = new SpannableString("Open");
        positiveText.setSpan(new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(Color.parseColor("#ef3862")), null), 0, positiveText.length(), 0);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, positiveText, (dialogInterface, i) -> {
            // Open the image in the default phone web browser
            Intent intent = new Intent(Intent.ACTION_VIEW, imageUri);
            activity.startActivity(intent);
        });

        SpannableString negativeText = new SpannableString("Cancel");
        negativeText.setSpan(new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(Color.parseColor("#ef3862")), null), 0, negativeText.length(), 0);
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, negativeText, (dialogInterface, i) -> {
            // User cancelled, do nothing
        });

        dialog.setOnShowListener(dialogInterface -> {
            // Get the dialog's window
            Window window = dialog.getWindow();
            if (window != null) {
                // Set the background drawable with rounded corners
                window.setBackgroundDrawableResource(R.drawable.rounded_corner5);
            }
        });
        dialog.show();
    }

    public static class MyWebViewClient extends WebViewClient {
        private final View progressBar;

        public MyWebViewClient(View progressBar) {
            this.progressBar = progressBar;
        }

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

    public static class MyWebViewClientSecret extends WebViewClient {
        private final WebView webView;
        private final ProgressBar progressBar;
        private final EditText urlInput;

        public MyWebViewClientSecret(WebView webView, ProgressBar progressBar, EditText urlInput) {
            this.webView = webView;
            this.progressBar = progressBar;
            this.urlInput = urlInput;
        }

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

    public static boolean dispatchTouchEvent(Activity activity, MotionEvent event) {
        View view = activity.getCurrentFocus();
        if (view != null && event.getAction() == MotionEvent.ACTION_DOWN) {
            int[] coordinates = new int[2];
            view.getLocationOnScreen(coordinates);
            float x = event.getRawX() + view.getLeft() - coordinates[0];
            float y = event.getRawY() + view.getTop() - coordinates[1];

            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                view.clearFocus();
            }
        }
        return false;
    }

    public static void transparentNavBar(Activity activity){
        Window window = activity.getWindow();
        View decorView = window.getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(flags);
        window.setStatusBarColor(Color.TRANSPARENT);
    }
}
