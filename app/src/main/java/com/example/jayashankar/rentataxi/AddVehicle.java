package com.example.jayashankar.rentataxi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddVehicle extends AppCompatActivity {
    String regno, lic;
    EditText addvehicle_editText_regnumber, addvehicle_editText_color, addvehicle_editText_lisence;
    String[] categoryid, categoryname, typeId, typeName, typeSeats;
    Button btnSubmit;
    String type_id = "";
    Spinner spinnercategory, spinnertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        addvehicle_editText_regnumber = (EditText) findViewById(R.id.addvehicle_editText_regnumber);
        addvehicle_editText_color = (EditText) findViewById(R.id.addvehicle_editText_color);
        addvehicle_editText_lisence = (EditText) findViewById(R.id.addvehicle_editText_lisence);

        spinnercategory = (Spinner) findViewById(R.id.spinnercategory);
        btnSubmit = (Button) findViewById(R.id.advehicle_btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences shPreferences = getSharedPreferences("login", MODE_PRIVATE);
                String lisence = addvehicle_editText_lisence.getText().toString().trim();
                String color1 = addvehicle_editText_color.getText().toString().trim();
                String regno = addvehicle_editText_regnumber.getText().toString().trim();

                addvehicle av = new addvehicle();
                av.execute(regno, lisence, shPreferences.getString("usid", "0"), type_id, color1);
            }
        });


        select_category sc = new select_category();
        sc.execute();
        spinnercategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AddVehicle.this, categoryid[i] + "", Toast.LENGTH_SHORT).show();
                select_type st = new select_type();

                st.execute(categoryid[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnertype = (Spinner) findViewById(R.id.spinnertype);
        spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type_id = typeId[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnertype = (Spinner) findViewById(R.id.spinnertype);


    }

    private class addvehicle extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("addvehicle");
            caller.addProperty("vehicle_reg", strings[0]);
            caller.addProperty("license_number", strings[1]);
            caller.addProperty("userid", strings[2]);
            caller.addProperty("typeid", strings[3]);
            caller.addProperty("color", strings[4]);

            caller.callWebService();
            String res = caller.getResponse();
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("Registered")) {
                Toast.makeText(AddVehicle.this, "Registration Completed...!!!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(AddVehicle.this, AppHome.class);
                startActivity(i);


            }
        }
    }

    private class select_category extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("select_category");
            caller.callWebService();
            String res = caller.getResponse();
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray ar = new JSONArray(s);
                categoryid = new String[ar.length()];
                categoryname = new String[ar.length()];
                for (int i = 0; i < ar.length(); i++) {
                    JSONObject ob = ar.getJSONObject(i);
                    categoryid[i] = ob.getString("categoryID");
                    categoryname[i] = ob.getString("categoryName");
                }

                ArrayAdapter adapter = new ArrayAdapter(AddVehicle.this, android.R.layout.simple_spinner_dropdown_item, categoryname);
                //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnercategory.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Toast.makeText(AddVehicle.this, s + "", Toast.LENGTH_SHORT).show();
        }
    }

    private class select_type extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("select_type");
            caller.addProperty("category_id", strings[0]);
            caller.callWebService();
            String res = caller.getResponse();
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray ar = new JSONArray(s);
                typeName = new String[ar.length()];
                typeId = new String[ar.length()];
                typeSeats = new String[ar.length()];
                for (int i = 0; i < ar.length(); i++) {
                    JSONObject ob = ar.getJSONObject(i);
                    typeName[i] = ob.getString("type_name") + " " + ob.getString("type_no_seats");
                    typeId[i] = ob.getString("type_id");
                }

                ArrayAdapter adapter = new ArrayAdapter(AddVehicle.this, android.R.layout.simple_spinner_dropdown_item, typeName);
                //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnertype.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Toast.makeText(AddVehicle.this, s + "", Toast.LENGTH_SHORT).show();
        }
    }
}
