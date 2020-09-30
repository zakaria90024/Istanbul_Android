package com.copypasteit.istanbul.GovernmentActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;


import com.copypasteit.istanbul.HomeActivity.HomeActivity;
import com.copypasteit.istanbul.R;
import com.startapp.sdk.adsbase.StartAppSDK;


public class GovtActivity extends AppCompatActivity {
    Toolbar mToolbar;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GovtActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StartAppSDK.init(this, "208277975", true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_govt);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }
}
