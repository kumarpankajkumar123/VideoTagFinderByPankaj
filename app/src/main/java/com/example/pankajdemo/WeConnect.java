package com.example.pankajdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pankajdemo.databinding.ActivityMainBinding;
import com.example.pankajdemo.databinding.ActivityWeConnectBinding;

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