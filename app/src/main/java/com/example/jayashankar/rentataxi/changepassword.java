package com.example.jayashankar.rentataxi;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class changepassword extends AppCompatActivity {
    Button btn_changepassword;
    EditText current_password, re_new_password, new_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        btn_changepassword = (Button) findViewById(R.id.btn_changepassword);
        current_password = (EditText) findViewById(R.id.current_password);
        new_password = (EditText) findViewById(R.id.new_password);
        re_new_password = (EditText) findViewById(R.id.re_new_password);
        btn_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String current_pwd = current_password.getText().toString().trim();
                String new_pwd = new_password.getText().toString().trim();
                String re_pwd = re_new_password.getText().toString().trim();
                SharedPreferences sh = getSharedPreferences("login", MODE_PRIVATE);
                String id = sh.getString("uid", "null");
                if (new_pwd.equals(re_pwd)) {

                    Changepassword cp = new Changepassword();
                    cp.execute(id, current_pwd, new_pwd);
                } else {
                    Toast.makeText(changepassword.this, "Passwords are not same", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private class Changepassword extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("changepassword");

            caller.addProperty("id", strings[0]);
            caller.addProperty("current_password", strings[1]);
            caller.addProperty("new_password", strings[1]);
            caller.callWebService();
            String res = caller.getResponse();
            return res;
        }
    }
}
