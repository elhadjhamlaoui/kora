package com.app_republic.kora.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_republic.kora.R;
import com.app_republic.kora.activity.MainActivity;
import com.app_republic.kora.activity.MatchActivity;
import com.app_republic.kora.adapter.CommentsAdapter;
import com.app_republic.kora.model.Country;
import com.app_republic.kora.model.Feeling;
import com.app_republic.kora.model.Match;
import com.app_republic.kora.model.Comment;
import com.app_republic.kora.model.Prediction;
import com.app_republic.kora.model.User;
import com.app_republic.kora.utils.AppSingleton;
import com.app_republic.kora.utils.StaticConfig;
import com.app_republic.kora.utils.Utils;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.google.firebase.firestore.Query.Direction.DESCENDING;

public class CommentsFragment extends Fragment implements FirebaseAuth.AuthStateListener {

    List<Comment> comments = new ArrayList<>();
    ArrayList<Feeling> feelings = new ArrayList<>();
    List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    TextView TV_name, TV_time, TV_body;
    FloatingActionButton FB_close;
    View V_root;
    CircleImageView IV_photo, IV_user_photo;
    Gson gson;
    Comment mainComment;

    View V_prediction_layout;
    TextView TV_count1, TV_count2;
    ImageView IV_team1, IV_team2;


    CommentsAdapter commentsAdapter;
    RecyclerView commentsRecycler;
    EditText ET_comment;
    FloatingActionButton FB_send;
    AppSingleton appSingleton;

    EventListener commentsListener, feelingsListener, voteListener;
    String rootType, rootId;

    FirebaseFirestore db;
    User user;

    boolean firstRead = true, firstRead2 = true;
    String targetType, targetId;
    Prediction prediction = new Prediction();

    Country country;
    View V_team1, V_team2;
    Match match;
    private ListenerRegistration commentsRegistration,
            feelingsRegistration, voteRegistration;

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
        appSingleton = AppSingleton.getInstance(getActivity());

        gson = appSingleton.getGson();
        db = appSingleton.getDb();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;


        match = getArguments().getParcelable(StaticConfig.MATCH);
        mainComment = getArguments().getParcelable(StaticConfig.COMMENT);
        country = getArguments().getParcelable(StaticConfig.COUNTRY);

        targetId = getArguments().getString(StaticConfig.TARGET_ID);
        targetType = getArguments().getString(StaticConfig.TARGET_TYPE);


        if (targetType.equals(StaticConfig.COMMENT))
            view = inflater.inflate(R.layout.fragment_sub_comments, container, false);
        else
            view = inflater.inflate(R.layout.fragment_comments, container, false);


        initialiseViews(view);

        if (getActivity() instanceof MatchActivity) {
            rootType = StaticConfig.MATCH;
            rootId = match.getLiveId();
            prediction.setTargetId(targetId);
            prediction.setTargetType(targetType);


        } else if (getActivity() instanceof MainActivity) {
            getActivity().setTitle(country.getName());
            rootType = StaticConfig.CHAT;
            rootId = country.getId();

            if (!targetType.equals(StaticConfig.COMMENT))
                AppSingleton.getInstance(getActivity()).loadNativeAd(view.findViewById(R.id.adView));

        }


        commentsAdapter = new CommentsAdapter(comments, getActivity(), (view1, position) -> {
            switch (view1.getId()) {
                case R.id.root:
                    if (mainComment == null)
                        showSubComments(position);
                    break;
                case R.id.replies_layout:
                    if (mainComment == null)
                        showSubComments(position);

                    break;
                case R.id.like:

                    break;
                case R.id.dislike:

                    break;
                case R.id.reply:

                    break;
            }
        });


        commentsRecycler.setAdapter(commentsAdapter);

        commentsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        FB_send.setOnClickListener(view1 -> {

            if (appSingleton.getUserLocalStore().isLoggedIn()) {
                if (!ET_comment.getText().toString().isEmpty()) {

                    FB_send.hide();
                    sendComment(ET_comment.getText().toString());
                }
            } else {
                Utils.showLoginDialog(getContext());
            }


        });


        firstRead = true;
        firstRead2 = true;
        user = appSingleton.getUser();

        if (appSingleton.getUserLocalStore().isLoggedIn() && !user.getPhoto().isEmpty())
            appSingleton.getPicasso().load(user.getPhoto()).into(IV_user_photo);
        else
            IV_user_photo.setImageResource(R.drawable.ic_account);


        return view;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() instanceof MainActivity && !targetType.equals(StaticConfig.COMMENT)) {
            getActivity().setTitle(getString(R.string.chat));
        }

    }


    void showSubComments(int position) {
        Fragment fragment = CommentsFragment.newInstance();

        Bundle args = new Bundle();

        args.putParcelable(StaticConfig.MATCH,
                match);

        args.putParcelable(StaticConfig.COMMENT,
                comments.get(position));

        args.putString(StaticConfig.TARGET_TYPE,
                StaticConfig.COMMENT);


        args.putString(StaticConfig.TARGET_ID,
                comments.get(position).getId());

        args.putParcelable(StaticConfig.COUNTRY,
                country);

        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.comment_in, R.anim.comment_out);
        fragmentTransaction.replace(R.id.sub_comments, fragment, StaticConfig.SUB_COMMENT);

        fragmentTransaction.addToBackStack(StaticConfig.SUB_COMMENT);
        fragmentTransaction.commit();

    }



    private void sendComment(String body) {

        db.runTransaction(transaction -> {


            DocumentReference commentRef = db.collection("comments")
                    .document();

            Comment comment = new Comment(targetId, targetType,
                    appSingleton.getFirebaseAuth().getUid(), body, user.getPhoto(), user.getName(),
                    commentRef.getId(),
                    0, 0,
                    System.currentTimeMillis(), false, -1, rootType,
                    rootId, 0);


            DocumentReference mainCommentRef = db.collection("comments")
                    .document(comment.getTargetId());


            if (targetType.equals(StaticConfig.COMMENT)) {

                DocumentSnapshot mainCommentSnapshot = transaction.get(mainCommentRef);

                double newReplies = mainCommentSnapshot.getLong("replies") + 1;
                transaction.update(mainCommentRef, "replies", newReplies);
            }

            transaction.set(commentRef, comment);


            return 0;


        }).addOnSuccessListener(result -> {
            FB_send.show();
            ET_comment.setText("");
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            FB_send.show();

        });


    }

    private void initialiseViews(View view) {
        commentsRecycler = view.findViewById(R.id.comments_recycler);
        ET_comment = view.findViewById(R.id.comment_body);
        FB_send = view.findViewById(R.id.send);
        IV_user_photo = view.findViewById(R.id.user_photo);

        V_prediction_layout = view.findViewById(R.id.prediction_layout);
        TV_count1 = view.findViewById(R.id.count1);
        TV_count2 = view.findViewById(R.id.count2);
        IV_team1 = view.findViewById(R.id.logo_team1);
        IV_team2 = view.findViewById(R.id.logo_team2);
        V_team1 = view.findViewById(R.id.team1_layout);
        V_team2 = view.findViewById(R.id.team2_layout);


        if (targetType.equals(StaticConfig.CHAT)) {
            V_prediction_layout.setVisibility(View.GONE);

        } else if (targetType.equals(StaticConfig.MATCH)) {

            if (!match.getTeamLogoA().isEmpty())
                appSingleton.getPicasso().load(match.getTeamLogoA()).into(IV_team1);

            if (!match.getTeamLogoB().isEmpty())
                appSingleton.getPicasso().load(match.getTeamLogoB()).into(IV_team2);

            V_team1.setOnClickListener(view1 -> {
                if (appSingleton.getUserLocalStore().isLoggedIn()) {
                    Utils.updatePrediction(getActivity(), prediction, view1, true);
                } else {
                    Utils.showLoginDialog(getActivity());
                }
            });

            V_team2.setOnClickListener(view1 -> {
                if (appSingleton.getUserLocalStore().isLoggedIn()) {
                    Utils.updatePrediction(getActivity(), prediction, view1, false);
                } else {
                    Utils.showLoginDialog(getActivity());
                }

            });

        } else if (targetType.equals(StaticConfig.COMMENT)) {


            IV_photo = view.findViewById(R.id.photo);
            TV_name = view.findViewById(R.id.name);

            V_root = view.findViewById(R.id.parent_comment);
            FB_close = view.findViewById(R.id.close);

            TV_time = view.findViewById(R.id.time);
            TV_body = view.findViewById(R.id.body);
            TV_name.setText(mainComment.getName());
            TV_body.setText(mainComment.getText());
            TV_time.setText(Utils.getCommentDate(mainComment.getTimeStamp()));


            if (!mainComment.getPhoto().isEmpty())
                appSingleton.getPicasso().load(mainComment.getPhoto()).into(IV_photo);
            else
                IV_photo.setImageResource(R.drawable.ic_account);


            View V_header = view.findViewById(R.id.header_layout);
            V_header.setVisibility(View.VISIBLE);


            FB_close.setOnClickListener(view1 -> getActivity().onBackPressed());

        }


    }


    private void getComments() {
        comments.clear();
        Query query;
        if (getActivity() instanceof MainActivity) {
            query = db.collection("comments")
                    .orderBy("timeStamp", DESCENDING)
                    .orderBy("likes", DESCENDING)
                    .orderBy("replies", DESCENDING)
                    .whereEqualTo("targetType", targetType)
                    .whereEqualTo("targetId", targetId)
                    .limit(50);
        } else {
            query = db.collection("comments")
                    .orderBy("timeStamp", DESCENDING)
                    .orderBy("likes", DESCENDING)
                    .orderBy("replies", DESCENDING)
                    .whereEqualTo("targetType", targetType)
                    .whereEqualTo("targetId", targetId)
                    .limit(50);
        }

        commentsListener = (EventListener<QuerySnapshot>) (value, e) -> {
            String source = value != null && value.getMetadata().hasPendingWrites()
                    ? "Local" : "Server";


            if (value != null)
                for (DocumentChange dc : value.getDocumentChanges()) {
                    QueryDocumentSnapshot document = dc.getDocument();
                    Comment comment = document.toObject(Comment.class);

                    comment.setId(document.getId());

                    switch (dc.getType()) {
                        case ADDED:
                            comments.add(comment);
                            break;
                        case MODIFIED:
                            Comment c = comments.get(Utils.getCommentIndexById(comments, document.getId()));
                            c.setLikes(comment.getLikes());
                            c.setDislikes(comment.getDislikes());
                            c.setReplies(comment.getReplies());

                            break;
                        case REMOVED:
                            comments.remove(Utils.getCommentIndexById(comments, document.getId()));
                            break;

                    }
                }

            if (getActivity() instanceof MainActivity) {
                Collections.sort(comments, (comment, t1) -> {
                    if (comment.getTimeStamp() > t1.getTimeStamp())
                        return -1;
                    else if (comment.getTimeStamp() < t1.getTimeStamp())
                        return 1;
                    return 0;
                });
            } else {
                Collections.sort(comments, (comment, t1) -> {
                    if (comment.getTimeStamp() > t1.getTimeStamp())
                        return -1;
                    else if (comment.getTimeStamp() < t1.getTimeStamp())
                        return 1;
                    return 0;
                });
            }

            commentsAdapter.notifyDataSetChanged();

            if (firstRead) {
                firstRead = false;
                getFeelings();
               // getVote();
            }

        };

        commentsRegistration = query.addSnapshotListener(commentsListener);


    }

    void getFeelings() {

        feelings.clear();


        if (appSingleton.getUserLocalStore().isLoggedIn()) {
            Query query = db.collection("feelings")
                    .document(appSingleton.getFirebaseAuth().getUid())
                    .collection("topics")
                    .whereEqualTo("targetId", targetId);


            feelingsListener = (EventListener<QuerySnapshot>) (value, e) -> {
                String source = value != null && value.getMetadata().hasPendingWrites()
                        ? "Local" : "Server";


                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        QueryDocumentSnapshot document = dc.getDocument();
                        Feeling feeling = document.toObject(Feeling.class);

                        switch (dc.getType()) {
                            case ADDED:
                                feelings.add(feeling);

                                break;
                            case MODIFIED:
                                feelings.set(Utils.getFeelingIndexById(feelings, document.getId()), feeling);
                                break;
                            case REMOVED:
                                feelings.remove(Utils.getFeelingIndexById(feelings, document.getId()));
                                break;

                        }

                        if(Utils.getCommentIndexById(comments, feeling.getId()) != -1) {
                            Comment comment = comments.get(Utils.getCommentIndexById(comments, feeling.getId()));

                            if (Utils.getFeelingIndexById(feelings, comment.getId()) == -1)
                                comment.setLike(-1);
                            else if (feelings.get(Utils.getFeelingIndexById(feelings, comment.getId())).isState())
                                comment.setLike(1);
                            else
                                comment.setLike(0);
                        }

                    }

                    commentsAdapter.notifyDataSetChanged();
                }


            };
            feelingsRegistration = query.addSnapshotListener(feelingsListener);
        }


    }

    void getVote(String predictionId) {


        if (appSingleton.getUserLocalStore().isLoggedIn()) {
            DocumentReference documentReference = db.collection("votes")
                    .document(appSingleton.getFirebaseAuth().getUid())
                    .collection("topics")
                    .document(predictionId);


            voteListener = (EventListener<DocumentSnapshot>) (documentSnapshot, e) -> {

                if (documentSnapshot.exists()) {
                    Feeling feeling = documentSnapshot.toObject(Feeling.class);
                    if (feeling.isState()) {
                        V_team1.setBackgroundResource(R.drawable.bac_team_prediction_selected);
                        V_team2.setBackgroundResource(R.drawable.bac_team_prediction);

                    } else {
                        V_team1.setBackgroundResource(R.drawable.bac_team_prediction);
                        V_team2.setBackgroundResource(R.drawable.bac_team_prediction_selected);

                    }
                } else {
                    V_team1.setBackgroundResource(R.drawable.bac_team_prediction);
                    V_team2.setBackgroundResource(R.drawable.bac_team_prediction);


                }


            };
            voteRegistration = documentReference.addSnapshotListener(voteListener);
        }


    }


    @Override
    public void onResume() {
        super.onResume();

        getComments();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (commentsRegistration != null)
            commentsRegistration.remove();

        if (feelingsRegistration != null)
            feelingsRegistration.remove();
        firstRead = true;
        firstRead2 = true;
    }


    @Override
    public void onStart() {
        super.onStart();
        appSingleton.getFirebaseAuth().addAuthStateListener(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        appSingleton.getFirebaseAuth().removeAuthStateListener(this);

    }

    private void addLikeDislike(boolean like, String docId) {
        final DocumentReference docRef = db.collection("match_comments")
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

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null) {
            if (feelingsRegistration != null)
                feelingsRegistration.remove();
            if (voteRegistration != null)
                voteRegistration.remove();
            firstRead = true;
            firstRead2 = true;
        } else {
            user = appSingleton.getUser();

            if (appSingleton.getUserLocalStore().isLoggedIn() && !user.getPhoto().isEmpty()) {
                appSingleton.getPicasso().load(user.getPhoto()).into(IV_user_photo);
            } else
                IV_user_photo.setImageResource(R.drawable.ic_account);

        }
    }
}
