package com.example.jayashankar.rentataxi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText lg_uname;
    EditText lg_pwd;
    Button lg_loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        SharedPreferences shPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String id = shPreferences.getString("usid", "null");
        if (!id.equals("null")) {
            Intent i = new Intent(Login.this, AppHome.class);
            startActivity(i);
        }


        loginservice l = new loginservice();
        l.execute("ggg");

        lg_uname = (EditText) findViewById(R.id.lg_uname);
        lg_pwd = (EditText) findViewById(R.id.lg_pwd);
        lg_loginbtn = (Button) findViewById(R.id.lg_loginbtn);
        lg_loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Hello", Toast.LENGTH_SHORT).show();
                String uname = lg_uname.getText().toString().trim();
                String pwd = lg_pwd.getText().toString().trim();

                if ((!uname.equals("")) && (!pwd.equals(""))) {

                    loginServices l = new loginServices();
                    l.execute(uname, pwd);
                } else {
                    Toast.makeText(Login.this, "enter", Toast.LENGTH_SHORT).show();
                }
//                loginservice1 l2=new loginservice1();
//                l2.execute(uname,pwd);
            }
        });
    }

    private class loginservice extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("hello");
            caller.addProperty("name", strings[0]);
            caller.callWebService();
            String res = caller.getResponse();
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Login.this, s + "", Toast.LENGTH_SHORT).show();
        }

    }


    private class loginservice1 extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            //caller.setSoapObject();
            return null;
        }
    }

    private class loginServices extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("login");
            caller.addProperty("username", strings[0]);
            caller.addProperty("password", strings[1]);
            caller.callWebService();
            String res = caller.getResponse();
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Login.this, s + "", Toast.LENGTH_SHORT).show();

            String status[] = s.split(":");
            if (status[0].equals("true")) {
                SharedPreferences shPreferences = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor edt = shPreferences.edit();
                edt.putString("usid", status[1]);
                edt.commit();

                Intent i = new Intent(Login.this, AppHome.class);
                startActivity(i);
            } else {
                Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }


        }
    }
}

