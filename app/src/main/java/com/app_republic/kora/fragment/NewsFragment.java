package com.app_republic.kora.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_republic.kora.R;
import com.app_republic.kora.activity.NewsItemActivity;
import com.app_republic.kora.model.ApiResponse;
import com.app_republic.kora.model.News;
import com.app_republic.kora.model.TrendingTeam;
import com.app_republic.kora.utils.AppSingleton;
import com.app_republic.kora.utils.StaticConfig;
import com.app_republic.kora.utils.UnifiedNativeAdViewHolder;
import com.app_republic.kora.utils.Utils;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.gson.Gson;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app_republic.kora.utils.StaticConfig.CONTENT_ITEM_VIEW_TYPE;
import static com.app_republic.kora.utils.StaticConfig.NUMBER_OF_NATIVE_ADS_NEWS;
import static com.app_republic.kora.utils.StaticConfig.UNIFIED_NATIVE_AD_VIEW_TYPE;

public class NewsFragment extends Fragment implements View.OnClickListener {


    ArrayList<TrendingTeam> trendingTeams = new ArrayList<>();
    List<News> news = new ArrayList<>();
    List<Object> list = new ArrayList<>();
    List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    NewsAdapter news_adapter;
    TeamsAdapter teams_adapter;
    RecyclerView news_recycler, teams_recycler;
    long timeDifference;
    Gson gson;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = AppSingleton.getInstance(getActivity()).getGson();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);


        initialiseViews(view);


        news_adapter = new NewsAdapter(getActivity(), list);
        teams_adapter = new TeamsAdapter(getActivity(), trendingTeams);


        teams_recycler.setAdapter(teams_adapter);
        news_recycler.setAdapter(news_adapter);


        news_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                false);
        teams_recycler.setLayoutManager(layoutManager);


        getLatestNews();
        getTrendingTeams();


        return view;
    }

    private void initialiseViews(View view) {
        news_recycler = view.findViewById(R.id.newsRecycler);
        teams_recycler = view.findViewById(R.id.teamRecycler);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context context;
        List<Object> list;
        Picasso picasso;

        public NewsAdapter(Context context, List<Object> list) {
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
                            parent.getContext()).inflate(R.layout.ad_unified_news,
                            parent, false);
                    return new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);

                case CONTENT_ITEM_VIEW_TYPE:
                    View ContentLayoutView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_news, parent, false);
                    return new NewsViewHolder(ContentLayoutView);
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

                    NewsViewHolder newsViewHolder = (NewsViewHolder) viewHolder;
                    News news_item = (News) list.get(i);


                    if (!news_item.getImageThumb().isEmpty()) {
                        try {
                            picasso.cancelRequest(newsViewHolder.icon);

                            try {
                                picasso.load(news_item.getImageThumb())
                                        .fit()
                                        .placeholder(R.drawable.ic_news_large)
                                        .into(newsViewHolder.icon);
                            } catch (Exception e) {
                                picasso.load(news_item.getImageThumb())
                                        .fit()
                                        .into(newsViewHolder.icon);
                            }


                        } catch (OutOfMemoryError e) {
                            e.printStackTrace();
                        }
                    }


                    newsViewHolder.title.setText(news_item.getPostTitle());
                    newsViewHolder.time.setText(news_item.getPostDate());
                    break;
            }


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class NewsViewHolder extends RecyclerView.ViewHolder {

            TextView title, time;
            ImageView icon;
            View V_root;

            public NewsViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                time = itemView.findViewById(R.id.time);
                icon = itemView.findViewById(R.id.icon);
                V_root = itemView.findViewById(R.id.root);

                V_root.setOnClickListener(view -> {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(),
                                    icon, StaticConfig.NEWS);

                    Utils.loadInterstitialAd(getActivity().getSupportFragmentManager(), "any","news", getContext(), () -> {
                        Intent intent = new Intent(context, NewsItemActivity.class);
                        intent.putExtra(StaticConfig.NEWS, (News) list.get(getAdapterPosition()));
                        context.startActivity(intent, options.toBundle());
                    });

                });
            }
        }

        @Override
        public int getItemViewType(int position) {

            Object recyclerViewItem = list.get(position);
            if (recyclerViewItem instanceof UnifiedNativeAd) {
                return UNIFIED_NATIVE_AD_VIEW_TYPE;
            }
            return CONTENT_ITEM_VIEW_TYPE;
        }

    }

    class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder> {

        Context context;
        ArrayList<TrendingTeam> list;

        public TeamsAdapter(Context context, ArrayList<TrendingTeam> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public TeamsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_news_team, viewGroup, false);

            return new TeamsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TeamsViewHolder viewHolder, int i) {

            TrendingTeam trendingTeam = list.get(i);


            if (!trendingTeam.getDepId().equals("global")) {
                try {
                    Picasso.get().load(trendingTeam.getTeamLogo())
                            .placeholder(R.drawable.ic_ball)
                            .into(viewHolder.icon);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                    Picasso.get().load(trendingTeam.getTeamLogo())
                            .into(viewHolder.icon);
                }
            } else {
                viewHolder.icon.setImageResource(R.drawable.ic_ball_small);
            }




        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class TeamsViewHolder extends RecyclerView.ViewHolder {

            ImageView icon;
            View V_root;

            public TeamsViewHolder(@NonNull View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.icon);
                V_root = itemView.findViewById(R.id.root);

                V_root.setOnClickListener(view -> {
                    if (!list.get(getAdapterPosition()).getDepId().equals("global")) {
                        getTeamNews(list.get(getAdapterPosition()).getTeamId());
                    } else {
                        getLatestNews();
                    }


                });
            }
        }
    }


    public void getTeamNews(String team_id) {


        Call<ApiResponse> call1 = StaticConfig.apiInterface.getItemNews("1",
                "", StaticConfig.TEAM, team_id);
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

                    JSONArray items = new JSONArray(gson.toJson(response.getItems()));

                    list.clear();
                    news.clear();
                    news_adapter.notifyDataSetChanged();

                    for (int i = 0; i < items.length(); i++) {
                        String jsonString = items.getJSONObject(i).toString();
                        News news_item;

                        news_item = gson.fromJson(jsonString, News.class);
                        news.add(news_item);
                    }
                    list.addAll(news);


                    if (news.size() == 0)
                        AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, news_recycler, news_adapter,
                                news, list, 3);
                    else
                        AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, news_recycler, news_adapter,
                                news, list, NUMBER_OF_NATIVE_ADS_NEWS);

                    news_adapter.notifyItemRangeInserted(0, news.size());

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



        Call<ApiResponse> call1 = StaticConfig.apiInterface.getLatestNews("1",
                "");
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

                    JSONArray items = new JSONArray(gson.toJson(response.getItems()));

                    list.clear();
                    news.clear();
                    news_adapter.notifyDataSetChanged();

                    for (int i = 0; i < items.length(); i++) {
                        String jsonString = items.getJSONObject(i).toString();
                        News news_item;
                        news_item = gson.fromJson(jsonString, News.class);
                        news.add(news_item);
                    }
                    list.addAll(news);



                    if (news.size() == 0)
                        AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, news_recycler, news_adapter,
                            news, list, 3);
                    else
                        AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, news_recycler, news_adapter,
                                news, list, NUMBER_OF_NATIVE_ADS_NEWS);


                    news_adapter.notifyItemRangeInserted(0, news.size());



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


    public void getTrendingTeams() {

        trendingTeams.clear();

        Call<ApiResponse> call1 = StaticConfig.apiInterface.getTrendingTeams("1",
                "");
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

                    JSONArray items = new JSONArray(gson.toJson(response.getItems()));

                    TrendingTeam global = new TrendingTeam("global");
                    trendingTeams.add(global);

                    for (int i = 0; i < items.length(); i++) {
                        String jsonString = items.getJSONObject(i).toString();
                        TrendingTeam trendingTeam;
                        trendingTeam = gson.fromJson(jsonString, TrendingTeam.class);
                        trendingTeams.add(trendingTeam);
                    }


                    teams_adapter.notifyDataSetChanged();


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
