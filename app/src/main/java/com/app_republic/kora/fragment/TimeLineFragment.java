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
import com.app_republic.kora.model.Match;
import com.app_republic.kora.model.News;
import com.app_republic.kora.model.Team;
import com.app_republic.kora.model.TimeLine;
import com.app_republic.kora.request.GetLatestNews;
import com.app_republic.kora.request.GetTimeline;
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

import static com.app_republic.kora.utils.StaticConfig.LATEST_NEWS_REQUEST;
import static com.app_republic.kora.utils.StaticConfig.TIME_LINE_REQUEST;
import static com.app_republic.kora.utils.StaticConfig.TRENDING_TEAMS_REQUEST;

public class TimeLineFragment extends Fragment implements View.OnClickListener {


    ArrayList<TimeLine> list = new ArrayList<>();

    TimeLineAdapter timeLineAdapter;
    RecyclerView recyclerView;
    Match match;
    final int ITEM_TYPE_TEAM1 = 1;
    final int ITEM_TYPE_TEAM2 = 2;

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


    class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.viewHolder> {

        Context context;
        ArrayList<TimeLine> list;

        public TimeLineAdapter(Context context, ArrayList<TimeLine> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view;
            if (getItemViewType(i) == ITEM_TYPE_TEAM1) {
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

            TimeLine item = list.get(i);


            viewHolder.time.setText(item.getTime());
            viewHolder.title.setText(item.getText());
            if (item.getVideoItem() != null)
                viewHolder.video.setText(item.getVideoItem().getVideoTitle().split("\\|")[0]);
            else
                viewHolder.video.setText("");

            switch (Integer.parseInt(item.getType())) {
                case 1:
                    viewHolder.icon.setImageResource(R.drawable.ic_goal);
                    break;
                case 2:
                    viewHolder.icon.setImageResource(R.drawable.ic_ball_small);
                    break;
                case 3:
                    viewHolder.icon.setImageResource(R.drawable.ic_goal);
                    break;
                case 4:
                    viewHolder.icon.setImageResource(R.drawable.ic_goal);
                    break;
                case 5:
                    viewHolder.icon.setImageResource(R.drawable.ic_goal);
                    break;
                case 6:
                    viewHolder.icon.setImageResource(R.drawable.ic_yellow_card);
                    break;
                case 7:
                    viewHolder.icon.setImageResource(R.drawable.ic_red_card);
                    break;

            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public int getItemViewType(int position) {
            switch (Integer.parseInt(list.get(position).getTeam())) {
                case 1:
                    return ITEM_TYPE_TEAM1;

                case 2:
                    return ITEM_TYPE_TEAM2;
            }
            return 1;
        }

        class viewHolder extends RecyclerView.ViewHolder {

            TextView title, video, time;
            ImageView icon;
            View V_root;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                time = itemView.findViewById(R.id.time);
                video = itemView.findViewById(R.id.video);
                icon = itemView.findViewById(R.id.icon);
                V_root = itemView.findViewById(R.id.root);

                V_root.setOnClickListener(view -> {

                });


            }
        }
    }

    public void getTimeLine() {

        list.clear();


        GetTimeline getTimeline = new GetTimeline(
                match.getLiveId(),
                response -> {

                    try {


                        JSONObject object = new JSONObject(response);

                        JSONArray items = object.getJSONArray("items");

                        for (int i = 0; i < items.length(); i++) {
                            String jsonString = items.getJSONObject(i).toString();
                            TimeLine timeLine;
                            Gson gson = new Gson();
                            timeLine = gson.fromJson(jsonString, TimeLine.class);
                            list.add(timeLine);
                        }


                        timeLineAdapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }


                }, error ->
                error.printStackTrace());

        AppSingleton.getInstance(getActivity()).addToRequestQueue(getTimeline, TIME_LINE_REQUEST);


    }


}
