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
import com.app_republic.kora.model.MatchDetail;
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
import java.util.HashMap;

import static android.view.View.GONE;
import static com.app_republic.kora.utils.StaticConfig.MATCHES_REQUEST;

public class MatchDetailsFragment extends Fragment implements View.OnClickListener {


    ArrayList<MatchDetail> details = new ArrayList<>();

    Match match;
    Adapter details_adapter;
    RecyclerView details_recycler;

    public MatchDetailsFragment() {
        // Required empty public constructor
    }

    public static MatchDetailsFragment newInstance() {
        MatchDetailsFragment fragment = new MatchDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_match_details, container, false);

        initialiseViews(view);


        details_adapter = new Adapter(getActivity(), details);


        details_recycler.setAdapter(details_adapter);


        details_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        match = getArguments().getParcelable(StaticConfig.MATCH);

        long time = match.getLocalTime().isEmpty() ? 0 : Long.parseLong(match.getLocalTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        details.add(new MatchDetail(getString(R.string.department), match.getLiveDep(),
                R.drawable.ic_cup));
        details.add(new MatchDetail(getString(R.string.role), match.getLiveRole(),
                R.drawable.ic_tournament));
        details.add(new MatchDetail(getString(R.string.stadium), match.getLiveStad(),
                R.drawable.ic_field));
        details.add(new MatchDetail(getString(R.string.channel), match.getLiveTv(),
                R.drawable.ic_streaming));
        details.add(new MatchDetail(getString(R.string.commentator), match.getLiveComm(),
                R.drawable.ic_horn));
        details.add(new MatchDetail(getString(R.string.match_time), Utils.getFullTime(time),
                R.drawable.ic_stopwatch));
        details.add(new MatchDetail(getString(R.string.match_date), Utils.getReadableDate(calendar),
                R.drawable.ic_calendar));

        details_adapter.notifyDataSetChanged();

        return view;
    }

    private void initialiseViews(View view) {
        details_recycler = view.findViewById(R.id.recyclerView);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

        Context context;
        ArrayList<MatchDetail> list;

        public Adapter(Context context, ArrayList<MatchDetail> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_match_detail, viewGroup, false);

            return new viewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

            MatchDetail detail = list.get(i);

            viewHolder.label.setText(detail.getLabel());
            viewHolder.content.setText(detail.getContent());
            viewHolder.icon.setImageDrawable(context.getResources().getDrawable(detail.getIcon()));


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class viewHolder extends RecyclerView.ViewHolder {

            ImageView icon;
            TextView label, content;
            View V_root;

            viewHolder(@NonNull View itemView) {
                super(itemView);

                V_root = itemView.findViewById(R.id.root);
                label = itemView.findViewById(R.id.label);
                content = itemView.findViewById(R.id.content);
                icon = itemView.findViewById(R.id.icon);

                V_root.setOnClickListener(view -> {

                });

            }
        }
    }

}
