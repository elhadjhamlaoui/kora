package com.app_republic.kora.fragment;

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

import static com.app_republic.kora.utils.StaticConfig.DEP_STANDINGS_REQUEST;

public class StandingsFragment extends Fragment {

    ArrayList<Standing> standings = new ArrayList<>();;

    StandingsAdapter standingsAdapter;
    RecyclerView standingsRecyclerView;

    long timeDifference;
    String dep_id;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_standings, container, false);

        dep_id = getArguments().getString(StaticConfig.PARAM_DEP_ID);

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



        GetDepStandings getDepStandings = new GetDepStandings(
                dep_id,
                response -> {

                    try {


                        JSONObject object = new JSONObject(response);
                        String current_date = object.getString("current_date");
                        long currentServerTime = Utils.getMillisFromServerDate(current_date);

                        long currentClientTime = Calendar.getInstance().getTimeInMillis();

                        timeDifference = currentServerTime > currentClientTime ?
                                currentServerTime - currentClientTime : currentClientTime - currentServerTime;

                        JSONArray items = object.getJSONArray("items");

                        standings.clear();
                        standingsAdapter.notifyDataSetChanged();


                        for (int i = 0; i < items.length(); i++) {
                            String jsonString = items.getJSONObject(i).toString();
                            Standing standing;
                            Gson gson = new Gson();
                            standing = gson.fromJson(jsonString, Standing.class);
                            standings.add(standing);
                        }


                        standingsAdapter.notifyItemRangeInserted(0, standings.size());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }


                }, error ->
                error.printStackTrace());

        AppSingleton.getInstance(getActivity()).addToRequestQueue(getDepStandings, DEP_STANDINGS_REQUEST);


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
                   /* Intent intent = new Intent(context, NewsItemActivity.class);
                    intent.putExtra(StaticConfig.TEAM, list.get(getAdapterPosition()));
                    context.startActivity(intent);
                    */

                });
            }
        }
    }
}
