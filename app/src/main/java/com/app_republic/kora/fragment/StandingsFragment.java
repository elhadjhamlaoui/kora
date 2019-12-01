package com.app_republic.kora.fragment;

import android.content.Intent;
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

import com.app_republic.kora.R;
import com.app_republic.kora.activity.MatchActivity;
import com.app_republic.kora.activity.TeamInfoActivity;
import com.app_republic.kora.model.ApiResponse;
import com.app_republic.kora.model.Standing;
import com.app_republic.kora.request.GetDepStandings;
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

import static com.app_republic.kora.utils.StaticConfig.DEP_STANDINGS_REQUEST;
import static com.app_republic.kora.utils.StaticConfig.PARAM_TEAM_ID;

public class StandingsFragment extends Fragment {

    ArrayList<Standing> standings = new ArrayList<>();
    ;

    StandingsAdapter standingsAdapter;
    RecyclerView standingsRecyclerView;

    long timeDifference;
    String dep_id;
    String team_id, team_id_a, team_id_b;
    Gson gson;

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


        standingsAdapter = new StandingsAdapter(standings);


        standingsRecyclerView.setAdapter(standingsAdapter);


        standingsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        getStandings();

        return view;

    }

    private void initialiseViews(View view) {
        standingsRecyclerView = view.findViewById(R.id.recyclerView);

    }


    private void getStandings() {

        Call<ApiResponse> call1 = StaticConfig.apiInterface.getDepStandings("1",
                "", dep_id);
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

                    standings.clear();
                    standingsAdapter.notifyDataSetChanged();


                    for (int i = 0; i < items.length(); i++) {
                        String jsonString = items.getJSONObject(i).toString();
                        Standing standing;
                        ;
                        standing = gson.fromJson(jsonString, Standing.class);
                        standings.add(standing);
                    }


                    standingsAdapter.notifyItemRangeInserted(0, standings.size());


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

    class StandingsAdapter extends RecyclerView.Adapter<StandingsAdapter.StandingsViewHolder> {

        ArrayList<Standing> list;
        Picasso picasso;

        private StandingsAdapter(ArrayList<Standing> list) {
            this.list = list;
            picasso = AppSingleton.getInstance(getActivity()).getPicasso();

        }

        @NonNull
        @Override
        public StandingsAdapter.StandingsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_standing, viewGroup, false);

            return new StandingsAdapter.StandingsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StandingsAdapter.StandingsViewHolder viewHolder, int i) {

            Standing standing = list.get(i);

            viewHolder.standing.setText(String.valueOf(i + 1));
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
                picasso.load(standing.getTeamLogo()).into(viewHolder.icon);
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


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class StandingsViewHolder extends RecyclerView.ViewHolder {

            ImageView icon;

            LinearLayout V_root;
            TextView points, dep_for, against, goals_diff, lose, win, draw, play, name, standing;

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

                V_root.setOnClickListener(view -> {
                    String current_team_id = list.get(getAdapterPosition()).getTeamId();
                    if (!current_team_id.equals(team_id)) {
                        Intent intent = new Intent(getActivity(), TeamInfoActivity.class);
                        intent.putExtra(StaticConfig.PARAM_TEAM_ID,
                                list.get(getAdapterPosition()).getTeamId());
                        startActivity(intent);
                    }

                });


            }
        }
    }
}
