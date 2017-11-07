package com.example.jayashankar.rentataxi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AppHomePage extends AppCompatActivity {
    Button login, reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home_page);
        login = (Button) findViewById(R.id.login);
        reg = (Button) findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AppHomePage.this, Login.class);
                startActivity(intent);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AppHomePage.this, registration.class);
                startActivity(intent);
            }
        });
    }
}
