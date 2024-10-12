package com.example.TagFinder.ModalOfApi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteDataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserInfo_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Users";
    private static final String ID = "id";
    private static final String COLUMN_NAME = "Person_name";
    private static final String USER_NAME = "User_name";
    private static final String USER_EMAIL = "User_Email";
    private static final String USER_PASSWORD = "User_Password";
    private static final String PHONE_NUMBER = "Phone_no";
    private static final String USER_GENDER = "User_Gender";
    SQLiteDatabase dpHelper;

    public SqliteDataHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String create_tb = "CREATE TABLE  " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME +
                "   TEXT, " + USER_NAME + "   TEXT, " + USER_EMAIL + "   TEXT NOT NULL UNIQUE,  " + USER_PASSWORD + "   TEXT,  " + PHONE_NUMBER + " TEXT, " + USER_GENDER + "   TEXT)";

        sqLiteDatabase.execSQL(create_tb);
//
//         String create_table = "CREATE TABLE IF NOT EXISTS student (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                " Student_name TEXT, User_name TEXT, Phone_no TEXT, Roll_no TEXT )";
//
//        sqLiteDatabase.execSQL(create_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+"student");
//        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String std_name, String user_name, String user_email, String user_password, String Ph_no, String gender) {

        dpHelper = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, std_name);
        values.put(USER_NAME, user_name);
        values.put(USER_EMAIL, user_email);
        values.put(USER_PASSWORD, user_password);
        values.put(PHONE_NUMBER, Ph_no);
        values.put(USER_GENDER, gender);

        long ans = dpHelper.insert(TABLE_NAME, null, values);
        if (ans > 0) {
            dpHelper.close();
            return true;
        }
        return false;

//        values.put("Student_name",std_name);
//        values.put("User_name",user_name);
//        values.put("Phone_no",Ph_no);
//        values.put("Roll_no",Roll_no);
//        dpHelper.insert("student",null,values);
//        dpHelper.close();

    }

    public Cursor retriveValue(String email) {
        dpHelper = this.getReadableDatabase();
        String getAllValues = "SELECT * FROM " + TABLE_NAME + " WHERE " + USER_EMAIL + " = ?";
        Cursor cursor = dpHelper.rawQuery(getAllValues, new String[]{email});
        return cursor;

    }

    public boolean checkLogin(String email, String password) {
        dpHelper = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + USER_EMAIL + " = ? AND " + USER_PASSWORD + " = ? ";
        Cursor cursor = dpHelper.rawQuery(query, new String[]{email, password});

        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }

    public boolean updatevalues(String email,String username,String phone_no){

        dpHelper = this.getWritableDatabase();
        String q = "SELECT * FROM "+TABLE_NAME+" WHERE "+USER_EMAIL +" = ? ";
        ContentValues values = new ContentValues();
        values.put(USER_NAME,username);
        values.put(PHONE_NUMBER,phone_no);
        int i = dpHelper.update(TABLE_NAME,values,USER_EMAIL+" ? = ",new String[]{email});

        if(i>0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean deleteValues(String email){
        dpHelper = this.getWritableDatabase();
       int i =  dpHelper.delete(TABLE_NAME,USER_EMAIL+" ? = ",new String[]{email});

       if(i > 0){
           return true;
       }
       else {
           return false;
       }
    }
}
