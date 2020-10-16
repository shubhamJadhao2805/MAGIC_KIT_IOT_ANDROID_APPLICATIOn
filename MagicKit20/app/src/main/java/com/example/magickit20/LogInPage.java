package com.example.magickit20;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.Pulse;
import com.github.ybq.android.spinkit.style.RotatingCircle;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LogInPage extends AppCompatActivity {




    DatabaseReference Authentication = FirebaseDatabase.getInstance().getReference().child("Authentication");


    EditText username;
    EditText password;
    ImageButton button;
    View view2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        view2 =  findViewById(R.id.view2);
        button = findViewById(R.id.imageButton);


    }








    //Method to logIn

    public void logIn(View view){
        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.blink);
        button.setAnimation(animation);
       final ProgressBar progressBar = findViewById(R.id.progress);
        Sprite foldingCube = new FadingCircle();
        progressBar.setIndeterminateDrawable(foldingCube);

        progressBar.setVisibility(View.VISIBLE);
        view2.setVisibility(View.VISIBLE);


        Authentication.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    HashMap<String, String> map = (HashMap<String, String>) dataSnapshot.getValue();
                    if (map.get("pass").equals(password.getText().toString()) && map.get("user").equals(username.getText().toString())) {


                        Toast.makeText(LogInPage.this,"LogIn Succesful",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        view2.setVisibility(View.INVISIBLE);


                        Intent intent2  = new Intent(LogInPage.this,DashBoardActivity.class);
                                startActivity(intent2);

//                        Intent intent2  = new Intent(LogInPage.this,OnOfButton.class);
//                        startActivity(intent2);

                    }else{
                        Toast.makeText(LogInPage.this,"LogIn Failed",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        view2.setVisibility(View.INVISIBLE);


                    }

                }catch (Exception e ){

                    progressBar.setVisibility(View.INVISIBLE);
                    view2.setVisibility(View.INVISIBLE);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }




}
