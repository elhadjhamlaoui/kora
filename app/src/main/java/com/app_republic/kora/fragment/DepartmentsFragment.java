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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_republic.kora.R;
import com.app_republic.kora.model.Department;
import com.app_republic.kora.request.GetDepsWithPlayers;
import com.app_republic.kora.request.GetDepsWithStandings;
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

import static com.app_republic.kora.utils.StaticConfig.DEPS_WITH_PLAYERS_REQUEST;
import static com.app_republic.kora.utils.StaticConfig.DEPS_WITH_STANDINGS_REQUEST;

public class DepartmentsFragment extends Fragment {

    ArrayList<Department> departments = new ArrayList<>();
    ;

    DepartmentsAdapter departmentsAdapter;
    RecyclerView depsRecyclerView;

    long timeDifference;

    String type;

    public DepartmentsFragment() {
        // Required empty public constructor
    }

    public static DepartmentsFragment newInstance() {
        DepartmentsFragment fragment = new DepartmentsFragment();
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
        View view = inflater.inflate(R.layout.fragment_departments, container, false);

        initialiseViews(view);


        departmentsAdapter = new DepartmentsAdapter(getActivity(), departments);


        depsRecyclerView.setAdapter(departmentsAdapter);


        depsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        type = getArguments().getString(StaticConfig.PARAM_DEPS_TYPE);

        if (type.equals(StaticConfig.DEPS_TYPE_PLAYERS))
            getDepartmentsWithPlayers();
        else
            getDepartmentsWithStandings();

        return view;

    }

    private void initialiseViews(View view) {
        depsRecyclerView = view.findViewById(R.id.recyclerView);

    }


    private void getDepartmentsWithStandings() {


        GetDepsWithStandings getDepsWithStandings = new GetDepsWithStandings(
                response -> {
                    parseResponse(response);
                }, error ->
                error.printStackTrace());

        AppSingleton.getInstance(getActivity()).addToRequestQueue(getDepsWithStandings,
                DEPS_WITH_STANDINGS_REQUEST);

    }

    private void getDepartmentsWithPlayers() {

        GetDepsWithPlayers getDepsWithPlayers = new GetDepsWithPlayers(
                response -> {
                    parseResponse(response);
                }, error ->
                error.printStackTrace());

        AppSingleton.getInstance(getActivity()).addToRequestQueue(getDepsWithPlayers,
                DEPS_WITH_PLAYERS_REQUEST);

    }

    private void parseResponse(String response) {
        try {
            JSONObject object = new JSONObject(response);
            String current_date = object.getString("current_date");
            long currentServerTime = Utils.getMillisFromServerDate(current_date);

            long currentClientTime = Calendar.getInstance().getTimeInMillis();

            timeDifference = currentServerTime > currentClientTime ?
                    currentServerTime - currentClientTime : currentClientTime - currentServerTime;

            JSONArray items = object.getJSONArray("items");

            departments.clear();

            departmentsAdapter.notifyDataSetChanged();

            for (int i = 0; i < items.length(); i++) {
                String jsonString = items.getJSONObject(i).toString();
                Department department;
                Gson gson = new Gson();
                department = gson.fromJson(jsonString, Department.class);
                departments.add(department);
            }


            departmentsAdapter.notifyItemRangeInserted(0, departments.size());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }


    }

    class DepartmentsAdapter extends RecyclerView.Adapter<DepartmentsAdapter.DepsViewHolder> {

        Context context;
        ArrayList<Department> list;
        Picasso picasso;

        public DepartmentsAdapter(Context context, ArrayList<Department> list) {
            this.context = context;
            this.list = list;
            picasso = AppSingleton.getInstance(getActivity()).getPicasso();

        }

        @NonNull
        @Override
        public DepartmentsAdapter.DepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_department, viewGroup, false);

            return new DepartmentsAdapter.DepsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DepartmentsAdapter.DepsViewHolder viewHolder, int i) {

            Department department = list.get(i);


            viewHolder.name.setText(department.getDepName());

            if (!department.getDepLogo().isEmpty()) {
                picasso.cancelRequest(viewHolder.icon);

                picasso.load(department.getDepLogo())
                        .placeholder(R.drawable.ic_ball)
                        .into(viewHolder.icon);
            }



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class DepsViewHolder extends RecyclerView.ViewHolder {

            ImageView icon;
            TextView name;

            View V_root;

            public DepsViewHolder(@NonNull View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.icon);
                name = itemView.findViewById(R.id.name);

                V_root = itemView.findViewById(R.id.root);

                V_root.setOnClickListener(view -> {

                    if (type.equals(StaticConfig.DEPS_TYPE_PLAYERS)) {
                        Fragment fragment = ItemPlayersFragment.newInstance();
                        Bundle args = new Bundle();

                        args.putString(StaticConfig.PARAM_ITEM_TYPE,
                                StaticConfig.PARAM_ITEM_TYPE_DEPARTMENT);
                        args.putString(StaticConfig.PARAM_ITEM_ID,
                                departments.get(getAdapterPosition()).getDepId());

                        fragment.setArguments(args);
                        getFragmentManager().beginTransaction()
                                .addToBackStack(StaticConfig.FRAGMENT_PLAYERS)
                                .replace(R.id.container, fragment)
                                .commit();
                    } else {
                        Fragment fragment = StandingsFragment.newInstance();
                        Bundle args = new Bundle();
                        args.putString(StaticConfig.PARAM_DEP_ID,
                                departments.get(getAdapterPosition()).getDepId());

                        fragment.setArguments(args);
                        getFragmentManager().beginTransaction()
                                .addToBackStack(StaticConfig.FRAGMENT_STANDINGS)
                                .replace(R.id.container, fragment)
                                .commit();
                    }




                    /* Intent intent = new Intent(context, NewsItemActivity.class);
                    intent.putExtra(StaticConfig.TEAM, list.get(getAdapterPosition()));
                    context.startActivity(intent);
                    */

                });
            }
        }
    }
}
