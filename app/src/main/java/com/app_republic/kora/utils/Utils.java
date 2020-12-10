package com.app_republic.kora.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.app_republic.kora.R;
import com.app_republic.kora.activity.InterstitialCustomAd;
import com.app_republic.kora.activity.LoginActivity;
import com.app_republic.kora.activity.SplashActivity;
import com.app_republic.kora.activity.TeamInfoActivity;
import com.app_republic.kora.model.Advert;
import com.app_republic.kora.model.Comment;
import com.app_republic.kora.model.Feeling;
import com.app_republic.kora.model.Player;
import com.app_republic.kora.model.Prediction;
import com.app_republic.kora.model.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;




import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class Utils {

    public static long getMillisFromMatchDate(String match) {
        Calendar calendar = Calendar.getInstance();
        String[] date = match.split(" ");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]) - 1;
        int day = Integer.parseInt(date[2]);
        int hour = Integer.parseInt(date[3]);
        int minute = Integer.parseInt(date[4]);

        calendar.set(year, month, day, hour, minute);

        return calendar.getTimeInMillis();
    }

    public static String getReadableDate(Calendar cal) {
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(date);

        return formattedDate;
    }

    public static String getReadableFullDate(Calendar cal) {
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        String formattedDate = dateFormat.format(date);

        return formattedDate;
    }

    public static String getReadableDay(Calendar cal, Context context) {
        String[] days = context.getResources().getStringArray(R.array.days);
        String day = days[cal.get(Calendar.DAY_OF_WEEK) - 1];
        return day;
    }

    public static String getFullTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");
        String dateString = formatter.format(new Date(time));

        return dateString;

    }

    public static String getRemainingTime(long durationInMillis) {

        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;
        long hour = (durationInMillis / (1000 * 60 * 60));

        String time = String.format("%02d:%02d:%02d", hour, minute, second);

        return time;
    }

    public static long getMillisFromServerDate(String date) {
        long timeInMilliseconds = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(date);
            timeInMilliseconds = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timeInMilliseconds;


    }

    public static Player getPlayerFromDepId(Player player, String dep_id) {
        if (player.getOtherInfo() != null)
            for (Player player1 : player.getOtherInfo())
                if (player1.getDepId().equals(dep_id))
                    return player1;

        return player;

    }

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat formatterYear = new SimpleDateFormat("MM/dd/yyyy");

    public static String getCommentDate(Long timeAtMiliseconds) {

        if (timeAtMiliseconds == 0) {
            return "";
        }

        //API.log("Day Ago "+dayago);
        String result = "now";
        String dataSot = formatter.format(new Date());
        Calendar calendar = Calendar.getInstance();

        long dayagolong = timeAtMiliseconds;
        calendar.setTimeInMillis(dayagolong);
        String agoformater = formatter.format(calendar.getTime());

        Date CurrentDate = null;
        Date CreateDate = null;

        try {
            CurrentDate = formatter.parse(dataSot);
            CreateDate = formatter.parse(agoformater);

            long different = Math.abs(CurrentDate.getTime() - CreateDate.getTime());

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            if (elapsedDays == 0) {
                if (elapsedHours == 0) {
                    if (elapsedMinutes == 0) {
                        if (elapsedSeconds < 0) {
                            return "0" + " s";
                        } else {
                            if (elapsedSeconds > 0 && elapsedSeconds < 59) {
                                return "now";
                            }
                        }
                    } else {
                        return String.valueOf(elapsedMinutes) + "m ago";
                    }
                } else {
                    return String.valueOf(elapsedHours) + "h ago";
                }

            } else {
                if (elapsedDays <= 29) {
                    return String.valueOf(elapsedDays) + "d ago";
                }
                if (elapsedDays > 29 && elapsedDays <= 58) {
                    return "1Mth ago";
                }
                if (elapsedDays > 58 && elapsedDays <= 87) {
                    return "2Mth ago";
                }
                if (elapsedDays > 87 && elapsedDays <= 116) {
                    return "3Mth ago";
                }
                if (elapsedDays > 116 && elapsedDays <= 145) {
                    return "4Mth ago";
                }
                if (elapsedDays > 145 && elapsedDays <= 174) {
                    return "5Mth ago";
                }
                if (elapsedDays > 174 && elapsedDays <= 203) {
                    return "6Mth ago";
                }
                if (elapsedDays > 203 && elapsedDays <= 232) {
                    return "7Mth ago";
                }
                if (elapsedDays > 232 && elapsedDays <= 261) {
                    return "8Mth ago";
                }
                if (elapsedDays > 261 && elapsedDays <= 290) {
                    return "9Mth ago";
                }
                if (elapsedDays > 290 && elapsedDays <= 319) {
                    return "10Mth ago";
                }
                if (elapsedDays > 319 && elapsedDays <= 348) {
                    return "11Mth ago";
                }
                if (elapsedDays > 348 && elapsedDays <= 360) {
                    return "12Mth ago";
                }

                if (elapsedDays > 360 && elapsedDays <= 720) {
                    return "1 year ago";
                }

                if (elapsedDays > 720) {
                    Calendar calendarYear = Calendar.getInstance();
                    calendarYear.setTimeInMillis(dayagolong);
                    return formatterYear.format(calendarYear.getTime()) + "";
                }

            }

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getCommentIndexById(List<Comment> comments, String id) {
        for (Comment comment : comments)
            if (comment.getId().equals(id))
                return comments.indexOf(comment);

        return -1;

    }

    public static int getFeelingIndexById(ArrayList<Feeling> feelings, String id) {
        for (Feeling feeling : feelings)
            if (feeling.getId().equals(id))
                return feelings.indexOf(feeling);

        return -1;

    }

    public static void updateFeelings(Context context, Comment comment, View view, boolean state) {

        FirebaseFirestore db = AppSingleton.getInstance(context).getDb();
        DocumentReference feelingRef = db.collection("feelings")
                .document(AppSingleton.getInstance(context).getFirebaseAuth().getUid()).collection("topics")
                .document(comment.getId());
        DocumentReference commentRef = db.collection("comments")
                .document(comment.getId());


        db.runTransaction(transaction -> {
            DocumentSnapshot commentSnapshot = transaction.get(commentRef);
            DocumentSnapshot feelingSnapshot = transaction.get(feelingRef);


            int increment = 1;
            boolean switchedFeeling = false;
            if (feelingSnapshot.exists()) {
                if (feelingSnapshot.getBoolean("state") == state) {
                    transaction.delete(feelingRef);
                    increment = -1;
                } else {
                    switchedFeeling = true;
                    transaction.update(feelingRef, "state", state);
                }
            } else {

                Feeling feeling = new Feeling(state, comment.getTargetType(),
                        comment.getTargetId(), comment.getId());

                transaction.set(feelingRef, feeling);
            }


            if (state) {
                double newLikes = commentSnapshot.getLong("likes") + increment;
                transaction.update(commentRef, "likes", newLikes);
                if (switchedFeeling) {
                    double newDislikes = commentSnapshot.getLong("dislikes") - 1;
                    transaction.update(commentRef, "dislikes", newDislikes);
                }
            } else {
                double newDislikes = commentSnapshot.getLong("dislikes") + increment;
                transaction.update(commentRef, "dislikes", newDislikes);
                if (switchedFeeling) {
                    double newLikes = commentSnapshot.getLong("likes") - 1;
                    transaction.update(commentRef, "likes", newLikes);
                }

            }

            if (increment == -1)
                return -1;
            else if (state)
                return 1;
            else return 0;

        }).addOnSuccessListener(result -> {
            view.setEnabled(true);
            comment.setLike(result.intValue());
        })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    view.setEnabled(true);
                });


    }

    public static void updatePrediction(Context context, Prediction prediction, View view, boolean state) {

        FirebaseFirestore db = AppSingleton.getInstance(context).getDb();


        db.runTransaction(transaction -> {


            int increment = 1;
            boolean switchedFeeling = false;


            if (!(prediction.getId() == null)) {

                DocumentReference votesReference = db.collection("votes")
                        .document(AppSingleton.getInstance(context).getFirebaseAuth().getUid()).collection("topics")
                        .document(prediction.getId());
                DocumentSnapshot votesSnapshot = transaction.get(votesReference);
                DocumentReference predictionReference = db.collection("predictions")
                        .document(prediction.getId());
                DocumentSnapshot predictionSnapshot = transaction.get(predictionReference);


                if (votesSnapshot.exists()) {
                    if (votesSnapshot.getBoolean("state") == state) {
                        transaction.delete(votesReference);
                        increment = -1;
                    } else {
                        switchedFeeling = true;
                        transaction.update(votesReference, "state", state);
                    }

                } else {

                    Feeling feeling = new Feeling(state, StaticConfig.PREDICTION,
                            prediction.getId(), votesReference.getId());

                    transaction.set(votesReference, feeling);
                }


                if (state) {
                    double newCount1 = predictionSnapshot.getLong("count1") + increment;
                    transaction.update(predictionReference, "count1", newCount1);
                    if (switchedFeeling) {
                        double newCount2 = predictionSnapshot.getLong("count2") - 1;
                        transaction.update(predictionReference, "count2", newCount2);
                    }
                } else {
                    double newCount2 = predictionSnapshot.getLong("count2") + increment;
                    transaction.update(predictionReference, "count2", newCount2);
                    if (switchedFeeling) {
                        double newCount1 = predictionSnapshot.getLong("count1") - 1;
                        transaction.update(predictionReference, "count1", newCount1);
                    }

                }
            } else {
                int count1 = state ? 1 : 0;
                int count2 = state ? 0 : 1;

                DocumentReference newPredictionReference = db.collection("predictions")
                        .document();

                Prediction prediction1 = new Prediction(count1, count2, prediction.getTargetId(),
                        prediction.getTargetType(), newPredictionReference.getId());

                transaction.set(newPredictionReference, prediction1);


                DocumentReference votesReference = db.collection("votes")
                        .document(AppSingleton.getInstance(context).getFirebaseAuth().getUid()).collection("topics")
                        .document(newPredictionReference.getId());

                Feeling feeling = new Feeling(state, StaticConfig.PREDICTION,
                        newPredictionReference.getId(), votesReference.getId());

                transaction.set(votesReference, feeling);


            }


            if (increment == -1)
                return -1;
            else if (state)
                return 1;
            else return 0;

        }).addOnSuccessListener(result -> {
            view.setEnabled(true);
        })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    view.setEnabled(true);
                });


    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static void showSimpleDialogMessage(Context context, String message, String button,
                                               Dialog.OnClickListener listener, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(button, listener);
        builder.setCancelable(cancelable);
        builder.show();
    }

    public static void uploadBitmap(Context context, Bitmap bitmap, String uid) {

        try {
            OnImageUpload imageUploadInterface = (OnImageUpload) context;

            StorageReference storageReference = AppSingleton.getInstance(context).getFirebaseStorage()
                    .getReference();

            StorageReference reference = storageReference.child("users").child("photos")
                    .child(uid);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = reference.putBytes(data);
            uploadTask.addOnFailureListener(exception -> {
                // Handle unsuccessful uploads
            }).addOnSuccessListener(taskSnapshot -> {
                reference.getDownloadUrl().addOnSuccessListener(uri -> {
                    imageUploadInterface.imageUploaded(uri);
                }).addOnFailureListener(exception -> {
                    imageUploadInterface.failed();
                });
            }).addOnFailureListener(e -> imageUploadInterface.failed());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }



    }


    public interface OnImageUpload {
        void imageUploaded(Uri uri);

        void failed();

    }

    public static void saveUserProfile(AppCompatActivity context, User user, String uid) {


        AppSingleton.getInstance(context).getDb().collection("users").document(uid)
                .set(user).addOnSuccessListener(aVoid -> {
            AppSingleton.getInstance(context).getUserLocalStore().storeUserData(user);
            AppSingleton.getInstance(context).getUserLocalStore().setUserLoggedIn(true);
            Utils.showSimpleDialogMessage(context,
                    context.getString(R.string.signed_up_success),
                    context.getString(R.string.login_now),
                    (dialogInterface, i) -> {
                        context.finish();
                        context.startActivity(new Intent(context, SplashActivity.class));
                    },
                    false
            );
        }).addOnFailureListener(e -> {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }

    public static void getUserProfile(AppCompatActivity context, String uid) {
        AppSingleton.getInstance(context)
                .getDb()
                .collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    User user = documentSnapshot.toObject(User.class);
                    AppSingleton.getInstance(context).getUserLocalStore().storeUserData(user);
                    AppSingleton.getInstance(context).getUserLocalStore().setUserLoggedIn(true);
                    context.finish();
                    context.startActivity(new Intent(context, SplashActivity.class));
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    public static void showLoginDialog(Context context) {
        Utils.showSimpleDialogMessage(context,
                context.getString(R.string.have_to_login),
                context.getString(R.string.login_now),
                (dialogInterface, i) -> {
                    context.startActivity(new Intent(context,
                            LoginActivity.class));
                }, true
        );
    }

    public static void loadBannerAd(AppCompatActivity context, String screen) {
        try {
            AppSingleton appSingleton = AppSingleton.getInstance(context);
            int index = getAdvertIndex(appSingleton.banner_adverts, screen, "banner");

            if (index != -1 && appSingleton.banner_adverts.get(index) != null) {
                Advert advert = appSingleton.banner_adverts.get(index);
                View view = LayoutInflater.from(context).inflate(R.layout.advert_banner, null);
                GifImageView imageView = view.findViewById(R.id.image);
                if (!advert.getImage().isEmpty()) {
                    DrawableImageViewTarget imageViewPreview = new DrawableImageViewTarget(imageView);
                    Glide
                            .with(context)
                            .load(advert.getImage())
                            .into(imageViewPreview);
                   /* if(advert.isGif()) {

                    } else {
                        appSingleton.getPicasso().load(advert.getImage()).into(imageView);
                    }*/
                }
                imageView.setOnClickListener(view1 -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(advert.getUrl()));
                    context.startActivity(browserIntent);
                });
                ((FrameLayout) context.findViewById(R.id.adView)).addView(view);
            } else {

                AdView mAdView = new AdView(context);

                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.invalidate();
                mAdView.setAdUnitId(appSingleton.ADMOB_BANNER_UNIT_ID);
                mAdView.setAdSize(AdSize.SMART_BANNER);

                mAdView.loadAd(adRequest);


                ((FrameLayout) context.findViewById(R.id.adView)).removeAllViews();
                ((FrameLayout) context.findViewById(R.id.adView)).addView(mAdView);
            }
        } catch (Exception e) {

        }

    }

    public static void loadInterstitialAd(FragmentManager fragmentManager, String type, String screen, Context context, InterstitialAdListener interstitialAdListener) {

        try {
            AppSingleton appSingleton = AppSingleton.getInstance(context);

            int index = getAdvertIndex(appSingleton.inter_adverts, screen, "inter");
            SharedPreferences sharedPreferences = context.getSharedPreferences("custom_ads", Context.MODE_PRIVATE);
            boolean clicked = false;

            if (index != -1) clicked = sharedPreferences.getBoolean(appSingleton.inter_adverts.get(index).getId(), false);

            if (index != -1 && appSingleton.inter_adverts.get(index) != null && !clicked) {
                Advert advert = appSingleton.inter_adverts.get(index);

                InterstitialCustomAd interstitialCustomAd = new InterstitialCustomAd(() -> {
                    interstitialAdListener.done();
                });

                Bundle args = new Bundle();
                args.putParcelable(StaticConfig.ADVERT, advert);
                interstitialCustomAd.setArguments(args);
                interstitialCustomAd.show(fragmentManager,
                        StaticConfig.ADVERT);


            } else  if (type.equals("any")) {

                if (appSingleton.getInterstitialAd().isLoaded()) {
                    appSingleton.getInterstitialAd().show();
                    appSingleton.getInterstitialAd().setAdListener(new AdListener(){

                        @Override
                        public void onAdFailedToLoad(int i) {
                            super.onAdFailedToLoad(i);
                        }

                        @Override
                        public void onAdLeftApplication() {
                            super.onAdLeftApplication();
                        }

                        @Override
                        public void onAdOpened() {
                            super.onAdOpened();
                        }

                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                        }

                        @Override
                        public void onAdClicked() {
                            super.onAdClicked();
                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }

                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            interstitialAdListener.done();
                        }
                    });
                }
                else
                    interstitialAdListener.done();


            } else {
                interstitialAdListener.done();
            }

        } catch (Exception e) {
            interstitialAdListener.done();

        }

    }

    public interface InterstitialAdListener {
        void done();

    }

    public static void startTeamActivity(Context context, FragmentManager fragmentManager, String team_id) {
        Utils.loadInterstitialAd(fragmentManager, "any", "team",  context, () -> {
            Intent intent = new Intent(context, TeamInfoActivity.class);
            intent.putExtra(StaticConfig.PARAM_TEAM_ID, team_id);
            context.startActivity(intent);
        });
    }

    private static int getAdvertIndex(ArrayList<Advert> list, String screen, String type) {

        if (list != null)
            for (Advert advert : list)
                if (advert.getScreen().equals(screen) && advert.getType().equals(type))
                    return list.indexOf(advert);

        return -1;
    }

    public static Intent getOpenFacebookIntent(Context context, String page) {

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + page));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + page));
        }
    }

    public static String getElementContainString(List<String> list, String value) {
        for (String s : list)
            if (s.contains(value))
                return s;
        return "";
    }


}
