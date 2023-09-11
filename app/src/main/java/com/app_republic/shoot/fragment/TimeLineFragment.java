package com.app_republic.shoot.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_republic.shoot.R;
import com.app_republic.shoot.model.Events.Event;
import com.app_republic.shoot.model.Events.EventsResponse;
import com.app_republic.shoot.model.general.Match;
import com.app_republic.shoot.utils.AppSingleton;
import com.app_republic.shoot.utils.StaticConfig;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class TimeLineFragment extends Fragment implements View.OnClickListener {


    ArrayList<Event> list = new ArrayList<>();

    TimeLineAdapter timeLineAdapter;
    RecyclerView recyclerView;
    Match match;
    Gson gson;
    final int ITEM_TYPE_TEAM1 = 1;
    final int ITEM_TYPE_TEAM2 = 2;
    ShimmerFrameLayout shimmerFrameLayout;
    private AppSingleton appSingleton;

    public TimeLineFragment() {
        // Required empty public constructor
    }

    public static TimeLineFragment newInstance() {
        TimeLineFragment fragment = new TimeLineFragment();
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
        View view = inflater.inflate(R.layout.fragment_time_line, container, false);


        initialiseViews(view);

        match = getArguments().getParcelable(StaticConfig.MATCH);

        timeLineAdapter = new TimeLineAdapter(getActivity(), list);


        recyclerView.setAdapter(timeLineAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        getTimeLine();

        AppSingleton.getInstance(getActivity()).loadNativeAd(view.findViewById(R.id.adView));
        AppSingleton.getInstance(getActivity()).loadNativeAd(view.findViewById(R.id.adView2));

        return view;
    }

    private void initialiseViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.showShimmer(true);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.viewHolder> {

        Context context;
        ArrayList<Event> list;

        public TimeLineAdapter(Context context, ArrayList<Event> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view;
            if (viewType == ITEM_TYPE_TEAM1) {
                view = LayoutInflater.from(getActivity())
                        .inflate(R.layout.item_timeline_team1, viewGroup, false);

            } else {
                view = LayoutInflater.from(getActivity())
                        .inflate(R.layout.item_timeline_team2, viewGroup, false);

            }

            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

            Event item = list.get(i);

            viewHolder.time.setText(String.valueOf(item.getTime().getElapsed()));

            viewHolder.player.setText(item.getPlayer().getName());

            if (item.getAssist().getName() != null) {
                viewHolder.assist.setVisibility(View.VISIBLE);


                if (item.getType().equals("subst")) {
                    viewHolder.assist.setText(item.getAssist().getName());

                    viewHolder.player.setBackgroundResource(R.drawable.bac_round_red);
                    viewHolder.player
                            .setTextColor(getResources()
                                    .getColor(R.color.white));

                    viewHolder.assist.setBackgroundResource(R.drawable.bac_round_blue);

                    viewHolder.assist
                            .setTextColor(getResources()
                                    .getColor(R.color.white));
                } else {
                    viewHolder.assist.setText(getResources().getString(R.string.player_assist) + " " + item.getAssist().getName());
                }
            } else {
                viewHolder.assist.setVisibility(GONE);
            }

            /*if (item.getVideoItem() != null) {
                viewHolder.video.setText(context.getResources()
                        .getStringArray(R.array.timeline_types)[type]);
                viewHolder.video.
                        setCompoundDrawablesWithIntrinsicBounds(context.getResources()
                                        .getDrawable(R.drawable.ic_play_white),
                                null, null, null);
            }*/

            /*else {
                viewHolder.video.setText(context.getResources()
                        .getStringArray(R.array.timeline_types)[type]);

                viewHolder.video.
                        setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);
            }*/


            if (i == (list.size() - 1)) {
                viewHolder.watch.setVisibility(View.VISIBLE);
            } else {
                viewHolder.watch.setVisibility(View.GONE);
            }

            if (i == 0) {
                viewHolder.end.setVisibility(View.VISIBLE);
                viewHolder.end.setText(context.getString(R.string.end_match));
            } else {

                int mins = item.getTime().getElapsed();
                int previousMins = list.get(i - 1).getTime().getElapsed();



                if (previousMins > 45 && mins <= 45) {
                    viewHolder.end.setVisibility(View.VISIBLE);
                    viewHolder.end.setText(context.getString(R.string.end_half_time));
                } else {
                    viewHolder.end.setVisibility(View.GONE);
                }
            }
            switch (item.getType()) {
                case "Goal":
                    viewHolder.icon.setImageResource(R.drawable.ic_goal);

                    if (item.getDetail().equals("Normal Goal")) {
                        viewHolder.title.setText(getResources().getString(R.string.goal));
                    } else if(item.getDetail().equals("Own Goal")) {
                        viewHolder.title.setText(getResources().getString(R.string.own_goal));
                    } else if (item.getDetail().equals("Penalty")) {
                        viewHolder.title.setText(getResources().getString(R.string.penalty));
                        viewHolder.assist.setVisibility(GONE);
                    } else if (item.getDetail().equals("Missed Penalty")) {
                        viewHolder.title.setText(getResources().getString(R.string.missed_penalty2));
                        viewHolder.assist.setVisibility(GONE);
                    }
                    break;
                case "Var":
                    if (item.getDetail().equals("Goal cancelled")) {
                        viewHolder.title.setText(getResources().getString(R.string.goal_cancelled));
                    } else if(item.getDetail().equals("Penalty confirmed")) {
                        viewHolder.title.setText(getResources().getString(R.string.penalty_confirmed));
                    }
                    viewHolder.icon.setImageResource(R.drawable.ic_var);
                    break;
                case "Card":
                    if (item.getDetail().equals("Yellow Card")) {
                        viewHolder.title.setText(getResources().getString(R.string.yellow_card));
                        viewHolder.icon.setImageResource(R.drawable.ic_yellow_card);
                    } else {
                        viewHolder.title.setText(getResources().getString(R.string.red_card));
                        viewHolder.icon.setImageResource(R.drawable.ic_red_card);
                    }
                    break;
                case "subst":
                    viewHolder.title.setText(getResources().getString(R.string.subst));
                    viewHolder.icon.setImageResource(R.drawable.ic_switch);
                    break;
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (list.get(position).getTeam().getId() == match.getTeams().getHome().getId()) {
                return ITEM_TYPE_TEAM1;
            } else {
                return ITEM_TYPE_TEAM2;
            }
        }

        class viewHolder extends RecyclerView.ViewHolder {

            TextView title, player, assist, time, end;
            ImageView icon, watch;
            View V_root;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                time = itemView.findViewById(R.id.time);
                player = itemView.findViewById(R.id.player);
                assist = itemView.findViewById(R.id.assist);
                end = itemView.findViewById(R.id.end);

                icon = itemView.findViewById(R.id.icon);
                watch = itemView.findViewById(R.id.watch);
                V_root = itemView.findViewById(R.id.card);

                V_root.setOnClickListener(view -> {
                    /*if (list.get(getAdapterPosition()).getVideoItem() != null) {
                        Intent intent = new Intent(context, GoToVideoActivity.class);
                        intent.putExtra(StaticConfig.VIDEO_URI,
                                list.get(getAdapterPosition()).getVideoItem().getVideoCode());
                        startActivity(intent);
                    }*/

                });


            }
        }
    }

    public void getTimeLine() {

        list.clear();




        Call<EventsResponse> call1 = StaticConfig.apiInterface.getTimeline(String.valueOf(match.getFixture().getId()));
        call1.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> apiResponse) {
                try {


                    EventsResponse response = apiResponse.body();

                    JSONArray items = new JSONArray(gson.toJson(response.getResponse()));

                    for (int i = 0; i < items.length(); i++) {
                        String jsonString = items.getJSONObject(i).toString();
                        Event timeLine;

                        timeLine = gson.fromJson(jsonString, Event.class);
                        list.add(timeLine);
                    }


                    Collections.reverse(list);
                    shimmerFrameLayout.hideShimmer();
                    shimmerFrameLayout.setVisibility(GONE);
                    timeLineAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });


    }


}
