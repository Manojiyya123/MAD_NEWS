package com.example.newsnow;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayout loadingLayout;
    private TextView networkStatusBanner;
    private NewsAdapter newsAdapter;
    private ConnectivityManager.NetworkCallback networkCallback;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable loadTask = this::bindNewsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        loadingLayout = findViewById(R.id.loadingLayout);
        networkStatusBanner = findViewById(R.id.networkStatusBanner);
        recyclerView = findViewById(R.id.newsRecyclerView);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        int orientation = getResources().getConfiguration().orientation;

        if (isTablet) {
            int spanCount = orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
            recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        newsAdapter = new NewsAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(newsAdapter);

        refreshNews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerNetworkCallback();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterNetworkCallback();
    }

    private void registerNetworkCallback() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            networkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(@NonNull Network network) {
                    runOnUiThread(() -> {
                        networkStatusBanner.setText(R.string.network_connected);
                        networkStatusBanner.setVisibility(View.VISIBLE);
                        handler.postDelayed(() -> networkStatusBanner.setVisibility(View.GONE), 3000);
                    });
                }

                @Override
                public void onLost(@NonNull Network network) {
                    runOnUiThread(() -> {
                        networkStatusBanner.setText(R.string.network_disconnected);
                        networkStatusBanner.setVisibility(View.VISIBLE);
                    });
                }
            };
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        }
    }

    private void unregisterNetworkCallback() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(loadTask);
    }

    private void refreshNews() {
        loadingLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(RecyclerView.INVISIBLE);
        handler.removeCallbacks(loadTask);
        handler.postDelayed(loadTask, 1500);
    }

    private void bindNewsData() {
        List<NewsArticle> sampleArticles = new ArrayList<>();

        sampleArticles.add(new NewsArticle(
                "AI Breakthroughs Reshape Global Industry in 2025",
                "Reuters",
                "2 hrs ago",
                "Major tech firms announce sweeping changes to their AI deployment strategies..",
                "https://www.reuters.com/world/",
                "TECH"));

        sampleArticles.add(new NewsArticle(
                "New Climate Report Signals Critical Decade for Action",
                "BBC News",
                "4 hrs ago",
                "Scientists warn that this decade will define the trajectory of global warming..",
                "https://www.bbc.com/news",
                "SCIENCE"));

        sampleArticles.add(new NewsArticle(
                "Global Markets Adjust to New Economic Realities",
                "Financial Times",
                "6 hrs ago",
                "Investors weigh the impact of shifting interest rates and emerging market trends..",
                "https://www.ft.com/",
                "FINANCE"));

        newsAdapter.updateData(sampleArticles);
        loadingLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(RecyclerView.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_refresh) {
            refreshNews();
            return true;
        }

        if (itemId == R.id.action_about) {
            showAboutDialog();
            return true;
        }

        if (itemId == R.id.action_notifications) {
            Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (itemId == R.id.action_settings) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAboutDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.about_title)
                .setMessage(R.string.about_message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
