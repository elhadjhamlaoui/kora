package com.app_republic.kora.fragment;

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

import com.app_republic.kora.R;
import com.app_republic.kora.model.Player;
import com.app_republic.kora.model.PlayerDepartment;
import com.app_republic.kora.model.PlayerDetail;
import com.app_republic.kora.utils.AppSingleton;
import com.app_republic.kora.utils.StaticConfig;
import com.app_republic.kora.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlayerDetailsFragment extends Fragment implements View.OnClickListener {


    ArrayList<PlayerDetail> details = new ArrayList<>();
    ArrayList<PlayerDepartment> departments = new ArrayList<>();

    Player player, player_initial;
    Adapter details_adapter;
    DepartmentsAdapter departments_adapter;
    RecyclerView details_recycler, departments_recycler;

    String dep_id;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_details, container,
                false);

        initialiseViews(view);



        details_adapter = new Adapter(getActivity(), details);
        departments_adapter = new DepartmentsAdapter(getActivity(), departments);

        details_recycler.setAdapter(details_adapter);
        departments_recycler.setAdapter(departments_adapter);


        departments_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),
                RecyclerView.HORIZONTAL, false));
        details_recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        player_initial = getArguments().getParcelable(StaticConfig.PLAYER);


        departments.clear();
        dep_id = player_initial.getDepId();
        departments.add(new PlayerDepartment(player_initial.getDepName(),
                player_initial.getDepId(), player_initial.getDepImage(), true));

        if (player_initial.getOtherInfo() != null)
            for (Player player : player_initial.getOtherInfo()) {
                PlayerDepartment department = new PlayerDepartment(player.getDepName(),
                        player.getDepId(), player.getDepImage(), false);

                departments.add(department);
            }

        departments_adapter.notifyDataSetChanged();


        updateUI();


        return view;
    }
    private void updateUI() {
        details.clear();

        player = Utils.getPlayerFromDepId(player_initial, dep_id);

        details.add(new PlayerDetail(getString(R.string.scored_penalty), player.getScoredPenalty(),
                R.drawable.ic_goal));
        details.add(new PlayerDetail(getString(R.string.missed_penalty), player.getMissedPenalty(),
                R.drawable.ic_goal));
        details.add(new PlayerDetail(getString(R.string.goals), player.getGoals(),
                R.drawable.ic_goal));
        details.add(new PlayerDetail(getString(R.string.red_cards), player.getRedCards(),
                R.drawable.ic_red_card));
        details.add(new PlayerDetail(getString(R.string.yellow_cards), player.getYellowCards(),
                R.drawable.ic_yellow_card));
        details.add(new PlayerDetail(getString(R.string.goals_against), player.getGoalsAgainst(),
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
                   /* Intent intent = new Intent(context, NewsItemActivity.class);
                    intent.putExtra(StaticConfig.TEAM, list.get(getAdapterPosition()));
                    context.startActivity(intent);
                    */


                    for (PlayerDepartment playerDepartment : list)
                        playerDepartment.setSelected(false);

                    list.get(getAdapterPosition()).setSelected(true);

                    dep_id = list.get(getAdapterPosition()).getId();

                    notifyDataSetChanged();

                    updateUI();


                });
            }
        }

    }


}
