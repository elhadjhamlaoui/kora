package com.app_republic.kora.fragment;

import android.content.Context;
import android.content.res.Resources;
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
import com.app_republic.kora.model.ApiResponse;
import com.app_republic.kora.model.Department;
import com.app_republic.kora.utils.AppSingleton;
import com.app_republic.kora.utils.StaticConfig;
import com.app_republic.kora.utils.Utils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartmentsFragment extends Fragment {

    ArrayList<Department> departments = new ArrayList<>();
    ;

    DepartmentsAdapter departmentsAdapter;
    RecyclerView depsRecyclerView;
    Gson gson;
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
        gson = AppSingleton.getInstance(getActivity()).getGson();

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


        Call<ApiResponse> call1 = StaticConfig.apiInterface.getDepsWithStandings("1",
                "");
        call1.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> apiResponse) {
                parseResponse(apiResponse.body());

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });

    }

    private void getDepartmentsWithPlayers() {

        Call<ApiResponse> call1 = StaticConfig.apiInterface.getDepsWithPlayers("1",
                "");
        call1.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> apiResponse) {
                parseResponse(apiResponse.body());

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });

    }

    private void parseResponse(ApiResponse response) {
        try {
            String current_date = response.getCurrentDate();
            long currentServerTime = Utils.getMillisFromServerDate(current_date);

            long currentClientTime = Calendar.getInstance().getTimeInMillis();

            timeDifference = currentServerTime > currentClientTime ?
                            currentServerTime - currentClientTime : currentClientTime - currentServerTime;StaticConfig.TIME_DIFFERENCE = timeDifference;

            JSONArray items = new JSONArray(gson.toJson(response.getItems()));

            departments.clear();

            departmentsAdapter.notifyDataSetChanged();

            for (int i = 0; i < items.length(); i++) {
                String jsonString = items.getJSONObject(i).toString();
                Department department;
                department = gson.fromJson(jsonString, Department.class);
                departments.add(department);
            }


            departmentsAdapter.notifyItemRangeInserted(0, departments.size());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
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


                try {
                    picasso.load(department.getDepLogo())
                            .fit()
                            .placeholder(R.drawable.ic_ball)
                            .into(viewHolder.icon);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                    picasso.load(department.getDepLogo())
                            .fit()
                            .into(viewHolder.icon);
                }
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




                });
            }
        }
    }
}
