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
import com.app_republic.kora.model.Match;
import com.app_republic.kora.model.Comment;
import com.app_republic.kora.utils.AppSingleton;
import com.app_republic.kora.utils.StaticConfig;
import com.app_republic.kora.utils.Utils;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentsFragment extends Fragment {

    ArrayList<Comment> comments = new ArrayList<>();
    Gson gson;

    CommentsAdapter commentsAdapter;
    RecyclerView commentsRecycler;

    FirebaseFirestore db;

    Match match;

    public CommentsFragment() {
        // Required empty public constructor
    }

    public static CommentsFragment newInstance() {
        CommentsFragment fragment = new CommentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = AppSingleton.getInstance(getActivity()).getGson();
        db = AppSingleton.getInstance(getActivity()).getDb();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        match = getArguments().getParcelable(StaticConfig.MATCH);

        initialiseViews(view);


        commentsAdapter = new CommentsAdapter(comments, getActivity());


        commentsRecycler.setAdapter(commentsAdapter);

        commentsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        getMatchComments();


        return view;

    }

    private void initialiseViews(View view) {
        commentsRecycler = view.findViewById(R.id.comments_recycler);
    }


    private void getMatchComments() {
        db.collection("match_comments")
                .whereEqualTo("live_id", match.getLiveId())
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Comment comment = document.toObject(Comment.class);
                    //document.
                   // comment.setId(document.getId());
                    comments.add(comment);
                }
                commentsAdapter.notifyDataSetChanged();
            } else {
            }
        });


    }

    class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

        ArrayList<Comment> list;
        Picasso picasso;
        Context context;

        private CommentsAdapter(ArrayList<Comment> list, Context context) {
            this.context = context;
            this.list = list;
            picasso = AppSingleton.getInstance(getActivity()).getPicasso();
        }

        @NonNull
        @Override
        public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_comment, viewGroup, false);

            return new CommentsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder viewHolder, int i) {

            Comment comment = list.get(i);

            viewHolder.name.setText(comment.getName());
            viewHolder.num_likes.setText(String.valueOf(comment.getLikes()));
            viewHolder.num_dislikes.setText(String.valueOf(comment.getDislikes()));
            viewHolder.body.setText(comment.getText());
            viewHolder.time.setText(Utils.getCommentDate(comment.getTimeStamp()));

            picasso.cancelRequest(viewHolder.photo);
            picasso.load(comment.getPhoto()).into(viewHolder.photo);


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView photo, like, dislike;

            View V_root;

            TextView name, time, body, num_likes, num_dislikes, reply;

            private ViewHolder(@NonNull View itemView) {
                super(itemView);
                V_root = itemView.findViewById(R.id.root);
                photo = itemView.findViewById(R.id.photo);
                name = itemView.findViewById(R.id.name);

                like = itemView.findViewById(R.id.like);
                dislike = itemView.findViewById(R.id.dislike);
                time = itemView.findViewById(R.id.time);
                body = itemView.findViewById(R.id.body);
                num_likes = itemView.findViewById(R.id.num_likes);
                num_dislikes = itemView.findViewById(R.id.num_dislikes);
                reply = itemView.findViewById(R.id.reply);

                V_root.setOnClickListener(view -> {

                });

                like.setOnClickListener(view -> {

                });

                dislike.setOnClickListener(view -> {

                });
                reply.setOnClickListener(view -> {

                });


            }
        }

    }

    private void addLikeDislike(boolean like, String docId) {
        final DocumentReference docRef =  db.collection("match_comments")
                .document(docId);

        db.runTransaction((Transaction.Function<Void>) transaction -> {
            DocumentSnapshot snapshot = transaction.get(docRef);

            if (like) {
                /*snapshot.get
                double newLikes = snapshot.getLong("likes") + 1;
                transaction.update(docRef, "population", newPopulation);*/
            } else {
                double newPopulation = snapshot.getDouble("population") + 1;
                transaction.update(docRef, "population", newPopulation);
            }


            return null;
        }).addOnSuccessListener(aVoid -> {
        }).addOnFailureListener(e -> {
                });

    }


}
