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

public class Login extends AppCompatActivity {
    private ImageView img1_login;
    private TextView tv1, tv2;
    private TextInputLayout idnumber, password;
    private Button forgetPass, signIN, signUP;


    private DatabaseReference reference;
    private Query checkUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSharedElementsUseOverlay(true);

        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        findKey();
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Contact to your supervisor for this issue.", Toast.LENGTH_SHORT).show();
            }
        });
        signIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();

            }
        });
        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);

                //for anim
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(img1_login, "logo_anim");
                pairs[1] = new Pair<View, String>(tv1, "text_anim");
                pairs[2] = new Pair<View, String>(tv2, "text_anim2");
                pairs[3] = new Pair<View, String>(idnumber, "usr_name_anim");
                pairs[4] = new Pair<View, String>(password, "pass_anim");
                pairs[5] = new Pair<View, String>(signIN, "login_btn_anim");
                pairs[6] = new Pair<View, String>(signUP, "sign_up_btn_anim");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());

            }
        });
    }
    private void validate() {
        String IDNUMBER = idnumber.getEditText().getText().toString();
        String PASSWORD = password.getEditText().getText().toString();

        if(!validateUserName(IDNUMBER) || !validatePassWord(PASSWORD)){
            return;
        }else{
            isUser(IDNUMBER, PASSWORD);
        }
    }
    private Boolean validatePassWord(String val) {
        if(val.isEmpty()){
            idnumber.setError("Feild can not be Empty.");
            return false;
        }else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUserName(String val) {
        String noWhite_Space = getString(R.string.noWhiteSpace);
        if(val.isEmpty()){
            idnumber.setError("Feild can not be Empty.");
            return false;
        } else{
            idnumber.setError(null);
            idnumber.setErrorEnabled(false);
            return true;
        }
    }

    private void isUser(String IDNUMBER, String PASSWORD) {


        reference = FirebaseDatabase.getInstance().getReference("users");
        checkUser = reference.orderByChild("id_Number_txt").equalTo(IDNUMBER);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    idnumber.setError(null);
                    idnumber.setErrorEnabled(false);
                    String passwordFromDB = snapshot.child(IDNUMBER).child("password").getValue(String.class);
                    if(passwordFromDB.equals(PASSWORD)){
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String idNumberDB = snapshot.child(IDNUMBER).child("id_Number_txt").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), UserProfile.class);

                        intent.putExtra("idNumber", idNumberDB);

                        startActivity(intent);
                    } else{
                        password.setError("Wrong password");
                        password.requestFocus();
                    }
                }else{
                    idnumber.setError("No such user exists");
                    idnumber.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    private void findKey() {
        idnumber = findViewById(R.id.idnumber_txt_input);
        password = findViewById(R.id.password_txt_input);
        forgetPass = findViewById(R.id.btn1_f_pass_login);
        signIN = findViewById(R.id.btn2_log_in_login);
        signUP = findViewById(R.id.btn3_sign_up_login);
        img1_login = findViewById(R.id.imgV1_login);
        tv1 = findViewById(R.id.tv1_login);
        tv2 = findViewById(R.id.tv2_login);

    }
}