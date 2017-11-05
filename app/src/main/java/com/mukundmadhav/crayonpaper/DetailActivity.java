package com.mukundmadhav.crayonpaper;

import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setActionBar(toolbar);
        String reev = MainActivity.tContent;
        Log.i("Receiv:",reev);
        //detailAct(reev);
    }

    public void detailAct(String urlTOOprn){
        Uri uri = Uri.parse(urlTOOprn);
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        intentBuilder.setExitAnimations(this, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        CustomTabsIntent customTabsIntent = intentBuilder.build();

        customTabsIntent.launchUrl(this, uri);
    }
}
