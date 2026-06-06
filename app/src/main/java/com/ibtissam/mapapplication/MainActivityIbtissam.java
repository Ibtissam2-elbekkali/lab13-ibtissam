package com.ibtissam.mapapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivityIbtissam extends AppCompatActivity {

    private Button btnMapIbtissam;
    private double latitudeIbtissam;
    private double longitudeIbtissam;
    private double altitudeIbtissam;
    private float accuracyIbtissam;
    
    RequestQueue requestQueueIbtissam;
    String insertUrlIbtissam = "http://10.0.2.2/map_project_ibtissam/createPositionIbtissam.php";
    LocationManager locationManagerIbtissam;

    private static final int PERMISSION_REQUEST_CODE_IBTISSAM = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ibtissam);

        requestQueueIbtissam = Volley.newRequestQueue(getApplicationContext());
        locationManagerIbtissam = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        btnMapIbtissam = findViewById(R.id.btnMapIbtissam);

        btnMapIbtissam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityIbtissam.this, MapActivityIbtissam.class));
            }
        });
        
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.READ_PHONE_STATE
                    }, PERMISSION_REQUEST_CODE_IBTISSAM);
        } else {
            startLocationUpdatesIbtissam();
        }
    }

    private void startLocationUpdatesIbtissam() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManagerIbtissam.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 150, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitudeIbtissam = location.getLatitude();
                longitudeIbtissam = location.getLongitude();
                altitudeIbtissam = location.getAltitude();
                accuracyIbtissam = location.getAccuracy();
                
                String msg = String.format(
                        getResources().getString(R.string.new_location), latitudeIbtissam,
                        longitudeIbtissam, altitudeIbtissam, accuracyIbtissam);
                
                addPositionIbtissam(latitudeIbtissam, longitudeIbtissam);
                
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(@NonNull String provider) {}

            @Override
            public void onProviderDisabled(@NonNull String provider) {}
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE_IBTISSAM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdatesIbtissam();
            } else {
                Toast.makeText(this, "Permission refusée. Ibtissam app cannot work properly.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void addPositionIbtissam(final double lat, final double lon) {
        StringRequest request = new StringRequest(Request.Method.POST,
                insertUrlIbtissam, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Success handle
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handle
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                params.put("latitude", lat + "");
                params.put("longitude", lon + "");
                params.put("date", sdf.format(new Date()));

                String androidIdIbtissam = Settings.Secure.getString(
                        getContentResolver(),
                        Settings.Secure.ANDROID_ID
                );

                params.put("imei", androidIdIbtissam);

                return params;
            }
        };
        requestQueueIbtissam.add(request);
    }
}
