package com.app_republic.shoot.fragment;

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

import com.app_republic.shoot.R;
import com.app_republic.shoot.model.LeagueStandingResponse.League;
import com.app_republic.shoot.model.general.ApiResponse;
import com.app_republic.shoot.utils.AppSingleton;
import com.app_republic.shoot.utils.StaticConfig;
import com.app_republic.shoot.utils.UnifiedNativeAdViewHolder;
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

import static com.app_republic.shoot.utils.StaticConfig.CONTENT_ITEM_VIEW_TYPE;
import static com.app_republic.shoot.utils.StaticConfig.NUMBER_OF_NATIVE_ADS_NEWS;
import static com.app_republic.shoot.utils.StaticConfig.UNIFIED_NATIVE_AD_VIEW_TYPE;

public class DepartmentsFragment extends Fragment {

    ArrayList<League> departments = new ArrayList<>();
    List<Object> list = new ArrayList<>();

    List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    DepartmentsAdapter departmentsAdapter;
    RecyclerView depsRecyclerView;
    Gson gson;
    long timeDifference;

    String type;
    private AppSingleton appSingleton;

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
        appSingleton = AppSingleton.getInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_departments, container, false);

        initialiseViews(view);


        departmentsAdapter = new DepartmentsAdapter(getActivity(), list);


        depsRecyclerView.setAdapter(departmentsAdapter);


        depsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        type = getArguments().getString(StaticConfig.PARAM_DEPS_TYPE);

        /*
        if (type.equals(StaticConfig.DEPS_TYPE_PLAYERS))
            getDepartmentsWithPlayers();
        else
            getDepartmentsWithStandings();*/

        addLeagues();

        return view;

    }

    private void initialiseViews(View view) {
        depsRecyclerView = view.findViewById(R.id.recyclerView);

    }


    /*
    private void getDepartmentsWithStandings() {


        Call<ApiResponse> call1 = StaticConfig.apiInterface.getDepsWithStandings("1",
                appSingleton.JWS.equals("") ? "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJCQTozRTo3MzowRjpFMDo5MTo1QjpEMzpEQjoyQjoxRDowODoyNTpCOTpDMjpCNjpDRTo3MjpCMzpENiIsImlhdCI6MTYwNTk2MjYxNH0.PqYJXJQB30VPUPgLWYiUZ2eMfI5Yr00WxUyNqrmdE97jIDTqzlaH9pQE5tRA82S4IaVG1FEVq5JHXTuJ9Ik_Ag" : appSingleton.JWS);
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
                appSingleton.JWS.equals("") ? "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJCQTozRTo3MzowRjpFMDo5MTo1QjpEMzpEQjoyQjoxRDowODoyNTpCOTpDMjpCNjpDRTo3MjpCMzpENiIsImlhdCI6MTYwNTk2MjYxNH0.PqYJXJQB30VPUPgLWYiUZ2eMfI5Yr00WxUyNqrmdE97jIDTqzlaH9pQE5tRA82S4IaVG1FEVq5JHXTuJ9Ik_Ag" : appSingleton.JWS);
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

    }*/


    void addLeagues() {


        departments.clear();
        list.clear();

        departmentsAdapter.notifyDataSetChanged();

        for (int i = 0; i < appSingleton.leagues.size(); i++) {
            League league;
            league = new League(appSingleton.leagueNames.get(i),"https://media-4.api-sports.io/football/leagues/" + appSingleton.leagues.get(i) + ".png", appSingleton.leagues.get(i));
            departments.add(league);
        }
        list.addAll(departments);

        if (departments.size() == 0)
            AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, depsRecyclerView, departmentsAdapter,
                    departments, list, 3);
        else
            AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, depsRecyclerView, departmentsAdapter,
                    departments, list, NUMBER_OF_NATIVE_ADS_NEWS);



        departmentsAdapter.notifyItemRangeInserted(0, departments.size());
    }

    /*private void parseResponse(ApiResponse response) {
        try {

            long currentClientTime = Calendar.getInstance().getTimeInMillis();

            JSONArray items = new JSONArray(gson.toJson(response.getResponse()));

            departments.clear();
            list.clear();

            departmentsAdapter.notifyDataSetChanged();

            for (int i = 0; i < items.length(); i++) {
                String jsonString = items.getJSONObject(i).toString();
                Department department;
                department = gson.fromJson(jsonString, Department.class);
                departments.add(department);
            }
            list.addAll(departments);

            if (departments.size() == 0)
                AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, depsRecyclerView, departmentsAdapter,
                        departments, list, 3);
            else
                AppSingleton.getInstance(getActivity()).loadNativeAds(mNativeAds, depsRecyclerView, departmentsAdapter,
                        departments, list, NUMBER_OF_NATIVE_ADS_NEWS);



            departmentsAdapter.notifyItemRangeInserted(0, departments.size());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


    }

     */

    class DepartmentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context context;
        List<Object> list;
        Picasso picasso;

        public DepartmentsAdapter(Context context, List<Object> list) {
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
                            viewGroup.getContext()).inflate(R.layout.ad_unified_news,
                            viewGroup, false);
                    return new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);

                case CONTENT_ITEM_VIEW_TYPE:
                    View ContentLayoutView = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.item_department, viewGroup, false);
                    return new DepsViewHolder(ContentLayoutView);
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

                    League department = (League) list.get(i);

                    DepsViewHolder viewHolder = (DepsViewHolder) holder;


                    viewHolder.name.setText(department.getName());

                    if (!department.getLogo().isEmpty()) {
                        picasso.cancelRequest(viewHolder.icon);


                        try {
                            picasso.load(department.getLogo())
                                    .fit()
                                    .placeholder(R.drawable.ic_ball)
                                    .into(viewHolder.icon);
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                            picasso.load(department.getLogo())
                                    .fit()
                                    .into(viewHolder.icon);
                        }
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
                                String.valueOf(departments.get(getAdapterPosition()).getId()));

                        fragment.setArguments(args);
                        getFragmentManager().beginTransaction()
                                .addToBackStack(StaticConfig.FRAGMENT_PLAYERS)
                                .replace(R.id.container, fragment)
                                .commit();
                    } else {
                        Fragment fragment = StandingsFragment.newInstance();
                        Bundle args = new Bundle();
                        args.putString(StaticConfig.PARAM_DEP_ID,
                                String.valueOf(departments.get(getAdapterPosition()).getId()));

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
