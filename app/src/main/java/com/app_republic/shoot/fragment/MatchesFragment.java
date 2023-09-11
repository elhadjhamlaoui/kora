package com.app_republic.shoot.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_republic.shoot.R;
import com.app_republic.shoot.activity.MatchActivity;
import com.app_republic.shoot.activity.NewsItemActivity;
import com.app_republic.shoot.model.general.ApiResponse;
import com.app_republic.shoot.model.general.Match;
import com.app_republic.shoot.model.general.News;
import com.app_republic.shoot.utils.AppSingleton;
import com.app_republic.shoot.utils.StaticConfig;
import com.app_republic.shoot.utils.UnifiedNativeAdViewHolder;
import com.app_republic.shoot.utils.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.InterstitialAd;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.gson.Gson;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.app_republic.shoot.utils.StaticConfig.CONTENT_ITEM_VIEW_TYPE;
import static com.app_republic.shoot.utils.StaticConfig.UNIFIED_NATIVE_AD_VIEW_TYPE;

public class MatchesFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    List<Match> matches = new ArrayList<>();
    List<Object> list = new ArrayList<>();
    InterstitialAd interstitialAd;
    private Adapter matches_adapter;
    private RecyclerView matches_recycler;

    private final int NUMBER_OF_NATIVE_ADS_MATCHES = 1;
    Calendar calendar;

    View IV_next, IV_previous;
    TextView TV_date, TV_day;

    LinearLayout LL_choose_date, LL_date_layout;

    String dateString;
    DatePickerDialog datePickerDialog;

    Handler handler;
    Runnable runnable;
    long timeDifference;

    TextView TV_empty;
    ShimmerFrameLayout shimmerFrameLayout;

    String player_id;
    String league_id;

    String team_id;

    Gson gson;
    private AppSingleton appSingleton;

    public MatchesFragment() {
        // Required empty public constructor
    }

    public static MatchesFragment newInstance() {
        MatchesFragment fragment = new MatchesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = AppSingleton.getInstance(getActivity()).getGson();
        appSingleton = AppSingleton.getInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_matches, container, false);


        if (getArguments() != null) {
            player_id = getArguments().getString(StaticConfig.PARAM_PLAYER_ID);
            league_id = getArguments().getString(StaticConfig.PARAM_DEP_ID);
            team_id = getArguments().getString(StaticConfig.PARAM_TEAM_ID);
        }

        initialiseViews(view);


        matches_adapter = new Adapter(getActivity(), list);


        matches_recycler.setAdapter(matches_adapter);


        matches_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(
                getActivity(), this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


       if (player_id != null || team_id != null) {
            LL_date_layout.setVisibility(GONE);

            getPlayerMatches();

        } else {

            handler = new Handler();
            runnable = this::getMatches;
            getMatches();
            displayDate();

        }

        getLatestNews();


        return view;
    }

    private void initialiseViews(View view) {
        matches_recycler = view.findViewById(R.id.matches_recycler);

        LL_choose_date = view.findViewById(R.id.choose_date);
        LL_date_layout = view.findViewById(R.id.date_layout);

        TV_empty = view.findViewById(R.id.empty);

        IV_next = view.findViewById(R.id.arrow_next);
        IV_previous = view.findViewById(R.id.arrow_previous);
        TV_date = view.findViewById(R.id.date_text);
        TV_day = view.findViewById(R.id.day_text);

        LL_choose_date.setOnClickListener(this);
        IV_next.setOnClickListener(this);
        IV_previous.setOnClickListener(this);

        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.showShimmer(true);

    }

    private void displayDate() {
        TV_date.setText(Utils.getReadableDate(calendar.getTimeInMillis()));
        TV_day.setText(Utils.getReadableDay(calendar, getActivity()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_date:
                datePickerDialog.show();
                break;
            case R.id.arrow_previous:
                calendar.add(Calendar.DATE, -1);
                getMatches();
                displayDate();
                break;
            case R.id.arrow_next:
                calendar.add(Calendar.DATE, 1);
                getMatches();
                displayDate();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        calendar.set(year, month, day);
        getMatches();
        displayDate();
    }


    class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context context;
        List<Object> list;
        Picasso picasso;
        Calendar calendar = Calendar.getInstance();


        public Adapter(Context context, List<Object> list) {
            this.context = context;
            this.list = list;
            picasso = AppSingleton.getInstance(getActivity()).getPicasso();

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            switch (viewType) {
                case UNIFIED_NATIVE_AD_VIEW_TYPE:
                    View unifiedNativeLayoutView = LayoutInflater.from(
                            parent.getContext()).inflate(R.layout.ad_unified_match,
                            parent, false);
                    return new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);
                case CONTENT_ITEM_VIEW_TYPE:
                    View ContentLayoutView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_match, parent, false);
                    return new ContenetViewHolder(ContentLayoutView);
            }

            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            int viewType = getItemViewType(i);
            switch (viewType) {
                case UNIFIED_NATIVE_AD_VIEW_TYPE:
                    UnifiedNativeAd nativeAd = (UnifiedNativeAd) list.get(i);
                    UnifiedNativeAdViewHolder.populateNativeAdView(nativeAd, ((UnifiedNativeAdViewHolder) viewHolder).getAdView());
                    break;

                case CONTENT_ITEM_VIEW_TYPE:
                    ContenetViewHolder viewHolder1 = (ContenetViewHolder) viewHolder;

                    Match match = (Match) list.get(i);

                    // fill the score the and the state

                    String matchStatus = match.getFixture().getStatus().getJsonMemberShort();
                    if(matchStatus.equals("NS")) {
                        // match not started
                        viewHolder1.scoreTeamA.setText("");
                        viewHolder1.scoreTeamB.setText("");
                    } else if (Arrays.asList(StaticConfig.MATCH_STATUS_SHOW_SCORE).contains(matchStatus)){
                        //either finished or still playing

                        int homePenalty = match.getScore().getPenalty().getHome();
                        int awayPenalty = match.getScore().getPenalty().getAway();

                        viewHolder1.scoreTeamA.setText((homePenalty == 0 ? "" : "("
                                + homePenalty + ") ")
                                + match.getGoals().getHome()
                        );
                        viewHolder1.scoreTeamB.setText(match.getGoals().getAway() +
                                (awayPenalty == 0 ? "" : " (" + awayPenalty + ")")
                        );
                    } else {
                        viewHolder1.scoreTeamA.setText("");
                        viewHolder1.scoreTeamB.setText("");
                    }


                    viewHolder1.state.setBackground(getResources()
                            .getDrawable(R.drawable.bac_match_state));
                    viewHolder1.state.setTextColor(getResources()
                            .getColor(R.color.blue_9));

                    if(matchStatus.equals("NS")) {
                        // match not started
                       if (DateUtils.isToday(match.getFixture().getTimestamp() * 1000L) || dateString != null)
                           viewHolder1.state.setText(Utils.getReadableTime(match.getFixture().getTimestamp() * 1000L));
                       else
                           viewHolder1.state.setText(Utils.getReadableDateTime(match.getFixture().getTimestamp() * 1000L));

                    } else if (Arrays.asList(StaticConfig.MATCH_FINISHED).contains(matchStatus)){
                        // match finished

                        viewHolder1.state.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        viewHolder1.state.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                        viewHolder1.state.setText(" - ");

                    } else if (Arrays.asList(StaticConfig.MATCH_STILL_PLAYING).contains(matchStatus)){
                        // match still playing
                        viewHolder1.state.setText(String.valueOf(match.getFixture().getStatus().getElapsed()));
                    } else if(matchStatus.equals("TBD")){
                        viewHolder1.state.setText(Utils.getReadableDate(match.getFixture().getTimestamp() * 1000L));
                    } else if(matchStatus.equals("PST")){
                        viewHolder1.state.setText(getResources().getString(R.string.postponed));
                    } else if(Arrays.asList(StaticConfig.MATCH_STATUS_CANCELED).contains(matchStatus)){
                        viewHolder1.state.setText(getResources().getString(R.string.canceled));
                    }


                    viewHolder1.name1.setText(match.getTeams().getHome().getName());
                    viewHolder1.name2.setText(match.getTeams().getAway().getName());

                    if (!match.getTeams().getHome().getLogo().isEmpty()) {
                        picasso.cancelRequest(viewHolder1.logo1);
                        picasso.load(match.getTeams().getHome().getLogo()).fit().into(viewHolder1.logo1);
                    }

                    if (!match.getTeams().getAway().getLogo().isEmpty()) {
                        picasso.cancelRequest(viewHolder1.logo2);
                        picasso.load(match.getTeams().getAway().getLogo()).fit().into(viewHolder1.logo2);
                    }


                    try {
                        viewHolder1.department.setText(Utils.getLeagueNameById(match.getLeague(), appSingleton));
                        if (player_id != null) {
                            Match previousMatch;
                            if (i == 0 || i == 1 && !(list.get(i - 1) instanceof Match)) {
                                viewHolder1.department.setVisibility(View.VISIBLE);
                            } else {
                                if (i == 1)
                                    previousMatch = (Match) list.get(i - 1);
                                else
                                    previousMatch = list.get(i - 1) instanceof Match ? (Match) list.get(i - 1)
                                            : (Match) list.get(i - 2);

                                if (previousMatch.getLeague().getId() != match.getLeague().getId()) {
                                    viewHolder1.department.setVisibility(View.VISIBLE);
                                } else
                                    viewHolder1.department.setVisibility(GONE);

                            }

                        } else {
                            Match previousMatch;
                            if (i == 0 || i == 1 && !(list.get(i - 1) instanceof Match)) {
                                viewHolder1.department.setVisibility(View.VISIBLE);
                            } else {
                                if (i == 1)
                                    previousMatch = (Match) list.get(i - 1);
                                else
                                    previousMatch = list.get(i - 1) instanceof Match ? (Match) list.get(i - 1)
                                            : (Match) list.get(i - 2);

                                if (previousMatch.getLeague().getId() != match.getLeague().getId()) {
                                    viewHolder1.department.setVisibility(View.VISIBLE);
                                } else
                                    viewHolder1.department.setVisibility(GONE);

                            }
                        }
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }

                    break;
            }


        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        @Override
        public int getItemViewType(int position) {

            Object recyclerViewItem = list.get(position);
            if (recyclerViewItem instanceof UnifiedNativeAd) {
                return UNIFIED_NATIVE_AD_VIEW_TYPE;
            }
            return CONTENT_ITEM_VIEW_TYPE;
        }

        class ContenetViewHolder extends RecyclerView.ViewHolder {

            ImageView logo1, logo2;
            TextView name1, name2, state, department, scoreTeamA, scoreTeamB, section, live;
            View V_root, V_section_layout;

            public ContenetViewHolder(@NonNull View itemView) {
                super(itemView);
                logo1 = itemView.findViewById(R.id.logo_team_1);
                logo2 = itemView.findViewById(R.id.logo_team_2);
                name1 = itemView.findViewById(R.id.name_team_1);
                name2 = itemView.findViewById(R.id.name_team_2);
                state = itemView.findViewById(R.id.state);
                scoreTeamA = itemView.findViewById(R.id.scoreTeamA);
                scoreTeamB = itemView.findViewById(R.id.scoreTeamB);
                department = itemView.findViewById(R.id.department);
                section = itemView.findViewById(R.id.section);
                V_root = itemView.findViewById(R.id.root);
                V_section_layout = itemView.findViewById(R.id.section_layout);
                live = itemView.findViewById(R.id.liveTextView);

                V_root.setOnClickListener(view -> {

                    Utils.loadInterstitialAd(getActivity().getSupportFragmentManager(), "any","match", getContext(), () -> {
                        Intent intent = new Intent(context, MatchActivity.class);
                        if (getAdapterPosition() != -1) {
                            intent.putExtra(StaticConfig.MATCH, (Match) list.get(getAdapterPosition()));
                            context.startActivity(intent);
                        }

                    });

                });


            }
        }
    }


    public void getMatches() {


        dateString = Utils.getReadableDate(calendar.getTimeInMillis());

        Call<ApiResponse> call1 = StaticConfig.apiInterface.getMatches(dateString);
        call1.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> apiResponse) {
                try {



                    ApiResponse object = apiResponse.body();

                    JSONArray items = new JSONArray(gson.toJson(object.getResponse()));

                    boolean liveMatches = false;

                    matches.clear();
                    list.clear();
                    matches_adapter.notifyDataSetChanged();

                    for (int i = 0; i < items.length(); i++) {
                        String jsonString = items.getJSONObject(i).toString();
                        Match match;
                        match = gson.fromJson(jsonString, Match.class);

                        boolean found = false;

                        for (int league : appSingleton.leagues) {
                            if (match.getLeague().getId() == league) {
                                found = true;
                                break; // Exit the loop as soon as a match is found
                            }
                        }

                        if(found) {
                            matches.add(match);
                            if (Arrays.asList(StaticConfig.MATCH_IS_PLAYING).contains(match.getFixture().getStatus().getJsonMemberShort()))
                                liveMatches = true;
                        }


                    }

                    if (matches.isEmpty())
                        TV_empty.setVisibility(View.VISIBLE);
                    else
                        TV_empty.setVisibility(View.GONE);


                    if (liveMatches)
                        handler.postDelayed(runnable, 60 * 1000);


                    /*Collections.sort(matches, (o, t1) -> {
                        Match match = o;
                        Match match1 = t1;

                        return String.valueOf(match.getLeague().getId()).compareTo(String.valueOf(match1.getLeague().getId()));
                    });*/

                    Collections.sort(matches, (o, t1) -> {
                        Match match = o;
                        Match match1 = t1;

                        int matchLeague = 0;
                        int match1League = 0;

                        for (int league : appSingleton.leagues) {
                            if (match.getLeague().getId() == league) {
                                matchLeague = appSingleton.leagues.indexOf(league);
                            }
                            if (match1.getLeague().getId() == league) {
                                match1League = appSingleton.leagues.indexOf(league);
                            }
                        }

                        return Integer.compare(matchLeague, match1League);
                    });


                    list.addAll(matches);


                    if (matches.size() == 0)
                        AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, matches_recycler,
                                matches_adapter, matches, list, NUMBER_OF_NATIVE_ADS_MATCHES);
                    else
                        AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, matches_recycler,
                                matches_adapter, matches, list, NUMBER_OF_NATIVE_ADS_MATCHES);


                    matches_adapter.notifyItemRangeInserted(0, list.size());
                    shimmerFrameLayout.hideShimmer();
                    shimmerFrameLayout.setVisibility(GONE);

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
    public void getLatestNews() {
        if (getActivity().getIntent().getExtras() != null) {
            News article = gson.fromJson(getActivity().getIntent().getExtras().getString("article"), News.class);
            if (article != null) {
                getActivity().getIntent().removeExtra("article");
                Utils.loadInterstitialAd(getActivity().getSupportFragmentManager(), "any","news", getContext(), () -> {
                    Intent intent = new Intent(getActivity(), NewsItemActivity.class);
                    intent.putExtra(StaticConfig.NEWS, article);
                    startActivity(intent);
                });
            }
        }

    }

    public void getPlayerMatches() {

        Call<ApiResponse> call1 = StaticConfig.apiInterface.getPlayerMatches(team_id, Calendar.getInstance().get(Calendar.YEAR));
        call1.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> apiResponse) {
                try {


                    ApiResponse response = apiResponse.body();

                    long currentClientTime = Calendar.getInstance().getTimeInMillis();


                    JSONArray items = new JSONArray(gson.toJson(response.getResponse()));

                    matches.clear();
                    list.clear();

                    matches_adapter.notifyDataSetChanged();

                    for (int i = 0; i < items.length(); i++) {
                        String jsonString = items.getJSONObject(i).toString();
                        Match match;
                        match = gson.fromJson(jsonString, Match.class);

                        matches.add(match);
                    }

                    Collections.sort(matches, (o, t1) -> {
                        Match match = o;
                        Match match1 = t1;

                        int matchLeague = 0;
                        int match1League = 0;

                        for (int league : appSingleton.leagues) {
                            if (match.getLeague().getId() == league) {
                                matchLeague = appSingleton.leagues.indexOf(league);
                            }
                            if (match1.getLeague().getId() == league) {
                                match1League = appSingleton.leagues.indexOf(league);
                            }
                        }

                        return Integer.compare(matchLeague, match1League);
                    });

                    list.addAll(matches);

                    if (matches.size() == 0)
                        AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, matches_recycler,
                                matches_adapter, matches, list, NUMBER_OF_NATIVE_ADS_MATCHES);
                    else
                        AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, matches_recycler,
                                matches_adapter, matches, list, NUMBER_OF_NATIVE_ADS_MATCHES);


                    matches_adapter.notifyItemRangeInserted(0, list.size());
                    shimmerFrameLayout.hideShimmer();
                    shimmerFrameLayout.setVisibility(GONE);


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


}
