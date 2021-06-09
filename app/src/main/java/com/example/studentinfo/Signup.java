package com.example.studentinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity {

    private TextInputLayout fName, idNumber, email, pass, phone;
    private Button go, login;
    private ImageView imgV1_signUp;
    private TextView tv1_signUp, tv2_signUp;


    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private Query checkUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSharedElementsUseOverlay(true);
        setContentView(R.layout.activity_signup);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        findKey();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userRegister(v);
            }
        });
    }
    private void goBack(){

        finish();

    }

    private Boolean validateFullName(String val){
        //String val = fName.getEditText().getText().toString();
        if(val.isEmpty()){
            fName.setError("Feild cannot be Empty.");
            return false;
        } else{
            fName.setError(null);
            fName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateUsername(String val){
        //String val =uName.getEditText().getText().toString();
        String noWhite_Space = getString(R.string.noWhiteSpace);
        if(val.isEmpty()){
            idNumber.setError("Feild can not be Empty.");
            return false;
        } else if(val.length()>=8){
            idNumber.setError("ID Number too long.");
            return false;
        } else if(val.matches(noWhite_Space)){
            idNumber.setError("White spaces are not allowed.");
            return false;
        } else{
            idNumber.setError(null);
            idNumber.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail(String val){

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String noWhite_Space = getString(R.string.noWhiteSpace);
        if(val.isEmpty()){
            email.setError("Feild cannot be Empty.");
            return false;
        } else if(!val.matches(emailPattern)){
            email.setError("Invaild email address.");
            return false;
        } else if(val.matches(noWhite_Space)){
            email.setError("White spaces are not allowed.");
            return false;
        } else{
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePhoneNumber(String val){

        if(val.isEmpty()){
            phone.setError("Feild can not be Empty.");
            return false;
        } else{
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword(String val) {

        String passwordPattern = "^"+
                //"(?=.*[0-9])"+              //at least 1 digit
                //"(?=.*[a-z])"+              //at least 1 lowercase
                //"(?=.*[A-Z])"+              //at least 1 upercase
                "(?=.*[a-zA-Z])"+           //any letter
                //"(?=\\s+$)"+                //no white space
                ".{4,}"+                    //at least 4 character
                "$";
        if(val.isEmpty()){
            pass.setError("Feild can not be Empty.");
            return false;
        } else if(!val.matches(passwordPattern)){
            pass.setError("Password too weak.");
            return false;
        } else{
            pass.setError(null);
            pass.setErrorEnabled(false);
            return true;
        }
    }

    private void userRegister(View v) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        //get all values from screen
        String fullName, id_Number_txt, email_txt, phone_txt, password_txt;
        fullName = fName.getEditText().getText().toString();
        id_Number_txt = idNumber.getEditText().getText().toString();
        email_txt = email.getEditText().getText().toString();
        phone_txt = phone.getEditText().getText().toString();
        password_txt = pass.getEditText().getText().toString();

        if(validateFullName(fullName) && validateUsername(id_Number_txt) && validateEmail(email_txt) && validatePhoneNumber(phone_txt) && validatePassword(password_txt)) {
            checkUser = reference.orderByChild("id_Number_txt").equalTo(id_Number_txt);

            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        idNumber.setError("Same ID number exists");
                        idNumber.requestFocus();
                    }else{

                        UserHelperClass userHelperClass = new UserHelperClass(fullName, id_Number_txt, email_txt, phone_txt, password_txt, "", "", "");

                        reference.child(userHelperClass.getId_Number_txt()).setValue(userHelperClass);
                        Toast.makeText(Signup.this, "User registered\nNow Sign in.", Toast.LENGTH_SHORT).show();
                        goBack();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            //object of user helper class

        }
    }

    private void findKey() {
        fName = findViewById(R.id.fullName_txt_signup);
        idNumber = findViewById(R.id.idNumber_txt_signup);
        email = findViewById(R.id.email_txt_signup);
        pass = findViewById(R.id.password_txt_signup);
        phone = findViewById(R.id.phone_txt_signup);
        go = findViewById(R.id.btn1_signup);
        login = findViewById(R.id.btn2_signup);
        imgV1_signUp = findViewById(R.id.imgV1_signup);
        tv1_signUp = findViewById(R.id.tv1_signup);
        tv2_signUp = findViewById(R.id.tv2_signup);

    }
}