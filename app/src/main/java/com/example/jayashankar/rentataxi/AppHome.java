package com.example.jayashankar.rentataxi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AppHome extends AppCompatActivity {
    Button submittrip, searchtrip, addvehicle, myrides, myrequests, requeststoyou, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_homr);

        submittrip = (Button) findViewById(R.id.submittrip);
        searchtrip = (Button) findViewById(R.id.searchtrip);
        addvehicle = (Button) findViewById(R.id.addvehicle);
        myrides = (Button) findViewById(R.id.myrides);
        myrequests = (Button) findViewById(R.id.myrequests);
        requeststoyou = (Button) findViewById(R.id.requeststoyou);
        logout = (Button) findViewById(R.id.logout);


        submittrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(AppHome.this, TripRegistration.class);
                startActivity(I);
            }
        });
        searchtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(AppHome.this, SearchTrip.class);
                startActivity(I);
            }
        });
        addvehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(AppHome.this, AddVehicle.class);
                startActivity(I);
            }
        });
        myrides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(AppHome.this, HomePage.class);
                startActivity(I);
            }
        });
        myrequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(AppHome.this, UserRequest.class);
                startActivity(I);
            }
        });
        requeststoyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(AppHome.this, DriverViewRequest.class);
                startActivity(I);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences shPreferences = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor ed = shPreferences.edit();
                ed.putString("usid", "null");
                ed.commit();

                Intent I = new Intent(AppHome.this, AppHomePage.class);
                startActivity(I);
            }
        });


    }
}
