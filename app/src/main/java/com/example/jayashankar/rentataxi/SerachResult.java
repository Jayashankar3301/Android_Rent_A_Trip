package com.example.jayashankar.rentataxi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.jayashankar.rentataxi.ADapter.searchResult;
import com.example.jayashankar.rentataxi.Data.dataList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SerachResult extends AppCompatActivity {
    RecyclerView search_result_rec;
    String[] tripid, strip_from, strip_to, strip_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_result);
        search_result_rec = (RecyclerView) findViewById(R.id.search_result_rec);
        search_result_rec.setLayoutManager(new LinearLayoutManager(this));


        Bundle bun = getIntent().getExtras();
        String from = bun.getString("from");
        String to = bun.getString("to");
        String date = bun.getString("date");
        search s = new search();
        s.execute(from, to, date);


    }

    private class search extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("searchtrip");
            caller.addProperty("from", strings[0]);
            caller.addProperty("to", strings[1]);
            caller.addProperty("date", strings[2]);
            caller.callWebService();

            String res = caller.getResponse();
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(SerachResult.this, s + "", Toast.LENGTH_SHORT).show();
            try {
                JSONArray ar = new JSONArray(s);
                tripid = new String[ar.length()];
                strip_from = new String[ar.length()];

                strip_to = new String[ar.length()];
                strip_time = new String[ar.length()];

                for (int i = 0; i < ar.length(); i++) {
                    JSONObject ob = ar.getJSONObject(i);
                    tripid[i] = ob.getString("tripid");
                    strip_to[i] = ob.getString("strip_to");
                    strip_time[i] = ob.getString("strip_time");

                    strip_from[i] = ob.getString("strip_from");
                }

                searchResult adp = new searchResult(SerachResult.this, dataList.getsearchData(tripid, strip_from, strip_to, strip_time));
                search_result_rec.setAdapter(adp);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
