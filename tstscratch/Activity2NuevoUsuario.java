package com.example.tstscratch;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Activity2NuevoUsuario extends AppCompatActivity {

    String strPasswordError ="";
    EditText txtNickName;
    EditText txtemail;
    EditText txtPassword ;
    EditText txtRepeat ;
    CheckBox chkShowPassWord;


    private FirebaseDatabase database;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity02nuevo_usuario);
         txtNickName = findViewById(R.id.txtNewUserUsuario);
         txtemail = findViewById(R.id.txtNewUserEMail);
         txtPassword = findViewById(R.id.txtNewUserPassword);
         txtRepeat = findViewById(R.id.txtNewUserRepeatPassword);
         chkShowPassWord=findViewById(R.id.chkNewUserMostrar);


    }
    @Override
    protected void onResume (){
        super.onResume();


        switch (SharingClass.fromActivity)
        {
            case Start:
                loadSharedPreferencesToTextViews();
                break;
            case A1Login:
                loadSharedPreferencesToTextViews();
                break;
            case A3ComprobarUser:
                loadSharedPreferencesToTextViews();
                break;
            default:
        }


        SharingClass.fromActivity= SharingClass.FromActivity.A2NewUser;
    }
public void onClickRegistrar(View view){
        RegistrarUsuario_and_gotoMainActivity();
}
public void onClickShowPasword(View view) {
    if (chkShowPassWord.isChecked()) {
        int inputType=txtPassword.getInputType();
        txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        txtRepeat.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    } else {
        txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        txtRepeat.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
}
@Override
public void onBackPressed(){
        super.onBackPressed();
        saveTextDataToUserDataAndSharedPreferences();
}
    public void onClickVolver(View view){

        onBackPressed();
    }
    public boolean testPassword (String strPassword,String strRepeat){

        if(!strPassword.equals(strRepeat))
        {
            Toast.makeText(this, "Los Passwords no coinciden!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(strPassword.length()<8){

            Toast.makeText(this, "Password debe tener al menos 8 letras!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void saveTextDataToUserDataAndSharedPreferences ()
    {
        SharingClass.data1User=new Data1User(
                txtNickName.getText().toString(),
                txtemail.getText().toString(),
                txtPassword.getText().toString(),
                SharingClass.data1User.DontAskPasword
        );
        Data2LocalStore localStore=new Data2LocalStore(this);
        localStore.setUserInSharedPreferences(SharingClass.data1User);

    }
    public void loadSharedPreferencesToTextViews()
    {

        Data2LocalStore localStore=new Data2LocalStore(this);
        Data1User data1User=localStore.getUserInSharedPreferences();
        txtNickName.setText(data1User.NickName);
        txtemail.setText(data1User.eMail);
        txtPassword.setText(data1User.Password);
        txtRepeat.setText(data1User.Password);
        txtNickName.invalidate();
        txtemail.invalidate();
    }
    public void RegistrarUsuario_and_gotoMainActivity()
    {
        if(!testPassword(txtPassword.getText().toString(),txtRepeat.getText().toString()))return;

        saveTextDataToUserDataAndSharedPreferences();

        String strNickName=txtNickName.getText().toString();
        Data1User cUser =new Data1User(
                strNickName,
                txtemail.getText().toString(),
                txtPassword.getText().toString(),
                false

        );
        database= FirebaseDatabase.getInstance();
        myRef= database.getReference("User");

        ValueEventListener vel=myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.removeEventListener(this);
                if (!nickNameAlreadyExists(snapshot, strNickName)) {
                    myRef.child(strNickName).setValue(cUser);

                    //myRef.child(userName1).setValue(cUser);
                    Toast.makeText(Activity2NuevoUsuario.this, strNickName+" registrado.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Activity2NuevoUsuario.this, Activity5Main.class);
                    i.putExtra("userName",strNickName);
                    startActivity(i);
                } else {
                    Toast.makeText(Activity2NuevoUsuario.this, "User Name Already Exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Activity2NuevoUsuario.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                myRef.removeEventListener(this);
            }
        });
    }

    private boolean nickNameAlreadyExists(DataSnapshot snapshot, String strUser) {
        String user1;
        for (DataSnapshot ds : snapshot.getChildren()) {
            user1 = ds.child("nickName").getValue(String.class);
            if(strUser.equals(user1)){
                return true;
            }
        }
        return false;
    }
}