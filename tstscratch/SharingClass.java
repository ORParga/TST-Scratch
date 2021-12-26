package com.example.tstscratch;


public class SharingClass {
    public enum FromActivity {Start ,A1Login,A2NewUser,A3ComprobarUser,A4RegistrarUser}

    public static FromActivity fromActivity= FromActivity.Start;
    public static final String EXTRA_USER ="ExtraUser";
    public static final String EXTRA_PASSWORD ="ExtraPassword";
    public static final String EXTRA_REPEATPASSWORD ="ExtraRepeatPassword";
    public static final String EXTRA_DONTASK ="ExtraDontAsk";


    public static Data1User data1User=new Data1User("User","User@email.com","password",false);
}
