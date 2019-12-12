package com.app_republic.kora.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.app_republic.kora.R;
import com.app_republic.kora.fragment.ItemNewsFragment;
import com.app_republic.kora.fragment.ItemPlayersFragment;
import com.app_republic.kora.fragment.MatchDetailsFragment;
import com.app_republic.kora.fragment.StandingsFragment;
import com.app_republic.kora.fragment.TimeLineFragment;
import com.app_republic.kora.model.ApiResponse;
import com.app_republic.kora.model.Match;
import com.app_republic.kora.request.GetMatchInfo;
import com.app_republic.kora.utils.AppSingleton;
import com.app_republic.kora.utils.StaticConfig;
import com.app_republic.kora.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app_republic.kora.utils.StaticConfig.MATCH_INFO_REQUEST;

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
    long timeDifference;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        gson = AppSingleton.getInstance(this).getGson();
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
            Intent intent = new Intent(MatchActivity.this, TeamInfoActivity.class);
            intent.putExtra(StaticConfig.PARAM_TEAM_ID, match.getTeamIdA());
            startActivity(intent);
        });
        IV_logoTeamB.setOnClickListener(view -> {
            Intent intent = new Intent(MatchActivity.this, TeamInfoActivity.class);
            intent.putExtra(StaticConfig.PARAM_TEAM_ID, match.getTeamIdB());
            startActivity(intent);
        });


        IV_back.setOnClickListener(this);

        IV_share.setOnClickListener(this);


        AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        //appBarLayout.addOnOffsetChangedListener(new FadingViewOffsetListener(Layout_root));

        TV_nameTeamA.setText(match.getLiveTeam1());
        TV_nameTeamB.setText(match.getLiveTeam2());

        Picasso.get().load(match.getTeamLogoA()).placeholder(R.drawable.ic_ball).into(IV_logoTeamA);
        Picasso.get().load(match.getTeamLogoB()).placeholder(R.drawable.ic_ball).into(IV_logoTeamB);


        handler = new Handler();
        runnable = () -> getMatchInfo();


        if ("0".equals(match.getHasPlayers())) {
            fragmentList.remove(4);
            tabList.remove(4);


        }
        if ("0".equals(match.getHasStandings())) {
            fragmentList.remove(3);
            tabList.remove(3);

        }
        if ("0".equals(match.getHasTimeline())) {
            fragmentList.remove(0);
            tabList.remove(0);
        }


        sectionsPagerAdapter.notifyDataSetChanged();

        AdView mAdView = new AdView(this);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setAdUnitId(StaticConfig.ADMOB_BANNER_UNIT_ID);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        ((FrameLayout) findViewById(R.id.adView)).addView(mAdView);
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
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
                        match.getLiveTeam1() + " " + result + " " + match.getLiveTeam2()
                                + "\n\n" + getString(R.string.app_name) + "\n" +
                                getString(R.string.play_store_link));


                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                break;
        }
    }


    public void getMatchInfo() {

        Call<ApiResponse> call1 = StaticConfig.apiInterface.getMatchInfo("1",
                "", match.getLiveId());
        call1.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> apiResponse) {

                try {


                    ApiResponse response = apiResponse.body();
                    String current_date = response.getCurrentDate();
                    long currentServerTime = Utils.getMillisFromServerDate(current_date);

                    long currentClientTime = Calendar.getInstance().getTimeInMillis();

                    timeDifference = currentServerTime > currentClientTime ?
                            currentServerTime - currentClientTime : currentClientTime - currentServerTime;

                    JSONArray items = new JSONArray(gson.toJson(response.getItems()));

                    String jsonString = items.getJSONObject(0).toString();
                    Match match;
                    ;
                    match = gson.fromJson(jsonString, Match.class);

                    updateUI(match);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
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
        long now = System.currentTimeMillis();
        int delay = Integer.parseInt(match.getLiveM3());
        long originalTime = Utils.getMillisFromMatchDate(match.getFullDatetimeSpaces());
        long difference = now - originalTime + timeDifference;
        String match_time = Utils.getFullTime(originalTime - timeDifference);

        MatchActivity.this.match.setFullTime(match_time);

        if (difference > 0) {
            TV_scoreTeamA.setText(match.getLiveRe1());
            TV_scoreTeamB.setText(match.getLiveRe2());
            TV_extra.setText("");

            int minutes = Integer.parseInt(match.getActualMinutes());
            if (minutes > 0) {
                handler.postDelayed(runnable, 60 * 1000);
                if (minutes > 45) {
                    if (minutes > (60 - delay))
                        TV_state.setText(String.valueOf(minutes - 15 + delay));
                    else
                        TV_state.setText(String.valueOf(45));

                } else
                    TV_state.setText(String.valueOf(minutes));
            } else {
                TV_state.setText(" - ");
            }
        } else {
            TV_scoreTeamA.setText("");
            TV_scoreTeamB.setText("");
            TV_state.setText(match_time);

            if (firstCall) {
                firstCall = false;
                try {
                    int seconds = Integer.parseInt(match.getRemainingSeconds());
                    new CountDownTimer(seconds * 1000,
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
        }
    }

    public void initialiseFragments() {
        fragmentList = new ArrayList<>();

        ItemPlayersFragment itemPlayersFragment = ItemPlayersFragment.newInstance();
        Bundle args0 = new Bundle();

        args0.putString(StaticConfig.PARAM_ITEM_TYPE,
                StaticConfig.PARAM_ITEM_TYPE_DEPARTMENT);
        args0.putString(StaticConfig.PARAM_ITEM_ID,
                match.getDepId());
        args0.putString(StaticConfig.PARAM_TEAM_ID_A,
                match.getTeamIdA());
        args0.putString(StaticConfig.PARAM_TEAM_ID_B,
                match.getTeamIdB());

        itemPlayersFragment.setArguments(args0);
        itemPlayersFragment.setRetainInstance(true);

        StandingsFragment standingsFragment = StandingsFragment.newInstance();
        Bundle args1 = new Bundle();

        args1.putString(StaticConfig.PARAM_DEP_ID,
                match.getDepId());
        args1.putString(StaticConfig.PARAM_TEAM_ID_A,
                match.getTeamIdA());
        args1.putString(StaticConfig.PARAM_TEAM_ID_B,
                match.getTeamIdB());

        standingsFragment.setArguments(args1);
        standingsFragment.setRetainInstance(true);

        ItemNewsFragment itemNewsFragment = ItemNewsFragment.newInstance();
        Bundle args2 = new Bundle();

        args2.putString(StaticConfig.PARAM_ITEM_TYPE,
                StaticConfig.PARAM_TYPE_MATCH);
        args2.putString(StaticConfig.PARAM_ITEM_ID,
                match.getLiveId());

        itemNewsFragment.setArguments(args2);
        itemNewsFragment.setRetainInstance(true);

        MatchDetailsFragment matchDetailsFragment = MatchDetailsFragment.newInstance();
        Bundle args3 = new Bundle();

        args3.putParcelable(StaticConfig.MATCH,
                match);

        matchDetailsFragment.setArguments(args3);
        matchDetailsFragment.setRetainInstance(true);

        TimeLineFragment timeLineFragment = TimeLineFragment.newInstance();
        Bundle args4 = new Bundle();

        args4.putParcelable(StaticConfig.MATCH,
                match);


        timeLineFragment.setArguments(args4);
        timeLineFragment.setRetainInstance(true);

        fragmentList.add(timeLineFragment);
        fragmentList.add(matchDetailsFragment);
        fragmentList.add(itemNewsFragment);
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
            // Show 5 total pages.
            return fragmentList.size();
        }
    }
}