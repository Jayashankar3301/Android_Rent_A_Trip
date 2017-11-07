package com.example.jayashankar.rentataxi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registration extends AppCompatActivity {
    EditText fname, lname, uname, pswd, pswd2, email, phone;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.editText2);
        uname = (EditText) findViewById(R.id.editText3);
        pswd = (EditText) findViewById(R.id.editText4);
        pswd2 = (EditText) findViewById(R.id.editText5);
        email = (EditText) findViewById(R.id.editText6);
        phone = (EditText) findViewById(R.id.editText7);
        button = (Button) findViewById(R.id.button);

        String firstname = fname.getText().toString().trim();
        String lastname = lname.getText().toString().trim();
        String username = uname.getText().toString().trim();
        String paswd = pswd.getText().toString().trim();
        String emailid = email.getText().toString().trim();
        String phoneno = phone.getText().toString().trim();

        register l = new register();
        l.execute(firstname, lastname, username, paswd, emailid, phoneno);


    }

    private class register extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("register");
            caller.addProperty("fname", strings[0]);
            caller.addProperty("lname", strings[1]);
            caller.addProperty("username", strings[2]);
            caller.addProperty("password", strings[3]);
            caller.addProperty("email", strings[4]);
            caller.addProperty("phone", strings[5]);

            caller.callWebService();
            String res = caller.getResponse();
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(registration.this, s + "", Toast.LENGTH_SHORT).show();
        }
    }
}
