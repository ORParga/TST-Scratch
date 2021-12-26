package com.example.tstscratch;

public class Data1User {
    String NickName="";
    String Name="";
    String SubName="";
    String Phone="";
    String eMail="";
    String Password="";
    boolean DontAskPasword=false;

    public Data1User(String nickName, String name, String subName, String phone, String eMail, String password, boolean dontAskPasword) {
        NickName = nickName;
        Name = name;
        SubName = subName;
        Phone = phone;
        this.eMail = eMail;
        Password = password;
        DontAskPasword = dontAskPasword;
    }

    public Data1User (String nickName,String Password,boolean DontAskPassword)
    {
        this.NickName=nickName;
        this.Password=Password;
        this.DontAskPasword=DontAskPassword;
    }
    public Data1User (String nickName,String Password)
    {
        this.NickName=nickName;
        this.Password=Password;
    }
    public Data1User (String nickName,String eMail,String Password,boolean DontAskPassword)
    {
        this.NickName=nickName;
        this.eMail=eMail;
        this.Password=Password;
        this.DontAskPasword=DontAskPassword;
    }
    public void setDontAskPasword(boolean DontAsk)
    {
        DontAskPasword=DontAsk;
    }
    public boolean getDontAskPassword()
    {
        return DontAskPasword;
    }
    public void set (String NickName,String eMail,String Password,boolean DontAskPassword){

        this.Name=NickName;
        this.eMail=eMail;
        this.Password=Password;
        this.DontAskPasword=DontAskPassword;
    }
    public void set (String NickName,String Password,boolean DontAskPassword) {

        this.NickName=NickName;
        this.Password=Password;
        this.DontAskPasword=DontAskPassword;
    }

    public String getNickName() {
        return NickName;
    }

    public String getName() {
        return Name;
    }

    public String getSubName() {
        return SubName;
    }

    public String getPhone() {
        return Phone;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPassword() {
        return Password;
    }

}
