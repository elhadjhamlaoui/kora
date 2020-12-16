package com.app_republic.shoot.utils;

import android.view.View;

import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;

public class FadingViewOffsetListener implements AppBarLayout.OnOffsetChangedListener {
        private View mView;
        public FadingViewOffsetListener(View view) {
            mView = view;
        }


        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            verticalOffset = Math.abs(verticalOffset);
            float halfScrollRange = (int) (appBarLayout.getTotalScrollRange() * 0.5f);
            float ratio = (float) verticalOffset / halfScrollRange;
            ratio = Math.max(0f, Math.min(1f, ratio));
            ViewCompat.setAlpha(mView, ratio);

        }
}