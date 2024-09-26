package com.example.pankajdemo;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagerClass {

    Context mContext;
    private final String PROFILE_NAME = "profile_name";
    private final int MODE = 0;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public String Name="NAME";
    public String UserName="USER_NAME";
    public String Email="EMAIL";
    public String Password="PASSWORD";
    public String PH_NO = "USER_PHONE_NO";
    public String Roll_No = "Roll_No";
    public String KEY_SESSION ="USER_LOGGED_IN";

    public SessionManagerClass(Context context){
        this.mContext = context;
        sp = context.getSharedPreferences(PROFILE_NAME,MODE);
        editor = sp.edit();
    }

    public void createSession(String name,String ph_no){
       editor.putString(Name,name);
       editor.putString(PH_NO,ph_no);
       editor.putBoolean(KEY_SESSION,true);
       editor.commit();
    }
    public void createSession1(String name,String username,String email,String password,String ph_no,String roll_no){
        editor.putString(Name,name);
        editor.putString(UserName,username);
        editor.putString(Email,email);
        editor.putString(Password,password);
        editor.putString(PH_NO,ph_no);
        editor.putString(Roll_No,roll_no);
        editor.putBoolean(KEY_SESSION,true);
        editor.commit();
    }

    public boolean checkSession(){
         if(sp.contains(KEY_SESSION)){
             return true;
         }
         else{
             return false;
         }
    }
    public void logOutSession(){
        editor.clear();
        editor.commit();
    }
    public String getSessionDetail(String key_name){
       String name = sp.getString(key_name,null);
       return name;
    }

    public void deleteData(String key){

    }
}
