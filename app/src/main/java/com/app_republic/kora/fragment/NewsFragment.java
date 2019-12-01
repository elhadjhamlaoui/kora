package com.app_republic.kora.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.app_republic.kora.request.GetLatestNews;
import com.app_republic.kora.request.GetTrendingTeams;
import com.app_republic.kora.utils.AppSingleton;
import com.app_republic.kora.utils.StaticConfig;
import com.app_republic.kora.utils.Utils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app_republic.kora.utils.StaticConfig.LATEST_NEWS_REQUEST;
import static com.app_republic.kora.utils.StaticConfig.TRENDING_TEAMS_REQUEST;

public class NewsFragment extends Fragment implements View.OnClickListener {


    ArrayList<News> news = new ArrayList<>();
    ArrayList<TrendingTeam> trendingTeams = new ArrayList<>();

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


        news_adapter = new NewsAdapter(getActivity(), news);
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



    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

        Context context;
        ArrayList<News> list;
        Picasso picasso;

        public NewsAdapter(Context context, ArrayList<News> list) {
            this.context = context;
            this.list = list;
            picasso = AppSingleton.getInstance(getActivity()).getPicasso();
        }

        @NonNull
        @Override
        public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_news, viewGroup, false);

            return new NewsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NewsViewHolder viewHolder, int i) {

            News news_item = list.get(i);



            if (!news_item.getPostImage().isEmpty()) {
                picasso.cancelRequest(viewHolder.icon);
                picasso.load(news_item.getPostImage())
                        .placeholder(R.drawable.ic_news_large)
                        .into(viewHolder.icon);
            }


            viewHolder.title.setText(news_item.getPostTitle());
            viewHolder.time.setText(news_item.getPostDate());


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

                    Intent intent = new Intent(context, NewsItemActivity.class);
                    intent.putExtra(StaticConfig.NEWS, list.get(getAdapterPosition()));
                    context.startActivity(intent, options.toBundle());
                });
            }
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


            Picasso.get().load(trendingTeam.getTeamLogo())
                    .placeholder(R.drawable.ic_ball)
                    .into(viewHolder.icon);

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
                    /* Intent intent = new Intent(context, NewsItemActivity.class);
                    intent.putExtra(StaticConfig.TEAM, list.get(getAdapterPosition()));
                    context.startActivity(intent);
                    */
                });
            }
        }
    }





    public void getLatestNews() {


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

                    JSONArray items = new JSONArray(gson.toJson(response.getItems()));

                    news.clear();
                    news_adapter.notifyDataSetChanged();

                    for (int i = 0; i < items.length(); i++) {
                        String jsonString = items.getJSONObject(i).toString();
                        News news_item;
                        ;
                        news_item = gson.fromJson(jsonString, News.class);
                        news.add(news_item);
                    }


                    news_adapter.notifyItemRangeInserted(0, news.size());

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

                    JSONArray items = new JSONArray(gson.toJson(response.getItems()));

                    for (int i = 0; i < items.length(); i++) {
                        String jsonString = items.getJSONObject(i).toString();
                        TrendingTeam trendingTeam;
                        ;
                        trendingTeam = gson.fromJson(jsonString, TrendingTeam.class);
                        trendingTeams.add(trendingTeam);
                    }


                    teams_adapter.notifyDataSetChanged();


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


}
