package javeriana.edu.co.donlimpio;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javeriana.edu.co.util.DirectionsJSONParser;

public class SetPositionActivity extends FragmentActivity implements OnMapReadyCallback {


    //final
    public static final String TAG = "LOCATION_APP";
    public static final int ID_PERMISSION_LOCATION = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;
    // Limits for the geocoder search (colombia)
    public static final double lowerLeftLatitude = 1.396967;
    public static final double lowerLeftLongitude = -78.903968;
    public static final double upperRightLatitude = 11.983639;
    public static final double upperRigthLongitude = -71.869905;
    private static final double RADIUS_OF_EARTH_KM = 6.371;


    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private Geocoder mGeocoder;

    private SupportMapFragment mapFragment;
    double lat = 0;
    double lng = 0;
    double latDevice = 0;
    double lngDevice = 0;
    EditText dir;
    private String directionToPass = "";
    Button mOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_position);

        // Inflate map
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION,
                "El permiso es necesario para acceder a la localización", ID_PERMISSION_LOCATION);

        mLocationRequest = createLocationRequest();

        mapFragment.getMapAsync(SetPositionActivity.this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                Log.i("LOCATION", "Location update in the callback: " + location);
                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    mapFragment.getMapAsync(SetPositionActivity.this);
                }
            }
        };

        //Inicialización del objeto
        mGeocoder = new Geocoder(getBaseContext());
        //mGeocoder.getFromLocationName("BOGOTA", 2,)
        dir = (EditText) findViewById(R.id.addr);
        dir.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    Log.i(TAG, "KEY PRESSED");
                    if (!dir.getText().toString().isEmpty()) {
                        try {
                            List<Address> addresses = mGeocoder
                                    .getFromLocationName(dir.getText().toString(), 2, lowerLeftLatitude,
                                            lowerLeftLongitude,
                                            upperRightLatitude, upperRigthLongitude);
                            if (addresses != null && !addresses.isEmpty()) {
                                Address addressResult = addresses.get(0);
                                Log.i(TAG, addresses.get(0).getAddressLine(0));
                                LatLng location = new LatLng(addressResult.getLatitude(),
                                        addressResult.getLongitude());
                                if (mMap != null) {
                                    Log.i(TAG, "MAP NOT NULL");
                                    lat = location.latitude;
                                    lng = location.longitude;
                                    directionToPass = addresses.get(0).getAddressLine(0);
                                    mapFragment.getMapAsync(SetPositionActivity.this);
                                    return true;
                                }
                            } else {
                                Toast.makeText(SetPositionActivity.this, "Dirección no encontrada",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(SetPositionActivity.this, "La dirección esta vacía", Toast.LENGTH_SHORT)
                                .show();
                    }
                    return true;
                }
                return false;
            }
        });

    mOK = findViewById(R.id.btnOK);
    mOK.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), ScheduleActivity.class);
            i.putExtra("DIRECTION", directionToPass);
            startActivity(i);
        }
    });

    }

    private void requestLocation() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Log.i(TAG, "localización obtenida!" + location);
                            if (location != null) {
                                Log.i(TAG, "longitud: " + location.getLongitude());
                                Log.i(TAG, "latitud: " + location.getLatitude());
                                Toast.makeText(getBaseContext(), "longitud: " + location.getLongitude() + "latitud: " + location.getLatitude(), Toast.LENGTH_SHORT).show();
                                lng = location.getLongitude();
                                lat = location.getLatitude();
                                lngDevice = location.getLongitude();
                                latDevice = location.getLatitude();
                                mapFragment.getMapAsync(SetPositionActivity.this);
                            }
                        }
                    });
        }
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); //tasa de refresco en milisegundos
        mLocationRequest.setFastestInterval(5000); //máxima tasa de refresco
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    /**
     * make map with everything
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (null == mMap) {
            requestLocation();
        }
        mMap = googleMap;
        mMap.clear();
        LatLng actual = new LatLng(lat, lng);

        mMap.addMarker(new MarkerOptions().position(actual)
                .title("Latitude: " + lat + " Longitude: " + lng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(actual));
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        Date now = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 6 && timeOfDay < 18) {
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.default_map_style));
        } else {
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.night_map_style));
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                try {
                    lat = latLng.latitude;
                    lng = latLng.longitude;
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                    Log.i(TAG, addresses.get(0).getAddressLine(0));
                    directionToPass = addresses.get(0).getAddressLine(0);
                    dir.setText(addresses.get(0).getAddressLine(0));
                    mMap.clear();
                    LatLng actual = new LatLng(lat, lng);

                    mMap.addMarker(new MarkerOptions().position(actual)
                            .title("Latitude: " + lat + " Longitude: " + lng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(actual));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void requestPermission(Activity context, String permiso, String justificacion,
                                   int idCode) {
        if (ContextCompat.checkSelfPermission(context, permiso) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permiso)) {
                Toast.makeText(context, justificacion, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permiso}, idCode);
        } else {
            requestLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ID_PERMISSION_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Ya hay permiso para acceder a la localización", Toast.LENGTH_LONG)
                            .show();
                    LocationSettingsRequest.Builder builder = new
                            LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
                    SettingsClient client = LocationServices.getSettingsClient(this);
                    Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
                    ((Task) task)
                            .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                                @Override
                                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                                    startLocationUpdates(); //Todas las condiciones para recibir localizaciones
                                    requestLocation();
                                }
                            });
                    task.addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            int statusCode = ((ApiException) e).getStatusCode();
                            Log.i(TAG, e.getMessage());
                            switch (statusCode) {
                                case CommonStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied, but this can be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
                                        ResolvableApiException resolvable = (ResolvableApiException) e;
                                        resolvable.startResolutionForResult(SetPositionActivity.this,
                                                REQUEST_CHECK_SETTINGS);
                                    } catch (IntentSender.SendIntentException sendEx) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied. No way to fix the settings so we won't show the dialog.
                                    break;
                            }
                        }
                    });
                    requestLocation();
                } else {
                    Toast.makeText(this, "No hay Permiso", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void startLocationUpdates() {
        //Verificación de permiso!!
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

}
