package com.example.newsnow;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NewsDetailActivity extends AppCompatActivity {
    public static final String EXTRA_ARTICLE_URL = "extra_article_url";
    public static final String EXTRA_ARTICLE_TITLE = "extra_article_title";

    private WebView webView;
    private ProgressBar pageProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Toolbar toolbar = findViewById(R.id.detailToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String title = getIntent().getStringExtra(EXTRA_ARTICLE_TITLE);
        if (title != null && !title.isEmpty()) {
            toolbar.setTitle(title);
        }

        pageProgressBar = findViewById(R.id.pageProgressBar);
        webView = findViewById(R.id.newsWebView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pageProgressBar.setProgress(newProgress);
                if (newProgress >= 100) {
                    pageProgressBar.setVisibility(View.GONE);
                } else if (pageProgressBar.getVisibility() != View.VISIBLE) {
                    pageProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pageProgressBar.setVisibility(View.VISIBLE);
                pageProgressBar.setProgress(10);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, @NonNull WebResourceRequest request) {
                return false;
            }
        });

        String url = getIntent().getStringExtra(EXTRA_ARTICLE_URL);
        if (url == null || url.isEmpty()) {
            url = "https://news.google.com";
        }
        webView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }
}
