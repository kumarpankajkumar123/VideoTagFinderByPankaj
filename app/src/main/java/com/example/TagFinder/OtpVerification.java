package com.example.TagFinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerification extends AppCompatActivity {

    FirebaseAuth mAuth;
    MaterialButton check_otp;
    TextInputEditText code;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        mAuth = FirebaseAuth.getInstance();

        String mPhone = getIntent().getStringExtra("phone");
        Log.e("check phone", ":=" + mPhone);
        check_otp = findViewById(R.id.check_otp);
        code = findViewById(R.id.phoneNumber);



        sendotpToPhoneNo(mPhone);

        check_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String m_otp = code.getText().toString();
                PhoneAuthCredential crendential = PhoneAuthProvider.getCredential(mVerificationId, m_otp);
                Log.e("the verification id",":="+ mVerificationId);
                Log.e("the otp",":="+ m_otp);

                signInWithPhoneAuthCredential(crendential);
            }
        });
    }

    public void sendotpToPhoneNo(String phone_no) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone_no)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Log.e("otp verification", ":= success");
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.e("failed otp", "onVerificationFailed", e);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                mVerificationId = s;
                                mResendToken = forceResendingToken;
                                Toast.makeText(OtpVerification.this, "otp sent sucessfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("safalta", "signInWithCredential:success");
                            Toast.makeText(OtpVerification.this, "otp verify", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(OtpVerification.this, VideoTagsGet.class);
                            startActivity(intent);
                            // Update UI
                        } else {
                            // Sign in failed
                            Log.e("failed to verify", "signInWithCredential:failure", task.getException());

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OtpVerification.this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Other exceptions
                                Toast.makeText(OtpVerification.this, "Verification failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}