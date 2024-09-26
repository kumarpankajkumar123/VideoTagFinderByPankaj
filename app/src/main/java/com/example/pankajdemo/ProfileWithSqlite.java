package com.example.pankajdemo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pankajdemo.ModalOfApi.SqliteDataHelper;

import java.util.ArrayList;

public class ProfileWithSqlite extends AppCompatActivity {

    TextView writeName, writeUsername, writeEmail, writePhone, writeGender;
    SqliteDataHelper sqliteDataHelper;
    ArrayList<String> value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_with_sqlite);

        sqliteDataHelper = new SqliteDataHelper(this);

        writeName = findViewById(R.id.writeName);
        writeUsername = findViewById(R.id.writeUsername);
        writeEmail = findViewById(R.id.writeEmail);
        writePhone = findViewById(R.id.writePhone);
        writeGender = findViewById(R.id.writeGender);

        value = new ArrayList<>();

        String email = getIntent().getStringExtra("key_email");
        Cursor cursor = sqliteDataHelper.retriveValue(email);
        getValues(cursor);



    }
    public void getValues(Cursor cursor){
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(1);
            String username = cursor.getString(2);
            String wemail = cursor.getString(3);
            String phone = cursor.getString(5);
            String gender = cursor.getString(6);

            writeName.setText(name);
            writeUsername.setText(username);
            writeEmail.setText(wemail);
            writePhone.setText(phone);
            writeGender.setText(gender);
        }
        else{
            Toast.makeText(this, "try again", Toast.LENGTH_SHORT).show();
        }
    }
    public void updatevalue(String email,String username,String phone){
        sqliteDataHelper.updatevalues(email,username,phone);

    }
}