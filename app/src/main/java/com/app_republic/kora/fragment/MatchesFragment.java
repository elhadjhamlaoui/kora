package com.app_republic.kora.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.app_republic.kora.R;
import com.app_republic.kora.activity.MatchActivity;
import com.app_republic.kora.activity.NewsItemActivity;
import com.app_republic.kora.model.ApiResponse;
import com.app_republic.kora.model.Match;
import com.app_republic.kora.model.News;
import com.app_republic.kora.model.TeamInfo;
import com.app_republic.kora.utils.AppSingleton;
import com.app_republic.kora.utils.StaticConfig;
import com.app_republic.kora.utils.UnifiedNativeAdViewHolder;
import com.app_republic.kora.utils.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.InterstitialAd;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.gson.Gson;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.app_republic.kora.utils.StaticConfig.CONTENT_ITEM_VIEW_TYPE;
import static com.app_republic.kora.utils.StaticConfig.NUMBER_OF_NATIVE_ADS_NEWS;
import static com.app_republic.kora.utils.StaticConfig.UNIFIED_NATIVE_AD_VIEW_TYPE;

public class MatchesFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    List<Match> matches = new ArrayList<>();
    List<Object> list = new ArrayList<>();
    InterstitialAd interstitialAd;
    private Adapter matches_adapter;
    private RecyclerView matches_recycler;

    private final int NUMBER_OF_NATIVE_ADS_MATCHES = 4;
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

    TeamInfo teamInfo;
    String player_id;
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
            teamInfo = getArguments().getParcelable(StaticConfig.TEAM_INFO);
            player_id = getArguments().getString(StaticConfig.PARAM_PLAYER_ID);
        }

        initialiseViews(view);


        matches_adapter = new Adapter(getActivity(), list);


        matches_recycler.setAdapter(matches_adapter);


        matches_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(
                getActivity(), this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        if (teamInfo != null && teamInfo.getRecentMatches() != null) {
            LL_date_layout.setVisibility(GONE);

            matches.addAll(teamInfo.getRecentMatches());

            Collections.sort(matches, (o, t1) -> {
                Match match = o;
                Match match1 = t1;

                long millis1 = Utils.getMillisFromMatchDate(match.getFullDatetimeSpaces());
                long millis2 = Utils.getMillisFromMatchDate(match1.getFullDatetimeSpaces());

                if (millis1 > millis2)
                    return 1;
                else if (millis1 < millis2)
                    return -1;
                else
                    return 0;
            });
            list.addAll(matches);

            if (matches.size() == 0)
                AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, matches_recycler,
                        matches_adapter, matches, list, 3);
            else
                AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, matches_recycler,
                        matches_adapter, matches, list, NUMBER_OF_NATIVE_ADS_MATCHES);

            matches_adapter.notifyDataSetChanged();
            shimmerFrameLayout.hideShimmer();
            shimmerFrameLayout.setVisibility(GONE);
        } else if (player_id != null) {
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
        TV_date.setText(Utils.getReadableDate(calendar));
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

                    long now = System.currentTimeMillis();
                    int delay = Integer.parseInt(match.getLiveM3());
                    long originalTime = Utils.getMillisFromMatchDate(match.getFullDatetimeSpaces());


                    long difference = now - originalTime + timeDifference;
                    viewHolder1.state.setBackground(getResources()
                            .getDrawable(R.drawable.bac_match_state));
                    viewHolder1.state.setTextColor(getResources()
                            .getColor(R.color.blue_9));

                    if (difference > 0) {

                        viewHolder1.live.setVisibility(View.GONE);

                        viewHolder1.scoreTeamA.setText((match.getLivePe1().equals("0") ? "" : "("
                                + match.getLivePe1() + ") ")
                                + match.getLiveRe1()
                        );
                        viewHolder1.scoreTeamB.setText(match.getLiveRe2() +
                                (match.getLivePe2().equals("0") ? "" : " (" + match.getLiveRe2() + ")"));

                        int minutes = Integer.parseInt(match.getActualMinutes());
                        if (minutes > 0) {

                            match.setState(getString(R.string.match_state_live));
                            if (minutes > 45) {
                                if (minutes > (60 - delay))
                                    viewHolder1.state.setText(String.valueOf(minutes - 15 + delay));
                                else
                                    viewHolder1.state.setText(String.valueOf(45));

                            } else
                                viewHolder1.state.setText(String.valueOf(minutes));
                        } else {
                            viewHolder1.state.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                            viewHolder1.state.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                            match.setState(getString(R.string.match_state_finished));
                            viewHolder1.state.setText(" - ");
                        }
                    } else {
                        match.setState(getString(R.string.match_state_coming));
                        viewHolder1.scoreTeamA.setText("");
                        viewHolder1.scoreTeamB.setText("");

                        if (teamInfo != null) {
                            calendar.setTimeInMillis(originalTime - timeDifference);
                            viewHolder1.state.setText(Utils.getReadableDate(calendar));

                        } else
                            viewHolder1.state.setText(Utils.getFullTime(originalTime - timeDifference));

                        if (match.getLiveIslive().equals("1"))
                            viewHolder1.live.setVisibility(View.VISIBLE);
                        else
                            viewHolder1.live.setVisibility(View.GONE);
                    }


                    viewHolder1.name1.setText(match.getLiveTeam1());
                    viewHolder1.name2.setText(match.getLiveTeam2());

                    if (!match.getTeamLogoA().isEmpty()) {
                        picasso.cancelRequest(viewHolder1.logo1);
                        picasso.load(match.getTeamLogoA()).fit().into(viewHolder1.logo1);
                    }

                    if (!match.getTeamLogoB().isEmpty()) {
                        picasso.cancelRequest(viewHolder1.logo2);
                        picasso.load(match.getTeamLogoB()).fit().into(viewHolder1.logo2);
                    }


                    try {
                        if (teamInfo != null || player_id != null) {
                            Match previousMatch;

                            viewHolder1.department.setVisibility(View.VISIBLE);
                            viewHolder1.department.setText(match.getLiveDep());

                            if (i == 0 || i == 1 && !(list.get(i - 1) instanceof Match)) {
                                viewHolder1.section.setText(match.getState());
                                viewHolder1.V_section_layout.setVisibility(View.VISIBLE);
                            } else {

                                if (i == 1)
                                    previousMatch = (Match) list.get(i - 1);
                                else
                                    previousMatch = list.get(i - 1) instanceof Match ? (Match) list.get(i - 1)
                                            : (Match) list.get(i - 2);

                                if (!match.getState().equals((previousMatch).getState())) {
                                    viewHolder1.section.setText(match.getState());
                                    viewHolder1.V_section_layout.setVisibility(View.VISIBLE);
                                } else
                                    viewHolder1.V_section_layout.setVisibility(GONE);

                            }

                        } else {
                            Match previousMatch;
                            if (i == 0 || i == 1 && !(list.get(i - 1) instanceof Match)) {
                                viewHolder1.department.setText(match.getLiveDep());
                                viewHolder1.department.setVisibility(View.VISIBLE);
                            } else {
                                if (i == 1)
                                    previousMatch = (Match) list.get(i - 1);
                                else
                                    previousMatch = list.get(i - 1) instanceof Match ? (Match) list.get(i - 1)
                                            : (Match) list.get(i - 2);

                                if (!(previousMatch).getDepId().equals(match.getDepId())) {
                                    viewHolder1.department.setText(match.getLiveDep());
                                    viewHolder1.department.setVisibility(View.VISIBLE);
                                } else
                                    viewHolder1.department.setVisibility(GONE);

                            }
                        }
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }



                    /*if (i % 2 == 0)
                        viewHolder1.V_root
                                .setBackgroundColor(getResources()
                                        .getColor(R.color.gray_200));
                    else

                        viewHolder1.V_root
                                .setBackgroundColor(getResources()
                                        .getColor(R.color.light_blue_0));*/

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


        dateString = Utils.getReadableDate(calendar);

        Call<ApiResponse> call1 = StaticConfig.apiInterface.getMatches("0",
                appSingleton.JWS.equals("") ? "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJCQTozRTo3MzowRjpFMDo5MTo1QjpEMzpEQjoyQjoxRDowODoyNTpCOTpDMjpCNjpDRTo3MjpCMzpENiIsImlhdCI6MTYwNTk2MjYxNH0.PqYJXJQB30VPUPgLWYiUZ2eMfI5Yr00WxUyNqrmdE97jIDTqzlaH9pQE5tRA82S4IaVG1FEVq5JHXTuJ9Ik_Ag" : appSingleton.JWS, dateString);
        call1.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> apiResponse) {
                try {



                    ApiResponse object = apiResponse.body();
                    String current_date = object.getCurrentDate();
                    long currentServerTime = Utils.getMillisFromServerDate(current_date);

                    long currentClientTime = Calendar.getInstance().getTimeInMillis();

                    timeDifference = currentServerTime > currentClientTime ?
                            currentServerTime - currentClientTime : currentClientTime - currentServerTime;
                    StaticConfig.TIME_DIFFERENCE = timeDifference;


                    JSONArray items = new JSONArray(gson.toJson(object.getItems()));

                    boolean liveMatches = false;

                    matches.clear();
                    list.clear();
                    matches_adapter.notifyDataSetChanged();

                    for (int i = 0; i < items.length(); i++) {
                        String jsonString = items.getJSONObject(i).toString();
                        Match match;
                        match = gson.fromJson(jsonString, Match.class);

                        long localTime = Utils.
                                getMillisFromMatchDate(match.getFullDatetimeSpaces()) - timeDifference;

                        match.setLocalTime(String.valueOf(localTime));
                        matches.add(match);

                        int minutes = Integer.parseInt(match.getActualMinutes());
                        if (minutes > 0)
                            liveMatches = true;
                    }

                    if (matches.isEmpty())
                        TV_empty.setVisibility(View.VISIBLE);
                    else
                        TV_empty.setVisibility(View.GONE);


                    if (liveMatches)
                        handler.postDelayed(runnable, 60 * 1000);

                    Collections.sort(matches, (o, t1) -> {
                        Match match = o;
                        Match match1 = t1;

                        return match.getDepId().compareTo(match1.getDepId());
                    });

                    list.addAll(matches);


                    if (matches.size() == 0)
                        AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, matches_recycler,
                                matches_adapter, matches, list, 2);
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

        Call<ApiResponse> call1 = StaticConfig.apiInterface.getPlayerMatches("0",
                appSingleton.JWS.equals("") ? "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJCQTozRTo3MzowRjpFMDo5MTo1QjpEMzpEQjoyQjoxRDowODoyNTpCOTpDMjpCNjpDRTo3MjpCMzpENiIsImlhdCI6MTYwNTk2MjYxNH0.PqYJXJQB30VPUPgLWYiUZ2eMfI5Yr00WxUyNqrmdE97jIDTqzlaH9pQE5tRA82S4IaVG1FEVq5JHXTuJ9Ik_Ag" : appSingleton.JWS, player_id);
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
                    StaticConfig.TIME_DIFFERENCE = timeDifference;

                    StaticConfig.TIME_DIFFERENCE = timeDifference;

                    JSONArray items = new JSONArray(gson.toJson(response.getItems()));

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
                        long millis1 = Utils.getMillisFromMatchDate(match.getFullDatetimeSpaces());
                        long millis2 = Utils.getMillisFromMatchDate(match1.getFullDatetimeSpaces());

                        if (millis1 > millis2)
                            return 1;
                        else if (millis1 < millis2)
                            return -1;
                        else
                            return 0;
                    });

                    list.addAll(matches);

                    if (matches.size() == 0)
                        AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, matches_recycler,
                                matches_adapter, matches, list, 3);
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
