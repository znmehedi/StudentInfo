package com.example.studentinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView bImage;
    private TextView tv1, tv2;
    private Animation top, right, left;

    private static int WAIT_TIME=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        findV();

        startAnim();

        //start after delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Login.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(bImage, "logo_anim");
                pairs[1] = new Pair<View, String>(tv1, "text_anim");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent, options.toBundle());
                finish();

            }
        }, WAIT_TIME);
    }

    private void startAnim() {
        bImage.setAnimation(top);
        tv1.setAnimation(right);
        tv2.setAnimation(left);
    }

    private void findV() {
        bImage=findViewById(R.id.imgV1_ss);
        tv1=findViewById(R.id.tv1_ss);
        tv2=findViewById(R.id.tv2_ss);

        top= AnimationUtils.loadAnimation(this, R.anim.top_animation);
        right=AnimationUtils.loadAnimation(this, R.anim.right_animation);
        left=AnimationUtils.loadAnimation(this, R.anim.left_animation);
    }
}