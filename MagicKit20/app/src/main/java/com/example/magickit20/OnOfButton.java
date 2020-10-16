package com.example.magickit20;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.steamcrafted.lineartimepicker.dialog.LinearTimePickerDialog;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class OnOfButton extends AppCompatActivity {


    HashMap<String,Integer> map;
    ImageView lamp1;
    ImageView lamp2;
    ImageView lamp3;
    ImageView lamp4;
    HashMap<String,String> mapDevice;
    final Integer[] toggleArray = {0,0,0,0};
    ProgressBar progressBar1;
    ProgressBar progressBar2;
    ProgressBar progressBar3;
    ProgressBar progressBar4;
    Animation animation;
    int hourSelected;
    int minuteSelected;
    LinearTimePickerDialog.Builder linearTimePickerDialog;

    ImageView timmer1;
    ImageView timmer2;
    ImageView timmer3;
    ImageView timmer4;




    CountDownTimer cTimmer1;
    CountDownTimer cTimmer2;
    CountDownTimer cTimmer3;
    CountDownTimer cTimmer4;




    long time1;
    long time2;
    long time3;
    long time4;



    int switchNumberForTimmer;


    Integer[] isTimmerSet = {0,0,0,0};


    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Device");
    DatabaseReference referenceHashmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_of_button);
        mapDevice = new HashMap<>();
        linearTimePickerDialog = LinearTimePickerDialog.Builder.with(OnOfButton.this);
        linearTimePickerDialog.setTextColor(Color.parseColor("red"));
        linearTimePickerDialog.setDialogBackgroundColor(Color.parseColor("black"));
        linearTimePickerDialog.setPickerBackgroundColor(Color.parseColor("black"));
//        linearTimePickerDialog.setDialogBackgroundColor()



        linearTimePickerDialog.setButtonCallback(new LinearTimePickerDialog.ButtonCallback() {
            @Override
            public void onPositive(DialogInterface dialog, int hour, int minutes) {


                hourSelected = hour;
                minuteSelected = minutes;
                Calendar rightNow = Calendar.getInstance();
               boolean is24Hour = DateFormat.is24HourFormat(OnOfButton.this);
               minuteSelected = minuteSelected - rightNow.get(Calendar.MINUTE);
               if(is24Hour) {
                   hourSelected = hourSelected - rightNow.get(Calendar.HOUR_OF_DAY);
                   if(hourSelected < 0){

                       hourSelected = 24 + hourSelected;
                   }
               }else {
                   hourSelected = hourSelected - rightNow.get(Calendar.HOUR);
               }



                int miliSecond = minuteSelected*60000 + hourSelected*3600000;

                if(switchNumberForTimmer == 0){
                    timmerForOne(miliSecond);
                    isTimmerSet[0] = 1;
                    timmer1.setImageResource(R.drawable.timmer);

                }else if(switchNumberForTimmer == 1){

                    timmerForTwo(miliSecond);
                    timmer2.setImageResource(R.drawable.timmer);
                    isTimmerSet[1] = 1;


                }else if(switchNumberForTimmer == 2){

                    timmerForThree(miliSecond);
                    timmer3.setImageResource(R.drawable.timmer);
                    isTimmerSet[2] = 1;


                }else if(switchNumberForTimmer == 3){
                    timmerForFour(miliSecond);
                    timmer4.setImageResource(R.drawable.timmer);
                    isTimmerSet[3] = 1;


                }


            }

            @Override
            public void onNegative(DialogInterface dialog) {


            }
        });
        linearTimePickerDialog.build();
        iniProgressBar();

        lamp1 = findViewById(R.id.lamp1);
        lamp2= findViewById(R.id.lamp2);
        lamp3 = findViewById(R.id.lamp3);
        lamp4 = findViewById(R.id.lamp4);

        timmer1 = findViewById(R.id.timmer1);
        timmer2 = findViewById(R.id.timmer2);
        timmer3 = findViewById(R.id.timmer3);
        timmer4 = findViewById(R.id.timmer4);

        animation = AnimationUtils.loadAnimation(this,R.anim.blink);
        String labName = DataModel.labSelected;




        final Integer[] toggleArray = {0,0,0,0};

        referenceHashmap = FirebaseDatabase.getInstance().getReference().child(labName);
//
//        mapDevice.put("L1","0");
//        mapDevice.put("L2","0");
//        mapDevice.put("L3","0");
//        mapDevice.put("L4","0");
//        referenceHashmap.setValue(mapDevice);


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        referenceHashmap.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {



//                    mapDevice = (HashMap<String, String>) dataSnapshot.getValue();
                    String values = dataSnapshot.getValue().toString();
                    Log.i("StringValue",values);
                    String[] valuesArrary = values.split(",");
                    Log.i("ValuesArray",mapDevice.toString());

                    mapDevice.put("L1",valuesArrary[0]);
                    mapDevice.put("L2",valuesArrary[1]);
                    mapDevice.put("L3",valuesArrary[2]);
                    mapDevice.put("L4",valuesArrary[3]);
                    Log.i("MapDevice",mapDevice.toString());


                    if(mapDevice != null) {
                        if (mapDevice.get("L1").equals("1")) {
                            lamp1.setImageResource(R.drawable.bulbon);
                            toggleArray[0] = 1;
                        } else {
                            lamp1.setImageResource(R.drawable.bulboff);
                            toggleArray[0] = 0;
                        }
                        if (mapDevice.get("L2").equals("1")) {
                            lamp2.setImageResource(R.drawable.bulbon);
                            toggleArray[1] = 1;
                        } else {
                            lamp2.setImageResource(R.drawable.bulboff);
                            toggleArray[1] = 0;
                        }

                        if (mapDevice.get("L3").equals("1")) {
                            lamp3.setImageResource(R.drawable.bulbon);
                            toggleArray[2] = 1;
                        } else {
                            lamp3.setImageResource(R.drawable.bulboff);
                            toggleArray[2] = 0;
                        }

                        if (mapDevice.get("L4").equals("1")) {
                            lamp4.setImageResource(R.drawable.bulbon);
                            toggleArray[3] = 1;
                        } else {
                            lamp4.setImageResource(R.drawable.bulboff);
                            toggleArray[3] = 0;
                        }

                        progressDialog.dismiss();

                    }else{

                        progressDialog.dismiss();
                        Toast.makeText(OnOfButton.this,"Please check your Internet",Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){

                    Toast.makeText(OnOfButton.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(OnOfButton.this,databaseError.getMessage().toString(),Toast.LENGTH_SHORT).show();

            }
        });






        lamp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lamp1.startAnimation(animation);
                setValue("L1",0,progressBar1);


            }
        });

        lamp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setValue("L2",1,progressBar2);
                lamp2.startAnimation(animation);


            }
        });

        lamp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setValue("L3",2,progressBar3);
                lamp3.startAnimation(animation);


            }
        });

        lamp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setValue("L4",3,progressBar4);
                lamp4.startAnimation(animation);


            }

        });


        timmer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timmer1.startAnimation(animation);


                if(isTimmerSet[0] == 0){
                    switchNumberForTimmer = 0;
                    linearTimePickerDialog.build().show();


                }else{


                    stopTimmer(cTimmer1,0,timmer1,time1);

                }

            }
        });



        timmer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timmer2.startAnimation(animation);
                if(isTimmerSet[1] == 0){
                    switchNumberForTimmer = 1;
                    linearTimePickerDialog.build().show();

                }else{
                    stopTimmer(cTimmer2,1,timmer2,time2);




                }

            }
        });


        timmer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timmer3.startAnimation(animation);
                if(isTimmerSet[2] == 0){
                    switchNumberForTimmer = 2;
                    linearTimePickerDialog.build().show();

                }else{

                    stopTimmer(cTimmer3,2,timmer3,time3);


                }

            }
        });


        timmer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timmer4.startAnimation(animation);
                if(isTimmerSet[3] == 0){
                    switchNumberForTimmer = 3;
                    linearTimePickerDialog.build().show();

                }else{

                    stopTimmer(cTimmer4,3,timmer4,time4);



                }

            }
        });












    }














    //Method to initializing of progress bar
    public void iniProgressBar(){

    progressBar1 =findViewById(R.id.spin_kit);
    progressBar2 = findViewById(R.id.spin_kit2);
    progressBar3 = findViewById(R.id.spin_kit3);
    progressBar4 = findViewById(R.id.spin_kit4);

        Sprite pro = new Circle();
        Sprite pro1 = new Circle();
        Sprite pro2 = new Circle();
        Sprite pro3 = new Circle();


        progressBar1.setIndeterminateDrawable(pro);
        progressBar2.setIndeterminateDrawable(pro1);
        progressBar3.setIndeterminateDrawable(pro2);
        progressBar4.setIndeterminateDrawable(pro3);


    }



    public void setValue(String id, final int switchNo, final ProgressBar progressBar){
        if(toggleArray[switchNo] == 1){
            String toSet = "";
            mapDevice.put(id,"0");
            toSet = toSet + mapDevice.get("L1") +","+ mapDevice.get("L2") +","+ mapDevice.get("L3") + ","+ mapDevice.get("L4");


            progressBar.setVisibility(View.VISIBLE);
            referenceHashmap.setValue(toSet).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.INVISIBLE);
//                        Toast.makeText(OnOfButton.this,"Succesful",Toast.LENGTH_SHORT).show();
                        toggleArray[switchNo] = 0;
                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
//                        Toast.makeText(OnOfButton.this,"Failed",Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }else {


            mapDevice.put(id,"1");
            String toSet = "";
            toSet = toSet + mapDevice.get("L1") +","+ mapDevice.get("L2") +","+ mapDevice.get("L3") + ","+ mapDevice.get("L4");

            progressBar.setVisibility(View.VISIBLE);
            referenceHashmap.setValue(toSet).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.INVISIBLE);
                        toggleArray[switchNo] = 1;
//                        Toast.makeText(OnOfButton.this,"Succesful",Toast.LENGTH_SHORT).show();
                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
//                        Toast.makeText(OnOfButton.this,"Failed",Toast.LENGTH_SHORT).show();

                    }
                }
            });



        }




    }



    //Method to set Timmer
    public void setTimmer(View view){








    }


//    public void implementTimmer(int mili, final int timmer){
//
//
//        new CountDownTimer(mili, 1000) {
//            public void onTick(long millisUntilFinished) {
//                secondRemaning = millisUntilFinished/1000;
//
//                assignValue(timmer,secondRemaning);
//            }
//
//            public void onFinish() {
//
//                setValue(labSelectedForTimmer,switchNumberForTimmer,progressBarForTimmer);
//                isTimmerSet[switchNumberForTimmer] = 0;
//                if(switchNumberForTimmer == 0){
//                    timmer1.setImageResource(R.drawable.watch);
//                }else if(switchNumberForTimmer == 1){
//                    timmer2.setImageResource(R.drawable.watch);
//                }else if(switchNumberForTimmer== 2){
//                    timmer3.setImageResource(R.drawable.watch);
//                }else if(switchNumberForTimmer == 3) {
//                    timmer4.setImageResource(R.drawable.watch);
//
//                }
//
//                Toast.makeText(OnOfButton.this,String.valueOf(switchNumberForTimmer),Toast.LENGTH_SHORT).show();
//
//            }
//        }.start();
//
//    }





    public void timmerForOne(long mili){

        cTimmer1 = new CountDownTimer(mili,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                time1 = millisUntilFinished/1000;

            }

            @Override
            public void onFinish() {

                setValue("L1",0,progressBar1);
                isTimmerSet[0] = 0;
                timmer1.setImageResource(R.drawable.watch);


            }
        }.start();




    }



    public void timmerForTwo(long mili){

        cTimmer2 = new CountDownTimer(mili,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                time2 = millisUntilFinished/1000;
            }

            @Override
            public void onFinish() {

                setValue("L2",1,progressBar2);
                isTimmerSet[1] = 0;
                timmer2.setImageResource(R.drawable.watch);

            }
        }.start();


    }



    public void timmerForThree(long mili){

        cTimmer3 = new CountDownTimer(mili,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                time3 = millisUntilFinished/1000;
            }

            @Override
            public void onFinish() {

                setValue("L3",2,progressBar3);
                isTimmerSet[2] = 0;
                timmer3.setImageResource(R.drawable.watch);


            }
        }.start();


    }



    public void timmerForFour(long mili){

        cTimmer4 = new CountDownTimer(mili,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                time4 = millisUntilFinished/1000;
            }

            @Override
            public void onFinish() {

                setValue("L4",3,progressBar4);
                isTimmerSet[3] = 0;
                timmer4.setImageResource(R.drawable.watch);


            }
        }.start();


    }




    public void stopTimmer(final CountDownTimer countDownTimer, final int switchNumber, final ImageView timeerImage ,long timeRemaning){

        TextView textView = new TextView(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to STOP Timmer");
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        textView.setText("        Time remaning " + TimeUnit.SECONDS.toMinutes(timeRemaning) + "Minutes");
        linearLayout.addView(textView);
        builder.setView(linearLayout);
        builder.setPositiveButton("STOP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                countDownTimer.cancel();
                isTimmerSet[switchNumber] = 0;
                timeerImage.setImageResource(R.drawable.watch);
            }
        });


        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });


AlertDialog alertDialog = builder.create();
alertDialog.show();

    }



}
