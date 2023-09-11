package com.app_republic.shoot.fragment;

import static com.app_republic.shoot.utils.StaticConfig.CONTENT_ITEM_VIEW_TYPE;
import static com.app_republic.shoot.utils.StaticConfig.NUMBER_OF_NATIVE_ADS_NEWS;
import static com.app_republic.shoot.utils.StaticConfig.PLAYER_INFO;
import static com.app_republic.shoot.utils.StaticConfig.UNIFIED_NATIVE_AD_VIEW_TYPE;

import android.content.Context;
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

import com.app_republic.shoot.R;
import com.app_republic.shoot.activity.PlayerActivity;
import com.app_republic.shoot.model.PlayerSquads.PlayerSquadsResponse;
import com.app_republic.shoot.model.PlayerSquads.PlayersItem;
import com.app_republic.shoot.model.PlayersResponse.Player;
import com.app_republic.shoot.model.PlayersResponse.PlayersResponse;
import com.app_republic.shoot.model.PlayersResponse.StatisticsItem;
import com.app_republic.shoot.model.general.PlayerDepartment;
import com.app_republic.shoot.utils.AppSingleton;
import com.app_republic.shoot.utils.StaticConfig;
import com.app_republic.shoot.utils.UnifiedNativeAdViewHolder;
import com.app_republic.shoot.utils.Utils;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamPlayersFragment extends Fragment {

    ArrayList<PlayersItem> players = new ArrayList<>();

    List<Object> list = new ArrayList<>();

    List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    Gson gson;
    ArrayList<PlayerDepartment> departments = new ArrayList<>();

    PlayersAdapter playersAdapter;
    DepartmentsAdapter departmentsAdapter;
    RecyclerView playersRecyclerView, departmentsRecyclerView;

    long timeDifference;
    String item_id, item_type, team_id_a, team_id_b;
    PlayersItem selectedPlayer;
    private AppSingleton appSingleton;

    public TeamPlayersFragment() {
        // Required empty public constructor
    }

    public static TeamPlayersFragment newInstance() {
        TeamPlayersFragment fragment = new TeamPlayersFragment();
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
        View view = inflater.inflate(R.layout.fragment_team_players, container, false);


        item_id = getArguments().getString(StaticConfig.PARAM_ITEM_ID);
        item_type = getArguments().getString(StaticConfig.PARAM_ITEM_TYPE);

        team_id_a = getArguments().getString(StaticConfig.PARAM_TEAM_ID_A, "");
        team_id_b = getArguments().getString(StaticConfig.PARAM_TEAM_ID_B, "");

        initialiseViews(view);


        playersAdapter = new PlayersAdapter(list, getActivity());
        departmentsAdapter = new DepartmentsAdapter(departments, getActivity());


        playersRecyclerView.setAdapter(playersAdapter);
        //departmentsRecyclerView.setAdapter(departmentsAdapter);


        playersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.HORIZONTAL, false);
        //departmentsRecyclerView.setLayoutManager(linearLayoutManager);


        selectedPlayer = getArguments().getParcelable(PLAYER_INFO);

        /*if (player != null) {
            departments.clear();
            item_id = player.getDepId();
            departments.add(new PlayerDepartment(player.getDepName(),
                    player.getDepId(), player.getDepImage(), true));

            if (player.getOtherInfo() != null)
                for (Player player : player.getOtherInfo()) {
                    PlayerDepartment department = new PlayerDepartment(player.getDepName(),
                            player.getDepId(), player.getDepImage(), false);

                    departments.add(department);
                }

            departmentsAdapter.notifyDataSetChanged();
        }*/

        getPlayers();




        return view;

    }

    private void initialiseViews(View view) {
        playersRecyclerView = view.findViewById(R.id.recyclerView);
        departmentsRecyclerView = view.findViewById(R.id.departments_recycler);

    }


    private void getPlayers() {

        Call<PlayerSquadsResponse> call1 = StaticConfig.apiInterface.getPlayerSquads(item_id);
        call1.enqueue(new Callback<PlayerSquadsResponse>() {
            @Override
            public void onResponse(Call<PlayerSquadsResponse> call, Response<PlayerSquadsResponse> apiResponse) {
                try {


                    PlayerSquadsResponse response = apiResponse.body();


                    JSONArray items = new JSONArray(gson.toJson(response.getResponse()));

                    players.clear();
                    list.clear();

                    playersAdapter.notifyDataSetChanged();


                    JSONArray tealPlayers = items.getJSONObject(0).getJSONArray("players");
                    for (int i = 0; i < tealPlayers.length(); i++) {
                        String jsonString = tealPlayers.get(i).toString();
                        PlayersItem player;
                        player = gson.fromJson(jsonString, PlayersItem.class);
                        players.add(player);
                    }
                    Collections.reverse(players);


                    list.addAll(players);


                    if (players.size() == 0)
                        AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, playersRecyclerView, playersAdapter,
                                players, list, 3);
                    else
                        AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, playersRecyclerView, playersAdapter,
                                players, list, NUMBER_OF_NATIVE_ADS_NEWS);


                    playersAdapter.notifyItemRangeInserted(0, players.size());


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<PlayerSquadsResponse> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });

    }

    class PlayersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<Object> list;
        Picasso picasso;
        Context context;

        private PlayersAdapter(List<Object> list, Context context) {
            this.context = context;
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
                            .inflate(R.layout.item_team_player, viewGroup, false);
                    return new PlayersViewHolder(ContentLayoutView);
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

                    PlayersViewHolder viewHolder = (PlayersViewHolder) holder;

                    PlayersItem player = (PlayersItem) list.get(i);

                    viewHolder.name.setText(player.getName());
                    viewHolder.number.setText(String.valueOf(player.getNumber()));

                    switch (player.getPosition()) {

                        case "Goalkeeper" :
                            viewHolder.position.setText(getString(R.string.goalkeeper));
                            break;
                        case "Defender" :
                            viewHolder.position.setText(getString(R.string.defender));
                            break;
                        case "Midfielder" :
                            viewHolder.position.setText(getString(R.string.midfielder));
                            break;
                        case "Attacker" :
                            viewHolder.position.setText(getString(R.string.attacker));
                            break;
                    }
                    viewHolder.age.setText(String.valueOf(player.getAge()));

                    //viewHolder.standing.setText(String.valueOf(i + 1));


                    if (!player.getPhoto().isEmpty()) {
                        picasso.cancelRequest(viewHolder.icon);
                        picasso.load(player.getPhoto()).fit().into(viewHolder.icon);
                    }

                    if (i % 2 == 0)
                        viewHolder.V_root
                                .setBackgroundColor(getResources()
                                        .getColor(R.color.gray_200));
                    else

                        viewHolder.V_root
                                .setBackgroundColor(getResources()
                                        .getColor(android.R.color.transparent));

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

        class PlayersViewHolder extends RecyclerView.ViewHolder {

            ImageView icon;

            LinearLayout V_root;

            TextView name, number, age, position;

            private PlayersViewHolder(@NonNull View itemView) {
                super(itemView);
                V_root = itemView.findViewById(R.id.root);
                icon = itemView.findViewById(R.id.icon);
                name = itemView.findViewById(R.id.name);
                number = itemView.findViewById(R.id.number);
                age = itemView.findViewById(R.id.age);
                position = itemView.findViewById(R.id.position);

                V_root.setOnClickListener(view -> {
                    PlayersItem clickedPlayer = (PlayersItem) list.get(getAdapterPosition());
                    if (selectedPlayer == null || selectedPlayer.getId() != clickedPlayer.getId()) {

                        Utils.loadInterstitialAd(getActivity().getSupportFragmentManager(), "any","player", getContext(), () -> {
                            getPlayerStatistics(String.valueOf(clickedPlayer.getId()), clickedPlayer.getPhoto());
                        });

                    }


                });
            }
        }

    }

    private void getPlayerStatistics(String id, String photo) {

        Call<PlayersResponse> call1 = StaticConfig.apiInterface.getPlayerStatistics(id, Calendar.getInstance().get(Calendar.YEAR));
        call1.enqueue(new Callback<PlayersResponse>() {
            @Override
            public void onResponse(Call<PlayersResponse> call, Response<PlayersResponse> apiResponse) {
                try {


                    PlayersResponse response = apiResponse.body();

                    JSONArray items = new JSONArray(gson.toJson(response.getResponse()));

                    JSONObject jsonObject = items.getJSONObject(0);
                    JSONArray statistics = jsonObject.getJSONArray("statistics");
                    JSONObject playerObject = jsonObject.getJSONObject("player");

                    StatisticsItem statisticsItem = gson.fromJson(statistics.getJSONObject(0).toString(), StatisticsItem.class);
                    Player player = gson.fromJson(playerObject.toString(), Player.class);
                    Intent intent = new Intent(getActivity().getApplicationContext(), PlayerActivity.class);
                    intent.putExtra(StaticConfig.PLAYER, player);
                    intent.putExtra(StaticConfig.PLAYER_STATISTIC, statisticsItem);
                    intent.putExtra(StaticConfig.PHOTO, photo);

                    startActivity(intent);

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

        private DepartmentsAdapter(ArrayList<PlayerDepartment> list, Context context) {
            this.context = context;
            this.list = list;
            picasso = AppSingleton.getInstance(getActivity()).getPicasso();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_player_department, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            PlayerDepartment department = list.get(i);


            viewHolder.name.setText(department.getName());

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

                    item_id = list.get(getAdapterPosition()).getId();

                    notifyDataSetChanged();

                    getPlayers();


                });
            }
        }

    }


}
