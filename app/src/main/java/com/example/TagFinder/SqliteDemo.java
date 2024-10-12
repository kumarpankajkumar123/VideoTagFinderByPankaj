package com.example.TagFinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.TagFinder.ModalOfApi.SqliteDataHelper;

public class SqliteDemo extends AppCompatActivity {


    EditText name, userName, userEmail, userPassword, phn, gender;
    Button register;
    SqliteDataHelper sqliteDataHelper;

    String nameR = "^[a-zA-Z\\s]{5,}+$";
    String userNameR = "^[\\w][\\S]+$";
    String userEmailR = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    String userPasswordR = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    String phnR = "^[0-9]{10}$";
    String genderR = "(?:m|M|male|Male|MALE|f|F|female|Female|FEMALE)";

    SessionManagerClass sessionManagerClass;

    //    String roll_NoR = "^[a-zA-Z0-9]+$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sqlite_demo);

        name = findViewById(R.id.regName);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        phn = findViewById(R.id.phone);
        gender = findViewById(R.id.gender);
        register = findViewById(R.id.register);

        sqliteDataHelper = new SqliteDataHelper(this);

        sessionManagerClass = new SessionManagerClass(this);

        boolean isChecked = sessionManagerClass.checkSession();

        if (isChecked) {
            startActivity(new Intent(SqliteDemo.this, ProfileWithSqlite.class));
            finish();
        } else {
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name1 = name.getText().toString();
                    String userName1 = userName.getText().toString();
                    String userEmail1 = userEmail.getText().toString();
                    String userPassword1 = userPassword.getText().toString();
                    String phn1 = phn.getText().toString();
                    String gender1 = gender.getText().toString();

                    if (checkAllInputvalue(name1, userName1, userEmail1, userPassword1, phn1, gender1)) {
//                    Toast.makeText(SqliteDemo.this, "all details correct", Toast.LENGTH_SHORT).show();
                        register(name1, userName1, userEmail1, userPassword1, phn1, gender1);
                    } else {
                        Toast.makeText(SqliteDemo.this, "enter right values", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void register(String name3, String userName3, String userEmail3, String userPassword3, String ph3, String gender1) {
        sessionManagerClass.createSession1(name3, userName3, userEmail3, userPassword3, ph3, gender1);

        boolean getlong = sqliteDataHelper.insertData(name3, userName3, userEmail3, userPassword3, ph3, gender1);
        if (getlong) {
            Toast.makeText(this, "register successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SqliteDemo.this, LoginWithSqlite.class));
            finish();
        } else {
            Toast.makeText(this, "register failed", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkAllInputvalue(String name2, String userName2, String userEmail2, String userPassword2, String phn2, String gender2) {
        return checkName(name2) && checkUserName(userName2) && checkEmail(userEmail2) && checkPassword(userPassword2) && checkPhn(phn2) && checkGender(gender2);
    }

    public boolean checkName(String name2) {

        if (name2.equals("")) {
            name.setError("please enter name");
            name.requestFocus();
            return false;
        } else if (!name2.matches(nameR)) {
            name.setError("write correct name");
            name.requestFocus();
            return false;
        } else if (name2.length() < 5) {
            name.setError("name should be atleast 5 word");
            name.requestFocus();
            return false;
        }
        return true;
    }

    public boolean checkUserName(String userName2) {
        if (userName2.equals("")) {
            userName.setError("please write username");
            userName.requestFocus();
            return false;
        } else if (!userName2.matches(userNameR)) {
            userName.setError("please write correct username");
            userName.requestFocus();
            return false;
        }
        return true;
    }

    public boolean checkEmail(String userEmail2) {
        if (userEmail2.equals("")) {
            userEmail.setError("email can't empty");
            userEmail.requestFocus();
            return false;
        } else if (!userEmail2.matches(userEmailR)) {
            userEmail.setError("write correct email");
            userEmail.requestFocus();
            return false;
        }
        return true;
    }

    public boolean checkPassword(String userPassword2) {
        if (userPassword2.equals("")) {
            userPassword.setError("password can't empty");
            userPassword.requestFocus();
            return false;
        } else if (!userPassword2.matches(userPasswordR)) {
            userPassword.setError("password should contain upper,lower,letter number");
            userPassword.requestFocus();
            return false;
        }
        return true;
    }


    public boolean checkPhn(String phn2) {
        if (phn2.equals("")) {
            phn.setError("phone_no can't empty");
            phn.requestFocus();
            return false;
        } else if (!phn2.matches(phnR)) {
            phn.setError("write correct phone number");
            phn.requestFocus();
            return false;
        }
        return true;
    }

    public boolean checkGender(String gender2) {

        if (gender2.equals("")) {
            gender.setError("gender can't empty");
            gender.requestFocus();
            return false;

        } else if (!gender2.matches(genderR)) {
            gender.setError("gender required");
            gender.requestFocus();
            return false;
        }
        return true;
    }

//    public boolean checkRoll(String roll2){
//        if(roll2.equals("")){
//            Toast.makeText(this, "roll_no should not empty", Toast.LENGTH_SHORT).show();
//        }
//       else if (roll2.matches(roll_NoR)){
//            Toast.makeText(this, "roll_no is correct", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        else{
//            roll_no.setError("please enter correct roll_no");
//        }
//        return false;
//    }

}