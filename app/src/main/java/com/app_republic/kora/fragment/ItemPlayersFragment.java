package com.app_republic.kora.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import com.app_republic.kora.activity.PlayerActivity;
import com.app_republic.kora.model.ApiResponse;
import com.app_republic.kora.model.Department;
import com.app_republic.kora.model.Player;
import com.app_republic.kora.model.PlayerDepartment;
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
import static com.app_republic.kora.utils.StaticConfig.PLAYER_INFO;
import static com.app_republic.kora.utils.StaticConfig.UNIFIED_NATIVE_AD_VIEW_TYPE;

public class ItemPlayersFragment extends Fragment {

    ArrayList<Player> players = new ArrayList<>();

    List<Object> list = new ArrayList<>();

    List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    Gson gson;
    ArrayList<PlayerDepartment> departments = new ArrayList<>();

    PlayersAdapter playersAdapter;
    DepartmentsAdapter departmentsAdapter;
    RecyclerView playersRecyclerView, departmentsRecyclerView;

    long timeDifference;
    String item_id, item_type, team_id_a, team_id_b;
    Player player;
    private AppSingleton appSingleton;

    public ItemPlayersFragment() {
        // Required empty public constructor
    }

    public static ItemPlayersFragment newInstance() {
        ItemPlayersFragment fragment = new ItemPlayersFragment();
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
        View view = inflater.inflate(R.layout.fragment_players, container, false);


        item_id = getArguments().getString(StaticConfig.PARAM_ITEM_ID);
        item_type = getArguments().getString(StaticConfig.PARAM_ITEM_TYPE);

        team_id_a = getArguments().getString(StaticConfig.PARAM_TEAM_ID_A, "");
        team_id_b = getArguments().getString(StaticConfig.PARAM_TEAM_ID_B, "");

        initialiseViews(view);


        playersAdapter = new PlayersAdapter(list, getActivity());
        departmentsAdapter = new DepartmentsAdapter(departments, getActivity());


        playersRecyclerView.setAdapter(playersAdapter);
        departmentsRecyclerView.setAdapter(departmentsAdapter);


        playersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.HORIZONTAL, false);
        departmentsRecyclerView.setLayoutManager(linearLayoutManager);


        player = getArguments().getParcelable(PLAYER_INFO);

        if (player != null) {
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
        }

        getPlayers();



        return view;

    }

    private void initialiseViews(View view) {
        playersRecyclerView = view.findViewById(R.id.recyclerView);
        departmentsRecyclerView = view.findViewById(R.id.departments_recycler);

    }


    private void getPlayers() {


        Call<ApiResponse> call1 = StaticConfig.apiInterface.getItemPlayersDetailed("0",
                appSingleton.JWS.equals("") ? "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJCQTozRTo3MzowRjpFMDo5MTo1QjpEMzpEQjoyQjoxRDowODoyNTpCOTpDMjpCNjpDRTo3MjpCMzpENiIsImlhdCI6MTYwNTk2MjYxNH0.PqYJXJQB30VPUPgLWYiUZ2eMfI5Yr00WxUyNqrmdE97jIDTqzlaH9pQE5tRA82S4IaVG1FEVq5JHXTuJ9Ik_Ag" : appSingleton.JWS, item_type, item_id);
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

                    players.clear();
                    list.clear();

                    playersAdapter.notifyDataSetChanged();


                    for (int i = 0; i < items.length(); i++) {
                        String jsonString = items.getJSONObject(i).toString();
                        Player player;
                        ;
                        player = gson.fromJson(jsonString, Player.class);
                        players.add(player);
                    }

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
            public void onFailure(Call<ApiResponse> call, Throwable t) {
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
                            .inflate(R.layout.item_player, viewGroup, false);
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

                    Player player = (Player) list.get(i);


                    viewHolder.name.setText(player.getName());
                    //viewHolder.standing.setText(String.valueOf(i + 1));
                    viewHolder.standing.setText(String.valueOf(players.indexOf(player) + 1));
                    viewHolder.goals.setText(player.getGoals());
                    viewHolder.scored_penalty.setText(player.getScoredPenalty());
                    viewHolder.missed_penalty.setText(player.getMissedPenalty());

                    if (item_type.equals(StaticConfig.PARAM_ITEM_TYPE_TEAM))
                        viewHolder.extra.setText(player.getPlace());
                    else
                        viewHolder.extra.setText(player.getTeamName());


                    if (!player.getPlayerImage().isEmpty()) {
                        picasso.cancelRequest(viewHolder.icon);
                        picasso.load(player.getPlayerImage()).fit().into(viewHolder.icon);
                    }

                    if (team_id_a.equals(player.getTeamId()) || team_id_b.equals(player.getTeamId())) {
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

        class PlayersViewHolder extends RecyclerView.ViewHolder {

            ImageView icon;

            LinearLayout V_root;

            TextView name, extra, standing, goals, scored_penalty, missed_penalty;

            private PlayersViewHolder(@NonNull View itemView) {
                super(itemView);
                V_root = itemView.findViewById(R.id.root);
                icon = itemView.findViewById(R.id.icon);
                name = itemView.findViewById(R.id.name);
                standing = itemView.findViewById(R.id.standing);
                extra = itemView.findViewById(R.id.extra);
                goals = itemView.findViewById(R.id.goals);
                scored_penalty = itemView.findViewById(R.id.scored_penalty);
                missed_penalty = itemView.findViewById(R.id.missed_penalty);

                V_root.setOnClickListener(view -> {
                    Player clickedPlayer = (Player) list.get(getAdapterPosition());
                    if (player == null || !player
                            .getPlayerId().equals(clickedPlayer.getPlayerId())) {

                        Utils.loadInterstitialAd(getActivity().getSupportFragmentManager(), "any","player", getContext(), () -> {
                            Intent intent = new Intent(context, PlayerActivity.class);
                            intent.putExtra(StaticConfig.PLAYER, clickedPlayer);
                            context.startActivity(intent);
                        });

                    }


                });
            }
        }

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
        public DepartmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_player_department, viewGroup, false);

            return new DepartmentsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DepartmentsAdapter.ViewHolder viewHolder, int i) {

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
