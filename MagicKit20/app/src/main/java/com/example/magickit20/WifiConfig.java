package com.example.magickit20;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.view.View;

import java.util.HashMap;

public class WifiConfig extends AppCompatActivity {

    EditText wifiName;
    EditText wifiPassword;
    ProgressBar progressBarWIFI;
    Button change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_config);

        wifiName = findViewById(R.id.wifiName);
        wifiPassword = findViewById(R.id.passwordWifi);
        progressBarWIFI = findViewById(R.id.progressBarWIFI);
        change = findViewById(R.id.changeButton);




        change.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                try {
                    if (TextUtils.isEmpty(wifiName.getText()) && TextUtils.isEmpty(wifiPassword.getText())) {

                        if (TextUtils.isEmpty(wifiName.getText())) {

                            Toast.makeText(WifiConfig.this, "Please Enter WI-FI Name", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(WifiConfig.this, "Please Enter WI-FI Password", Toast.LENGTH_SHORT).show();

                        }

                    } else {


                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("WI-FI");
                        HashMap<String, String> map = new HashMap<>();
                        map.put("Na", wifiName.getText().toString());
                        map.put("Pa", wifiPassword.getText().toString());

                        progressBarWIFI.setVisibility(android.view.View.VISIBLE);
                        reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(WifiConfig.this, "WI-FI Configuration Successful", Toast.LENGTH_SHORT).show();
                                    progressBarWIFI.setVisibility(android.view.View.INVISIBLE);
                                }else{
                                    Toast.makeText(WifiConfig.this, "WI-FI Configuration failed", Toast.LENGTH_SHORT).show();
                                    progressBarWIFI.setVisibility(android.view.View.INVISIBLE);

                                }
                            }
                        });

                    }


                }catch (Exception e){

                    Toast.makeText(WifiConfig.this, "Please try Again?", Toast.LENGTH_SHORT).show();
                    progressBarWIFI.setVisibility(android.view.View.INVISIBLE);



                }
            }
        });

        
    }












}


