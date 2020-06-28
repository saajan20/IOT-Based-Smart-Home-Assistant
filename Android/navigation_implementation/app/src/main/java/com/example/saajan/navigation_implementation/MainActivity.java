package com.example.saajan.navigation_implementation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.saajan.navigation_implementation.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    BottomNavigationView bottom;
    private ImageView image1,image2;
    private DatabaseReference ref,warning;
    public static String AlertTopic="Feedback";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom=findViewById(R.id.menu_bar);
        notificationManager = NotificationManagerCompat.from(this);
        bottom.setOnNavigationItemSelectedListener(nav_listener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        warning = FirebaseDatabase.getInstance().getReference("Warning");
        firealert();
        intrusion();
        gas();
        temperature();
        airquality();
    }

    private void airquality() {

        ref = FirebaseDatabase.getInstance().getReference("LiveMonitoring/Air Quality");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data=dataSnapshot.getValue(String.class);
                int temp = Integer.parseInt(data);
                int limit = 500;
                if(temp>limit)
                {
                    notification("Don’t let our future go up in the smoke","Air Quality is higher than the limit value, Stay Safe","Air Quality",5);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void temperature() {

        ref = FirebaseDatabase.getInstance().getReference("LiveMonitoring/Temperature");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data=dataSnapshot.getValue(String.class);
                Double temp = Double.parseDouble(data);
                Double limit = 40.00d;
                if(temp>limit)
                {
                    notification("I’m sweating in spots I didn’t know I had","Temperature is higher than the limit value, Stay Cool","Temperature",4);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void notification(String title, String message, String type,int id) {

    Map<String,Object> insertvalues=new HashMap<>();
    insertvalues.put("Type",type);
    String date = (new Date()).toString();
    date = date.substring(4,16);
    insertvalues.put("Time",date);
    String key=warning.push().getKey();
    warning.child(key).setValue(insertvalues);

    Intent activityintent = new Intent(MainActivity.this,login.class);
    PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,0,activityintent,0);

    Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1_ID)
            .setSmallIcon(R.drawable.smart_home)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(Color.BLACK)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .build();

    notificationManager.notify(id, notification);


}

    private void gas() {

        ref = FirebaseDatabase.getInstance().getReference("LiveMonitoring/Gas");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data=dataSnapshot.getValue(String.class);
                if(data.equals("1"))
                {
                    notification_with_call("I want to grow my own food but I can't find Butter Chicken seeds","A Gas leakage has been detected, please don't waste gas","Gas",3);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void intrusion() {

        ref = FirebaseDatabase.getInstance().getReference("LiveMonitoring/Intrusion");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data=dataSnapshot.getValue(String.class);
                if(data.equals("1"))
                {
                    notification_with_call("Intrusion Alert","A Intrusion has been detected","Intrusion",2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firealert() {

    ref = FirebaseDatabase.getInstance().getReference("LiveMonitoring/Fire");
    ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            String data=dataSnapshot.getValue(String.class);
            if(data.equals("0"))
            {

                notification_with_call("Fire Alert","A fire has been detected","Fire",1);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    }

    public void notification_with_call(String title, String message, String type,int id){
    Map<String,Object> insertvalues=new HashMap<>();
    insertvalues.put("Type",type);
    String date = (new Date()).toString();
    date = date.substring(4,16);
    insertvalues.put("Time",date);
    String key=warning.push().getKey();
    warning.child(key).setValue(insertvalues);

    Intent activityintent = new Intent(MainActivity.this,login.class);
    PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,0,activityintent,0);

    Intent  callIntent = new Intent(Intent.ACTION_CALL);
    callIntent.setData(Uri.parse("tel:9881766466"));

    PendingIntent pcallIntent = PendingIntent.getActivity(MainActivity.this,0,callIntent,0);

    Intent  EmergencyIntent = new Intent(Intent.ACTION_CALL);
    EmergencyIntent.setData(Uri.parse("tel:112"));

    PendingIntent ecallIntent = PendingIntent.getActivity(MainActivity.this,0,EmergencyIntent,0);


    Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1_ID)
            .setSmallIcon(R.drawable.smart_home)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(Color.BLACK)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentIntent(contentIntent)
            .addAction(R.mipmap.ic_launcher,"Call Neighbour",pcallIntent)
            .addAction(R.mipmap.ic_launcher,"Call Emergency",ecallIntent)
            .setAutoCancel(true)
            .build();

    notificationManager.notify(id, notification);
}




    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Confirm Exit..!!");

        alertDialogBuilder.setIcon(R.drawable.exit);

        alertDialogBuilder.setMessage("Are you sure you want to exit ?");

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }
        });


        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alert= alertDialogBuilder.create();
        alert.show();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener nav_listener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selecteditem = null;
            switch (item.getItemId()){
                case R.id.nav_home:         selecteditem= new HomeFragment();
                                          break;
                case R.id.nav_appliance:     selecteditem= new ApplianceFragment();
                                          break;
                case R.id.nav_warning:      selecteditem= new WarningFragment();
                                          break;
                case R.id.nav_profile:      selecteditem = new ProfileFragment();
                                          break;
            }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selecteditem).commit();
            return  true;
        }
    };

    public void logout(View view) {

        FirebaseAuth.getInstance().signOut();
        Intent i= new Intent(MainActivity.this,login.class);
        startActivity(i);

    }

    public void feedback(View view) {
        AlertTopic = "Feedback";
        ExampleDialog example = new ExampleDialog();
        example.show(getSupportFragmentManager(),"example dialog");
    }

    public void request(View view) {

        AlertTopic = "Request";
        ExampleDialog example = new ExampleDialog();
        example.show(getSupportFragmentManager(),"example dialog");
    }

    public void problem(View view) {
        AlertTopic = "Problem";
        ExampleDialog example = new ExampleDialog();
        example.show(getSupportFragmentManager(),"example dialog");
    }
}
