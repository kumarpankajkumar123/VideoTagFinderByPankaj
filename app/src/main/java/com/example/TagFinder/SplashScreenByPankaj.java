package com.example.TagFinder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class SplashScreenByPankaj extends AppCompatActivity {

    MaterialCardView cards;
    TextView tags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen_by_pankaj);

        cards = findViewById(R.id.cards);
        tags = findViewById(R.id.tags);

        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.animation);

        // Start the animation
        cards.startAnimation(slideDown);
        tags.startAnimation(slideDown);

        // Start the main activity after a delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenByPankaj.this, VideoTagsGet.class));
                finish();
            }
        }, 2000); // Delay for 2 seconds
    }
}