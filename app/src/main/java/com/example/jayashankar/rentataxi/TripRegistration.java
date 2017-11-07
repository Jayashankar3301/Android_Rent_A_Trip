package com.example.jayashankar.rentataxi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class TripRegistration extends AppCompatActivity {

    static EditText date;
    EditText autocomplete1;
    EditText autocomplete;
    EditText time;
    Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_registration);

        AutoCompleteTextView autocompleteView = (AutoCompleteTextView) findViewById(R.id.autocomplete);

        autocomplete1 = (EditText) findViewById(R.id.autocomplete1);
        autocomplete = (EditText) findViewById(R.id.autocomplete);
        date = (EditText) findViewById(R.id.date);
        time = (EditText) findViewById(R.id.time);
        reg = (Button) findViewById(R.id.reg);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment f2 = new DatePickerFragment2();
                f2.show(getFragmentManager(), "datePicker");
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {

                                   @Override
                                   public void onClick(View view) {
                                       String tfrom = autocomplete1.getText().toString().trim();
                                       String tto = autocomplete.getText().toString().trim();
                                       String tdate = date.getText().toString().trim();
                                       String ttime = time.getText().toString().trim();

                                       trip_registration t = new trip_registration();
                                       t.execute(tfrom, tto, tdate, ttime, "1");
                                   }
                               }
        );

        AutoCompleteTextView autocompleteView1 = (AutoCompleteTextView) findViewById(R.id.autocomplete1);
        autocompleteView1.setAdapter(new PlacesAutoCompleteAdapter(TripRegistration.this, R.layout.autocomplete_list_item));
        autocompleteView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                Toast.makeText(TripRegistration.this, description, Toast.LENGTH_SHORT).show();
            }
        });


        autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(TripRegistration.this, R.layout.autocomplete_list_item));
        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                Toast.makeText(TripRegistration.this, description, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class DatePickerFragment2 extends DialogFragment

            implements DatePickerDialog.OnDateSetListener {

        public EditText editText;
        DatePicker dpResult;

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            date.setText(String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(day));

        }
    }

    private class trip_registration extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("tripreg");
            caller.addProperty("tfrom", strings[0]);
            caller.addProperty("tto", strings[1]);
            caller.addProperty("tdate", strings[2]);
            caller.addProperty("ttime", strings[3]);
            caller.addProperty("vid", strings[4]);
            caller.callWebService();
            String res = caller.getResponse();
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(TripRegistration.this, s + "", Toast.LENGTH_SHORT).show();
            String aa[] = s.split(":");

            if (aa[0].equals("true")) {
                Intent i = new Intent(TripRegistration.this, AddCheckPoints.class).putExtra("tid", aa[1]);
                startActivity(i);
            }


        }

    }
}
