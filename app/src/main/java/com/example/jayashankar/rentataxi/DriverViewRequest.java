package com.example.jayashankar.rentataxi;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.jayashankar.rentataxi.ADapter.AdapterDriverViewRequest;
import com.example.jayashankar.rentataxi.Data.dataList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DriverViewRequest extends AppCompatActivity {
    String[] from, to, tfrom, tto, username, phone, date, reqId;
    RecyclerView driver_view_req_rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_view_request);
        driver_view_req_rec = (RecyclerView) findViewById(R.id.driver_view_req_rec);
        driver_view_req_rec.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences shPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String id = shPreferences.getString("usid", "null");
        ViewRequest vr = new ViewRequest();
        vr.execute(id);


    }

    private class ViewRequest extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("DriverViewRequest");
            caller.addProperty("userId", strings[0]);
            caller.callWebService();
            String res = caller.getResponse();
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(DriverViewRequest.this, s + "", Toast.LENGTH_SHORT).show();
            try {
                JSONArray ar = new JSONArray(s);
                from = new String[ar.length()];
                to = new String[ar.length()];
                phone = new String[ar.length()];
                username = new String[ar.length()];
                date = new String[ar.length()];
                reqId = new String[ar.length()];
                tfrom = new String[ar.length()];
                tto = new String[ar.length()];

                for (int i = 0; i < ar.length(); i++) {
                    JSONObject ob = ar.getJSONObject(i);
                    from[i] = ob.getString("tfrom");
                    date[i] = ob.getString("tdate");
                    to[i] = ob.getString("tto");
                    phone[i] = ob.getString("phone");
                    tfrom[i] = ob.getString("from");
                    tto[i] = ob.getString("to");
                    reqId[i] = ob.getString("reqId");
                    username[i] = ob.getString("user");
                }
                AdapterDriverViewRequest adp = new AdapterDriverViewRequest(DriverViewRequest.this, dataList.getDriverrequest(from, to, tfrom, tto, username, phone, date, reqId));
                driver_view_req_rec.setAdapter(adp);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
