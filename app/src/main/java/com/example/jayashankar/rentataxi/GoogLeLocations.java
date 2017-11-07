package com.example.jayashankar.rentataxi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogLeLocations extends FragmentActivity implements OnMapReadyCallback {
    String lat[], log[];
    String tid;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goog_le_locations);
        tid = getIntent().getStringExtra("tid");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        getLat g = new getLat();
        g.execute(tid);

        mMap = googleMap;

        // Add a marker in Sydney and move the camera

    }

    private class getLat extends AsyncTask<String, String, String> {
        int len = 0;

        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller caller = new WebServiceCaller();
            caller.setSoapObject("checkpoints");
            caller.addProperty("trid", strings[0]);
            caller.callWebService();
            String res = caller.getResponse();
            return res;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(GoogLeLocations.this, s + "", Toast.LENGTH_SHORT).show();
            try {
                JSONArray jsonArray = new JSONArray(s);
                len = jsonArray.length();
                lat = new String[jsonArray.length()];
                log = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);
                    lat[i] = ob.getString("lat");
                    log[i] = ob.getString("lng");

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < len; i++) {
                LatLng sydney = new LatLng(Double.parseDouble(lat[i]), Double.parseDouble(log[i]));
                mMap.addMarker(new MarkerOptions().position(sydney).title("Location " + i));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        }
    }
}
