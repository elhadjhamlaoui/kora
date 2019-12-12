package com.app_republic.kora.activity;

import android.os.Bundle;

import com.app_republic.kora.R;
import com.app_republic.kora.fragment.DepartmentsFragment;
import com.app_republic.kora.fragment.GamesFragment;
import com.app_republic.kora.utils.StaticConfig;

import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.app_republic.kora.fragment.MatchesFragment;
import com.app_republic.kora.fragment.NewsFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MobileAds.initialize(this, StaticConfig.ADMOB_APP_ID);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        replaceFragment(MatchesFragment.newInstance());

        AdView mAdView = new AdView(this);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.invalidate();
        mAdView.setAdUnitId(StaticConfig.ADMOB_BANNER_UNIT_ID);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        ((FrameLayout) findViewById(R.id.adView)).addView(mAdView);
        mAdView.loadAd(adRequest);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_matches:
                replaceFragment(MatchesFragment.newInstance());
                setTitle(getString(R.string.app_name));

                break;
            case R.id.nav_news:
                replaceFragment(NewsFragment.newInstance());
                setTitle(getString(R.string.news));

                break;
            case R.id.nav_departments:
                Fragment fragment = DepartmentsFragment.newInstance();
                Bundle args = new Bundle();
                args.putString(StaticConfig.PARAM_DEPS_TYPE, StaticConfig.DEPS_TYPE_STANDINGS);
                fragment.setArguments(args);
                replaceFragment(fragment);
                setTitle(getString(R.string.departments));
                break;
            case R.id.nav_players:
                Fragment fragment1 = DepartmentsFragment.newInstance();
                Bundle args1 = new Bundle();
                args1.putString(StaticConfig.PARAM_DEPS_TYPE, StaticConfig.DEPS_TYPE_PLAYERS);
                fragment1.setArguments(args1);
                replaceFragment(fragment1);
                setTitle(getString(R.string.players));

                break;

            case R.id.nav_games:
                Fragment gamesFragment = GamesFragment.newInstance();

                replaceFragment(gamesFragment);
                setTitle(getString(R.string.games));

                break;


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void replaceFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()

                .replace(R.id.container,fragment).commitNow();
    }
}
