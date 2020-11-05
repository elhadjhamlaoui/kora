package com.app_republic.kora.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_republic.kora.R;
import com.app_republic.kora.activity.GoToVideoActivity;
import com.app_republic.kora.activity.VideoActivity;
import com.app_republic.kora.model.ApiResponse;
import com.app_republic.kora.model.Match;
import com.app_republic.kora.model.Video;
import com.app_republic.kora.utils.AppSingleton;
import com.app_republic.kora.utils.StaticConfig;
import com.app_republic.kora.utils.UnifiedNativeAdViewHolder;
import com.app_republic.kora.utils.Utils;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.gson.Gson;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app_republic.kora.utils.StaticConfig.CONTENT_ITEM_VIEW_TYPE;
import static com.app_republic.kora.utils.StaticConfig.IS_LIVE;
import static com.app_republic.kora.utils.StaticConfig.MATCH;
import static com.app_republic.kora.utils.StaticConfig.NUMBER_OF_NATIVE_ADS_VIDEOS;
import static com.app_republic.kora.utils.StaticConfig.UNIFIED_NATIVE_AD_VIEW_TYPE;

public class VideosFragment extends Fragment implements View.OnClickListener {


    List<Video> videos = new ArrayList<>();
    List<Object> list = new ArrayList<>();
    List<UnifiedNativeAd> mNativeAds = new ArrayList<>();
    List<String> matches = new ArrayList<>();

    VideosAdapter videos_adapter;
    RecyclerView recyclerView;
    long timeDifference;
    Gson gson;
    Match match;
    Handler handler;
    public VideosFragment() {
        // Required empty public constructor
    }

    public static VideosFragment newInstance() {
        VideosFragment fragment = new VideosFragment();
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
        View view = inflater.inflate(R.layout.fragment_videos, container, false);

        match = getArguments().getParcelable(MATCH);

        handler = new Handler();

        initialiseViews(view);


        videos_adapter = new VideosAdapter(getActivity(), list);

        recyclerView.setAdapter(videos_adapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        getVideos();

        AppSingleton.getInstance(getActivity()).loadNativeAd(view.findViewById(R.id.adView));
        AppSingleton.getInstance(getActivity()).loadNativeAd(view.findViewById(R.id.adView2));

        return view;
    }

    private void initialiseViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }



    class VideosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context context;
        List<Object> list;
        Picasso picasso;

        public VideosAdapter(Context context, List<Object> list) {
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
                            .inflate(R.layout.item_video, parent, false);
                    return new VideosViewHolder(ContentLayoutView);
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

                    VideosViewHolder videosViewHolder = (VideosViewHolder) viewHolder;
                    Video video = (Video) list.get(i);



                    if (!video.getVideoImage().isEmpty()) {
                        picasso.cancelRequest(videosViewHolder.icon);
                        picasso.load(video.getVideoImage())
                                .fit()
                                .placeholder(R.drawable.ic_video)
                                .into(videosViewHolder.icon);
                    } else {
                        videosViewHolder.icon.setImageResource(R.drawable.ic_video);
                    }


                    videosViewHolder.title.setText(video.getVideoTitle());
                    break;
            }



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class VideosViewHolder extends RecyclerView.ViewHolder {

            TextView title;
            CircleImageView icon;
            View V_root;

            public VideosViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                icon = itemView.findViewById(R.id.icon);
                V_root = itemView.findViewById(R.id.root);

                V_root.setOnClickListener(view -> {
                    Utils.loadInterstitialAd(getActivity().getSupportFragmentManager(), "any","video", getContext(), () -> {
                        if (!((Video)list.get(getAdapterPosition())).getVideoCode().equals("-1")) {
                            Intent intent = new Intent(context, GoToVideoActivity.class);
                            intent.putExtra(StaticConfig.VIDEO_URI,
                                    ((Video)list.get(getAdapterPosition())).getVideoCode());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getActivity(), VideoActivity.class);
                            intent.putExtra(MATCH, match);

                            startActivity(intent);
                        }
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


    public void getVideos() {


        Call<ApiResponse> call1 = StaticConfig.apiInterface.getVideos("0",
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
                            currentServerTime - currentClientTime : currentClientTime - currentServerTime;StaticConfig.TIME_DIFFERENCE = timeDifference;

                    JSONArray items = new JSONArray(gson.toJson(response.getItems()));

                    list.clear();
                    videos.clear();
                    videos_adapter.notifyDataSetChanged();


                    for (int i = 0; i < items.length(); i++) {
                        String jsonString = items.getJSONObject(i).toString();
                        Video video;
                        video = gson.fromJson(jsonString, Video.class);
                        videos.add(video);
                    }
                    list.addAll(videos);


                    AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, recyclerView, videos_adapter,
                            videos,list, NUMBER_OF_NATIVE_ADS_VIDEOS);



                    videos_adapter.notifyItemRangeInserted(0, videos.size());

                    loadLiveMatches();

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


    private void loadLiveMatches() {

        Video video1 = new Video(match.getLiveId(),"-1",
                "","",getString(R.string.live_stream));
        videos.add(video1);
        list.add(video1);

        videos_adapter.notifyDataSetChanged();

        /*

        String url = "http://www.yalla-shoot.com/live/index.php";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            Elements elements = doc.select(".matsh_live");
            for (Element element : elements) {
                String match_url = element.attr("href");
                matches.add(match_url);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
*/


    }





}
