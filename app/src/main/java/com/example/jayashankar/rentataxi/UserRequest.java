package com.example.jayashankar.rentataxi;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.jayashankar.rentataxi.ADapter.AdapterViewRequest;
import com.example.jayashankar.rentataxi.Data.dataList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserRequest extends AppCompatActivity {
    RecyclerView user_request_rec;
    String[] from, to, seats, date, name, rqid;
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request);

        user_request_rec = (RecyclerView) findViewById(R.id.user_request_rec);
        user_request_rec.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences shPreferences = getSharedPreferences("login", MODE_PRIVATE);
        userId = shPreferences.getString("usid", "0");

        getRequest get = new getRequest();
        get.execute(userId);


    }

    private class getRequest extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("myrequests");
            caller.addProperty("userId", strings[0]);
            caller.callWebService();
            String res = caller.getResponse();
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(UserRequest.this, s + "", Toast.LENGTH_SHORT).show();

            try {
                JSONArray ar = new JSONArray(s);
                from = new String[ar.length()];
                to = new String[ar.length()];
                name = new String[ar.length()];
                date = new String[ar.length()];
                seats = new String[ar.length()];
                rqid = new String[ar.length()];


                for (int i = 0; i < ar.length(); i++) {
                    JSONObject ob = ar.getJSONObject(i);
                    from[i] = ob.getString("tfrom");
                    to[i] = ob.getString("tto");
                    seats[i] = ob.getString("seats");
                    date[i] = ob.getString("tdate");
                    name[i] = ob.getString("fname");
                    rqid[i] = ob.getString("reqid");

                    AdapterViewRequest adp = new AdapterViewRequest(UserRequest.this, dataList.getViewrequest(from, to, date, rqid, seats));
                    user_request_rec.setAdapter(adp);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
