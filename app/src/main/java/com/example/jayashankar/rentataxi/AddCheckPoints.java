package com.example.jayashankar.rentataxi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class AddCheckPoints extends AppCompatActivity {
    String tripId = "", location = "";
    Button btn_submit, btn_finish;
    AutoCompleteTextView autocompleteViewfrom;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_check_points);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddCheckPoints.this, AppHome.class);
                startActivity(i);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add a = new Add();
                a.execute(location, i + "", tripId);
                i++;

            }
        });


        tripId = getIntent().getExtras().getString("tid");
        autocompleteViewfrom = (AutoCompleteTextView) findViewById(R.id.autocompleteText);
        autocompleteViewfrom.setAdapter(new PlacesAutoCompleteAdapter(AddCheckPoints.this, R.layout.autocomplete_list_item));
        autocompleteViewfrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                location = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(AddCheckPoints.this, location, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private class Add extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("addCheckpoint");
            caller.addProperty("check_location", strings[0]);
            caller.addProperty("check_priority", strings[1]);
            caller.addProperty("trip_id", strings[2]);


            caller.callWebService();
            String res = caller.getResponse();
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("true")) {
                autocompleteViewfrom.setText("");
                Toast.makeText(AddCheckPoints.this, "Succses", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddCheckPoints.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
