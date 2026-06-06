package com.ibtissam.mapapplication;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MapActivityIbtissam extends AppCompatActivity {

    private MapView mapIbtissam;
    private RequestQueue requestQueueIbtissam;
    private String showUrlIbtissam = "http://10.0.2.2/map_project_ibtissam/getPositionIbtissam.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().load(getApplicationContext(), getSharedPreferences("prefs", MODE_PRIVATE));

        setContentView(R.layout.activity_map_ibtissam);

        mapIbtissam = findViewById(R.id.mapIbtissam);
        mapIbtissam.setTileSource(TileSourceFactory.MAPNIK);
        mapIbtissam.setBuiltInZoomControls(true);
        mapIbtissam.setMultiTouchControls(true);

        mapIbtissam.getController().setZoom(15.0);
        mapIbtissam.getController().setCenter(new GeoPoint(37.272525, -122.12106));

        requestQueueIbtissam = Volley.newRequestQueue(getApplicationContext());

        loadPositionsIbtissam();
    }

    private void loadPositionsIbtissam() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                showUrlIbtissam,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray positionsIbtissam = response.getJSONArray("positions");
                            
                            for (int i = 0; i < positionsIbtissam.length(); i++) {
                                JSONObject position = positionsIbtissam.getJSONObject(i);
                                double lat = position.getDouble("latitude");
                                double lng = position.getDouble("longitude");

                                Marker markerIbtissam = new Marker(mapIbtissam);
                                markerIbtissam.setPosition(new GeoPoint(lat, lng));
                                markerIbtissam.setTitle("Marker Ibtissam " + (i + 1));
                                
                                Drawable original = getResources().getDrawable(R.drawable.marker_ibtissam);
                                // For vector drawables, we should be careful with getBitmap(), but OSMDroid requires a Drawable icon,
                                // Let's set the icon directly without scaling if it's a VectorDrawable, or scale if it's a bitmap.
                                // It's safer to just set the drawable for OSMDroid in modern versions, 
                                // but the guide explicitly uses BitmapDrawable casting.
                                // We'll bypass the strict casting to support our VectorDrawable better.
                                markerIbtissam.setIcon(original);
                                
                                markerIbtissam.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                                
                                mapIbtissam.getOverlays().add(markerIbtissam);
                                
                                Toast.makeText(getApplicationContext(),"Ibtissam Pos : "+ lat+" , "+lng,Toast.LENGTH_SHORT).show();
                            }
                            
                            mapIbtissam.invalidate();
                            
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        
        requestQueueIbtissam.add(jsonObjectRequest);
    }
}
