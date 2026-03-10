package com.example.newsnow.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.newsnow.utils.NetworkUtils;

public class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetworkUtils.isConnected(context)) {
            Toast.makeText(context, "Network connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show();
        }
    }
}
