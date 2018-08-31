package com.example.trancaoviet.NoodleDrug.Activities;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.trancaoviet.NoodleDrug.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ArrayList<LatLng> listLatLng = new ArrayList<>();
        listLatLng.add (new LatLng(10.766631, 106.695074));
        listLatLng.add (new LatLng(10.767631, 106.696074));
        listLatLng.add (new LatLng(10.768631, 106.699074));
        listLatLng.add (new LatLng(10.769631, 106.694074));
        listLatLng.add (new LatLng(10.761631, 106.692074));

        for(LatLng latLng : listLatLng){
            mMap.addMarker(new MarkerOptions().position(latLng).title("Pharmacy"));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(listLatLng.get(0)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15f));
    }
}
