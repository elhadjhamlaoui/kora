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
import com.app_republic.kora.model.News;
import com.app_republic.kora.model.Team;
import com.app_republic.kora.request.GetItemNews;
import com.app_republic.kora.request.GetItemPlayersDetailed;
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

import static com.app_republic.kora.utils.StaticConfig.ITEM_NEWS_REQUEST;
import static com.app_republic.kora.utils.StaticConfig.LATEST_NEWS_REQUEST;
import static com.app_republic.kora.utils.StaticConfig.TRENDING_TEAMS_REQUEST;

public class ItemNewsFragment extends Fragment implements View.OnClickListener {


    ArrayList<News> news = new ArrayList<>();
    ArrayList<Team> teams = new ArrayList<>();

    NewsAdapter news_adapter;
    RecyclerView news_recycler;
    long timeDifference;
    String item_id, item_type;

    public ItemNewsFragment() {
        // Required empty public constructor
    }

    public static ItemNewsFragment newInstance() {
        ItemNewsFragment fragment = new ItemNewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_news, container, false);

        item_id = getArguments().getString(StaticConfig.PARAM_ITEM_ID);
        item_type = getArguments().getString(StaticConfig.PARAM_ITEM_TYPE);

        initialiseViews(view);



        news_adapter = new NewsAdapter(getActivity(), news);

        news_recycler.setAdapter(news_adapter);


        news_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));



        getNews();


        return view;
    }

    private void initialiseViews(View view) {
        news_recycler = view.findViewById(R.id.newsRecycler);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }



    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

        Context context;
        ArrayList<News> list;

        public NewsAdapter(Context context, ArrayList<News> list) {
            this.context = context;
            this.list = list;
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



            if (!news_item.getPostImage().isEmpty())
            Picasso.get().load(news_item.getPostImage())
                    .placeholder(R.drawable.ic_news_large)
                    .into(viewHolder.icon);

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



    public void getNews() {



        GetItemNews getItemNews = new GetItemNews(
                item_type,
                item_id,
                response -> {

                    try {


                        JSONObject object = new JSONObject(response);
                        String current_date = object.getString("current_date");
                        long currentServerTime = Utils.getMillisFromServerDate(current_date);

                        long currentClientTime = Calendar.getInstance().getTimeInMillis();

                        timeDifference = currentServerTime > currentClientTime ?
                                currentServerTime - currentClientTime : currentClientTime - currentServerTime;

                        JSONArray items = object.getJSONArray("items");

                        news.clear();
                        news_adapter.notifyDataSetChanged();

                        for (int i = 0; i < items.length(); i++) {
                            String jsonString = items.getJSONObject(i).toString();
                            News news_item;
                            Gson gson = new Gson();
                            news_item = gson.fromJson(jsonString, News.class);
                            news.add(news_item);
                        }


                        news_adapter.notifyItemRangeInserted(0, news.size());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }


                }, error ->
                error.printStackTrace());

        AppSingleton.getInstance(getActivity()).addToRequestQueue(getItemNews, ITEM_NEWS_REQUEST);


    }



}
