package com.example.tstscratch;

import android.content.Context;
import android.content.SharedPreferences;

public class Data2LocalStore {
    public static final String sp_FILE = "userDetails";
    public static final String spNick = "sp_nick";
    public static final String spName = "sp_name";
    public static final String spSubNames = "sp_subnames";
    public static final String spPhone = "sp_phone";
    public static final String speMail = "sp_email";
    public static final String spPassword = "sp_password";
    public static final String spDontAsk = "sp_dontask";

    SharedPreferences sharedPreferences;

    public Data2LocalStore (Context context)
    {
        sharedPreferences =context.getSharedPreferences(sp_FILE,0);
    }
    public void setUserInSharedPreferences(Data1User user)
    {
        SharedPreferences.Editor spEditor= sharedPreferences.edit();
        spEditor.putString(spNick,user.NickName);
        spEditor.putString(spName,user.Name);
        spEditor.putString(spSubNames,user.SubName);
        spEditor.putString(speMail,user.eMail);
        spEditor.putString(spPassword,user.Password);
        spEditor.putBoolean(spDontAsk,user.DontAskPasword);
        spEditor.commit();
    }
    public Data1User getUserInSharedPreferences(){
        String nickName= sharedPreferences.getString(spNick,"");
        String name= sharedPreferences.getString(spName,"");
        String subnames= sharedPreferences.getString(spSubNames,"");
        String phone= sharedPreferences.getString(spPhone,"");
        String email= sharedPreferences.getString(speMail,"");
        String password= sharedPreferences.getString(spPassword,"");
        boolean dontask= sharedPreferences.getBoolean(spDontAsk,false);

            Data1User user=new Data1User(
                    nickName,
                    name,
                    subnames,
                    phone,
                    email,
                    password,
                    dontask);

        return user;
    }
    public void setUserLoggedIn(boolean loggedIn)
    {
        SharedPreferences.Editor spEditor= sharedPreferences.edit();
        spEditor.putBoolean("loogedIn",loggedIn);
        spEditor.commit();
    }
    public void clearUserData(){
        SharedPreferences.Editor spEditor= sharedPreferences.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
