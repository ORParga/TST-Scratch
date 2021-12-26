package com.example.tstscratch;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
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

public class Activity1Login extends AppCompatActivity {

    Button btnEntrar,btnNuevoUsuario;
    EditText txtNickName,txtPassword;
    CheckBox chkShowPassWord,chkDontAsk;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity01login);
        btnEntrar = findViewById(R.id.btnloginEntrar);
        btnNuevoUsuario = findViewById(R.id.btnLoginNewUser);
        txtNickName = findViewById(R.id.txtLoginNickEMail);
        txtPassword = findViewById(R.id.txtLoginPassword);
        chkShowPassWord=findViewById(R.id.chkLoginMostrar);
        chkDontAsk=findViewById(R.id.chkLoginDontAsk);

    }
    @Override
    public void onResume(){
        super.onResume();

        loadSharedPreferencesToTextViews();
        switch (SharingClass.fromActivity)
        {
            case Start:
                break;
            case A2NewUser:
                break;
            case A3ComprobarUser:
                break;
            default:
        }
        SharingClass.fromActivity=SharingClass.FromActivity.A1Login;

    }
    public void onClickShowPasword(View view) {
        if (chkShowPassWord.isChecked()) {
            int inputType=txtPassword.getInputType();
            txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
    public void onClick_Entrar (View view){
        saveUserDataToSharedPreferences();
        ComprobarUsuario_and_gotoMainActivity();
    }
    public void onClick_NuevoUSuario (View view){
        saveUserDataToSharedPreferences();
        goToNuevoUsuario();
    }
    public void saveUserDataToSharedPreferences ()
    {
        SharingClass.data1User.set(
                txtNickName.getText().toString(),
                txtPassword.getText().toString(),
                chkDontAsk.isChecked()
                );
        Data2LocalStore localStore=new Data2LocalStore(this);
        localStore.setUserInSharedPreferences(SharingClass.data1User);

    }
    public void loadSharedPreferencesToTextViews()
    {

        Data2LocalStore localStore=new Data2LocalStore(this);
        SharingClass.data1User=localStore.getUserInSharedPreferences();
        txtNickName.setText(SharingClass.data1User.NickName);
        txtPassword.setText(SharingClass.data1User.Password);
        txtNickName.invalidate();
        txtPassword.invalidate();
    }
    public void goToNuevoUsuario ()
    {
        Intent intentNewUser = new Intent(this, Activity2NuevoUsuario.class);
        String message = txtNickName.getText().toString();
        intentNewUser.putExtra(SharingClass.EXTRA_USER, message);
        startActivity(intentNewUser);
    }
    public void ComprobarUsuario_and_gotoMainActivity()
    {

        String strNickName=txtNickName.getText().toString();
        String strPassword=txtPassword.getText().toString();
        Data1User cUser =new Data1User(
                strNickName,
                txtPassword.getText().toString(),
                chkDontAsk.isChecked()
        );
        database= FirebaseDatabase.getInstance();
        myRef= database.getReference("User");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.removeEventListener(this);
                if (passwordAccepted(snapshot, strNickName,strPassword)) {
                    myRef.child(strNickName).setValue(cUser);

                    //myRef.child(userName1).setValue(cUser);
                    Toast.makeText(Activity1Login.this, "Usuario "+strNickName+" aceptado.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Activity1Login.this, Activity5Main.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Activity1Login.this, "Nombre de Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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
    private boolean passwordAccepted(DataSnapshot snapshot,String strNickName,String strPassword)
    {
        String user1,password;
        for (DataSnapshot ds : snapshot.getChildren()) {
            user1 = ds.child("nickName").getValue(String.class);
            password =ds.child("password").getValue(String.class);
            if(strNickName.equals(user1)&&strPassword.equals(password)){
                return true;
            }
        }
        return false;

    }
}