package com.pepperpop.skansen;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * http://stackoverflow.com/questions/13756758/how-to-directly-move-camera-to-current-location-in-google-maps-android-api-v2
 */
public class MapsActivity extends FragmentActivity {

  private static final long MIN_TIME = 400;
  private static final float MIN_DISTANCE = 1000;

  private LocationManager mLocationManager;
  private GoogleMap mMap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);

    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(onMapReadyCallback);

    //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
    mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    try {
      mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
    } catch (SecurityException ex) {
      // No permission.
    }
  }

  private final OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {

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
      mMap = googleMap;

//      // Add a marker in Sydney and move the camera
//      LatLng sydney = new LatLng(-34, 151);
//      mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
  };

  private final LocationListener locationListener = new LocationListener() {
    @Override
    public void onLocationChanged(Location location) {
      LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
      CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLocation, 10);
      if (mMap != null) {
        mMap.addMarker(
            new MarkerOptions()
                .position(currentLocation)
                .title("Me"));

        mMap.animateCamera(cameraUpdate);

        try {
          mLocationManager.removeUpdates(this);
        } catch (SecurityException ex) {
          // No permission.
        }
      }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
  };
}
