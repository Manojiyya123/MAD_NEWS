package com.example.newsnow;

import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
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
    private ProgressBar loadingProgressBar;
    private NewsAdapter newsAdapter;
    private ConnectivityReceiver connectivityReceiver;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable loadTask = this::bindNewsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadingProgressBar = findViewById(R.id.loadingProgressBar);
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

        connectivityReceiver = new ConnectivityReceiver();
        refreshNews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            unregisterReceiver(connectivityReceiver);
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(loadTask);
    }

    private void refreshNews() {
        loadingProgressBar.setVisibility(ProgressBar.VISIBLE);
        recyclerView.setVisibility(RecyclerView.INVISIBLE);
        handler.removeCallbacks(loadTask);
        handler.postDelayed(loadTask, 900);
    }

    private void bindNewsData() {
        List<NewsArticle> sampleArticles = new ArrayList<>();

        sampleArticles.add(new NewsArticle(
                "Global Markets Rally as Tech Stocks Lead Gains",
                "Financial Desk",
                "March 5, 2026",
                "Technology stocks climbed sharply after strong quarterly guidance from major chipmakers and cloud providers.",
                "https://www.reuters.com/world/"));

        sampleArticles.add(new NewsArticle(
                "Scientists Announce Breakthrough in Battery Efficiency",
                "Science Daily",
                "March 4, 2026",
                "A new lithium-sulfur prototype could significantly increase electric vehicle range while reducing charging time.",
                "https://www.nature.com/news/"));

        sampleArticles.add(new NewsArticle(
                "City Transit Expands with New Electric Bus Network",
                "Metro Updates",
                "March 3, 2026",
                "The city introduced 120 electric buses and smart route scheduling to reduce emissions and improve commute reliability.",
                "https://www.bbc.com/news"));

        sampleArticles.add(new NewsArticle(
                "Championship Preview: Key Players to Watch This Weekend",
                "Sports Hub",
                "March 2, 2026",
                "Analysts break down tactical matchups and the players likely to influence the season-defining clash.",
                "https://www.espn.com/"));

        sampleArticles.add(new NewsArticle(
                "Health Officials Launch New Preventive Care Initiative",
                "Public Health Review",
                "March 1, 2026",
                "Clinics across the region are offering free screenings as part of a broader preventive health campaign.",
                "https://www.who.int/news"));

        newsAdapter.updateData(sampleArticles);
        loadingProgressBar.setVisibility(ProgressBar.GONE);
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
            Toast.makeText(this, R.string.refreshing_news, Toast.LENGTH_SHORT).show();
            return true;
        }

        if (itemId == R.id.action_about) {
            showAboutDialog();
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
