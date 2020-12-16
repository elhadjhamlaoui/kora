package com.app_republic.shoot;

import android.app.Application;

import com.ap.ApSdk;
import com.ap.gdpr.ApAgreement;
import com.ap.gdpr.ApGdpr;


public class App extends Application {
    @Override
    public void onCreate() {

        ApSdk.init(this, "1545429550324138827", "449888");

        ApGdpr.init(getApplicationContext());
        ApGdpr.registerAgreement(ApAgreement.getAgreement(this));
        Thread fetchThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ApGdpr.fetchRemoteStatuses();
            }
        });
        fetchThread.setPriority(Thread.MIN_PRIORITY);
        fetchThread.start();

        super.onCreate();
    }
}
