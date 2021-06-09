package com.example.studentinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class UserProfile extends AppCompatActivity {

    private TextView fullNameUP, idNumberUP, crdv1, crdv2, crdv3;
    private String _fullname, _idnumber, _e_mail, _phone_number, _password, _balance, _semester, _cgpa;
    private TextInputLayout fullNameTIL, idNumberTIL, emailTIL, phoneTIL, passWordTIL, balanceTIL, semesterTIL, cgpaTIL;
    private MaterialButton updateBtn, logoutBtn;
    private DatabaseReference reference;
    private Query checkUser;
    private Boolean UpdateFlag[] = {false, false, false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        findkey();
        setValue();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                update(view);
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(UserProfile.this, Login.class);
                //startActivity(intent);
                finish();
            }
        });
    }
    private void update(View view){
        reference = FirebaseDatabase.getInstance().getReference("users");
        fullname();
        e_mail();
        phone_number();
        password();
        balance();
        semester();
        cgpa();
        boolean ft = false;
        for(int i=0;i<7;i++){
            if(UpdateFlag[i]){
                ft=true;
                UpdateFlag[i] = false;
            }
        }
        if(!ft)
        {
            Toast.makeText(this, "No Update Occured.", Toast.LENGTH_SHORT).show();
            ft=false;
        }
    }
    private void fullname() {

            if(validateFullName()==true && !_fullname.equals(fullNameTIL.getEditText().getText().toString())){
                _fullname = fullNameTIL.getEditText().getText().toString();
                reference.child(_idnumber).child("fullName").setValue(_fullname);
                fullNameUP.setText(_fullname);
                Toast.makeText(this, "Name updated", Toast.LENGTH_SHORT).show();
            }
            else UpdateFlag[0] = true;
    }

    private void e_mail() {

            if(validateEmail()==true && !_e_mail.equals(emailTIL.getEditText().getText().toString())){
                _e_mail = emailTIL.getEditText().getText().toString();
                reference.child(_idnumber).child("email").setValue(_e_mail);
                Toast.makeText(this, "Email updated", Toast.LENGTH_SHORT).show();

            }
            else UpdateFlag[1] = true;
    }
    private void phone_number() {

        if(validatePhoneNumber()==true && !_phone_number.equals(phoneTIL.getEditText().getText().toString())){
            _phone_number = phoneTIL.getEditText().getText().toString();
            reference.child(_idnumber).child("phone").setValue(_phone_number);
            Toast.makeText(this, "Phone number updated", Toast.LENGTH_SHORT).show();
        }
        else UpdateFlag[2] = true;
    }
    private void password() {

        if(validatePassword()==true && !_password.equals(passWordTIL.getEditText().getText().toString())){
            _password = passWordTIL.getEditText().getText().toString();
            reference.child(_idnumber).child("password").setValue(_password);
            Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show();
        }
        else UpdateFlag[3] = true;
    }
    private void balance() {

        if(!_balance.equals(balanceTIL.getEditText().getText().toString())){
            _balance = balanceTIL.getEditText().getText().toString();
            reference.child(_idnumber).child("balance").setValue(_balance);
            crdv1.setText(_balance);
            Toast.makeText(this, "Balance updated", Toast.LENGTH_SHORT).show();
        }
        else UpdateFlag[4] = true;


    }
    private void semester() {

        if(!_semester.equals(semesterTIL.getEditText().getText().toString())){
            _semester = semesterTIL.getEditText().getText().toString();
            reference.child(_idnumber).child("semester").setValue(_semester);
            crdv2.setText(_semester);
            Toast.makeText(this, "Semester updated", Toast.LENGTH_SHORT).show();
        }
        else UpdateFlag[5] = true;

    }
    private void cgpa() {

        if(!_cgpa.equals(cgpaTIL.getEditText().getText().toString())){
            _cgpa = cgpaTIL.getEditText().getText().toString();
            reference.child(_idnumber).child("cgpa").setValue(_cgpa);
            crdv3.setText(_cgpa);
            Toast.makeText(this, "CGPA updated", Toast.LENGTH_SHORT).show();
        }
        else UpdateFlag[6] = true;

    }

    private Boolean validateFullName(){
        String val = fullNameTIL.getEditText().getText().toString();
        if(val.isEmpty()){
            fullNameTIL.setError("Feild cannot be Empty.");
            return false;
        } else{
            fullNameTIL.setError(null);
            fullNameTIL.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail(){
        String val = emailTIL.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String noWhite_Space = getString(R.string.noWhiteSpace);
        if(val.isEmpty()){
            emailTIL.setError("Feild cannot be Empty.");
            return false;
        } else if(!val.matches(emailPattern)){
            emailTIL.setError("Invaild email address.");
            return false;
        } else if(val.matches(noWhite_Space)){
            emailTIL.setError("White spaces are not allowed.");
            return false;
        } else{
            emailTIL.setError(null);
            emailTIL.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePhoneNumber(){
        String val = phoneTIL.getEditText().getText().toString();
        if(val.isEmpty()){
            phoneTIL.setError("Feild can not be Empty.");
            return false;
        } else{
            phoneTIL.setError(null);
            phoneTIL.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = passWordTIL.getEditText().getText().toString();
        String passwordPattern = "^"+
                //"(?=.*[0-9])"+              //at least 1 digit
                //"(?=.*[a-z])"+              //at least 1 lowercase
                //"(?=.*[A-Z])"+              //at least 1 upercase
                "(?=.*[a-zA-Z])"+           //any letter
                //"(?=\\s+$)"+                //no white space
                ".{4,}"+                    //at least 4 character
                "$";
        if(val.isEmpty()){
            passWordTIL.setError("Feild can not be Empty.");
            return false;
        } else if(!val.matches(passwordPattern)){
            passWordTIL.setError("Password too weak.");
            return false;
        } else{
            passWordTIL.setError(null);
            passWordTIL.setErrorEnabled(false);
            return true;
        }
    }

    private void setValue() {
        Intent intent = getIntent();

        _idnumber = intent.getStringExtra("idNumber");


        reference = FirebaseDatabase.getInstance().getReference("users");
        checkUser = reference.orderByChild("id_Number_txt").equalTo(_idnumber);
        //checkUser = reference.orderByKey().equalTo(IDNUMBER);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                        _fullname= snapshot.child(_idnumber).child("fullName").getValue(String.class);
                        _e_mail = snapshot.child(_idnumber).child("email").getValue(String.class);
                        _phone_number = snapshot.child(_idnumber).child("phone").getValue(String.class);
                        _password = snapshot.child(_idnumber).child("password").getValue(String.class);
                        _balance = snapshot.child(_idnumber).child("balance").getValue(String.class);
                        _semester = snapshot.child(_idnumber).child("semester").getValue(String.class);
                        _cgpa = snapshot.child(_idnumber).child("cgpa").getValue(String.class);


                    fullNameUP.setText(_fullname);
                    idNumberUP.setText(_idnumber);
                    fullNameTIL.getEditText().setText(_fullname);
                    idNumberTIL.getEditText().setText(_idnumber);
                    emailTIL.getEditText().setText(_e_mail);
                    phoneTIL.getEditText().setText(_phone_number);
                    passWordTIL.getEditText().setText(_password);
                    balanceTIL.getEditText().setText(_balance);
                    semesterTIL.getEditText().setText(_semester);
                    cgpaTIL.getEditText().setText(_cgpa);
                    crdv1.setText(_balance);
                    crdv2.setText(_semester);
                    crdv3.setText(_cgpa);


                }else{
                    Toast.makeText(UserProfile.this, "Database error to find details", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


    private void findkey() {
        fullNameUP = findViewById(R.id.usr_name1);
        idNumberUP = findViewById(R.id.idnumber_id1);
        fullNameTIL = findViewById(R.id.down_ll_txt_input1);
        idNumberTIL = findViewById(R.id.down_ll_txt_input2);
        emailTIL = findViewById(R.id.down_ll_txt_input3);
        phoneTIL = findViewById(R.id.down_ll_txt_input4);
        passWordTIL = findViewById(R.id.down_ll_txt_input5);
        balanceTIL = findViewById(R.id.down_ll_txt_input6);
        semesterTIL = findViewById(R.id.down_ll_txt_input7);
        cgpaTIL = findViewById(R.id.down_ll_txt_input8);
        crdv1 = findViewById(R.id.txt1_middle_linearLayout_cardview1);
        crdv2 = findViewById(R.id.txt1_middle_linearLayout_cardview2);
        crdv3 = findViewById(R.id.txt1_middle_linearLayout_cardview3);
        updateBtn = findViewById(R.id.up_update_btn);
        logoutBtn = findViewById(R.id.up_logout_btn);;
        idNumberTIL.setEnabled(false);

    }


}