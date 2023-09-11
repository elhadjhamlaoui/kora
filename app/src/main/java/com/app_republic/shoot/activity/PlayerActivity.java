package com.app_republic.shoot.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app_republic.shoot.R;
import com.app_republic.shoot.fragment.ItemPlayersFragment;
import com.app_republic.shoot.fragment.MatchesFragment;
import com.app_republic.shoot.fragment.PlayerDetailsFragment;
import com.app_republic.shoot.model.PlayersResponse.Player;
import com.app_republic.shoot.model.PlayersResponse.StatisticsItem;
import com.app_republic.shoot.utils.AppSingleton;
import com.app_republic.shoot.utils.StaticConfig;
import com.app_republic.shoot.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {

    TextView TV_name, TV_age, TV_position, TV_country, TV_team;
    ImageView IV_photo, IV_back;
    ConstraintLayout Layout_root;
    Player player;
    StatisticsItem statisticsItem;
    Picasso picasso;
    AppSingleton appSingleton;

    String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        appSingleton = AppSingleton.getInstance(this);

        player = getIntent().getParcelableExtra(StaticConfig.PLAYER);
        statisticsItem = getIntent().getParcelableExtra(StaticConfig.PLAYER_STATISTIC);
        photo = getIntent().getParcelableExtra(StaticConfig.PHOTO);

        photo = "https://media-2.api-sports.io/football/players/" + player.getId() + ".png";
        picasso = appSingleton.getPicasso();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TV_name = findViewById(R.id.name);
        TV_age = findViewById(R.id.age);
        TV_position = findViewById(R.id.position);
        TV_country = findViewById(R.id.country);
        TV_team = findViewById(R.id.team);
        IV_photo = findViewById(R.id.photo);
        Layout_root = findViewById(R.id.root);
        IV_back = findViewById(R.id.back);

        TV_country.setOnClickListener(this);
        TV_team.setOnClickListener(this);


        IV_back.setOnClickListener(this);


        TV_name.setText(player.getName());
        TV_country.setText(player.getNationality());
        TV_team.setText(statisticsItem.getTeam().getName());
        TV_age.setText(getString(R.string.year) + player.getAge());

        switch (statisticsItem.getGames().getPosition()) {

            case "Goalkeeper" :
                TV_position.setText(getString(R.string.goalkeeper));
                break;
            case "Defender" :
                TV_position.setText(getString(R.string.defender));
                break;
            case "Midfielder" :
                TV_position.setText(getString(R.string.midfielder));
                break;
            case "Attacker" :
                TV_position.setText(getString(R.string.attacker));
                break;
        }


        if (!statisticsItem.getTeam().getLogo().isEmpty()) {
            try {
                picasso.load(statisticsItem.getTeam().getLogo()).placeholder(R.drawable.ic_ball).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        TV_team.setCompoundDrawablesWithIntrinsicBounds(
                                null,
                                null, new BitmapDrawable(getResources(), bitmap), null);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
                picasso.load(statisticsItem.getTeam().getLogo()).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        TV_team.setCompoundDrawablesWithIntrinsicBounds(
                                null,
                                null, new BitmapDrawable(getResources(), bitmap), null);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }
        }

        if (statisticsItem != null) {
            try {
                picasso.load(statisticsItem.getLeague().getFlag()).placeholder(R.drawable.ic_ball).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        TV_country.setCompoundDrawablesWithIntrinsicBounds(
                                null,
                                null, new BitmapDrawable(getResources(), bitmap), null);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
                picasso.load(statisticsItem.getLeague().getFlag()).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        TV_country.setCompoundDrawablesWithIntrinsicBounds(
                                null,
                                null, new BitmapDrawable(getResources(), bitmap), null);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }
        }

        if (!player.getPhoto().isEmpty()) {
            try {
                picasso.load(photo).placeholder(R.drawable.ic_ball).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        IV_photo.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
                picasso.load(photo).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        IV_photo.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }
        }


        Utils.loadBannerAd(this, "player");

    }

    @Override
    protected void onResume() {
        super.onResume();
        appSingleton.getInterstitialAd().loadAd(new AdRequest.Builder().build());

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.country:
                //Utils.startTeamActivity(this, getSupportFragmentManager(), player.getOtherTeamId());
                break;
            case R.id.team:
                Utils.startTeamActivity(this, getSupportFragmentManager(), String.valueOf(statisticsItem.getTeam().getId()), String.valueOf(statisticsItem.getLeague().getId()));
                break;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final Context mContext;
        private String[] tabs;


        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
            tabs = mContext.getResources().getStringArray(R.array.player_tabs);
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = ItemPlayersFragment.newInstance();
                    Bundle args0 = new Bundle();

                    args0.putString(StaticConfig.PARAM_ITEM_TYPE,
                            StaticConfig.PARAM_ITEM_TYPE_DEPARTMENT);
                    args0.putString(StaticConfig.PARAM_ITEM_ID,
                            String.valueOf(statisticsItem.getLeague().getId()));
                    args0.putParcelable(StaticConfig.PLAYER_INFO,
                            player);


                    fragment.setArguments(args0);

                    break;
                case 1:
                    fragment = MatchesFragment.newInstance();
                    Bundle args1 = new Bundle();

                    args1.putString(StaticConfig.PARAM_PLAYER_ID,
                            String.valueOf(player.getId()));
                    args1.putString(StaticConfig.PARAM_TEAM_ID,
                            String.valueOf(statisticsItem.getTeam().getId()));

                    fragment.setArguments(args1);

                    break;
                case 2:
                    fragment = PlayerDetailsFragment.newInstance();

                    Bundle args2 = new Bundle();

                    args2.putParcelable(StaticConfig.PLAYER,
                            player);
                    args2.putParcelable(StaticConfig.PLAYER_STATISTIC,
                            statisticsItem);
                    fragment.setArguments(args2);

                    break;

            }
            fragment.setRetainInstance(true);
            return fragment;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 3;
        }
    }


}