package com.example.pankajdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pankajdemo.ModalOfApi.SqliteDataHelper;

public class LoginWithSqlite extends AppCompatActivity {

    EditText userEmail,userPassword;
    Button loginButton;
      SqliteDataHelper sqliteDataHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_with_sqlite);

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        loginButton = findViewById(R.id.loginButton);

        sqliteDataHelper = new SqliteDataHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();

               boolean isLogin = sqliteDataHelper.checkLogin(email,password);

               if(isLogin){
                  Intent intent = new Intent(LoginWithSqlite.this,ProfileWithSqlite.class);
                  intent.putExtra("key_email",email);
                  startActivity(intent);
                  finish();
               }
               else{
                   Toast.makeText(LoginWithSqlite.this, "please enter correct email and password", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}