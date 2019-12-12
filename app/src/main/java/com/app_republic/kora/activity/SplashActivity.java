package com.app_republic.kora.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app_republic.kora.R;
import com.app_republic.kora.utils.AppSingleton;
import com.app_republic.kora.utils.StaticConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth = AppSingleton.getInstance(this).getFirebaseAuth();
        db = AppSingleton.getInstance(this).getDb();
        firebaseAuth.signInAnonymously().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                DocumentReference docRef = db.collection("settings")
                        .document("admob");
                docRef.get().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful() && task1.getResult() != null) {
                        StaticConfig.ADMOB_APP_ID = task1.getResult().get("app_id").toString();
                        StaticConfig.ADMOB_BANNER_UNIT_ID = task1.getResult().get("banner_unit_id").toString();
                        StaticConfig.ADMOB_NATIVE_UNIT_ID = task1.getResult().get("native_unit_id").toString();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();

                    } else {
                    }
                });


            }
        });
    }
}
