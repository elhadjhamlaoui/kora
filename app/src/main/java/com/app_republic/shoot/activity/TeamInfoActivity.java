package com.app_republic.shoot.activity;

import android.content.Context;
import android.content.res.Resources;
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
import com.app_republic.shoot.fragment.ItemNewsFragment;
import com.app_republic.shoot.fragment.ItemPlayersFragment;
import com.app_republic.shoot.fragment.MatchesFragment;
import com.app_republic.shoot.fragment.StandingsFragment;
import com.app_republic.shoot.fragment.TeamPlayersFragment;
import com.app_republic.shoot.model.TeamInfos.ResponseItem;
import com.app_republic.shoot.model.TeamInfos.TeamInfos;
import com.app_republic.shoot.model.TeamInfos.TeamInfosResponse;
import com.app_republic.shoot.utils.AppSingleton;
import com.app_republic.shoot.utils.StaticConfig;
import com.app_republic.shoot.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamInfoActivity extends AppCompatActivity implements View.OnClickListener {

    ConstraintLayout Layout_root;
    TeamInfos teamInfo;

    TextView TV_name, TV_country;
    ImageView IV_logo, IV_back;

    String team_id;

    String league_id;

    long timeDifference;
    AppSingleton appSingleton;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_info);
        appSingleton = AppSingleton.getInstance(this);
        gson = appSingleton.getGson();

        team_id = getIntent().getStringExtra(StaticConfig.PARAM_TEAM_ID);
        league_id = getIntent().getStringExtra(StaticConfig.PARAM_DEP_ID);




        Layout_root = findViewById(R.id.root);
        TV_name = findViewById(R.id.name);
        TV_country = findViewById(R.id.country);
        IV_logo = findViewById(R.id.logo);
        IV_back = findViewById(R.id.back);



        IV_back.setOnClickListener(view -> onBackPressed());

        getTeamInfo();

        Utils.loadBannerAd(this, "match");


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
            case R.id.share:

                break;
        }
    }


    public void getTeamInfo() {
        Call<TeamInfosResponse> call1 = StaticConfig.apiInterface.getTeamInfos(team_id);
        call1.enqueue(new Callback<TeamInfosResponse>() {
            @Override
            public void onResponse(Call<TeamInfosResponse> call, Response<TeamInfosResponse> apiResponse) {

                try {


                    TeamInfosResponse response = apiResponse.body();

                    JSONArray items = new JSONArray(gson.toJson(response.getResponse()));

                    String jsonString = items.getJSONObject(0).toString();
                    teamInfo = gson.fromJson(jsonString, ResponseItem.class).getTeam();

                    updateUI(teamInfo);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TeamInfosResponse> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });


    }

    private void updateUI(TeamInfos teamInfo) {

        Picasso picasso = appSingleton.getPicasso();

        TV_name.setText(teamInfo.getName());
        TV_country.setText(teamInfo.getCountry());

        try {
            picasso.load(teamInfo.getLogo())
                    .fit()
                    .placeholder(R.drawable.ic_ball_small)
                    .into(IV_logo);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            picasso.load(teamInfo.getLogo())
                    .fit()
                    .into(IV_logo);
        }


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
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final Context mContext;
        private String[] tabs;


        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
            tabs = mContext.getResources().getStringArray(R.array.team_info_tabs);
        }



        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = TeamPlayersFragment.newInstance();
                    Bundle args0 = new Bundle();

                    args0.putString(StaticConfig.PARAM_ITEM_TYPE,
                            StaticConfig.PARAM_ITEM_TYPE_TEAM);
                    args0.putString(StaticConfig.PARAM_ITEM_ID,
                            String.valueOf(teamInfo.getId()));
                    args0.putString(StaticConfig.PARAM_TEAM_ID_A,
                            String.valueOf(teamInfo.getId()));

                    fragment.setArguments(args0);

                    break;

                case 1:
                    fragment = StandingsFragment.newInstance();
                    Bundle args1 = new Bundle();

                    args1.putString(StaticConfig.PARAM_TEAM_ID,
                            String.valueOf(teamInfo.getId()));

                    args1.putString(StaticConfig.PARAM_DEP_ID,
                            String.valueOf(league_id));
                    args1.putString(StaticConfig.PARAM_TEAM_ID_A,
                            String.valueOf(teamInfo.getId()));


                    fragment.setArguments(args1);

                    break;
                case 2:
                    fragment = ItemNewsFragment.newInstance();
                    Bundle args2 = new Bundle();

                    args2.putString(StaticConfig.PARAM_ITEM_TYPE,
                            StaticConfig.PARAM_ITEM_TYPE_TEAM);
                    args2.putString(StaticConfig.PARAM_ITEM_ID,
                            String.valueOf(teamInfo.getId()));

                    fragment.setArguments(args2);

                    break;
                case 3:
                    fragment = MatchesFragment.newInstance();
                    Bundle args3 = new Bundle();

                    args3.putParcelable(StaticConfig.TEAM_INFO,
                            teamInfo);
                    args3.putString(StaticConfig.PARAM_TEAM_ID,
                            String.valueOf(team_id));
                    fragment.setArguments(args3);

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
            // Show 3 total pages.
            return 3;
        }
    }
}