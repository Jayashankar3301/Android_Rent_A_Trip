package com.example.jayashankar.rentataxi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jayashankar.rentataxi.ADapter.searchResultDetails;
import com.example.jayashankar.rentataxi.Data.dataList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchTripDetails extends AppCompatActivity {
    String[] check_location;
    RecyclerView search_details_rec;
    EditText search_trip_det_nouser;
    String id = "", from = "", to = "", userId = "";
    Button search_trip_det_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip_details);


        search_trip_det_nouser = (EditText) findViewById(R.id.search_trip_det_nouser);
        id = getIntent().getExtras().getString("tid");
        SharedPreferences shPreferences = getSharedPreferences("login", MODE_PRIVATE);
        userId = shPreferences.getString("usid", "0");

        SharedPreferences sharedPreferences = getSharedPreferences("myTrip", MODE_PRIVATE);
        from = sharedPreferences.getString("fromLoc", "0");
        to = sharedPreferences.getString("toLoc", "0");


        search_trip_det_submit = (Button) findViewById(R.id.search_trip_det_submit);
        search_details_rec = (RecyclerView) findViewById(R.id.search_details_rec);
        search_details_rec.setLayoutManager(new LinearLayoutManager(this));

        allCheckPoint all = new allCheckPoint();
        all.execute(id);

        search_trip_det_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                trip_requestt tr = new trip_requestt();
                tr.execute(id, userId, search_trip_det_nouser.getText().toString(), from, to);

            }
        });


    }

    private class allCheckPoint extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("AllcheckPoints");
            caller.addProperty("tripId", strings[0]);

            caller.callWebService();

            String res = caller.getResponse();
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(SearchTripDetails.this, s + "", Toast.LENGTH_SHORT).show();
            try {
                JSONArray ar = new JSONArray(s);
                check_location = new String[ar.length()];
                if (ar.length() > 0) {
                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject ob = ar.getJSONObject(i);
                        check_location[i] = ob.getString("check_location");

                    }
                    searchResultDetails adp = new searchResultDetails(SearchTripDetails.this, dataList.getsearchDataDetails(check_location));
                    search_details_rec.setAdapter(adp);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private class trip_requestt extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("trip_request");
            caller.addProperty("trip_id", strings[0]);
            caller.addProperty("user_id", strings[1]);
            caller.addProperty("noofseats", strings[2]);
            caller.addProperty("tripfrom", strings[3]);
            caller.addProperty("tripto", strings[4]);
            caller.callWebService();
            String res = caller.getResponse();
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(SearchTripDetails.this, s + "", Toast.LENGTH_SHORT).show();
            String st[] = s.split(":");
            if (st[0].equals("true")) {
                Toast.makeText(SearchTripDetails.this, st[1] + "", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SearchTripDetails.this, AppHome.class);
                startActivity(i);
            } else {
                Toast.makeText(SearchTripDetails.this, st[1] + "", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
