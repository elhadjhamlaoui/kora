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

public class ItemNewsFragment extends Fragment implements View.OnClickListener {

    List<News> news = new ArrayList<>();
    List<Object> list = new ArrayList<>();
    List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    NewsAdapter news_adapter;
    RecyclerView news_recycler;
    long timeDifference;
    String item_id, item_type;
    Gson gson;

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
        gson = AppSingleton.getInstance(getActivity()).getGson();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_news, container, false);

        item_id = getArguments().getString(StaticConfig.PARAM_ITEM_ID);
        item_type = getArguments().getString(StaticConfig.PARAM_ITEM_TYPE);

        initialiseViews(view);


        news_adapter = new NewsAdapter(getActivity(), list);

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


    class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context context;
        List<Object> list;
        Picasso picasso;

        NewsAdapter(Context context, List<Object> list) {
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
                    return new NewsAdapter.NewsViewHolder(ContentLayoutView);
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
                    News news_item = (News) list.get(i);
                    NewsViewHolder newsViewHolder = (NewsViewHolder) viewHolder;
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


    public void getNews() {


        Call<ApiResponse> call1 = StaticConfig.apiInterface.getItemNews("1",
                "", item_type, item_id);
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
                        ;
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


}
