package com.app_republic.kora.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.app_republic.kora.R;
import com.app_republic.kora.utils.StaticConfig;

public class GoToVideoActivity extends AppCompatActivity implements View.OnClickListener {

    Button BT_video;
    ImageView IV_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to_video);

        BT_video = findViewById(R.id.video);
        IV_back = findViewById(R.id.back);

        BT_video.setOnClickListener(this);
        IV_back.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.video:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(getIntent().getStringExtra(StaticConfig.VIDEO_URI))));
                    finish();
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
