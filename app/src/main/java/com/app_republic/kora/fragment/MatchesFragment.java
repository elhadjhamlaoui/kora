package com.app_republic.kora.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_republic.kora.R;
import com.app_republic.kora.activity.MatchActivity;
import com.app_republic.kora.model.Match;
import com.app_republic.kora.request.GetMatches;
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
import java.util.Collections;

import static android.view.View.GONE;
import static com.app_republic.kora.utils.StaticConfig.MATCHES_REQUEST;

public class MatchesFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    ArrayList<Match> matches = new ArrayList<>();

    Adapter matches_adapter;
    RecyclerView matches_recycler;

    Calendar calendar;

    ImageView IV_next, IV_previous;
    TextView TV_date, TV_day;

    LinearLayout LL_choose_date;

    String dateString;
    DatePickerDialog datePickerDialog;

    Handler handler;
    Runnable runnable;
    long timeDifference;

    public MatchesFragment() {
        // Required empty public constructor
    }

    public static MatchesFragment newInstance() {
        MatchesFragment fragment = new MatchesFragment();
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
        View view = inflater.inflate(R.layout.fragment_matches, container, false);


        initialiseViews(view);


        matches_adapter = new Adapter(getActivity(), matches);


        matches_recycler.setAdapter(matches_adapter);


        matches_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(
                getActivity(), this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        handler = new Handler();
        runnable = () -> {
            getMatches();
        };

        getMatches();
        displayDate();


        return view;
    }

    private void initialiseViews(View view) {
        matches_recycler = view.findViewById(R.id.matches_recycler);

        LL_choose_date = view.findViewById(R.id.choose_date);
        IV_next = view.findViewById(R.id.arrow_next);
        IV_previous = view.findViewById(R.id.arrow_previous);
        TV_date = view.findViewById(R.id.date_text);
        TV_day = view.findViewById(R.id.day_text);

        LL_choose_date.setOnClickListener(this);
        IV_next.setOnClickListener(this);
        IV_previous.setOnClickListener(this);
    }

    private void displayDate() {
        TV_date.setText(Utils.getReadableDate(calendar));
        TV_day.setText(Utils.getReadableDay(calendar, getActivity()));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_date:
                datePickerDialog.show();
                break;
            case R.id.arrow_previous:
                calendar.add(Calendar.DATE, -1);
                getMatches();
                displayDate();
                break;
            case R.id.arrow_next:
                calendar.add(Calendar.DATE, 1);
                getMatches();
                displayDate();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        calendar.set(year, month, day);
        getMatches();
        displayDate();
    }


    class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

        Context context;
        ArrayList<Match> list;
        Picasso picasso;

        public Adapter(Context context, ArrayList<Match> list) {
            this.context = context;
            this.list = list;
            picasso = AppSingleton.getInstance(getActivity()).getPicasso();

        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_match, viewGroup, false);

            return new viewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

            Match match = list.get(i);

            long now = System.currentTimeMillis();
            int delay = Integer.parseInt(match.getLiveM3());
            long originalTime = Utils.getMillisFromMatchDate(match.getFullDatetimeSpaces());


            long difference = now - originalTime + timeDifference;

            if (difference > 0) {
                viewHolder.scoreTeamA.setText(match.getLiveRe1());
                viewHolder.scoreTeamB.setText(match.getLiveRe2());

                int minutes = Integer.parseInt(match.getActualMinutes());
                if (minutes > 0) {

                    if (minutes > 45) {
                        if (minutes > (60 - delay))
                            viewHolder.state.setText(" <" + (minutes - 15 + delay) + "> ");
                        else
                            viewHolder.state.setText(" <" + 45 + "> ");

                    } else
                        viewHolder.state.setText(" <" + minutes + "> ");
                } else {
                    viewHolder.state.setText(" - ");
                }
            } else {
                viewHolder.scoreTeamA.setText("");
                viewHolder.scoreTeamB.setText("");
                viewHolder.state.setText(Utils.getFullTime(originalTime - timeDifference));
            }


            viewHolder.name1.setText(match.getLiveTeam1());
            viewHolder.name2.setText(match.getLiveTeam2());

            if (!match.getTeamLogoA().isEmpty()) {
                picasso.cancelRequest(viewHolder.logo1);
                picasso.load(match.getTeamLogoA()).into(viewHolder.logo1);
            }
            if (!match.getTeamLogoB().isEmpty()) {
                picasso.cancelRequest(viewHolder.logo2);
                picasso.load(match.getTeamLogoB()).into(viewHolder.logo2);
            }

            if (i == 0 || !matches.get(i - 1).getDepId().equals(match.getDepId())) {
                viewHolder.department.setText(match.getLiveDep());
                viewHolder.department.setVisibility(View.VISIBLE);
            } else
                viewHolder.department.setVisibility(GONE);


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class viewHolder extends RecyclerView.ViewHolder {

            ImageView logo1, logo2;
            TextView name1, name2, state, department, scoreTeamA, scoreTeamB;
            View V_root;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                logo1 = itemView.findViewById(R.id.logo_team_1);
                logo2 = itemView.findViewById(R.id.logo_team_2);
                name1 = itemView.findViewById(R.id.name_team_1);
                name2 = itemView.findViewById(R.id.name_team_2);
                state = itemView.findViewById(R.id.state);
                scoreTeamA = itemView.findViewById(R.id.scoreTeamA);
                scoreTeamB = itemView.findViewById(R.id.scoreTeamB);
                department = itemView.findViewById(R.id.department);
                V_root = itemView.findViewById(R.id.root);

                V_root.setOnClickListener(view -> {
                    Intent intent = new Intent(context, MatchActivity.class);
                    intent.putExtra(StaticConfig.MATCH, list.get(getAdapterPosition()));
                    context.startActivity(intent);
                });

            }
        }
    }


    public void getMatches() {




        dateString = Utils.getReadableDate(calendar);
        GetMatches matchesRequest = new GetMatches(
                dateString,
                response -> {

                    try {


                        JSONObject object = new JSONObject(response);
                        String current_date = object.getString("current_date");
                        long currentServerTime = Utils.getMillisFromServerDate(current_date);

                        long currentClientTime = Calendar.getInstance().getTimeInMillis();

                        timeDifference = currentServerTime > currentClientTime ?
                                currentServerTime - currentClientTime : currentClientTime - currentServerTime;

                        JSONArray items = object.getJSONArray("items");

                        boolean liveMatches = false;

                        matches.clear();
                        matches_adapter.notifyDataSetChanged();

                        for (int i = 0; i < items.length(); i++) {
                            String jsonString = items.getJSONObject(i).toString();
                            Match match;
                            Gson gson = new Gson();
                            match = gson.fromJson(jsonString, Match.class);

                            matches.add(match);

                            int minutes = Integer.parseInt(match.getActualMinutes());
                            if (minutes > 0)
                                liveMatches = true;
                        }


                        if (liveMatches)
                            handler.postDelayed(runnable, 60 * 1000);

                        Collections.sort(matches);

                        matches_adapter.notifyItemRangeInserted(0, matches.size());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }


                }, error ->
                error.printStackTrace());

        AppSingleton.getInstance(getActivity()).addToRequestQueue(matchesRequest, MATCHES_REQUEST);


    }


}
