package com.copypasteit.istanbul.HomeActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.copypasteit.istanbul.GalleryActivity.GalleryActivity;
import com.copypasteit.istanbul.GovernmentActivity.GovtActivity;
import com.copypasteit.istanbul.LocationActivity.LocationActivity;
import com.copypasteit.istanbul.MainActivity.MainActivity;
import com.copypasteit.istanbul.R;
import com.copypasteit.istanbul.SliderFragment.HistoryFragment;
import com.copypasteit.istanbul.SliderFragment.SliderFragment;
import com.copypasteit.istanbul.TouristFragment.TouristFragment;
import com.copypasteit.istanbul.main.model.Post;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.material.navigation.NavigationView;
import com.startapp.sdk.adsbase.StartAppSDK;


import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , RewardedVideoAdListener {
    //STEP-1 for InterstitialAd=====================================
    public InterstitialAd interstitialAd;
    private RewardedVideoAd mRewardedVideoAd ;

    Button btnFlag;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    ActionBarDrawerToggle mToogle;
    Toolbar mToolbar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CustomAdapter adapter;
    ProgressDialog pd;
    List<Post> modelList = new ArrayList<>();
    SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StartAppSDK.init(this, "208277975", true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //apps pub id
        MobileAds.initialize(HomeActivity.this, "ca-app-pub-3010341507881755~8074731338");
        mRewardedVideoAd  = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd .setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        //STEP - 2 Intertatial ads =======================================
        //add below 3 lines with rewordvideo ads
        interstitialAd = new InterstitialAd(this);
        //Interstitial ads unit add
        interstitialAd.setAdUnitId("ca-app-pub-3010341507881755/7125144578");
        loadInterstitialAd();

        //ENDSTEP - 2 Intertatial ads ====================================



        mDrawerLayout = findViewById(R.id.drawlayoutId);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        btnFlag = findViewById(R.id.btnflagid);
        btnFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mRewardedVideoAd.isLoaded()) {
//                    mRewardedVideoAd.show();
//                    Toast.makeText(HomeActivity.this, "cckddf", Toast.LENGTH_SHORT).show();
//                }
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
//        finish();
            }
        });


        //star for fragmant -=======================================================================

        //for load fragment
        ConstraintLayout constraintLayout = findViewById(R.id.layout_change_slider);
        FragmentManager manager = getSupportFragmentManager();
        SliderFragment sliderFragment = new SliderFragment();
        manager.beginTransaction().replace(constraintLayout.getId(), sliderFragment).commit();

        //end Fragment =============================================================================

        //for load History fragment
        ConstraintLayout HistoryconstraintLayout = findViewById(R.id.history_fragment_id);
        FragmentManager HistoryFragmentmanager = getSupportFragmentManager();
        HistoryFragment HistoryFragment = new HistoryFragment();
        HistoryFragmentmanager.beginTransaction().replace(HistoryconstraintLayout.getId(), HistoryFragment).commit();

        //end load History Fragment =============================================================================

        //for load History fragment
        ConstraintLayout TouristconstraintLayout = findViewById(R.id.tourist_fragment_id);
        FragmentManager TouristFragmentmanager = getSupportFragmentManager();
        TouristFragment TouristFragment = new TouristFragment();
        TouristFragmentmanager.beginTransaction().replace(TouristconstraintLayout.getId(), TouristFragment).commit();

        //end load History Fragment =============================================================================
        //start recyclerview =======================================================================
        //recyclerView = findViewById(R.id.recycler_id_home);
        //refreshLayout = findViewById(R.id.swipeId);
        pd = new ProgressDialog(HomeActivity.this);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        //adapter = new CustomAdapter(HomeActivity.this, modelList);
        //layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //recyclerView.setLayoutManager(layoutManager);
        //End recyclerview =========================================================================

        // START for internet connection ===========================================================

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null) && (wifi.isConnected() | datac.isConnected())) {
            //connection is avlilable
        }else{
            //no connection
            showAlertDialog();
        }

        // END for internet connection ==========================================================================
        //for title color
        //getSupportActionBar().setTitle(Html.fromHtml("<font color='#17BC1E' size='15px'>An</font>"));

        setupNavigationView();
        //getData();
    }
//    private void getData() {
//        //pd.setTitle("Loading data...");
//        //pd.show();
//        Call<List<Post>> userList = ApiUtils.getAPIService().getpost();
//        userList.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                List<Post> posts = response.body();
//                adapter.setData(posts);
//                recyclerView.setAdapter(adapter);
//
//                //pd.dismiss();
//                //show data
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//
//            }
//        });
//
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.action_menu,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                setupNavigationView();
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
                return true;

            case R.id.menu_rateus:
                Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getBaseContext().getPackageName()));
                startActivity(rateIntent);
                return true;

            case R.id.menu_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "https://play.google.com/store/apps/details?id=com.copypasteit.Istanbul";
                String shareSub = "Three Pieces 2021";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                return true;
            case R.id.menu_privacy:

                Intent intentprivacy = new Intent(Intent.ACTION_VIEW, Uri.parse("https://apps.androwep.com/istanbul/privacy-policy.html"));
                startActivity(intentprivacy);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setupNavigationView() {

        mNavigationView.bringToFront();
        mToogle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mToogle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.fontcolor));
        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_rateus) {
            Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getBaseContext().getPackageName()));
            startActivity(rateIntent);


        }
        else if(item.getItemId() == R.id.menu_flag){
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);

        }
        else if(item.getItemId() == R.id.menu_location){
           Intent intent = new Intent(HomeActivity.this, LocationActivity.class);
           startActivity(intent);
        }
        else if(item.getItemId() == R.id.menu_gallery){
            Intent intent = new Intent(HomeActivity.this, GalleryActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menu_privacy){
            //PRIVACY ACCEPT SECTION====================================================================

            //do something
            LinearLayout item_contact = (LinearLayout) findViewById(R.id.contect_id);
            //for diaglog show
            final Dialog myDialog  = new Dialog(HomeActivity.this);
            myDialog.setContentView(R.layout.dialog_contact);
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();

            myDialog.findViewById(R.id.btn_accept).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });

            //PRIVACY ACCEPT SECTION====================================================================
        }

        else if (item.getItemId() == R.id.menu_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "https://play.google.com/store/apps/details?id=com.copypasteit.Istanbul";
            String shareSub = "Monaco";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        } else if (item.getItemId() == R.id.menu_gallery) {
            Intent intent = new Intent(HomeActivity.this, GalleryActivity.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.menu_refresh) {
            setupNavigationView();
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(intent);
        }

        mDrawerLayout.closeDrawers();
        return true;
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Device Offline");
        builder.setMessage(" No Internet Connection").setCancelable(false)
                .setIcon(R.drawable.ic_error);
        builder.setCancelable(true);
        AlertDialog alert = builder.create();
        alert.show();
    }



    /////Activity lifecycle =========================================================
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();

        mRewardedVideoAd.show();

    }


//    @Override
//    protected void onPause() {
//        Toast.makeText(this, "paused", Toast.LENGTH_SHORT).show();
//        super.onPause();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onStop() {
//        Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
//        super.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        Toast.makeText(this, "Destroy", Toast.LENGTH_SHORT).show();
//        super.onDestroy();
//    }

    //END Activity life cycle ==================================================================

//    public void WhenFlagClicked(View view) {
//        if (mRewardedVideoAd.isLoaded()) {
//            mRewardedVideoAd.show();
//        }
//        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
//    }

    public void WhenAndthemClicked(View view) {

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.turkey);
        mediaPlayer.start();
        Toast.makeText(this, "Played", Toast.LENGTH_SHORT).show();
    }

    public void WhenLocarionClicked(View view) {
        Intent intent = new Intent(HomeActivity.this, LocationActivity.class);
        startActivity(intent);
        finish();

    }

    public void WhenGalleryClicked(View view) {
        Intent intent = new Intent(HomeActivity.this, GalleryActivity.class);
        startActivity(intent);
        finish();

    }

    public void AdThreePieceClickred(View view) {
        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.copypasteit.monaco"));
        startActivity(rateIntent);
    }

    public void AdBikeClickred(View view) {
        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.copypasteit.Canada"));
        startActivity(rateIntent);
    }


    public void WhenShareClicked(View view) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=com.copypasteit.Istanbul";
        String shareSub = "Monaco";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    public void WhenPrinenceClicked(View view) {
        Intent rateIntent = new Intent(HomeActivity.this, GovtActivity.class);
        startActivity(rateIntent);
    }

    //STEP - 3 InterstitialAd load ads ===================================
    //====================================================================
    private void loadInterstitialAd() {
        //ads unit id
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }
    //ENDSTEP - 3 InterstitialAd load ads =================================


    //for rewordvideo ads
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
        mRewardedVideoAd.loadAd("ca-app-pub-3010341507881755/7883159644", new AdRequest.Builder().build());
    }

    //copy threds method onResume, onPause, onDestroy=========
    //========================================================
    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
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

    public void WhenFlagClicked(View view) {
    }
    //implements methods ======================================
    //from RewardAdListener ===================================

}
