package com.example.TagFinder;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Explore extends AppCompatActivity {

    TextView weatherview;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_explore);

        weatherview = findViewById(R.id.textview);
        String text = "Weather ForeCast";
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);

// Apply bold and white color to "Weather"
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new TypefaceSpan(getResources().getFont(R.font.poppins_bold)), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

// Apply yellow color and regular font to "Forecast"
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#DDB130")), 8, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new TypefaceSpan("poppins"), 8, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        weatherview.setText(spannable);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(Explore.this, WeatherData.class);
                startActivity(i);
                // close this activity
                finish();
            }
        },2000);
    }
}