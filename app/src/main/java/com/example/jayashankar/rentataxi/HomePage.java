package com.example.jayashankar.rentataxi;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.jayashankar.rentataxi.ADapter.tripListAdapter;
import com.example.jayashankar.rentataxi.Data.dataList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomePage extends AppCompatActivity {
    String frm[], to[], date[], time[], id[];
    String usid = "";
    RecyclerView home_rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        home_rec = (RecyclerView) findViewById(R.id.home_rec);
        home_rec.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences shPreferences = getSharedPreferences("login", MODE_PRIVATE);
        usid = shPreferences.getString("usid", "0");

        tripList t = new tripList();
        t.execute(usid);
    }

    private class tripList extends AsyncTask<String, String, String> {
        int len = 0;

        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("triplist");
            caller.addProperty("uid", strings[0]);
            caller.callWebService();
            String res = caller.getResponse();
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(HomePage.this, s + "", Toast.LENGTH_SHORT).show();
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(s);
                len = jsonArray.length();
                if (len > 0) {
                    frm = new String[jsonArray.length()];
                    to = new String[jsonArray.length()];
                    date = new String[jsonArray.length()];
                    time = new String[jsonArray.length()];
                    id = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject ob = jsonArray.getJSONObject(i);
                        frm[i] = ob.getString("strip_from");
                        to[i] = ob.getString("strip_to");
                        date[i] = ob.getString("strip_date");
                        time[i] = ob.getString("strip_time");
                        id[i] = ob.getString("strip_id");
                    }
                    tripListAdapter ADP = new tripListAdapter(HomePage.this, dataList.getData(id, frm, to, date, time));
                    home_rec.setAdapter(ADP);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}
