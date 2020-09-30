package com.copypasteit.istanbul.GalleryActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.copypasteit.istanbul.HomeActivity.HomeActivity;
import com.copypasteit.istanbul.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.lang.annotation.IncompleteAnnotationException;

public class GalleryActivity extends AppCompatActivity implements RewardedVideoAdListener {
    private RewardedVideoAd mRewardedVideoAd ;
    Toolbar mToolbar;
        private String[] imageUrls = new String[]{

                "https://apps.bncodeing.com/public/frontend/monako_slider/1679165722228735.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679165741524867.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679165758093549.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679165794388371.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679165808269911.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679165823831234.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679165839482504.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679165758093549.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679165855340651.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679165870420503.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679165887815762.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679165902869161.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679165919691912.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679165936487637.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679165839482504.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679166245451111.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679166267867340.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679166287903864.jpg",
                "https://apps.bncodeing.com/public/frontend/monako_slider/1679166304692003.jpg",

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        //apps pub id

        MobileAds.initialize(GalleryActivity.this, "ca-app-pub-3010341507881755~8074731338");
        mRewardedVideoAd  = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd .setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, imageUrls);
        viewPager.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        Intent intent = new Intent(GalleryActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        mRewardedVideoAd.show();
    }

    //for video ads
    public void BackClick(View view) {
        Intent intent = new Intent(GalleryActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }


    //user define method for loadRewardedVideoAd load ads=================
    //===================================================================
    private void loadRewardedVideoAd() {

        //ads unit id
        mRewardedVideoAd.loadAd("ca-app-pub-3010341507881755/7883159644",
                new AdRequest.Builder().build());
    }

    //copy threds method onResume, onPause, onDestroy=========
    //========================================================
    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        //interstitialAd.show();
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    //implements methods ======================================
    //from RewardAdListener ===================================
}
