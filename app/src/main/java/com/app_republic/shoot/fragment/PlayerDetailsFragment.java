package com.app_republic.shoot.fragment;

import static com.app_republic.shoot.utils.StaticConfig.NUMBER_OF_NATIVE_ADS_NEWS;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_republic.shoot.R;
import com.app_republic.shoot.model.PlayersResponse.League;
import com.app_republic.shoot.model.PlayersResponse.Player;
import com.app_republic.shoot.model.PlayersResponse.PlayersResponse;
import com.app_republic.shoot.model.PlayersResponse.ResponseItem;
import com.app_republic.shoot.model.PlayersResponse.StatisticsItem;
import com.app_republic.shoot.model.general.LeagueModel;
import com.app_republic.shoot.model.general.PlayerDepartment;
import com.app_republic.shoot.model.general.PlayerDetail;
import com.app_republic.shoot.utils.AppSingleton;
import com.app_republic.shoot.utils.StaticConfig;
import com.app_republic.shoot.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerDetailsFragment extends Fragment implements View.OnClickListener {


    ArrayList<PlayerDetail> details = new ArrayList<>();
    ArrayList<PlayerDepartment> departments = new ArrayList<>();

    ArrayList<League> leagues = new ArrayList<>();

    ArrayList<StatisticsItem> playerStatistics = new ArrayList<>();

    Player player_initial;
    StatisticsItem statisticItem;
    Adapter details_adapter;
    DepartmentsAdapter departments_adapter;
    RecyclerView details_recycler, departments_recycler;

    AppSingleton appSingleton;
    String dep_id;
    private Gson gson;

    public PlayerDetailsFragment() {
        // Required empty public constructor
    }

    public static PlayerDetailsFragment newInstance() {
        PlayerDetailsFragment fragment = new PlayerDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_player_details, container,
                false);

        initialiseViews(view);

        appSingleton = AppSingleton.getInstance(getActivity());


        details_adapter = new Adapter(getActivity(), details);
        departments_adapter = new DepartmentsAdapter(getActivity(), departments);

        details_recycler.setAdapter(details_adapter);
        departments_recycler.setAdapter(departments_adapter);


        departments_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),
                RecyclerView.HORIZONTAL, false));
        details_recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        player_initial = getArguments().getParcelable(StaticConfig.PLAYER);

        getPlayerStatistics();

        return view;
    }
    private void updateUI(String leagueId) {
        details.clear();

        statisticItem = Utils.getPlayerStatisticFromDepId(playerStatistics, leagueId);

        details.add(new PlayerDetail(getString(R.string.scored_penalty), String.valueOf(statisticItem.getPenalty().getScored()),
                R.drawable.ic_goal));
        details.add(new PlayerDetail(getString(R.string.missed_penalty), String.valueOf(statisticItem.getPenalty().getMissed()),
                R.drawable.ic_goal));
        details.add(new PlayerDetail(getString(R.string.goals), String.valueOf(statisticItem.getGoals().getTotal()),
                R.drawable.ic_goal));
        details.add(new PlayerDetail(getString(R.string.red_cards), String.valueOf(statisticItem.getCards().getRed()),
                R.drawable.ic_red_card));
        details.add(new PlayerDetail(getString(R.string.yellow_cards), String.valueOf(statisticItem.getCards().getYellow()),
                R.drawable.ic_yellow_card));
        details.add(new PlayerDetail(getString(R.string.goals_assist), String.valueOf(statisticItem.getGoals().getAssists()),
                R.drawable.ic_goal));

        details_adapter.notifyDataSetChanged();
    }

    private void initialiseViews(View view) {
        details_recycler = view.findViewById(R.id.recyclerView);
        departments_recycler = view.findViewById(R.id.departments_recycler);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

        Context context;
        ArrayList<PlayerDetail> list;

        public Adapter(Context context, ArrayList<PlayerDetail> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_player_detail, viewGroup, false);

            return new viewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

            PlayerDetail detail = list.get(i);

            viewHolder.name.setText(detail.getName());
            viewHolder.count.setText(detail.getCount());
            viewHolder.icon.setImageDrawable(context.getResources().getDrawable(detail.getIcon()));


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class viewHolder extends RecyclerView.ViewHolder {

            ImageView icon;
            TextView name, count;
            View V_root;

            viewHolder(@NonNull View itemView) {
                super(itemView);

                V_root = itemView.findViewById(R.id.root);
                name = itemView.findViewById(R.id.name);
                count = itemView.findViewById(R.id.count);
                icon = itemView.findViewById(R.id.icon);

                V_root.setOnClickListener(view -> {

                });

            }
        }
    }

    private void getPlayerStatistics() {

        Call<PlayersResponse> call1 = StaticConfig.apiInterface.getPlayerStatistics(String.valueOf(player_initial.getId()), Calendar.getInstance().get(Calendar.YEAR));
        call1.enqueue(new Callback<PlayersResponse>() {
            @Override
            public void onResponse(Call<PlayersResponse> call, Response<PlayersResponse> apiResponse) {
                try {


                    departments.clear();
                    leagues.clear();

                    playerStatistics.clear();

                    PlayersResponse response = apiResponse.body();


                    JSONArray items = new JSONArray(gson.toJson(response.getResponse()));

                    JSONObject jsonObject = items.getJSONObject(0);
                    JSONArray statistics = jsonObject.getJSONArray("statistics");

                    for (int i = 0; i < statistics.length(); i++) {
                        boolean isSelected = false;
                        if (i == 0)
                            isSelected = true;
                        StatisticsItem statisticsItem;
                        statisticsItem = gson.fromJson(statistics.getJSONObject(i).toString(), StatisticsItem.class);
                        leagues.add(statisticsItem.getLeague());
                        playerStatistics.add(statisticsItem);
                        departments.add(new PlayerDepartment(statisticsItem.getLeague().getName(), String.valueOf(statisticsItem.getLeague().getId()), statisticsItem.getLeague().getLogo(), isSelected));
                    }
                    departments_adapter.notifyDataSetChanged();

                    updateUI(String.valueOf(playerStatistics.get(0).getLeague().getId()));


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<PlayersResponse> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });

    }

    class DepartmentsAdapter extends RecyclerView.Adapter<DepartmentsAdapter.ViewHolder> {

        ArrayList<PlayerDepartment> list;
        Picasso picasso;
        Context context;

        private DepartmentsAdapter(Context context, ArrayList<PlayerDepartment> list) {
            this.context = context;
            this.list = list;
            picasso = AppSingleton.getInstance(getActivity()).getPicasso();
        }

        @NonNull
        @Override
        public DepartmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_player_department, viewGroup, false);

            return new DepartmentsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DepartmentsAdapter.ViewHolder viewHolder, int i) {

            PlayerDepartment department = list.get(i);

            String name;
            if (appSingleton.leagues.contains(Integer.parseInt(department.getId())))
                name = appSingleton.leagueNames.get(appSingleton.leagues.indexOf(Integer.parseInt(department.getId())));
            else
                name = department.getName();

            viewHolder.name.setText(name);

            if (list.get(i).isSelected())
                viewHolder.V_root.setSelected(true);
            else
                viewHolder.V_root.setSelected(false);


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            View V_root;
            TextView name;

            private ViewHolder(@NonNull View itemView) {
                super(itemView);
                V_root = itemView.findViewById(R.id.root);
                name = itemView.findViewById(R.id.name);


                V_root.setOnClickListener(view -> {



                    for (PlayerDepartment playerDepartment : list)
                        playerDepartment.setSelected(false);

                    list.get(getAdapterPosition()).setSelected(true);

                    dep_id = list.get(getAdapterPosition()).getId();

                    notifyDataSetChanged();

                    updateUI(dep_id);


                });
            }
        }

    }


}
