package com.example.TagFinder;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.TagFinder.databinding.ActivitySharedPreferenceUndersatandingBinding;

public class SharedPreferenceUndersatanding extends AppCompatActivity {

    SessionManagerClass sessionManagerClass;
    ActivitySharedPreferenceUndersatandingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySharedPreferenceUndersatandingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManagerClass = new SessionManagerClass(getApplicationContext());


//        binding.editPhoneNo.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                if(charSequence.length() > 10){
//                    binding.myPhoneNo.setError("No more!");
//                }
//                else if(charSequence.length() < 10){
//                    binding.myPhoneNo.setError(null);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        sessionManagerClass.getSessionDetail(sessionManagerClass.Name);

        

    }
}