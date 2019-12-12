package com.app_republic.kora.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_republic.kora.R;
import com.app_republic.kora.activity.GameActivity;
import com.app_republic.kora.activity.PlayerActivity;
import com.app_republic.kora.model.Game;
import com.app_republic.kora.model.GameCategory;
import com.app_republic.kora.model.Player;
import com.app_republic.kora.utils.AppSingleton;
import com.app_republic.kora.utils.StaticConfig;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class GamesFragment extends Fragment {
    RecyclerView famousGamesRecycler, categoriesRecycler;
    private FamousGamesAdapter gamesAdapter;
    private CategoriesAdapter categoriesAdapter;

    private ArrayList<Game> famousGames = new ArrayList<>();
    private ArrayList<GameCategory> categories = new ArrayList<>();
    Handler handler;
    public GamesFragment() {
        // Required empty public constructor
    }

    public static GamesFragment newInstance() {
        GamesFragment fragment = new GamesFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        initialiseViews(view);

        handler = new Handler();



        gamesAdapter = new FamousGamesAdapter(famousGames, getActivity());
        categoriesAdapter = new CategoriesAdapter(categories, getActivity());

        famousGamesRecycler.setAdapter(gamesAdapter);
        categoriesRecycler.setAdapter(categoriesAdapter);

        famousGamesRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        categoriesRecycler.setLayoutManager(linearLayoutManager);






        AsyncTask.execute(() -> loadCategories("https://html5games.com/"));
        AsyncTask.execute(() -> loadFamousGames("https://html5games.com/"));

        return view;

    }

    private void initialiseViews(View view) {
        famousGamesRecycler = view.findViewById(R.id.famousGamesRecycler);
        categoriesRecycler = view.findViewById(R.id.categoriesRecycler);

    }

    private void loadFamousGames(String url) {
        famousGames.clear();
        handler.post(() -> gamesAdapter.notifyDataSetChanged());

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            Elements games = doc.select(".games li a");
            for (Element element : games) {
                String href = element.attr("href");

                String icon = element.select(".icon img").attr("src");

                String name = element.select(".name").html();

                Game game = new Game(icon, name, href);
                famousGames.add(game);
            }
            handler.post(() -> gamesAdapter.notifyItemRangeInserted(0, famousGames.size()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCategories(String url) {
        categories.clear();
        handler.post(() -> categoriesAdapter.notifyDataSetChanged());

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            Elements elements = doc.select(".tags .main ul").first().select("li");
            for (Element element : elements) {
                String name = element.select("li a").text();

                String href = element.select("li a").attr("href");

                GameCategory gameCategory = new GameCategory(name, href);
                categories.add(gameCategory);
            }

            handler.post(() -> categoriesAdapter.notifyItemRangeInserted(0, categories.size()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    class FamousGamesAdapter extends RecyclerView.Adapter<FamousGamesAdapter.GamesViewHolder> {

        ArrayList<Game> list;
        Picasso picasso;
        Context context;

        private FamousGamesAdapter(ArrayList<Game> list, Context context) {
            this.context = context;
            this.list = list;
            picasso = AppSingleton.getInstance(getActivity()).getPicasso();
        }

        @NonNull
        @Override
        public GamesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_game, viewGroup, false);

            return new GamesViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GamesViewHolder holder, int position) {

            Game game = list.get(position);

            holder.name.setText(game.getName());
            if (!game.getIcon().isEmpty())
            picasso.load(game.getIcon()).into(holder.icon);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class GamesViewHolder extends RecyclerView.ViewHolder {

            ImageView icon;

            View V_root;

            TextView name;

            private GamesViewHolder(@NonNull View itemView) {
                super(itemView);
                V_root = itemView.findViewById(R.id.root);
                icon = itemView.findViewById(R.id.icon);
                name = itemView.findViewById(R.id.name);

                V_root.setOnClickListener(view -> {
                    Intent intent = new Intent(context, GameActivity.class);
                    String[] elements = list.get(getAdapterPosition())
                            .getUrl().split("/");
                    elements[2] = elements[2].toLowerCase();

                    intent.putExtra(StaticConfig.GAME, TextUtils.join("/", elements));
                    startActivity(intent);
                });
            }
        }

    }

    class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

        ArrayList<GameCategory> list;
        Picasso picasso;
        Context context;

        private CategoriesAdapter(ArrayList<GameCategory> list, Context context) {
            this.context = context;
            this.list = list;
            picasso = AppSingleton.getInstance(getActivity()).getPicasso();
        }

        @NonNull
        @Override
        public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_game_category, viewGroup, false);

            return new CategoriesViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {

            GameCategory gameCategory = list.get(position);

            holder.name.setText(gameCategory.getName());


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class CategoriesViewHolder extends RecyclerView.ViewHolder {

            View V_root;

            TextView name;

            private CategoriesViewHolder(@NonNull View itemView) {
                super(itemView);
                V_root = itemView.findViewById(R.id.root);
                name = itemView.findViewById(R.id.name);

                V_root.setOnClickListener(view -> {
                    AsyncTask.execute(() -> loadFamousGames("https://html5games.com/" +
                            list.get(getAdapterPosition()).getUrl()));
                });
            }
        }

    }


}
