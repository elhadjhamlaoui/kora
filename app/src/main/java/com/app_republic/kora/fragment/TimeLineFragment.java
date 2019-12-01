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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_republic.kora.R;
import com.app_republic.kora.activity.GoToVideoActivity;
import com.app_republic.kora.model.ApiResponse;
import com.app_republic.kora.model.Match;
import com.app_republic.kora.model.TimeLine;
import com.app_republic.kora.request.GetTimeline;
import com.app_republic.kora.utils.AppSingleton;
import com.app_republic.kora.utils.StaticConfig;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app_republic.kora.utils.StaticConfig.TIME_LINE_REQUEST;

public class TimeLineFragment extends Fragment implements View.OnClickListener {


    ArrayList<TimeLine> list = new ArrayList<>();

    TimeLineAdapter timeLineAdapter;
    RecyclerView recyclerView;
    Match match;
    Gson gson;
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
        gson = AppSingleton.getInstance(getActivity()).getGson();
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

            TimeLine item = list.get(i);

            int type = Integer.parseInt(item.getType()) - 1;


            viewHolder.time.setText(item.getTime());
            viewHolder.title.setText(item.getText());



            if (item.getVideoItem() != null) {
                viewHolder.video.setText(context.getResources()
                        .getStringArray(R.array.timeline_types)[type]);
                viewHolder.video.
                        setCompoundDrawablesWithIntrinsicBounds(context.getResources()
                                        .getDrawable(R.drawable.ic_play_white),
                                null, null, null);
            }

            else {
                viewHolder.video.setText(context.getResources()
                        .getStringArray(R.array.timeline_types)[type]);

                viewHolder.video.
                        setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);
            }




            if (i == 0) {
                viewHolder.end.setVisibility(View.VISIBLE);
                viewHolder.end.setText(context.getString(R.string.end_match));
            } else {

                int mins = Integer.parseInt(item.getTime());
                int previousMins = Integer.parseInt(list.get(i - 1).getTime());

                if (i == (list.size() - 1)) {
                    viewHolder.watch_bac.setVisibility(View.VISIBLE);
                    viewHolder.watch.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.watch_bac.setVisibility(View.GONE);
                    viewHolder.watch.setVisibility(View.GONE);
                }

                if (previousMins > 45 && mins <= 45) {
                    viewHolder.end.setVisibility(View.VISIBLE);
                    viewHolder.end.setText(context.getString(R.string.end_half_time));
                } else {
                    viewHolder.end.setVisibility(View.GONE);
                }
            }
            switch (Integer.parseInt(item.getType())) {
                case 1:
                    viewHolder.icon.setImageResource(R.drawable.ic_goal);
                    break;
                case 2:
                    viewHolder.icon.setImageResource(R.drawable.ic_streaming);
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

            TextView title, video, time, end;
            ImageView icon, watch, watch_bac;
            View V_root;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                time = itemView.findViewById(R.id.time);
                video = itemView.findViewById(R.id.video);
                end = itemView.findViewById(R.id.end);

                icon = itemView.findViewById(R.id.icon);
                watch = itemView.findViewById(R.id.watch);
                watch_bac = itemView.findViewById(R.id.watch_bac);
                V_root = itemView.findViewById(R.id.card);

                V_root.setOnClickListener(view -> {
                    Intent intent = new Intent(context, GoToVideoActivity.class);
                    intent.putExtra(StaticConfig.VIDEO_URI,
                            list.get(getAdapterPosition()).getVideoItem().getVideoCode());
                    startActivity(intent);
                });


            }
        }
    }

    public void getTimeLine() {

        list.clear();




        Call<ApiResponse> call1 = StaticConfig.apiInterface.getTimeline("1",
                "", match.getLiveId());
        call1.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> apiResponse) {
                try {


                    ApiResponse response = apiResponse.body();

                    JSONArray items = new JSONArray(gson.toJson(response.getItems()));

                    for (int i = 0; i < items.length(); i++) {
                        String jsonString = items.getJSONObject(i).toString();
                        TimeLine timeLine;
                        ;
                        timeLine = gson.fromJson(jsonString, TimeLine.class);
                        list.add(timeLine);
                    }


                    Collections.reverse(list);

                    timeLineAdapter.notifyDataSetChanged();


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
