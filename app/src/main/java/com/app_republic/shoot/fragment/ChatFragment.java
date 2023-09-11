package com.app_republic.shoot.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_republic.shoot.R;
import com.app_republic.shoot.model.general.Country;
import com.app_republic.shoot.utils.AppSingleton;
import com.app_republic.shoot.utils.StaticConfig;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.Gson;
import com.jwang123.flagkit.FlagKit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    ArrayList<Country> countries = new ArrayList<>();

    Adapter adapter;
    RecyclerView depsRecyclerView;
    Gson gson;
    AppSingleton appSingleton;


    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appSingleton = AppSingleton.getInstance(getContext());

        gson = appSingleton.getGson();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        initialiseViews(view);


        adapter = new Adapter(getActivity(), countries);


        depsRecyclerView.setAdapter(adapter);


        depsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        getCountries();

        return view;

    }

    private void initialiseViews(View view) {
        depsRecyclerView = view.findViewById(R.id.recyclerView);

    }


    private void getCountries() {
        appSingleton.getDb()
                .collection("countries")
                .orderBy("index")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    countries.clear();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        countries.add(documentSnapshot.toObject(Country.class));
                    }
                    adapter.notifyDataSetChanged();

                }).addOnFailureListener(e -> {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        });


    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        Context context;
        ArrayList<Country> list;
        Picasso picasso;

        public Adapter(Context context, ArrayList<Country> list) {
            this.context = context;
            this.list = list;
            picasso = AppSingleton.getInstance(getActivity()).getPicasso();

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_country, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            Country country = list.get(i);

            try {
                viewHolder.icon.setImageDrawable(FlagKit.drawableWithFlag(context, country.getId()));
            } catch (Resources.NotFoundException e) {
                viewHolder.icon.setImageResource(R.drawable.ic_chat_big_dark);
            }


            viewHolder.name.setText(country.getName());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView name;

            ImageView icon;
            View V_root;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                icon = itemView.findViewById(R.id.icon);

                V_root = itemView.findViewById(R.id.root);

                V_root.setOnClickListener(view -> {

                    CommentsFragment fragment = CommentsFragment.newInstance();
                    Bundle args = new Bundle();

                    args.putString(StaticConfig.TARGET_TYPE,
                            StaticConfig.CHAT);
                    args.putString(StaticConfig.TARGET_ID,
                            list.get(getAdapterPosition()).getId());

                    args.putParcelable(StaticConfig.COUNTRY,
                            list.get(getAdapterPosition()));

                    fragment.setArguments(args);
                    getFragmentManager().beginTransaction()
                            .addToBackStack(StaticConfig.FRAGMENT_COMMENTS)
                            .replace(R.id.container, fragment)
                            .commit();

                });
            }
        }
    }
}
