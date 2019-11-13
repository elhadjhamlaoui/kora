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
import com.app_republic.kora.model.Player;
import com.app_republic.kora.request.GetItemPlayersDetailed;
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

import static com.app_republic.kora.utils.StaticConfig.DEP_PLAYERS_REQUEST;

public class ItemPlayersFragment extends Fragment {

    ArrayList<Player> players = new ArrayList<>();;

    PlayersAdapter playersAdapter;
    RecyclerView playersRecyclerView;

    long timeDifference;
    String item_id, item_type;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_players, container, false);

        item_id = getArguments().getString(StaticConfig.PARAM_ITEM_ID);
        item_type = getArguments().getString(StaticConfig.PARAM_ITEM_TYPE);

        initialiseViews(view);


        playersAdapter = new PlayersAdapter(players);


        playersRecyclerView.setAdapter(playersAdapter);


        playersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        getPlayers();

        return view;

    }

    private void initialiseViews(View view) {
        playersRecyclerView = view.findViewById(R.id.recyclerView);

    }


    private void getPlayers() {



        GetItemPlayersDetailed getItemPlayersDetailed = new GetItemPlayersDetailed(
                item_type,
                item_id,
                response -> {

                    try {


                        JSONObject object = new JSONObject(response);
                        String current_date = object.getString("current_date");
                        long currentServerTime = Utils.getMillisFromServerDate(current_date);

                        long currentClientTime = Calendar.getInstance().getTimeInMillis();

                        timeDifference = currentServerTime > currentClientTime ?
                                currentServerTime - currentClientTime : currentClientTime - currentServerTime;

                        JSONArray items = object.getJSONArray("items");

                        players.clear();
                        playersAdapter.notifyDataSetChanged();


                        for (int i = 0; i < items.length(); i++) {
                            String jsonString = items.getJSONObject(i).toString();
                            Player player;
                            Gson gson = new Gson();
                            player = gson.fromJson(jsonString, Player.class);
                            players.add(player);
                        }


                        playersAdapter.notifyItemRangeInserted(0, players.size());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }


                }, error ->
                error.printStackTrace());

        AppSingleton.getInstance(getActivity()).addToRequestQueue(getItemPlayersDetailed, DEP_PLAYERS_REQUEST);


    }

    class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.PlayersViewHolder> {

        ArrayList<Player> list;
        Picasso picasso;

        private PlayersAdapter(ArrayList<Player> list) {
            this.list = list;
            picasso = AppSingleton.getInstance(getActivity()).getPicasso();
        }

        @NonNull
        @Override
        public PlayersAdapter.PlayersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_player, viewGroup, false);

            return new PlayersAdapter.PlayersViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlayersAdapter.PlayersViewHolder viewHolder, int i) {

            Player player = list.get(i);


            viewHolder.name.setText(player.getName());
            viewHolder.standing.setText(String.valueOf(i + 1));
            viewHolder.department.setText(player.getDepName());
            viewHolder.goals.setText(player.getGoals());
            viewHolder.scored_penalty.setText(player.getScoredPenalty());
            viewHolder.missed_penalty.setText(player.getMissedPenalty());

            if (!player.getPlayerImage().isEmpty()) {
                picasso.cancelRequest(viewHolder.icon);
                picasso.load(player.getPlayerImage()).into(viewHolder.icon);
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class PlayersViewHolder extends RecyclerView.ViewHolder {

            ImageView icon;

            LinearLayout V_root;
            TextView name, department, standing, goals, scored_penalty, missed_penalty;

            private PlayersViewHolder(@NonNull View itemView) {
                super(itemView);
                V_root = itemView.findViewById(R.id.root);
                icon = itemView.findViewById(R.id.icon);
                name = itemView.findViewById(R.id.name);
                standing = itemView.findViewById(R.id.standing);
                department = itemView.findViewById(R.id.department);
                goals = itemView.findViewById(R.id.goals);
                scored_penalty = itemView.findViewById(R.id.scored_penalty);
                missed_penalty = itemView.findViewById(R.id.missed_penalty);

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
