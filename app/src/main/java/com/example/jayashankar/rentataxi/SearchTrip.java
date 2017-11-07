package com.example.jayashankar.rentataxi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class SearchTrip extends AppCompatActivity {
    static EditText date;
    String from = "", to = "";
    Button search_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip);
        date = (EditText) findViewById(R.id.serach_date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment f2 = new DatePickerFragment2();
                f2.show(getFragmentManager(), "datePicker");
            }
        });
        search_submit = (Button) findViewById(R.id.search_submit);


        AutoCompleteTextView autocompleteViewfrom = (AutoCompleteTextView) findViewById(R.id.autocompletefrom);

        autocompleteViewfrom.setAdapter(new PlacesAutoCompleteAdapter(SearchTrip.this, R.layout.autocomplete_list_item));
        autocompleteViewfrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                from = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(SearchTrip.this, from, Toast.LENGTH_SHORT).show();
            }
        });

        AutoCompleteTextView autocompleteViewto = (AutoCompleteTextView) findViewById(R.id.autocompleteto);

        autocompleteViewto.setAdapter(new PlacesAutoCompleteAdapter(SearchTrip.this, R.layout.autocomplete_list_item));
        autocompleteViewto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                to = (String) adapterView.getItemAtPosition(i);
            }
        });


        search_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchTrip.this, SerachResult.class);


                SharedPreferences sharedPreferences = getSharedPreferences("myTrip", MODE_PRIVATE);
                SharedPreferences.Editor edt = sharedPreferences.edit();
                edt.putString("fromLoc", from);
                edt.putString("toLoc", to);
                edt.commit();

                Bundle bun = new Bundle();
                bun.putString("from", from);
                bun.putString("to", to);
                bun.putString("date", date.getText().toString());
                i.putExtras(bun);


                startActivity(i);


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

}
