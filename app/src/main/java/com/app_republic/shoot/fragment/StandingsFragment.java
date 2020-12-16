package com.app_republic.shoot.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_republic.shoot.R;
import com.app_republic.shoot.model.ApiResponse;
import com.app_republic.shoot.model.News;
import com.app_republic.shoot.model.Standing;
import com.app_republic.shoot.utils.AppSingleton;
import com.app_republic.shoot.utils.StaticConfig;
import com.app_republic.shoot.utils.UnifiedNativeAdViewHolder;
import com.app_republic.shoot.utils.Utils;
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

import static com.app_republic.shoot.utils.StaticConfig.CONTENT_ITEM_VIEW_TYPE;
import static com.app_republic.shoot.utils.StaticConfig.NUMBER_OF_NATIVE_ADS_NEWS;
import static com.app_republic.shoot.utils.StaticConfig.PARAM_TEAM_ID;
import static com.app_republic.shoot.utils.StaticConfig.UNIFIED_NATIVE_AD_VIEW_TYPE;

public class StandingsFragment extends Fragment {

    ArrayList<Standing> standings = new ArrayList<>();
    List<Object> list = new ArrayList<>();

    List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    StandingsAdapter standingsAdapter;
    RecyclerView standingsRecyclerView;

    long timeDifference;
    String dep_id;
    String team_id, team_id_a, team_id_b;
    Gson gson;
    private AppSingleton appSingleton;

    public StandingsFragment() {
        // Required empty public constructor
    }

    public static StandingsFragment newInstance() {
        StandingsFragment fragment = new StandingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_standings, container, false);

        dep_id = getArguments().getString(StaticConfig.PARAM_DEP_ID);
        team_id = getArguments().getString(PARAM_TEAM_ID, "");
        team_id_a = getArguments().getString(StaticConfig.PARAM_TEAM_ID_A, "");
        team_id_b = getArguments().getString(StaticConfig.PARAM_TEAM_ID_B, "");

        initialiseViews(view);


        standingsAdapter = new StandingsAdapter(list);


        standingsRecyclerView.setAdapter(standingsAdapter);


        standingsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        getStandings();

        return view;

    }

    private void initialiseViews(View view) {
        standingsRecyclerView = view.findViewById(R.id.recyclerView);

    }


    private void getStandings() {

        Call<ApiResponse> call1 = StaticConfig.apiInterface.getDepStandings("0",
                appSingleton.JWS.equals("") ? "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJCQTozRTo3MzowRjpFMDo5MTo1QjpEMzpEQjoyQjoxRDowODoyNTpCOTpDMjpCNjpDRTo3MjpCMzpENiIsImlhdCI6MTYwNTk2MjYxNH0.PqYJXJQB30VPUPgLWYiUZ2eMfI5Yr00WxUyNqrmdE97jIDTqzlaH9pQE5tRA82S4IaVG1FEVq5JHXTuJ9Ik_Ag" : appSingleton.JWS, dep_id);
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
                    standings.clear();
                    standingsAdapter.notifyDataSetChanged();


                    for (int i = 0; i < items.length(); i++) {
                        String jsonString = items.getJSONObject(i).toString();
                        Standing standing;
                        ;
                        standing = gson.fromJson(jsonString, Standing.class);
                        standings.add(standing);
                    }

                    list.addAll(standings);


                    if (standings.size() == 0)
                        AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, standingsRecyclerView, standingsAdapter,
                                standings, list, 3);
                    else
                        AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, standingsRecyclerView, standingsAdapter,
                                standings, list, NUMBER_OF_NATIVE_ADS_NEWS);


                    standingsAdapter.notifyItemRangeInserted(0, standings.size());


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

    class StandingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<Object> list;
        Picasso picasso;

        private StandingsAdapter(List<Object> list) {
            this.list = list;
            picasso = AppSingleton.getInstance(getActivity()).getPicasso();

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            switch (i) {
                case UNIFIED_NATIVE_AD_VIEW_TYPE:
                    View unifiedNativeLayoutView = LayoutInflater.from(
                            viewGroup.getContext()).inflate(R.layout.ad_unified,
                            viewGroup, false);
                    return new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);

                case CONTENT_ITEM_VIEW_TYPE:
                    View ContentLayoutView = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.item_standing, viewGroup, false);
                    return new StandingsViewHolder(ContentLayoutView);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

            int viewType = getItemViewType(i);
            switch (viewType) {
                case UNIFIED_NATIVE_AD_VIEW_TYPE:
                    UnifiedNativeAd nativeAd = (UnifiedNativeAd) list.get(i);
                    UnifiedNativeAdViewHolder.populateNativeAdView(nativeAd, ((UnifiedNativeAdViewHolder) holder).getAdView());
                    break;
                case CONTENT_ITEM_VIEW_TYPE:

                    StandingsViewHolder viewHolder = (StandingsViewHolder) holder;

                    Standing standing = (Standing) list.get(i);

                    //viewHolder.standing.setText(String.valueOf(i + 1));
                    viewHolder.standing.setText(String.valueOf(standings.indexOf(standing) + 1));

                    viewHolder.points.setText(standing.getPoints());
                    viewHolder.goals_diff.setText(standing.getGoalsDiff());
                    viewHolder.against.setText(standing.getAgainst());
                    viewHolder.dep_for.setText(standing.getDepFor());
                    viewHolder.lose.setText(standing.getLose());
                    viewHolder.draw.setText(standing.getDraw());
                    viewHolder.win.setText(standing.getWin());
                    viewHolder.play.setText(standing.getPlay());
                    viewHolder.name.setText(standing.getTeamName());

                    if (!standing.getTeamLogo().isEmpty()) {
                        picasso.cancelRequest(viewHolder.icon);
                        picasso.load(standing.getTeamLogo()).fit()
                                .into(viewHolder.icon);
                    }

                    if (standing.getHasGroups().equals("1")) {
                        if (i == 0) {
                            viewHolder.group.setText(getString(R.string.group) + " " +
                                    standing.getGroupNo());
                            viewHolder.V_group.setVisibility(View.VISIBLE);
                        } else {
                            Standing previousStanding = (Standing) list.get(i - 1);

                            if (!previousStanding.getGroupNo().equals(standing.getGroupNo())) {
                                viewHolder.group.setText(getString(R.string.group) + " " +
                                        standing.getGroupNo());
                                viewHolder.V_group.setVisibility(View.VISIBLE);
                            } else
                                viewHolder.V_group.setVisibility(View.GONE);
                        }

                    }

                    if (team_id.equals(standing.getTeamId()) || team_id_a.equals(standing.getTeamId())
                            || team_id_b.equals(standing.getTeamId())
                    ) {
                        viewHolder.V_root
                                .setBackgroundColor(getResources()
                                        .getColor(android.R.color.holo_orange_light));
                    } else {
                        if (i % 2 == 0)
                            viewHolder.V_root
                                    .setBackgroundColor(getResources()
                                            .getColor(R.color.gray_200));
                        else

                            viewHolder.V_root
                                    .setBackgroundColor(getResources()
                                            .getColor(android.R.color.transparent));
                    }


                    break;
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

        @Override
        public int getItemCount() {
            return list.size();
        }

        class StandingsViewHolder extends RecyclerView.ViewHolder {

            ImageView icon;

            View V_root;
            View V_group;
            TextView points, dep_for, against, goals_diff, lose, win, draw, play, name, standing, group;

            private StandingsViewHolder(@NonNull View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.icon);

                V_root = itemView.findViewById(R.id.root);
                points = itemView.findViewById(R.id.points);
                dep_for = itemView.findViewById(R.id.dep_for);
                against = itemView.findViewById(R.id.against);
                goals_diff = itemView.findViewById(R.id.goals_diff);
                lose = itemView.findViewById(R.id.lose);
                win = itemView.findViewById(R.id.win);
                draw = itemView.findViewById(R.id.draw);
                play = itemView.findViewById(R.id.play);
                name = itemView.findViewById(R.id.name);
                standing = itemView.findViewById(R.id.standing);
                V_group = itemView.findViewById(R.id.group_layout);
                group = itemView.findViewById(R.id.group);

                V_root.setOnClickListener(view -> {
                    Standing standing = (Standing)list.get(getAdapterPosition());
                    String current_team_id = standing.getTeamId();
                    if (!current_team_id.equals(team_id)) {
                        Utils.startTeamActivity(getActivity(),
                                getActivity().getSupportFragmentManager(),
                                standing.getTeamId());
                    }

                });


            }
        }
    }
}
