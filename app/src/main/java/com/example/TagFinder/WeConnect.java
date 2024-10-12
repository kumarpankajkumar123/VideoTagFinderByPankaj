package com.example.TagFinder;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.TagFinder.databinding.ActivityWeConnectBinding;

public class WeConnect extends AppCompatActivity {

    ActivityWeConnectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityWeConnectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}