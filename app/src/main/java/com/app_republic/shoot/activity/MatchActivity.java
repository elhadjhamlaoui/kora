package com.app_republic.shoot.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.app_republic.shoot.R;
import com.app_republic.shoot.fragment.CommentsFragment;
import com.app_republic.shoot.fragment.ItemNewsFragment;
import com.app_republic.shoot.fragment.ItemPlayersFragment;
import com.app_republic.shoot.fragment.MatchDetailsFragment;
import com.app_republic.shoot.fragment.StandingsFragment;
import com.app_republic.shoot.fragment.TimeLineFragment;
import com.app_republic.shoot.fragment.VideosFragment;
import com.app_republic.shoot.model.general.ApiResponse;
import com.app_republic.shoot.model.general.Match;
import com.app_republic.shoot.utils.AppSingleton;
import com.app_republic.shoot.utils.StaticConfig;
import com.app_republic.shoot.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchActivity extends AppCompatActivity implements View.OnClickListener {

    TextView TV_nameTeamA, TV_nameTeamB, TV_scoreTeamA, TV_scoreTeamB, TV_state, TV_extra;
    ImageView IV_logoTeamA, IV_logoTeamB, IV_back, IV_share;
    ConstraintLayout Layout_root;
    Match match;
    String result;
    Handler handler;
    Runnable runnable;

    List<Fragment> fragmentList;
    List<String> tabList = new ArrayList<>();

    boolean firstCall = true;

    AppSingleton appSingleton;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        appSingleton = AppSingleton.getInstance(this);
        gson = appSingleton.getGson();
        tabList.addAll(Arrays.asList(getResources().getStringArray(R.array.match_tabs)));
        match = getIntent().getParcelableExtra(StaticConfig.MATCH);
        result = getIntent().getStringExtra(StaticConfig.RESULT);

        initialiseFragments();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager());



        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(5);
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

        TV_nameTeamA = findViewById(R.id.nameTeamA);
        TV_nameTeamB = findViewById(R.id.nameTeamB);
        TV_scoreTeamA = findViewById(R.id.scoreTeamA);
        TV_scoreTeamB = findViewById(R.id.scoreTeamB);
        TV_state = findViewById(R.id.state);
        IV_logoTeamA = findViewById(R.id.logoTeamA);
        IV_logoTeamB = findViewById(R.id.logoTeamB);
        Layout_root = findViewById(R.id.root);
        IV_back = findViewById(R.id.back);
        IV_share = findViewById(R.id.share);
        TV_extra = findViewById(R.id.extra);

        IV_logoTeamA.setOnClickListener(view -> {
            Utils.startTeamActivity(MatchActivity.this, getSupportFragmentManager(), String.valueOf(match.getTeams().getHome().getId()), String.valueOf(match.getLeague().getId()));
        });
        IV_logoTeamB.setOnClickListener(view -> {
            Utils.startTeamActivity(MatchActivity.this, getSupportFragmentManager(), String.valueOf(match.getTeams().getAway().getId()), String.valueOf(match.getLeague().getId()));

        });


        IV_back.setOnClickListener(this);

        IV_share.setOnClickListener(this);


        AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        //appBarLayout.addOnOffsetChangedListener(new FadingViewOffsetListener(Layout_root));

        TV_nameTeamA.setText(match.getTeams().getHome().getName());
        TV_nameTeamB.setText(match.getTeams().getAway().getName());


        try {
            Picasso.get().load(match.getTeams().getHome().getLogo()).placeholder(R.drawable.ic_ball).into(IV_logoTeamA);
            Picasso.get().load(match.getTeams().getAway().getLogo()).placeholder(R.drawable.ic_ball).into(IV_logoTeamB);

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            Picasso.get().load(match.getTeams().getHome().getLogo()).into(IV_logoTeamA);
            Picasso.get().load(match.getTeams().getAway().getLogo()).into(IV_logoTeamB);
        }


        handler = new Handler();
        runnable = () -> getMatchInfo();


        /*
        if ("0".equals()) {
            fragmentList.remove(6);
            tabList.remove(6);
        }*/

        /*if ("0".equals(match.getHasStandings())) {
            fragmentList.remove(5);
            tabList.remove(5);
        }*/



        /*
        if ("0".equals(match.getHasTimeline())) {
            fragmentList.remove(0);
            tabList.remove(0);
        }*/


        sectionsPagerAdapter.notifyDataSetChanged();


        Utils.loadBannerAd(this, "match");


    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 1000);
        appSingleton.getInterstitialAd().loadAd(new AdRequest.Builder().build());
    }


    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);

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
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        match.getTeams().getHome().getName() + " " + TV_state.getText().toString() + " " + match.getTeams().getAway().getName()
                                + "\n\n" + getString(R.string.app_name) + "\n\n" +
                                getString(R.string.play_store_link));


                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                break;
        }
    }


    public void getMatchInfo() {

        Call<ApiResponse> call1 = StaticConfig.apiInterface.getMatchById(String.valueOf(match.getFixture().getId()));
        call1.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> apiResponse) {

                try {


                    ApiResponse response = apiResponse.body();

                    JSONArray items = new JSONArray(gson.toJson(response.getResponse()));

                    String jsonString = items.getJSONObject(0).toString();
                    Match match;
                    match = gson.fromJson(jsonString, Match.class);

                    updateUI(match);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });
    }

    private void updateUI(Match match) {
        String matchStatus = match.getFixture().getStatus().getJsonMemberShort();
        TV_scoreTeamA.setText("");
        TV_scoreTeamB.setText("");

        if(matchStatus.equals("NS")) {
            // match not started
            TV_state.setText(Utils.getReadableDateTime(match.getFixture().getTimestamp() * 1000L));

            if (firstCall) {
                firstCall = false;
                try {
                    long diffenrenceMillis = (match.getFixture().getTimestamp() * 1000) - System.currentTimeMillis();
                    new CountDownTimer(diffenrenceMillis,
                            1000) {
                        public void onTick(long millisUntilFinished) {
                            TV_extra.setText(Utils.getRemainingTime(millisUntilFinished));
                        }

                        public void onFinish() {
                            handler.postDelayed(runnable, 1000);
                        }
                    }.start();
                } catch (NumberFormatException e) {

                }

            }


        } else if (Arrays.asList(StaticConfig.MATCH_FINISHED).contains(matchStatus)){
            // match finished
            int homePenalty = match.getScore().getPenalty().getHome();
            int awayPenalty = match.getScore().getPenalty().getAway();

            TV_scoreTeamA.setText((homePenalty == 0 ? "" : "("
                    + homePenalty + ") ")
                    + match.getGoals().getHome()
            );
            TV_scoreTeamB.setText(match.getGoals().getAway() +
                    (awayPenalty == 0 ? "" : " (" + awayPenalty + ")")
            );

            TV_extra.setText("");

            TV_state.setText(" - ");


        } else if (Arrays.asList(StaticConfig.MATCH_STILL_PLAYING).contains(matchStatus)){
            // match still playing

            int homePenalty = match.getScore().getPenalty().getHome();
            int awayPenalty = match.getScore().getPenalty().getAway();

            TV_scoreTeamA.setText((homePenalty == 0 ? "" : "("
                    + homePenalty + ") ")
                    + match.getGoals().getHome()
            );
            TV_scoreTeamB.setText(match.getGoals().getAway() +
                    (awayPenalty == 0 ? "" : " (" + awayPenalty + ")")
            );


            TV_extra.setText("");

            handler.postDelayed(runnable, 60 * 1000);

            TV_state.setText(String.valueOf(match.getFixture().getStatus().getElapsed()));
        } else if(matchStatus.equals("TBD")){
            TV_state.setText(Utils.getReadableDate(match.getFixture().getTimestamp() * 1000L));
        } else if(matchStatus.equals("PST")){
            TV_state.setText(getResources().getString(R.string.postponed));
        } else if(matchStatus.equals("CANC")){
            TV_state.setText(getResources().getString(R.string.canceled));
        }
    }

    public void initialiseFragments() {
        fragmentList = new ArrayList<>();


        // players fragment

        ItemPlayersFragment itemPlayersFragment = ItemPlayersFragment.newInstance();
        Bundle args0 = new Bundle();

        args0.putString(StaticConfig.PARAM_ITEM_TYPE,
                StaticConfig.PARAM_ITEM_TYPE_DEPARTMENT);
        args0.putString(StaticConfig.PARAM_ITEM_ID,
                String.valueOf(match.getLeague().getId()));
        args0.putString(StaticConfig.PARAM_TEAM_ID_A,
                String.valueOf(match.getTeams().getHome().getId()));
        args0.putString(StaticConfig.PARAM_TEAM_ID_B,
                String.valueOf(match.getTeams().getAway().getId()));

        itemPlayersFragment.setArguments(args0);
        itemPlayersFragment.setRetainInstance(true);

        // Standings fragment

        StandingsFragment standingsFragment = StandingsFragment.newInstance();
        Bundle args1 = new Bundle();

        args1.putString(StaticConfig.PARAM_DEP_ID,
                String.valueOf(match.getLeague().getId()));
        args1.putString(StaticConfig.PARAM_TEAM_ID_A,
                String.valueOf(match.getTeams().getHome().getId()));
        args1.putString(StaticConfig.PARAM_TEAM_ID_B,
                String.valueOf(match.getTeams().getAway().getId()));

        standingsFragment.setArguments(args1);
        standingsFragment.setRetainInstance(true);

        // News fragment

        ItemNewsFragment itemNewsFragment = ItemNewsFragment.newInstance();
        Bundle args2 = new Bundle();

        args2.putString(StaticConfig.PARAM_ITEM_TYPE,
                StaticConfig.PARAM_TYPE_MATCH);
        args2.putString(StaticConfig.PARAM_ITEM_ID,
                String.valueOf(match.getFixture().getId()));

        itemNewsFragment.setArguments(args2);
        itemNewsFragment.setRetainInstance(true);

        // Videos Fragment

        VideosFragment videosFragment = VideosFragment.newInstance();
        Bundle args3 = new Bundle();

        args3.putParcelable(StaticConfig.MATCH,
                match);

        videosFragment.setArguments(args3);
        videosFragment.setRetainInstance(true);

        // Comments Fragment

        CommentsFragment commentsFragment = CommentsFragment.newInstance();
        Bundle args4 = new Bundle();

        args4.putParcelable(StaticConfig.MATCH,
                match);
        args4.putString(StaticConfig.TARGET_TYPE,
                StaticConfig.MATCH);

        args4.putString(StaticConfig.TARGET_ID,
                String.valueOf(match.getFixture().getId()));


        commentsFragment.setArguments(args4);
        commentsFragment.setRetainInstance(true);


        // MatchDetails fragment

        MatchDetailsFragment matchDetailsFragment = MatchDetailsFragment.newInstance();
        Bundle args5 = new Bundle();

        args5.putParcelable(StaticConfig.MATCH,
                match);

        matchDetailsFragment.setArguments(args5);
        matchDetailsFragment.setRetainInstance(true);

        // TimeLine fragment

        TimeLineFragment timeLineFragment = TimeLineFragment.newInstance();
        Bundle args6 = new Bundle();
        args6.putParcelable(StaticConfig.MATCH,
                match);
        timeLineFragment.setArguments(args6);
        timeLineFragment.setRetainInstance(true);

        fragmentList.add(timeLineFragment);
        fragmentList.add(matchDetailsFragment);
        fragmentList.add(commentsFragment);
        //fragmentList.add(videosFragment);
        //fragmentList.add(itemNewsFragment);
        fragmentList.add(standingsFragment);
        fragmentList.add(itemPlayersFragment);

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final Context mContext;


        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }


        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabList.get(position);
        }

        @Override
        public int getCount() {
            return tabList.size();
        }
    }
}