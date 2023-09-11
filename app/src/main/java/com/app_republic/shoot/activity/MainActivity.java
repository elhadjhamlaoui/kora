package com.app_republic.shoot.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.ap.ApNotification;
import com.app_republic.shoot.BuildConfig;
import com.app_republic.shoot.R;
import com.app_republic.shoot.fragment.ChatFragment;
import com.app_republic.shoot.fragment.DepartmentsFragment;
import com.app_republic.shoot.fragment.GamesFragment;
import com.app_republic.shoot.fragment.MatchesFragment;
import com.app_republic.shoot.fragment.NewsFragment;
import com.app_republic.shoot.model.general.User;
import com.app_republic.shoot.utils.AppSingleton;
import com.app_republic.shoot.utils.StaticConfig;
import com.app_republic.shoot.utils.Utils;
import com.facebook.login.LoginManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FirebaseAuth.AuthStateListener {

    AppSingleton appSingleton;
    DrawerLayout drawer;
    NavigationView navigationView;
    CircleImageView IV_photo;
    TextView TV_title;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        AppCompatActivity appCompatActivity = this;




        appSingleton = AppSingleton.getInstance(appCompatActivity);

        MobileAds.initialize(this, appSingleton.ADMOB_APP_ID);


        drawer = findViewById(R.id.drawer_layout);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        if (appSingleton.MAIN_SCREEN.equals("news")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, NewsFragment.newInstance())
                    .commit();
            setTitle(getString(R.string.news));
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MatchesFragment.newInstance())
                    .commit();
            setTitle(getString(R.string.app_name));
        }
        Utils.loadBannerAd(this, "main");
        ApNotification.start(MainActivity.this);


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

    @Override
    protected void onResume() {
        super.onResume();

        appSingleton.getInterstitialAd().setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });
        appSingleton.getInterstitialAd().loadAd(new AdRequest.Builder().build());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_matches:

                    replaceFragment(MatchesFragment.newInstance(), getString(R.string.app_name));
                    setTitle(getString(R.string.app_name));

                break;
            case R.id.nav_news:
                replaceFragment(NewsFragment.newInstance(), getString(R.string.news));
                setTitle(getString(R.string.news));

                break;
            case R.id.nav_departments:
                Fragment fragment = DepartmentsFragment.newInstance();
                Bundle args = new Bundle();
                args.putString(StaticConfig.PARAM_DEPS_TYPE, StaticConfig.DEPS_TYPE_STANDINGS);
                fragment.setArguments(args);
                replaceFragment(fragment, getString(R.string.departments));
                setTitle(getString(R.string.departments));
                break;
            case R.id.nav_players:
                Fragment fragment1 = DepartmentsFragment.newInstance();
                Bundle args1 = new Bundle();
                args1.putString(StaticConfig.PARAM_DEPS_TYPE, StaticConfig.DEPS_TYPE_PLAYERS);
                fragment1.setArguments(args1);
                replaceFragment(fragment1, getString(R.string.players));
                setTitle(getString(R.string.players));

                break;

            case R.id.nav_chat:
                replaceFragment(ChatFragment.newInstance(), getString(R.string.chat));
                setTitle(getString(R.string.chat));
                break;


            case R.id.nav_games:
                Fragment gamesFragment = GamesFragment.newInstance();

                replaceFragment(gamesFragment, getString(R.string.games));
                setTitle(getString(R.string.games));

                break;

            case R.id.nav_login:
                if (appSingleton.getUserLocalStore().isLoggedIn()) {
                    appSingleton.getFirebaseAuth().signOut();
                    navigationView.getMenu().findItem(R.id.nav_login).setTitle(getString(R.string.login_user));
                    appSingleton.getUserLocalStore().clearUserData();
                    appSingleton.getUserLocalStore().setUserLoggedIn(false);
                    LoginManager.getInstance().logOut();
                    IV_photo.setImageResource(R.mipmap.ic_launcher);
                    TV_title.setText(getString(R.string.app_name));
                } else {
                    finish();
                    startActivity(new Intent(this, LoginActivity.class));
                }

                break;

            case R.id.nav_facebook:
                startActivity(Utils.getOpenFacebookIntent(this, appSingleton.FACEBOOK_PAGE));
                break;

            case R.id.nav_website:
                try {
                    Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(appSingleton.WEBSITE_URL));
                    startActivity(browserIntent2);
                }catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.nav_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Download " + getString(R.string.app_name)
                                + " : https://play.google.com/store/apps/details?id="
                                + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;

            case R.id.nav_rate_app:

                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id="
                                + BuildConfig.APPLICATION_ID));
                startActivity(browserIntent);
                break;



        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void replaceFragment(Fragment fragment, String name) {

        getSupportFragmentManager().beginTransaction()
                .addToBackStack(name)
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        appSingleton.getFirebaseAuth().addAuthStateListener(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        appSingleton.getFirebaseAuth().removeAuthStateListener(this);

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() != null && appSingleton.getUserLocalStore().isLoggedIn()) {
            user = appSingleton.getUserLocalStore().getLoggedInUser();
            IV_photo = navigationView.getHeaderView(0).findViewById(R.id.user_photo);
            TV_title = navigationView.getHeaderView(0).findViewById(R.id.title);
            navigationView.getMenu().findItem(R.id.nav_login).setTitle(getString(R.string.logout));
            if (!user.getPhoto().isEmpty())
                appSingleton.getPicasso().load(user.getPhoto()).into(IV_photo);
            TV_title.setText(user.getName());
        }

    }
}
